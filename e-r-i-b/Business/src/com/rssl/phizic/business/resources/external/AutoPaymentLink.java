package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.longoffer.autopayment.AutoPaymentHelper;
import com.rssl.phizic.business.longoffer.autopayment.mock.MockAutoPayment;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.cache.CacheService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.longoffer.ExecutionEventType;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentService;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentStatus;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.utils.DocumentConfig;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.MockHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author osminin
 * @ created 31.01.2011
 * @ $Author$
 * @ $Revision$
 * ���� �����������
 */
public class AutoPaymentLink extends EditableExternalResourceLink
{
	public static final String CODE_PREFIX = "auto-payment";
	private static final String MOCK_STRING = "";

	private static ExternalResourceService resourceService = new ExternalResourceService();
	private static final ServiceProviderService serviceProviderService = new ServiceProviderService();

    private String number; // ����� �����������

	public String getCodePrefix()
	{
		return CODE_PREFIX;
	}

	public AutoPayment getValue()
	{
		return getAutoPayment();
	}

	private AutoPayment getAutoPayment()
	{
		try
		{
			AutoPaymentService autoPaymentService = GateSingleton.getFactory().service(AutoPaymentService.class);
			return GroupResultHelper.getOneResult(autoPaymentService.getAutoPayment(getExternalId()));
		}
		catch (InactiveExternalSystemException e)
	    {
		    throw e;
	    }
		catch (Exception e)
		{
			log.error("������ ��� ��������� ����������� �� externalId " + getExternalId(), e);
			return new MockAutoPayment(getExternalId());
		}
	}

	public void reset() throws BusinessException, BusinessLogicException
	{
		AutoPayment payment = getAutoPayment();
		if (!MockHelper.isMockObject(payment))
		{
			reset(payment, getLoginId());
		}
	}

	/**
	 * ��������� ����� ���� ��� �����������.
	 * @param payment ����������.
	 * @param login �����.
	 */
	public static void reset(AutoPayment payment, CommonLogin login) throws BusinessException, BusinessLogicException
	{
		reset(payment, login.getId());
	}

	/**
	 * ��������� ����� ���� ��� �����������.
	 * @param payment ����������.
	 * @param loginId �����.
	 */
	public static void reset(AutoPayment payment, Long loginId) throws BusinessException, BusinessLogicException
	{
		try
		{
			List<CardLink> cardLinks = resourceService.getLinks(loginId, CardLink.class);
			List<Card> cards = new ArrayList<Card>(cardLinks.size());

			for (CardLink link : cardLinks)
			{
				cards.add(link.toLinkedObjectInDBView());
			}

			GateSingleton.getFactory().service(CacheService.class).clearAutoPaymentCache(payment, cards.toArray(new Card[0]));
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}

	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	/**
	 * �������� ��������� ���������� �� ��������� ���������� �������
	 * @return ��������� ���������� �� ��������� ���������� �������
	 */
	public String getExecutionEventType()
	{
		AutoPayment payment = getAutoPayment();
		if (!MockHelper.isMockObject(payment))
		{
			StringBuilder algoritmInfo = new StringBuilder();
			// � ��������� �������� �������� ��������� �����
			ExecutionEventType eventType = payment.getExecutionEventType();
			if (ExecutionEventType.REDUSE_OF_BALANCE == eventType)
			{
				// �� �� ������������ "��� �������� ������� (����� ��������/������������ ��������) �� (����� ���������� ��������) �."
				algoritmInfo.append("��� �������� ������� ").append(payment.getRequisite()).append(" �� ").append(payment.getFloorLimit().getDecimal().toString()).append(" ���.");
			}
			else if (ExecutionEventType.ONCE_IN_MONTH == eventType || ExecutionEventType.ONCE_IN_QUARTER == eventType || ExecutionEventType.ONCE_IN_YEAR == eventType)
			{
				//��� ������������ ������������� �������� � ������������ �������� ������� � ���� ����������
				algoritmInfo.append(eventType.getDescription()).append(" ").append(payment.getStartDate().get(Calendar.DATE)).append("-�� �����");
			}
			else if(ExecutionEventType.BY_INVOICE == eventType)
			{
				algoritmInfo.append("��� ����������� ����� �� �����, ��������� � �����");
			}
			return algoritmInfo.toString();
		}
		return MOCK_STRING;
	}

	/**
	 * @return ���� ����� �� ������� ��������� ����������
	 */
	public CardLink getCardLink()
	{
		AutoPayment payment = getAutoPayment();
		if (MockHelper.isMockObject(payment))
			return null;

		try
		{
			return resourceService.findLinkByNumber(getLoginId(), ResourceType.CARD, payment.getCardNumber());
		}
		catch (BusinessException e)
		{
			log.error("������ ��� ��������� ����� �" + payment.getCardNumber(), e);
			return null;
		}
	}

	public ResourceType getResourceType()
	{
		return ResourceType.NULL;
	}

	public String getPatternForFavouriteLink()
	{
		return "$$autoPaymentName:" + this.getId();
	}

	public BillingServiceProvider getServiceProvider() throws BusinessException
	{
		return (BillingServiceProvider) serviceProviderService.findBySynchKey(getAutoPayment().getCodeService());
	}

	/**
	 * @return �������� ��������� �����������
	 */
	public String getRequisiteName() throws BusinessException
	{
		// ������� ���������������� ����������, ��������������� ����������
		BillingServiceProvider provider = getServiceProvider();
		// ���� ���������� ���, ��� �� �� ������������ ���������� => �������� �� ���������
		if(provider == null || !AutoPaymentHelper.checkAutoPaymentSupport(provider))
			return ConfigFactory.getConfig(DocumentConfig.class).getDefualtAutoPaymentReqisiteName();

		// ������� �������� ���� � ���������� ��� ��������
		for(Field field : provider.getFieldDescriptions())
			if(field.isKey())
				return field.getName();

		return ConfigFactory.getConfig(DocumentConfig.class).getDefualtAutoPaymentReqisiteName();
	}

	/**
	 * @return  ���������� ������ �����������
	 */
	public AutoPaymentStatus getStatusReport()
	{
		AutoPayment autoPayment = getAutoPayment();
		if (autoPayment instanceof AutoPayment)
			return autoPayment.getReportStatus();

		return null;
	}

	public String toString()
    {
        return "���������� externalId=" + getExternalId();
    }

	/**
	 * @return ���������� �������� �����������
	 */
	public String getRequisite()
	{
		return getExternalId().split("\\^")[1];
	}

	/**
	 * @return ���������� ����� �����
	 */
	public String getCardNumber()
	{
		return getExternalId().split("\\^")[0];
	}

	/**
	 * @return ���������� ��� ����������
	 */
	public String getReceiverCode()
	{
		return (getExternalId().split("\\^")[2]).split("\\|")[0];
	}
}
