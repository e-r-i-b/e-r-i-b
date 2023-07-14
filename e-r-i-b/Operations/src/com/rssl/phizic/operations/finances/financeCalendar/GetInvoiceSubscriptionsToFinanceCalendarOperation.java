package com.rssl.phizic.operations.finances.financeCalendar;

import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.basket.invoice.InvoiceService;
import com.rssl.phizic.business.finances.financeCalendar.CalendarDataDescription;
import com.rssl.phizic.business.finances.financeCalendar.CalendarDayExtractByInvoiceSubscriptionDescription;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.shop.ShopHelper;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.basket.InvoiceConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.einvoicing.OrderState;
import com.rssl.phizic.gate.einvoicing.ShopOrder;
import com.rssl.phizic.gate.einvoicing.ShopOrderService;
import com.rssl.phizic.gate.einvoicing.ShopProfile;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.finances.FinanceHelper;
import com.rssl.phizic.utils.DateHelper;
import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * @author lepihina
 * @ created 08.05.14
 * $Author$
 * $Revision$
 *
 * ѕолучает информацию по текущим/отложенным счетам дл€ финансового календар€
 */
public class GetInvoiceSubscriptionsToFinanceCalendarOperation extends OperationBase
{
	public static final InvoiceService invoiceService = new InvoiceService();

	/**
	 * ƒозаполн€ет информацию дл€ финансового календар€ по отложенным счетам
	 * @param calendarData - данные дл€ календар€
	 * @param selectedCards - карты, по которым сто€тс€ данные
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public void fillInvoiceSubscriptionData(List<CalendarDataDescription> calendarData, List<CardLink> selectedCards) throws BusinessException
	{
		if (CollectionUtils.isEmpty(selectedCards) || CollectionUtils.isEmpty(calendarData))
		{
			return;
		}

		Calendar startDate = calendarData.get(0).getDate();
		Calendar endDate = calendarData.get(calendarData.size()-1).getDate();
		if (DateHelper.getCurrentDate().after(endDate))
		{
			return;
		}

		fillInvoicesData(calendarData, selectedCards, startDate, endDate);
		fillShopOrdersData(calendarData, startDate, endDate);
	}

	private void fillInvoicesData(List<CalendarDataDescription> calendarData, List<CardLink> selectedCards, Calendar startDate, Calendar endDate) throws BusinessException
	{
		Calendar currentDate = DateHelper.getCurrentDate();
		List<String> cardNumbers = FinanceHelper.getCardNumbersList(selectedCards);
		List<CalendarDayExtractByInvoiceSubscriptionDescription> data = invoiceService.findInvoicesToFinanceCalendar(getLoginId(), DateHelper.maxOfDates(startDate, currentDate), endDate, getInvoiceFromDate(), cardNumbers, !currentDate.before(startDate));

		for(CalendarDayExtractByInvoiceSubscriptionDescription invoice : data)
		{
			Calendar invoicePayDate = invoice.getPayDate();
			if (invoicePayDate == null || invoicePayDate.before(currentDate))
			{
				invoicePayDate = currentDate;
			}

			Long daysDiff = DateHelper.daysDiff(startDate, invoicePayDate);
			CalendarDataDescription dataDescription = calendarData.get(daysDiff.intValue());
			dataDescription.setInvoiceSubscriptionsCount(dataDescription.getInvoiceSubscriptionsCount() + 1);

			BigDecimal amount = invoice.getAmount();
			if (amount != null)
				dataDescription.setPaymentsAmount(dataDescription.getPaymentsAmount().add(amount));
		}
	}

	private void fillShopOrdersData(List<CalendarDataDescription> calendarData, Calendar startDate, Calendar endDate) throws BusinessException
	{
		try
		{
			Calendar currentDate = DateHelper.getCurrentDate();
			List<ShopProfile> profiles = ShopHelper.get().getProfileHistory(AuthenticationContext.getContext());
			List<ShopOrder> shopOrders = GateSingleton.getFactory().service(ShopOrderService.class).getOrdersByProfileHistory(profiles, getInvoiceFromDate(), endDate, endDate, null, null, null, OrderState.DELAYED);

			for(ShopOrder shopOrder : shopOrders)
			{
				Calendar shopOrderPayDate = shopOrder.getDelayedPayDate();

				if (shopOrderPayDate.before(startDate) && currentDate.before(startDate))
					return;

				if (shopOrderPayDate.before(currentDate))
				{
					shopOrderPayDate = currentDate;
				}

				Long daysDiff = DateHelper.daysDiff(startDate, shopOrderPayDate);
				CalendarDataDescription dataDescription = calendarData.get(daysDiff.intValue());
				dataDescription.setInvoiceSubscriptionsCount(dataDescription.getInvoiceSubscriptionsCount() + 1);

				BigDecimal amount = shopOrder.getAmount().getDecimal();
				if (amount != null)
					dataDescription.setPaymentsAmount(dataDescription.getPaymentsAmount().add(amount));
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
	 * ¬озвращает информацию дл€ финансового календар€ по отложенным счетам
	 * @param selectedCardIds - идентификаторы карт, по которым сто€тс€ данные
	 * @param startDate - начало периода
	 * @param endDate - конец периода
	 * @return информаци€ по отложенным счетам
	 * @throws BusinessException
	 */
	public List<CalendarDayExtractByInvoiceSubscriptionDescription> getInvoiceSubscriptionData(String selectedCardIds, Calendar startDate, Calendar endDate) throws BusinessException, BusinessLogicException
	{
		if (DateHelper.getCurrentDate().after(endDate))
		{
			return Collections.emptyList();
		}

		List<CalendarDayExtractByInvoiceSubscriptionDescription> data = new ArrayList<CalendarDayExtractByInvoiceSubscriptionDescription>();
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		List<String> cardNumbers = FinanceHelper.getCardNumbersList(personData.getCards(), selectedCardIds);
		data.addAll(invoiceService.findInvoicesToFinanceCalendar(getLoginId(), startDate, endDate, getInvoiceFromDate(), cardNumbers, startDate.equals(DateHelper.getCurrentDate())));
		data.addAll(getShopOrdersData(startDate, endDate));

		return data;
	}

