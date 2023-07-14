package com.rssl.phizic.web.atm.deposits;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.common.FilterActionForm;

/**
 * @author Pankin
 * @ created 16.01.2012
 * @ $Author$
 * @ $Revision$
 */

public class DepositProductTypeInfoForm extends FilterActionForm
{
	protected static final String DEPOSIT_TYPE_FIELD = "depositType";
	protected static final String DEPOSIT_ID_FIELD = "depositId";
	protected static final String DEPOSIT_GROUP_FIELD = "depositGroup";
	protected static final String OPENING_DATE_FIELD = "openingDate";
	protected static final String PERIOD_FIELD = "period";
	protected static final String CURRENCY_CODE_FIELD = "currencyCode";
	protected static final String MIN_BALANCE_FIELD = "minBalance";

	protected static final Form FILTER_FORM = createFilterForm();

	private String depositType;
	private String depositId;
	private String depositGroup;
	private String openingDate;
	private String period;
	private String currencyCode;
	private String minBalance;

	public String getDepositType()
	{
		return depositType;
	}

	public void setDepositType(String depositType)
	{
		this.depositType = depositType;
	}

	public String getDepositId()
	{
		return depositId;
	}

	public void setDepositId(String depositId)
	{
		this.depositId = depositId;
	}

	public String getDepositGroup()
	{
		return depositGroup;
	}

	public void setDepositGroup(String depositGroup)
	{
		this.depositGroup = depositGroup;
	}

	public String getOpeningDate()
	{
		return openingDate;
	}

	public void setOpeningDate(String openingDate)
	{
		this.openingDate = openingDate;
	}

	public String getPeriod()
	{
		return period;
	}

	public void setPeriod(String period)
	{
		this.period = period;
	}

	public String getCurrencyCode()
	{
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode)
	{
		this.currencyCode = currencyCode;
	}

	public String getMinBalance()
	{
		return minBalance;
	}

	public void setMinBalance(String minBalance)
	{
		this.minBalance = minBalance;
	}

	private static Form createFilterForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Идентификатор вида вклада");
		fieldBuilder.setName(DEPOSIT_TYPE_FIELD);
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator("Не указан вид вклада"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Код группы вкладного продукта");
		fieldBuilder.setName(DEPOSIT_GROUP_FIELD);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Дата открытия вклада");
		fieldBuilder.setName(OPENING_DATE_FIELD);
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator("Не указана дата открытия вклада"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Срок хранения вклада");
		fieldBuilder.setName(PERIOD_FIELD);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("^(\\d{1,})-(\\d{1,})-(\\d{1,})",
				"Неверно указан срок хранения вклада"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Валюта вклада");
		fieldBuilder.setName(CURRENCY_CODE_FIELD);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator("Не указана валюта вклада"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Сумма неснижаемого остатка (сумма зачисления) вклада");
		fieldBuilder.setName(MIN_BALANCE_FIELD);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
