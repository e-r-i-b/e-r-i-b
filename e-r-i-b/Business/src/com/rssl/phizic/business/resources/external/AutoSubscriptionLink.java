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
 * простая подписка на автоплатеж.
 * необходима для совместимости с другими автоплатежами.
 * (подписка не хранится у нас в б.д. (как линк) поскольку АС "Автоплатежи" - централизованная система, запрашиваем информацию всегда из нее)
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
	 * Получить обощенную информацию об алгоритме исполнения платежа
	 * @return обощенная информация об алгоритме исполнения платежа
	 */
	public String getExecutionEventType() throws BusinessException, BusinessLogicException
	{
		if (MockHelper.isMockObject(autoSubscription))
		{
			return MOCK_STRING;
		}

		StringBuilder executionEventType = new StringBuilder();
		ExecutionEventType eventType = autoSubscription.getExecutionEventType();
		// по спецификации тип может не придти
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
	 * Возвращает детальную информацию по автоплатежу.
	 * @return детальная информация.
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
			log.error("Ошибка при получении информации о подписке на автоплатеж " + getExternalId(), e);
			return null;
		}
	}

	/**
	 * Получение списка платежей по подписке.
	 *
	 * @param begin начальная дата.
	 * @param end конечная дата.
	 * @return список платежей.
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public List<ScheduleItem> getSheduleReport(Calendar begin, Calendar end) throws BusinessException, BusinessLogicException
	{
		try
		{
			AutoSubscriptionService subscriptionService = GateSingleton.getFactory().service(AutoSubscriptionService.class);
			//по спецификации, если дата начала и окончания периода не приходят, то возвращаются 10 последных платежей.
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
		//ничего не делает.
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
	 * @return дата начала действия поручения
	 */
	public Calendar getStartDate()
	{
		return autoSubscription.getStartDate();
	}

	/**
	 * @return дата окончания действия поручения
	 */
	public Calendar getEndDate()
	{
		return autoSubscription.getEndDate();
	}

	/**
	 * @return дата следующего платежа
	 */
	public Calendar getNextPayDate()
	{
		return autoSubscription.getNextPayDate();
	}

	/**
	 * @return сумма исполнения. не может быть заполенно одновременно с getPercent
	 */
	public Money getAmount()
	{
		return autoSubscription.getAmount();
	}

	/**
	 * Статус автоплатежа.
	 * @return статус.
	 */
	public AutoPayStatusType getAutoPayStatusType()
	{
		return autoSubscription.getAutoPayStatusType();
	}

	/**
	 * @return тип суммы исполения регулярного платежа
	 */
	public SumType getSumType()
	{
		return autoSubscription.getSumType();
	}

	/**
	 * @return Мнемоническое имя автоплатежа
	 */
	public String getFriendlyName()
	{
		return autoSubscription.getFriendlyName();
	}

	/**
	 * @return имя получателя
	 */
	public String getReceiverName()
	{
		return autoSubscription.getReceiverName();
	}

	/**
	 * @return линк карты
	 * @throws BusinessException
	 */
	public CardLink getCardLink() throws BusinessException
	{
		return resourceService.findLinkByNumber(loginId, ResourceType.CARD, autoSubscription.getCardNumber());
	}

	/**
	 * @return линк счета
	 * @throws BusinessException
	 */
	public AccountLink getAccountLink() throws BusinessException
	{
		return resourceService.findLinkByNumber(loginId, ResourceType.ACCOUNT, autoSubscription.getAccountNumber());
	}

	/**
	 * @return поставщик, по которому создано подписка
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
	 * Получить расшифровку по периодичности исполнения
	 * @return периодичность исполнения
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
        return "Автоплатеж externalId=" + getExternalId();
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