	private Long getLoginId()
	{
		return PersonContext.getPersonDataProvider().getPersonData().getLogin().getId();
	}

	private List<CalendarDayExtractByInvoiceSubscriptionDescription> getShopOrdersData(Calendar startDate, Calendar endDate) throws BusinessException
	{
		try
		{
			List<ShopProfile> profiles = ShopHelper.get().getProfileHistory(AuthenticationContext.getContext());
			List<ShopOrder> shopOrders = GateSingleton.getFactory().service(ShopOrderService.class).getOrdersByProfileHistory(profiles, getInvoiceFromDate(), endDate, endDate, null, null, null, OrderState.DELAYED);
			List<CalendarDayExtractByInvoiceSubscriptionDescription> data = new ArrayList<CalendarDayExtractByInvoiceSubscriptionDescription>();

			for(ShopOrder shopOrder : shopOrders)
			{
				if (shopOrder.getDelayedPayDate().before(startDate) && !startDate.equals(DateHelper.getCurrentDate()))
					continue;

				CalendarDayExtractByInvoiceSubscriptionDescription description = new CalendarDayExtractByInvoiceSubscriptionDescription();
				description.setId(shopOrder.getUuid());
				description.setType("shopOrder");
				description.setServiceName("ќплата заказа " + shopOrder.getReceiverName());
				description.setReceiverName(shopOrder.getReceiverName());
				description.setAmount(shopOrder.getAmount().getDecimal());
				data.add(description);
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

	private Calendar getInvoiceFromDate()
	{
		InvoiceConfig config = ConfigFactory.getConfig(InvoiceConfig.class);
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DATE, -config.getDaysInvoiceLive());
		return date;
	}
}
