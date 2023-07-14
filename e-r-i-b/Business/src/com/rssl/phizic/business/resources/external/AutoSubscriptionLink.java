package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.billing.BillingService;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.common.types.LongOfferPayDay;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.autopayments.AutoSubscriptionService;
import com.rssl.phizic.gate.autopayments.ScheduleItem;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.longoffer.ExecutionEventType;
import com.rssl.phizic.gate.longoffer.PeriodicExecutionEventTypeWrapper;
import com.rssl.phizic.gate.longoffer.SumType;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoPayStatusType;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoSubscription;
import com.rssl.phizic.gate.payments.longoffer.AutoSubscriptionDetailInfo;
import com.rssl.phizic.gate.payments.longoffer.CardPaymentSystemPaymentLongOffer;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.MockHelper;

import java.util.Calendar;
import java.util.List;

/**
 * ������� �������� �� ����������.
 * ���������� ��� ������������� � ������� �������������.
 * (�������� �� �������� � ��� � �.�. (��� ����) ��������� �� "�����������" - ���������������� �������, ����������� ���������� ������ �� ���)
 *
 * @author bogdanov
 * @ created 23.01.2012
 * @ $Author$
 * @ $Revision$
 */

public class AutoSubscriptionLink extends EditableExternalResourceLink
{
	private static final String CODE_PREFIX = "auto-subscription";
	private static final String MOCK_STRING = "";

	private static final ExternalResourceService resourceService = new ExternalResourceService();
	private static final ServiceProviderService serviceProviderService = new ServiceProviderService();
	private static final BillingService billingService = new BillingService();

	private AutoSubscription autoSubscription;
	private Long loginId;

	public AutoSubscriptionLink(Long loginId, AutoSubscription autoSubscription)
	{
		this.loginId = loginId;
		this.autoSubscription = autoSubscription;

		setName(autoSubscription.getFriendlyName());
		setExternalId(autoSubscription.getExternalId());
	}

	public String getCodePrefix()
	{
		return CODE_PREFIX;
	}

	/**
	 * �������� ��������� ���������� �� ��������� ���������� �������
	 * @return ��������� ���������� �� ��������� ���������� �������
	 */
	public String getExecutionEventType() throws BusinessException, BusinessLogicException
	{
		if (MockHelper.isMockObject(autoSubscription))
		{
			return MOCK_STRING;
		}

		StringBuilder executionEventType = new StringBuilder();
		ExecutionEventType eventType = autoSubscription.getExecutionEventType();
		// �� ������������ ��� ����� �� ������
		if (eventType != null)
		{
			executionEventType.append(eventType.getDescription());
		}
		return executionEventType.toString();
	}

	public AutoSubscription getValue()
	{
		return autoSubscription;
	}

