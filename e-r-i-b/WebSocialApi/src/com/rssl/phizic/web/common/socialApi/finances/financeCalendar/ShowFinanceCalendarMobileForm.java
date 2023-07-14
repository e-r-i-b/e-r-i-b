package com.rssl.phizic.web.common.socialApi.finances.financeCalendar;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.MonthPeriodFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.common.client.finances.financeCalendar.ShowFinanceCalendarForm;

/**
 * @author lepihina
 * @ created 05.05.14
 * $Author$
 * $Revision$
 */
public class ShowFinanceCalendarMobileForm extends ShowFinanceCalendarForm
{
	private static final String DATE_FORMAT = "MM.yyyy";

	private String onDate;
	private String showCash;
	private String[] excludeCategories;
	private String showCashPayments;
	private String country;

	/**
	 * @return дата, на которую запрашивается информация (месяц и год)
	 */
	public String getOnDate()
	{
		return onDate;
	}

	/**
	 * @param onDate - дата, на которую запрашивается информация (месяц и год)
	 */
	public void setOnDate(String onDate)
	{
		this.onDate = onDate;
	}

	/**
	 * @return показать внесение/выдачу наличных
	 */
	public String getShowCash()
	{
		return showCash;
	}

	/**
	 * @param showCash - показать внесение/выдачу наличных
	 */
	public void setShowCash(String showCash)
	{
		this.showCash = showCash;
	}

	/**
	 * @return исключаемые категории операций
	 */
	public String[] getExcludeCategories()
	{
		return excludeCategories;
	}

	/**
	 * @param excludeCategories - исключаемые категории операций
	 */
	public void setExcludeCategories(String[] excludeCategories)
	{
		this.excludeCategories = excludeCategories;
	}

	/**
	 * @return true - отобразить траты наличными
	 */
	public String getShowCashPayments()
	{
		return showCashPayments;
	}

	/**
	 * @param showCashPayments - отобразить ли траты наличными
	 */
	public void setShowCashPayments(String showCashPayments)
	{
		this.showCashPayments = showCashPayments;
	}

	/**
	 * @return страна совершения операции
	 */
	public String getCountry()
	{
		return country;
	}

	/**
	 * @param country - страна совершения операции
	 */
	public void setCountry(String country)
	{
		this.country = country;
	}

	protected static Form createFilterForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		DateParser dataParser = new DateParser(DATE_FORMAT);

		RequiredFieldValidator requiredValidator = new RequiredFieldValidator();

		MonthPeriodFieldValidator dateFieldValidator = new MonthPeriodFieldValidator(DATE_FORMAT);
		dateFieldValidator.setParameter(MonthPeriodFieldValidator.BEFORE_MONTH, BEFORE_MONTHS);
		dateFieldValidator.setParameter(MonthPeriodFieldValidator.AFTER_MONTH, AFTER_MONTHS);
		dateFieldValidator.setMessage("Вы можете просмотреть движение средств только на год вперед и на год назад от текущего месяца.");

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("onDate");
		fieldBuilder.setDescription("Месяц");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredValidator, dateFieldValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("selectedId");
		fieldBuilder.setDescription("Карта");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("^(\\d*|allCards|mainCards|debitCards|overdraftCards|creditCards|ownAdditionalCards|otherAdditionalCards)$"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("showCash");
		fieldBuilder.setDescription("Показать внесение/выдачу наличных");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("excludeCategories");
		fieldBuilder.setDescription("Исключаемые категории операций");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("showCashPayments");
		fieldBuilder.setDescription("Отображать траты наличными");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("country");
		fieldBuilder.setDescription("Страна совершения операции");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators();
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
