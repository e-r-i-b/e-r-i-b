package com.rssl.phizic.business.limits;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizic.auth.imsi.LoginIMSIError;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.documents.AbstractAccountsTransfer;
import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.payments.*;
import com.rssl.phizic.business.documents.strategies.ClientAccumulateLimitsInfo;
import com.rssl.phizic.business.documents.strategies.DocumentLimitManager;
import com.rssl.phizic.business.documents.strategies.limits.GroupRiskDocumentLimitStrategy;
import com.rssl.phizic.business.documents.strategies.limits.IMSIDocumentLimitStrategy;
import com.rssl.phizic.business.documents.strategies.limits.MobileLightPlusLimitStrategy;
import com.rssl.phizic.business.documents.strategies.limits.ObstructionDocumentLimitStrategy;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.strategies.limits.BlockTemplateOperationLimitStrategy;
import com.rssl.phizic.business.limits.link.*;
import com.rssl.phizic.business.payments.forms.meta.ReceiverSocialCardFilter;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.limits.ChannelType;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.payments.autopayment.CreateAutoPayment;
import com.rssl.phizic.gate.payments.autopayment.EditAutoPayment;
import com.rssl.phizic.gate.payments.systems.AbstractPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.DateHelper;
import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * @author khudyakov
 * @ created 21.08.2012
 * @ $Authors$
 * @ $Revision$
 */