	/**
	 * ���������� ��������� ���������� �� �����������.
	 * @return ��������� ����������.
	 */
	public AutoSubscriptionDetailInfo getAutoSubscriptionInfo() throws BusinessLogicException
	{
		if (MockHelper.isMockObject(autoSubscription))
		{
			return null;
		}

		try
		{
			AutoSubscriptionService autoSubscriptionService = GateSingleton.getFactory().service(AutoSubscriptionService.class);
			return GroupResultHelper.getOneResult(autoSubscriptionService.getAutoSubscriptionInfo(autoSubscription));
		}
		catch (LogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (IKFLException e)
		{
			log.error("������ ��� ��������� ���������� � �������� �� ���������� " + getExternalId(), e);
			return null;
		}
	}

	/**
	 * ��������� ������ �������� �� ��������.
	 *
	 * @param begin ��������� ����.
	 * @param end �������� ����.
	 * @return ������ ��������.
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public List<ScheduleItem> getSheduleReport(Calendar begin, Calendar end) throws BusinessException, BusinessLogicException
	{
		try
		{
			AutoSubscriptionService subscriptionService = GateSingleton.getFactory().service(AutoSubscriptionService.class);
			//�� ������������, ���� ���� ������ � ��������� ������� �� ��������, �� ������������ 10 ��������� ��������.
			return subscriptionService.getSheduleReport(autoSubscription, begin, end);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	public void reset()
	{
		//������ �� ������.
	}

	public String getNumber()
	{
		return autoSubscription.getNumber();
	}

	public ResourceType getResourceType()
	{
		return ResourceType.NULL;
	}

	public String getPatternForFavouriteLink()
	{
		return "$$autoSubscriptionName:" + getExternalId();
	}

	/**
	 * @return ���� ������ �������� ���������
	 */
	public Calendar getStartDate()
	{
		return autoSubscription.getStartDate();
	}

	/**
	 * @return ���� ��������� �������� ���������
	 */
	public Calendar getEndDate()
	{
		return autoSubscription.getEndDate();
	}

	/**
	 * @return ���� ���������� �������
	 */
	public Calendar getNextPayDate()
	{
		return autoSubscription.getNextPayDate();
	}

	/**
	 * @return ����� ����������. �� ����� ���� ��������� ������������ � getPercent
	 */
	public Money getAmount()
	{
		return autoSubscription.getAmount();
	}

	/**
	 * ������ �����������.
	 * @return ������.
	 */
	public AutoPayStatusType getAutoPayStatusType()
	{
		return autoSubscription.getAutoPayStatusType();
	}

	/**
	 * @return ��� ����� ��������� ����������� �������
	 */
	public SumType getSumType()
	{
		return autoSubscription.getSumType();
	}

	/**
	 * @return ������������� ��� �����������
	 */
	public String getFriendlyName()
	{
		return autoSubscription.getFriendlyName();
	}

	/**
	 * @return ��� ����������
	 */
	public String getReceiverName()
	{
		return autoSubscription.getReceiverName();
	}

	/**
	 * @return ���� �����
	 * @throws BusinessException
	 */
	public CardLink getCardLink() throws BusinessException
	{
		return resourceService.findLinkByNumber(loginId, ResourceType.CARD, autoSubscription.getCardNumber());
	}

	/**
	 * @return ���� �����
	 * @throws BusinessException
	 */
	public AccountLink getAccountLink() throws BusinessException
	{
		return resourceService.findLinkByNumber(loginId, ResourceType.ACCOUNT, autoSubscription.getAccountNumber());
	}

	/**
	 * @return ���������, �� �������� ������� ��������
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public BillingServiceProvider getServiceProvider() throws BusinessLogicException, BusinessException
	{
		CardPaymentSystemPaymentLongOffer longOfferDetailInfo = getAutoSubscriptionInfo();
		return  serviceProviderService.find(
					longOfferDetailInfo.getReceiverPointCode(),
					longOfferDetailInfo.getService().getCode(),
					billingService.getByCode(longOfferDetailInfo.getBillingCode())
		);
	}

	/**
	 * �������� ����������� �� ������������� ����������
	 * @return ������������� ����������
	 */
	public String getStartExecutionDetail() throws BusinessLogicException, BusinessException
	{
		ExecutionEventType eventType = autoSubscription.getExecutionEventType();
		if (ExecutionEventType.isPeriodic(autoSubscription.getExecutionEventType()))
		{
			return PeriodicExecutionEventTypeWrapper.getExecutionDetail(eventType, getPayDay());
		}
		return getExecutionEventType();
	}

	public String toString()
    {
        return "���������� externalId=" + getExternalId();
    }

	public Calendar getPayDay()
	{
		Calendar date = Calendar.getInstance();
		LongOfferPayDay day = autoSubscription.getLongOfferPayDay();
		ExecutionEventType eventType = autoSubscription.getExecutionEventType();
		switch (eventType)
		{
			case ONCE_IN_WEEK:
				date.set(Calendar.DAY_OF_WEEK, day.getWeekDay());
				break;
			case ONCE_IN_YEAR:
				date = DateHelper.getNearDateByYear(day.getDay(), day.getMonthInYear());
				break;
			case ONCE_IN_QUARTER:
				date = DateHelper.getNearDateByQuarter(day.getMonthInQuarter(), day.getDay());
				break;
			case ONCE_IN_MONTH:
				date = DateHelper.getNearDateByMonth(day.getDay());
				break;
		}
		return date;
	}
}
