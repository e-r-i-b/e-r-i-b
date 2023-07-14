package com.rssl.phizic.web.common.client.finances.financeCalendar;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.MonthPeriodFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.finances.financeCalendar.*;
import com.rssl.phizic.web.common.FilterActionForm;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author lepihina
 * @ created 24.04.14
 * $Author$
 * $Revision$
 */
public class GetDayExtractToFinanceCalendarForm extends FilterActionForm
{
	public static final Form FILTER_FORM = getFilterForm();
	private static final String DATE_FORMAT = "dd/MM/yyyy";
	private static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";

	private List<CalendarDayExtractByOperationDescription> dayExtractByOperations;
	private List<CalendarDayExtractByAutoSubscriptionDescription> dayExtractByAutoSubscriptions;
	private List<CalendarDayExtractByInvoiceSubscriptionDescription> dayExtractByInvoiceSubscriptions;
	private List<CalendarDayExtractByReminderDescription> dayExtractByReminders;
	private Calendar extractDate;
	private CalendarDateType dateType;

	/**
	 * @return выписка за день по операциям
	 */
	public List<CalendarDayExtractByOperationDescription> getDayExtractByOperations()
	{
		return dayExtractByOperations;
	}

	/**
	 * @param dayExtractByOperations - выписка за день по операциям
	 */
	public void setDayExtractByOperations(List<CalendarDayExtractByOperationDescription> dayExtractByOperations)
	{
		this.dayExtractByOperations = dayExtractByOperations;
	}

	/**
	 * @return выписка за день по автоплатежам
	 */
	public List<CalendarDayExtractByAutoSubscriptionDescription> getDayExtractByAutoSubscriptions()
	{
		return dayExtractByAutoSubscriptions;
	}

	/**
	 * @param dayExtractByAutoSubscriptions - выписка за день по автоплатежам
	 */
	public void setDayExtractByAutoSubscriptions(List<CalendarDayExtractByAutoSubscriptionDescription> dayExtractByAutoSubscriptions)
	{
		this.dayExtractByAutoSubscriptions = dayExtractByAutoSubscriptions;
	}

	/**
	 * @return выписка за день по текущему/отложенному счету
	 */
	public List<CalendarDayExtractByInvoiceSubscriptionDescription> getDayExtractByInvoiceSubscriptions()
	{
		return dayExtractByInvoiceSubscriptions;
	}

	/**
	 * @param dayExtractByInvoiceSubscriptions - выписка за день по текущему/отложенному счету
	 */
	public void setDayExtractByInvoiceSubscriptions(List<CalendarDayExtractByInvoiceSubscriptionDescription> dayExtractByInvoiceSubscriptions)
	{
		this.dayExtractByInvoiceSubscriptions = dayExtractByInvoiceSubscriptions;
	}

	/**
	 * @return выписка за день по напоминаниям
	 */
	public List<CalendarDayExtractByReminderDescription> getDayExtractByReminders()
	{
		return dayExtractByReminders;
	}

	/**
	 * @param dayExtractByReminders выписка за день по напоминаниям
	 */
	public void setDayExtractByReminders(List<CalendarDayExtractByReminderDescription> dayExtractByReminders)
	{
		this.dayExtractByReminders = dayExtractByReminders;
	}

	/**
	 * @return дата, на которую строится выписка
	 */
	public Calendar getExtractDate()
	{
		return extractDate;
	}

	/**
	 * @param extractDate - дата, на которую строится выписка
	 */
	public void setExtractDate(Calendar extractDate)
	{
		this.extractDate = extractDate;
	}

	/**
	 * @return тип даты в финансовом календаре
	 */
	public CalendarDateType getDateType()
	{
		return dateType;
	}

	/**
	 * @param dateType - тип даты в финансовом календаре
	 */
	public void setDateType(CalendarDateType dateType)
	{
		this.dateType = dateType;
	}

	private static Form getFilterForm()
	{
		List<Field> fields = new ArrayList<Field>();
		DateParser dataParser = new DateParser(DATE_FORMAT);

		MonthPeriodFieldValidator dateFieldValidator = new MonthPeriodFieldValidator(DATE_FORMAT);
		dateFieldValidator.setParameter(MonthPeriodFieldValidator.BEFORE_MONTH, -13); // Можно просматривать на 1 год и 1 месяц назад
		dateFieldValidator.setParameter(MonthPeriodFieldValidator.AFTER_MONTH, 14); // Можно просматривать на 1 год и 1 месяц вперед + 1 месяц запас (т.к. в календаре отображаем начало следующего и конец предыдущего месяцев)
		dateFieldValidator.setMessage("Вы можете просмотреть движение средств только на год вперед и на год назад от текущего месяца.");

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("onDate");
		fieldBuilder.setDescription("Дата на которую запрашивается выписка");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(), dateFieldValidator);
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("selectedCardIds");
		fieldBuilder.setDescription("Идентификаторы карт");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("openPageDate");
		fieldBuilder.setDescription("Дата, когда клиент зашел на страницу");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setParser(new DateParser(DATE_TIME_FORMAT));
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new DateFieldValidator(DATE_TIME_FORMAT));
		fields.add(fieldBuilder.build());

		FormBuilder formBuilder = new FormBuilder();
		formBuilder.setFields(fields);

		return formBuilder.build();
	}
}