public class LimitHelper
{
	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE);
	private static final LimitPaymentsLinkService limitPaymentsLinkService = new LimitPaymentsLinkService();
	private static final LimitService limitService = new LimitService();

	/**
	 * �������� �� ������������� ��������������� ������������� ��������
	 *
	 * @param document ��������
	 * @return true - ����������
	 */
	public static boolean needAdditionalConfirm(BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		BusinessDocumentOwner documentOwner = document.getOwner();
		if (documentOwner.isGuest())
			throw new UnsupportedOperationException("�������� � �������� ������ �� ��������������");
		ClientAccumulateLimitsInfo limitsInfo = DocumentLimitManager.buildLimitAmountInfoByLogin(documentOwner.getLogin(), LimitHelper.getChannelType(document));

		return !(new GroupRiskDocumentLimitStrategy(document).check(limitsInfo) && new MobileLightPlusLimitStrategy(document).check(limitsInfo));
	}

	/**
	 * �������� �� ������������� ������������� sim-����� (�������� IMSI ������)
	 * ��� �������� ������ ���������� ������ �������� �� ������������� sim-�����.
	 *
	 * @param document ��������
	 * @return true - ����������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public static boolean needAdditionalCheck(BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		BusinessDocumentOwner documentOwner = document.getOwner();
		if (documentOwner.isGuest())
			throw new UnsupportedOperationException("�������� � �������� ������ �� ��������������");
		ClientAccumulateLimitsInfo limitsInfo = DocumentLimitManager.buildLimitAmountInfoByLogin(documentOwner.getLogin(), LimitHelper.getChannelType(document));

		return !(new IMSIDocumentLimitStrategy(document).check(limitsInfo));
	}

	/**
	 * �������� �� ������������� ��������� ��������
	 *
	 * @param document ��������
	 * @return true - ����������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public static boolean needObstructOperation(BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		ClientAccumulateLimitsInfo limitsInfo = DocumentLimitManager.buildLimitAmountInfoByPerson(PersonHelper.getContextPerson(), LimitHelper.getChannelType(document));
		return !(new ObstructionDocumentLimitStrategy(document).check(limitsInfo));
	}

	/**
	 * �������� �� ������������� ��������� ��������
	 *
	 * @param template ������
	 * @return true - ����������
	 * @throws BusinessException
	 */
	public static boolean needObstructOperation(TemplateDocument template) throws BusinessException, BusinessLogicException
	{
		return !(new BlockTemplateOperationLimitStrategy(template).check(null));
	}

	/**
	 * @param tb ��, ��� �������� ���������������
	 * @return ������� ������������� ������ ������� � ������� �� ������ �����
	 */
	public static List<LimitPaymentsLink> createPaymentLimitLinks(String tb)
	{
		List<LimitPaymentsLink> links = new ArrayList<LimitPaymentsLink>();

		links.add(new PhysicalExternalAccountLimitPaymentLink());
		links.add(new PhysicalExternalCardLimitPaymentLink());
		links.add(new PhysicalInternalLimitPaymentLink());
		links.add(new InternalSocialLimitPaymentLink());
		links.add(new ConversionLimitPaymentLink());
		links.add(new JuridicalExternalLimitPaymentLink());

		for (LimitPaymentsLink link : links)
		{
			((LimitPaymentsLinkBase)link).setTb(tb);
		}

		return links;
	}

	/**
	 * ������� ������ ������� �� ������ ����� ��� ������������ �������� � ������� ������ ����������
	 * @param document ��������
	 * @param channelType ��� ������
	 * @return ������ �������
	 */
	public static List<Limit> findBillingDocumentLimits(BusinessDocument document, ChannelType channelType) throws BusinessException
	{
		if (!(document instanceof RurPayment))
			throw new BusinessException("������������ ��� �������, �������� RurPayment");

		Long receiverInternalId = ((RurPayment) document).getReceiverInternalId();
		if (receiverInternalId != null)
		{
			return limitService.findActiveLimits(document, channelType, DocumentHelper.getGroupRisk(receiverInternalId));
		}

		//��� ���. �������� �� ��������� ���������� ���� �� ����� ������� � ������ �����
		return findDocumentLimits(document, channelType);
	}

	/**
	 * ������� ������ ������� �� ������ ����� ��� ������������ �������� � ������� ������ ����������
	 * @param document ��������
	 * @param channelType ��� ������
	 * @return ������ �������
	 */
	public static List<Limit> findAutoPaymentLimits(BusinessDocument document, ChannelType channelType) throws BusinessException
	{
		if (!(document instanceof CreateAutoPayment || document instanceof EditAutoPayment))
			throw new BusinessException("������������ ��� �������, �������� CreateAutoPayment ��� EditAutoPayment");

		AutoPaymentBase autoPayment = (AutoPaymentBase) document;

		return limitService.findActiveLimits(document, channelType, DocumentHelper.getGroupRisk(autoPayment.getReceiverInternalId()));
	}

	/**
	 * ������� ������ ������� �� ������ ����� ��� ��������� �� ����� ��������� � ������ ����� � ������� ������ ����������
	 * @param document ��������
	 * @param channelType ����� ����������
	 * @return ������ �������
	 */
	public static List<Limit> findDocumentLimits(BusinessDocument document, ChannelType channelType) throws BusinessException
	{
		try
		{
			//������ ���� �� ����� ������� � ������ �����
			List<LimitPaymentsLink> limitPaymentsLinks = limitPaymentsLinkService.findAll(((Department) document.getDepartment()).getRegion());
			if (CollectionUtils.isEmpty(limitPaymentsLinks))
				return Collections.emptyList();

			if (!(document instanceof ExchangeCurrencyTransferBase))
				return Collections.emptyList();

			List<Limit> limits = new ArrayList<Limit>();
			ExchangeCurrencyTransferBase convertionPayment = (ExchangeCurrencyTransferBase) document;

			for (LimitPaymentsLink limitPaymentsLink : limitPaymentsLinks)
			{
				//������� � ������ ����� �������� ������������ ������ �������
				if (limitPaymentsLink.getPaymentTypes().indexOf(convertionPayment.getType()) >= 0)
				{
					//���� �������� ��� ����� ��������� �� �� �� ������ �������� ������ �� ������������� ������ �����
					if (LimitPaymentsType.CONVERSION_OPERATION_PAYMENT_LINK == limitPaymentsLink.getLinkType()
							&& !convertionPayment.isConvertion())
						continue;

					//���� �� ������� �� ���. �����
					if (LimitPaymentsType.INTERNAL_SOCIAL_TRANSFER_LINK == limitPaymentsLink.getLinkType()
							&& !(new ReceiverSocialCardFilter().isEnabled(convertionPayment)))
						continue;

					limits.addAll(limitService.findActiveLimits(document, channelType, limitPaymentsLink.getGroupRisk()));
				}
			}

			return limits;
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ����� ��� �������� ������ �� ������ ����� ��� ��������� � ������� ��������, � ������� ������ ���������� ���������
	 * @param document ��������
	 * @param channelType ����� ���������� ���������
	 * @return ������ �������� ������� �� ������ �����
	 */
	public static List<Limit> findAllDocumentLimits(BusinessDocument document, ChannelType channelType) throws BusinessException
	{
		if (!(document instanceof GateDocument))
			throw new BusinessException("������������ ��� �������, �������� GateDocument.");

		GateDocument gateDocument = (GateDocument) document;
		//��� ������������ ����� ���� �� ����������
		if (CreateAutoPayment.class == gateDocument.getType() || EditAutoPayment.class == gateDocument.getType())
		{
			return findAutoPaymentLimits(document, channelType);
		}

		//��� ���. ������� ������ ���� �� ������ ����� ����������
		if (AbstractPaymentSystemPayment.class.isAssignableFrom(gateDocument.getType()))
		{
			return findBillingDocumentLimits(document, channelType);
		}

		//��� ��������� ��������
		return findDocumentLimits(document, channelType);
	}

	/**
	 * @return ������� ����� ��� �������
	 */
	public static ChannelType getCurrentChannelType()
	{
		ApplicationConfig config = ApplicationConfig.getIt();
		ApplicationInfo applicationInfo = config.getApplicationInfo();

		if (applicationInfo.isMobileApi())
		{
			return ChannelType.MOBILE_API;
		}

		if (applicationInfo.isATM())
		{
			return ChannelType.SELF_SERVICE_DEVICE;
		}

		if (applicationInfo.isWeb())
		{
			return ChannelType.INTERNET_CLIENT;
		}

		if (applicationInfo.isSMS())
		{
			return ChannelType.ERMB_SMS;
		}

        if (applicationInfo.isSocialApi())
        {
            return ChannelType.SOCIAL_API;
        }

		return null;
	}

	/**
	 * @param document ��������
	 * @return ��� ������, � ������� ����������� ��������
	 */
	public static ChannelType getChannelType(BusinessDocument document)
	{
		ChannelType channelType = getCurrentChannelType();
		if(channelType != null)
		{
			return channelType;
		}

		if (document.getClientOperationChannel() != null)
		{
			return getChannelType(document.getClientOperationChannel());
		}

		return getChannelType(document.getCreationType());
	}

	/**
	 * @param template ��������
	 * @return ��� ������, � ������� ����������� ��������
	 */
	public static ChannelType getChannelType(TemplateDocument template)
	{
		ChannelType channelType = getCurrentChannelType();
		if(channelType != null)
		{
			return channelType;
		}

		if (template.getClientOperationChannel() != null)
		{
			return getChannelType(template.getClientOperationChannel());
		}

		return getChannelType(template.getClientCreationChannel());
	}

	private static ChannelType getChannelType(CreationType type)
	{
		switch (type)
		{
			case atm:       return ChannelType.SELF_SERVICE_DEVICE;
			case mobile:    return ChannelType.MOBILE_API;
			case internet:  return ChannelType.INTERNET_CLIENT;
			case sms:       return ChannelType.ERMB_SMS;
			case social:    return ChannelType.SOCIAL_API;
			default: throw new IllegalArgumentException("������������ ��� ������ ������������� ��������.");
		}
	}

	/**
	 * ����� �� ��������� (� ������������ ������)
	 * @param document ��������
	 * @return �����
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public static Money getOperationAmount(BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		Money operationAmount = document.getExactAmount();
		if (operationAmount == null)
		{
			return new Money(BigDecimal.ZERO, MoneyUtil.getNationalCurrency());
		}
		return DocumentHelper.moneyConvert(operationAmount, MoneyUtil.getNationalCurrency(), document);
	}


	public static Money getOperationAmount(TemplateDocument template) throws BusinessException, BusinessLogicException
	{
		Money operationAmount = template.getExactAmount();

		if (operationAmount == null)
		{
			return new Money(BigDecimal.ZERO, MoneyUtil.getNationalCurrency());
		}

		return DocumentHelper.moneyConvert(operationAmount, MoneyUtil.getNationalCurrency(), template);
	}

	/**
	 * �������� �������� ������ ����� (���������� �������)
	 * @param document ��������
	 * @return ����� �����
	 */
	public static String getExternalCardProviderValue(BusinessDocument document) throws BusinessException
	{
		if (!(document instanceof AbstractPaymentDocument))
		{
			throw new BusinessException("������������ ��� ��������� id = " + document.getId() + ", �������� ��������� AbstractPaymentDocument");
		}

		AbstractAccountsTransfer payment = (AbstractAccountsTransfer) document;
		return payment.getReceiverAccount();
	}

	/**
	 * �������� ����� �������� (���������� �������)
	 * @param document ��������
	 * @return ����� ��������
	 */
	public static String getExternalPhoneProviderValue(BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		if (document instanceof JurPayment)
		{
			List<Field> fields = BillingPaymentHelper.getKeyFields((JurPayment) document);
			if (CollectionUtils.isEmpty(fields))
			{
				throw new BusinessException("� ��������� id " + document.getId() + " �� ����� ������������� �������� ���. ����");
			}
			return (String) fields.get(0).getValue();
		}
		if (document instanceof AutoPaymentBase)
		{
			AutoPaymentBase autoPayment = (AutoPaymentBase) document;
			return autoPayment.getRequisite();
		}
		throw new BusinessException("� ��������� id " + document.getId() + " �� ����� ������������� �������� ���. ����");
	}

	public static boolean needAdditionalConfirm(BusinessDocument document, RequireAdditionConfirmLimitException e)
	{
		//���� �� mAPI
		if (ApplicationUtil.isNotMobileApi())
			return true;

		//�� ������ �� �������
		if (RestrictionType.MAX_AMOUNT_BY_TEMPLATE != e.getRestrictionType())
			return true;

		//� mAPI ��� ��-full-����� ������ ��������� ���. �������������
		if (!MobileApiUtil.isFullScheme())
			return true;

		if (!(document instanceof AbstractPaymentDocument))
			return true;

		try
		{
			//� mAPI full-������ ��� ���������� ��������� � ���������� ��������� "�������� ��������� ����� � PRO-����" ��������� ������������� � ��
			AbstractPaymentDocument apd = (AbstractPaymentDocument) document;
			return apd.getSumIncreasedOverLimit() && DocumentHelper.getUseTemplateFactorInFullMAPI(((Department) document.getDepartment()).getId());
		}
		catch (BusinessException ex)
		{
			//log.error("������ ��� ������ ��������� \"�������� ��������� ����� � PRO-����\"", ex);
			return true;
		}
	}

	/**
	 * ����������� ����������� ����� �� ������
	 * @param limitsInfo ����������� ����� �� �������
	 * @param limit �����
	 * @return �����
	 */
	public static Money getClientTotalAmountByLimits(ClientAccumulateLimitsInfo limitsInfo, Limit limit)
	{
		try
		{
			return limitsInfo.getAccumulateAmount(limit);
		}
		catch (Exception e)
		{
			log.error("������ ����������� ����������� ����� �� ������ " + limit.getId(), e);
			return null;
		}
	}

	/**
	 * ����������� ���������� �������� �� ������
	 * @param limitsInfo ������� �������� �� �������
	 * @param limit �����
	 * @return ���������� ��������
	 */
	public static Long getClientOperCountByLimits(ClientAccumulateLimitsInfo limitsInfo, Limit limit)
	{
		try
		{
			return limitsInfo.getAccumulateCount(limit);
		}
		catch (Exception e)
		{
			log.error("������ ����������� ����������� ����� �� ������ " + limit.getId(), e);
			return null;
		}

	}

	/**
	 * ���� �� ����� ��� ����� � �������� ������.
	 *
	 * @param document ��������
	 * @return true - ����� sim-����� �� ����
	 * @throws BusinessException
	 */
	public static boolean isGoodIMSIChange(BusinessDocument document) throws BusinessException
	{
		long currentTimeInMs = Calendar.getInstance().getTimeInMillis();
		LoginIMSIError imsiError = limitService.getIMSICkeckResult(document);

		// ���� ����� �������� ������ �����, �� ���������� �������� ��������� ��������
		if (imsiError != null && (currentTimeInMs - imsiError.getCheckDate().getTime() <= DateHelper.MILLISECONDS_IN_DAY))
		{
			return imsiError.isGoodIMSI();
		}
		// � ��������� ������� ��������� �������� �� ����� ��� �����
		return false;
	}

}
