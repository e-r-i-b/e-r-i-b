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
	 * @return ������� �� ���� �� ���������
	 */
	public List<CalendarDayExtractByOperationDescription> getDayExtractByOperations()
	{
		return dayExtractByOperations;
	}

	/**
	 * @param dayExtractByOperations - ������� �� ���� �� ���������
	 */
	public void setDayExtractByOperations(List<CalendarDayExtractByOperationDescription> dayExtractByOperations)
	{
		this.dayExtractByOperations = dayExtractByOperations;
	}

	/**
	 * @return ������� �� ���� �� ������������
	 */
	public List<CalendarDayExtractByAutoSubscriptionDescription> getDayExtractByAutoSubscriptions()
	{
		return dayExtractByAutoSubscriptions;
	}

	/**
	 * @param dayExtractByAutoSubscriptions - ������� �� ���� �� ������������
	 */
	public void setDayExtractByAutoSubscriptions(List<CalendarDayExtractByAutoSubscriptionDescription> dayExtractByAutoSubscriptions)
	{
		this.dayExtractByAutoSubscriptions = dayExtractByAutoSubscriptions;
	}

	/**
	 * @return ������� �� ���� �� ��������/����������� �����
	 */
	public List<CalendarDayExtractByInvoiceSubscriptionDescription> getDayExtractByInvoiceSubscriptions()
	{
		return dayExtractByInvoiceSubscriptions;
	}

	/**
	 * @param dayExtractByInvoiceSubscriptions - ������� �� ���� �� ��������/����������� �����
	 */
	public void setDayExtractByInvoiceSubscriptions(List<CalendarDayExtractByInvoiceSubscriptionDescription> dayExtractByInvoiceSubscriptions)
	{
		this.dayExtractByInvoiceSubscriptions = dayExtractByInvoiceSubscriptions;
	}

	/**
	 * @return ������� �� ���� �� ������������
	 */
	public List<CalendarDayExtractByReminderDescription> getDayExtractByReminders()
	{
		return dayExtractByReminders;
	}

	/**
	 * @param dayExtractByReminders ������� �� ���� �� ������������
	 */
	public void setDayExtractByReminders(List<CalendarDayExtractByReminderDescription> dayExtractByReminders)
	{
		this.dayExtractByReminders = dayExtractByReminders;
	}

	/**
	 * @return ����, �� ������� �������� �������
	 */
	public Calendar getExtractDate()
	{
		return extractDate;
	}

	/**
	 * @param extractDate - ����, �� ������� �������� �������
	 */
	public void setExtractDate(Calendar extractDate)
	{
		this.extractDate = extractDate;
	}

	/**
	 * @return ��� ���� � ���������� ���������
	 */
	public CalendarDateType getDateType()
	{
		return dateType;
	}

	/**
	 * @param dateType - ��� ���� � ���������� ���������
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
		dateFieldValidator.setParameter(MonthPeriodFieldValidator.BEFORE_MONTH, -13); // ����� ������������� �� 1 ��� � 1 ����� �����
		dateFieldValidator.setParameter(MonthPeriodFieldValidator.AFTER_MONTH, 14); // ����� ������������� �� 1 ��� � 1 ����� ������ + 1 ����� ����� (�.�. � ��������� ���������� ������ ���������� � ����� ����������� �������)
		dateFieldValidator.setMessage("�� ������ ����������� �������� ������� ������ �� ��� ������ � �� ��� ����� �� �������� ������.");

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("onDate");
		fieldBuilder.setDescription("���� �� ������� ������������� �������");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(), dateFieldValidator);
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("selectedCardIds");
		fieldBuilder.setDescription("�������������� ����");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("openPageDate");
		fieldBuilder.setDescription("����, ����� ������ ����� �� ��������");
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
