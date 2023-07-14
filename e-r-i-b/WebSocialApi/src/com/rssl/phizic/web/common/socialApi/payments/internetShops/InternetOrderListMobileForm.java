package com.rssl.phizic.web.common.socialApi.payments.internetShops;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.web.common.client.payments.internetShops.InternetOrderListForm;

import java.util.Arrays;

/**
 * Список заказов интернет-магазинов
 * @author Dorzhinov
 * @ created 10.06.2013
 * @ $Author$
 * @ $Revision$
 */
public class InternetOrderListMobileForm extends InternetOrderListForm
{
	static final String DATE_FORMAT = "dd.MM.yyyy";
	//in
	private String dateFrom; //мин. дата
	private String dateTo; //макс. дата
	private String amountFrom; //мин. сумма
	private String amountTo; //макс. сумма
	private String currency; //валюта суммы
	private String state; //статус заказа

	public String getDateFrom()
	{
		return dateFrom;
	}

	public void setDateFrom(String dateFrom)
	{
		this.dateFrom = dateFrom;
	}

	public String getDateTo()
	{
		return dateTo;
	}

	public void setDateTo(String dateTo)
	{
		this.dateTo = dateTo;
	}

	public String getAmountFrom()
	{
		return amountFrom;
	}

	public void setAmountFrom(String amountFrom)
	{
		this.amountFrom = amountFrom;
	}

	public String getAmountTo()
	{
		return amountTo;
	}

	public void setAmountTo(String amountTo)
	{
		this.amountTo = amountTo;
	}

	public String getCurrency()
	{
		return currency;
	}

	public void setCurrency(String currency)
	{
		this.currency = currency;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public static final Form FILTER_FORM = createMobileForm();

	@SuppressWarnings({"OverlyLongMethod", "TooBroadScope"})
	private static Form createMobileForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Состояние");
		fieldBuilder.setName(STATE_CODE);
		fieldBuilder.setType("string");
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Сумма от");
		fieldBuilder.setName(FROM_AMOUNT);
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Сумма до");
		fieldBuilder.setName(TO_AMOUNT);
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Валюта");
		fieldBuilder.setName(CURRENCY);
		fieldBuilder.setType("string");
		fb.addField(fieldBuilder.build());

		DateParser dataParser = new DateParser(DATE_FORMAT);
		DateFieldValidator dateValidator = new DateFieldValidator(DATE_FORMAT, "Введите дату в формате ДД.ММ.ГГГГ");
		RequiredFieldValidator requiredValidator = new RequiredFieldValidator();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Конечная дата периода");
		fieldBuilder.setName(TO_DATE);
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredValidator, dateValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Начальная дата периода");
		fieldBuilder.setName(FROM_DATE);
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredValidator, dateValidator);
		fb.addField(fieldBuilder.build());

		CompareValidator compareValidator = new CompareValidator();
		compareValidator.setParameter(CompareValidator.OPERATOR, DateTimeCompareValidator.LESS);
		compareValidator.setBinding(CompareValidator.FIELD_O1, FROM_DATE);
		compareValidator.setBinding(CompareValidator.FIELD_O2, TO_DATE);
		compareValidator.setMessage("Конечная дата должна быть больше начальной!");
		fb.addFormValidators(compareValidator);

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тип периода");
		fieldBuilder.setName(TYPE_DATE);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
			new RequiredFieldValidator(),
			new ChooseValueValidator(Arrays.asList("month", "period")));
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
