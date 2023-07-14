package com.rssl.phizic.business.longoffer.autopayment;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentService;
import com.rssl.phizic.business.dictionaries.payment.services.PaymentServiceService;
import com.rssl.phizic.business.dictionaries.providers.*;
import com.rssl.phizic.business.documents.InternalTransfer;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.documents.payments.AutoSubscriptionPaymentBase;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.resources.external.AutoPaymentLink;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentStatus;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.manager.config.AdaptersConfig;
import com.rssl.phizicgate.manager.routing.Adapter;
import com.rssl.phizicgate.manager.services.IDHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * @author osminin
 * @ created 08.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class AutoPaymentHelper
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private static final PaymentServiceService paymentServiceService = new PaymentServiceService();

	/**
	 * �������� ���� �� ��������������
	 * @param links ������ ������
	 * @param externalId �������������
	 * @return ���� �����������
	 */
	public static AutoPaymentLink findAutoPaymentLinkByEternalId(List<AutoPaymentLink> links, String externalId)
	{
		for (AutoPaymentLink link: links)
			if (link.getExternalId().equals(externalId))
				return link;
		return null;
	}

	/**
	 * �������� ��� ������ ����� ���������� �����
	 * @param providerId ������������� ���������� �����
	 * @return ��� ������ ����� ���������� �����
	 */
	public static String getGroupServiceByProvider(Long providerId) throws BusinessException
	{
		if (providerId == null)
		{
			//���� ���������� ���, ������ �� ��������� ����������, �������� �������� �� ���������
			return AutoSubscriptionPaymentBase.DEFAULT_GROUP_SERVICE;
		}

		List<PaymentService> paymentServices = paymentServiceService.getServicesForProvider(providerId);
		if (CollectionUtils.isEmpty(paymentServices))
		{
			//���� ������ �� �������, ������ ����������
			return null;
		}
		//���������� ��� ������ ���������� ������
		return (String) paymentServices.get(0).getSynchKey();
	}

	/**
	 * ���������� �������� �� ��������� iqwave
	 * @param provider ���������� ���������
	 * @return true - iqwave
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public static boolean isIQWProvider(ServiceProviderBase provider) throws BusinessException
	{
		if (provider == null)
		{
			throw new IllegalArgumentException("��������� ����� �� ����� ���� null");
		}

		return isIQWProvider((String) provider.getSynchKey());
	}

	/**
	 * ���������� �������� �� ��������� iqwave
	 * @param synchKey ������� ������������� ����������
	 * @return �������� �� ��������� iqwave
	 * @throws BusinessException
	 */
	public static boolean isIQWProvider(String synchKey) throws BusinessException
	{
		if (StringHelper.isEmpty(synchKey))
		{
			throw new IllegalArgumentException("������� ������������� ���������� ����� �� ����� ���� null");
		}

		AdaptersConfig config = ConfigFactory.getConfig(AdaptersConfig.class);
		Adapter adapter = config.getCardTransfersAdapter();
		if (adapter == null)
			throw new BusinessException("�� ������ ������� ������� ��� ��������� ���������");

		return adapter.getUUID().equals(IDHelper.restoreRouteInfo(synchKey));
	}

	/**
	 * ��������� �� �������� ����������� �� ���������
	 * �� ������� ������ ���������� �������� ������ ��� ������ �����. ��� ��������� ����� �������� ������������ false
	 * @param document ��������
	 * @return ��/���
	 */
	public static boolean isAutoPaymentAllowed(BusinessDocument document)
	{
		if (document == null)
		{
			return false;
		}

		if (!(document instanceof JurPayment))
		{
			//�������� ������������ �������� ��� ����� ����� ��������, � �� ������.
			return false;
		}

		try
		{
			JurPayment jurPayment = (JurPayment) document;
			if (jurPayment.asGateDocument().getType() != CardPaymentSystemPayment.class)
			{
				return false;
			}
			return isAutoPaymentAllowed(ServiceProviderHelper.getServiceProvider(jurPayment.getReceiverInternalId()));
		}
		catch (Exception e)
		{
			log.error("������ ��� ����������� ����������� �������� �����������", e);
			return false;
		}
	}

	/**
	 * ��������� �� �������� ����������� P2P �� ���������
	 * @param document ��������
	 * @return ��/���
	 */
	public static boolean isAutoTransferAllowed(BusinessDocument document)
	{
		if (document == null)
		{
			return false;
		}

		if (document instanceof RurPayment)
		{
			return document.getType() == CardPaymentSystemPayment.class && "ourCard".equals(((RurPayment)document).getReceiverSubType());
		}

		if (document instanceof InternalTransfer)
		{
			return ((InternalTransfer)document).getChargeOffResourceType() == ResourceType.CARD
                    && ((InternalTransfer)document).getDestinationResourceType() == ResourceType.CARD;
		}

        return false;
	}

	public static boolean isAutoPaymentAllowed(ServiceProviderShort receiver) throws BusinessException
	{
		if (receiver != null )
		{
			if (!(receiver.getKind().equals("B")) || !(checkAutoPaymentSupport(receiver)) || receiver.getState() != ServiceProviderState.ACTIVE)
			{
				//������������� ���������� � ���������� � ���� ������������ ������������� �� ������������ �������� �����������
				return false;
			}
			if (isIQWProvider(receiver.getSynchKey()))
			{
				//��� iqw
				return PermissionUtil.impliesOperation("CreateFormPaymentOperation", "CreateAutoPaymentPayment");
			}
			//����������� ������ ����� ����
			return PermissionUtil.impliesOperation("CreateESBAutoPayOperation", "ClientCreateAutoPayment");
		}
		else
		{
			//��� ����������� �� ��������� ���������� - ���� �����.
			return PermissionUtil.impliesOperation("CreateFreeDetailAutoSubOperation", "ClientFreeDetailAutoSubManagement");
		}
	}

	/**
	 * ��������� �� �������� ����������� �� �������
	 * �� ������� ������ ���������� �������� ������ ��� ������ �����. ��� ��������� ����� �������� ������������ false
	 * @param templateId ������������� �������
	 * @return ��/���
	 */
	public static boolean isAutoPaymentAllowedForTemplate(Long templateId)
	{
		try
		{
			if (templateId == null)
			{
				return false;
			}

			TemplateDocument template = TemplateDocumentService.getInstance().findById(templateId);
			return isAutoPaymentAllowedForTemplate(template);
		}
		catch (Exception e)
		{
			log.error("������ ��� ����������� ����������� �������� �����������", e);
			return false;
		}
	}

	/**
	 * ��������� �� �������� ����������� �� �������
	 * �� ������� ������ ���������� �������� ������ ��� ������ �����. ��� ��������� ����� �������� ������������ false
	 * @param template ������
	 * @return ��/���
	 */
	public static boolean isAutoPaymentAllowedForTemplate(TemplateDocument template)
	{
		try
		{
			if (template == null)
			{
				return false;
			}
			if (FormType.INTERNAL_PAYMENT_SYSTEM_TRANSFER != template.getFormType())
			{
				return false;
			}
			Long receiverId = template.getReceiverInternalId();
			ServiceProviderShort provider = ServiceProviderHelper.getServiceProvider(receiverId);

			if (receiverId != null && provider == null)
			{
				//��������� ����������� � ��
				return false;
			}
			return isAutoPaymentAllowed(provider);
		}
		catch (Exception e)
		{
			log.error("������ ��� ����������� ����������� �������� �����������", e);
			return false;
		}
	}

	/**
	 * �������� ��������� ����������� � ����������� �� ����������
	 * @param billingProvider ��������� �����
	 * @return ������� ��������� �����������
	 */
	public static boolean checkAutoPaymentSupport(final BillingServiceProvider billingProvider)
	{
		ApplicationInfo applicationInfo = ApplicationConfig.getIt().getApplicationInfo();
		if (applicationInfo.isATM())
			return billingProvider.isAutoPaymentSupportedInATM();
		else if (applicationInfo.isMobileApi())
			return billingProvider.isAutoPaymentSupportedInApi();
		else
			return billingProvider.isAutoPaymentSupported();
	}


	/**
	 * �������� ��������� ����������� � ����������� �� ����������
	 * @param provider ��������� �����
	 * @return ������� ��������� �����������
	 */
	public static boolean checkAutoPaymentSupport(final ServiceProviderShort provider)
	{
		ApplicationInfo applicationInfo = ApplicationConfig.getIt().getApplicationInfo();
		if (applicationInfo.isATM())
			return provider.isAutoPaymentSupportedInATM();
		else if (applicationInfo.isMobileApi())
			return provider.isAutoPaymentSupportedInApi();
		else
			return provider.isAutoPaymentSupported();
	}

	/**
	 * @param autoPayment ����������
	 * @return �������������� �� ������ ����������� � ������ �������
	 */
	public static boolean isRefuseSupported(AutoPayment autoPayment)
	{
		if (autoPayment == null)
			return false;

		AutoPaymentStatus status = autoPayment.getReportStatus();
		return status == AutoPaymentStatus.NEW || status == AutoPaymentStatus.ACTIVE ||
				status == AutoPaymentStatus.UPDATING || status == AutoPaymentStatus.BLOCKED;
	}
}
