package com.rssl.phizic.operations.finances.financeCalendar;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.cards.CardImpl;
import com.rssl.phizic.business.finances.financeCalendar.CalendarDataDescription;
import com.rssl.phizic.business.finances.financeCalendar.CalendarDayExtractByAutoSubscriptionDescription;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.autopayments.AutoSubscriptionService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoPayStatusType;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoSubscription;
import com.rssl.phizic.gate.payments.longoffer.CardToAccountLongOffer;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.finances.FinanceHelper;
import com.rssl.phizic.utils.DateHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author lepihina
 * @ created 30.04.14
 * $Author$
 * $Revision$
 */
public class GetAutoSubscriptionsToFinanceCalendarOperation extends OperationBase
{
	/**
	 * Дозаполняет информацию для финансового календаря
	 * @param calendarData - данные для календаря
	 * @param selectedCards - карты, по которым стоятся данные
	 * @throws BusinessException
	 */
	public void fillAutoSubscriptionData(List<CalendarDataDescription> calendarData, List<CardLink> selectedCards) throws BusinessException
	{
		if (CollectionUtils.isEmpty(selectedCards) || CollectionUtils.isEmpty(calendarData))
		{
			return;
		}

		Calendar startDate = calendarData.get(0).getDate();
		Calendar endDate = DateHelper.endOfDay(calendarData.get(calendarData.size() - 1).getDate());
		if (DateHelper.getCurrentDate().after(endDate))
		{
			return;
		}

		List<Card> clientCards = new ArrayList<Card>();
		for (CardLink cardLink : selectedCards)
		{
			CardImpl card = new CardImpl(cardLink.getCard());
			card.setExpireDate(cardLink.getExpireDate());
			clientCards.add(card);
		}

		try
		{
			AutoSubscriptionService subscriptionService = GateSingleton.getFactory().service(AutoSubscriptionService.class);
			//Фильтруем по статусу на нашей стороне, тк по текущей xsd при передаче статусов возникает падение
			List<AutoSubscription> subscriptions = subscriptionService.getAutoSubscriptions(clientCards);
			if (CollectionUtils.isEmpty(subscriptions))
				return;

			for (AutoSubscription subscription : subscriptions)
			{
				if (AutoPayStatusType.Active != subscription.getAutoPayStatusType() || subscription.getType().equals(CardToAccountLongOffer.class))
				{
					//в календаре должны отображаться только активные автоплатежии автопереводы
					continue;
				}

				Calendar payDate = subscription.getNextPayDate();
				if (payDate != null && !DateHelper.getCurrentDate().after(payDate) && !payDate.before(startDate) && !payDate.after(endDate))
				{
					Long daysDiff = DateHelper.daysDiff(startDate, payDate);
					CalendarDataDescription dataDescription = calendarData.get(daysDiff.intValue());
					dataDescription.setAutoSubscriptionsCount(dataDescription.getAutoSubscriptionsCount() + 1);

					if (subscription.getAmount() != null)
						dataDescription.setPaymentsAmount(dataDescription.getPaymentsAmount().add(subscription.getAmount().getDecimal()));
				}
			}
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Возвращает информацию для финансового календаря по автоплатежам
	 * @param selectedCardIds - идентификаторы карт, по которым стоятся данные
	 * @param startDate - начало периода
	 * @param endDate - конец периода
	 * @return информация по автоплатежам
	 * @throws BusinessException
	 */
	public List<CalendarDayExtractByAutoSubscriptionDescription> getAutoSubscriptionData(String selectedCardIds, Calendar startDate, Calendar endDate) throws BusinessException, BusinessLogicException
	{
		List<CalendarDayExtractByAutoSubscriptionDescription> data = new ArrayList<CalendarDayExtractByAutoSubscriptionDescription>();

		if (DateHelper.getCurrentDate().after(endDate))
		{
			return data;
		}

		List<CardLink> selectedCards = FinanceHelper.getSelectedCards(selectedCardIds);
		if (CollectionUtils.isEmpty(selectedCards))
		{
			return data;
		}

		List<Card> clientCards = new ArrayList<Card>();
		for (CardLink cardLink : selectedCards)
		{
			CardImpl card = new CardImpl(cardLink.getCard());
			card.setExpireDate(cardLink.getExpireDate());
			clientCards.add(card);
		}

		try
		{
			AutoSubscriptionService subscriptionService = GateSingleton.getFactory().service(AutoSubscriptionService.class);
			//Фильтруем по статусу на нашей стороне, тк по текущей xsd при передаче статусов возникает падение
			List<AutoSubscription> subscriptions = subscriptionService.getAutoSubscriptions(clientCards);
			if (CollectionUtils.isEmpty(subscriptions))
				return data;

			for (AutoSubscription subscription : subscriptions)
			{
				if (AutoPayStatusType.Active != subscription.getAutoPayStatusType() || subscription.getType().equals(CardToAccountLongOffer.class))
				{
					//в календаре должны отображаться только активные автоплатежи и автопереводы
					continue;
				}

				Calendar payDate = subscription.getNextPayDate();
				if (payDate != null && !payDate.before(startDate) && !payDate.after(endDate))
				{
					CalendarDayExtractByAutoSubscriptionDescription description = new CalendarDayExtractByAutoSubscriptionDescription();
					description.setNumber(subscription.getNumber());
					description.setDescription(subscription.getFriendlyName());
					description.setReceiverName(subscription.getReceiverName());
					description.setExecutionEventType(subscription.getExecutionEventType());
					description.setSumType(subscription.getSumType());
					description.setPayDate(subscription.getNextPayDate());

					if (subscription.getAmount() != null)
						description.setAmount(subscription.getAmount().getDecimal());
					data.add(description);
				}
			}

			return data;
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessException(e);
		}
	}
}
