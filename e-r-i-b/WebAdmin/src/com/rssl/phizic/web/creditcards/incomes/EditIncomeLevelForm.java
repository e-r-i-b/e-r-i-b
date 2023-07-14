package com.rssl.phizic.web.creditcards.incomes;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.NumericRangeValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.cardAmountStep.CardAmountStep;
import com.rssl.phizic.business.creditcards.conditions.IncomeCondition;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dorzhinov
 * @ created 29.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class EditIncomeLevelForm extends EditFormBase
{
	private List<Currency> currencies = new ArrayList<Currency>();
	private List<IncomeCondition> conditions = new ArrayList<IncomeCondition>();
	private List<CardAmountStep> creditLimits = new ArrayList<CardAmountStep>();
	private String[] conditionId;
	private String[] currencyNumber;
	private String[] minCreditLimitId;
	private String[] maxCreditLimitId;
	private String[] isMaxCreditLimitInclude;

	public List<Currency> getCurrencies()
	{
		return currencies;
	}

	public void setCurrencies(List<Currency> currencies)
	{
		this.currencies = currencies;
	}

	public List<IncomeCondition> getConditions()
	{
		return conditions;
	}

	public void setConditions(List<IncomeCondition> conditions)
	{
		this.conditions = conditions;
	}

	public List<CardAmountStep> getCreditLimits()
	{
		return creditLimits;
	}

	public void setCreditLimits(List<CardAmountStep> creditLimits)
	{
		this.creditLimits = creditLimits;
	}

	public String[] getConditionId()
	{
		return conditionId;
	}

	public void setConditionId(String[] conditionId)
	{
		this.conditionId = conditionId;
	}

	public String[] getCurrencyNumber()
	{
		return currencyNumber;
	}

	public void setCurrencyNumber(String[] currencyNumber)
	{
		this.currencyNumber = currencyNumber;
	}

	public String[] getMinCreditLimitId()
	{
		return minCreditLimitId;
	}

	public void setMinCreditLimitId(String[] minCreditLimitId)
	{
		this.minCreditLimitId = minCreditLimitId;
	}

	public String[] getMaxCreditLimitId()
	{
		return maxCreditLimitId;
	}

	public void setMaxCreditLimitId(String[] maxCreditLimitId)
	{
		this.maxCreditLimitId = maxCreditLimitId;
	}

	public String[] getMaxCreditLimitInclude()
	{
		return isMaxCreditLimitInclude;
	}

	public void setMaxCreditLimitInclude(String[] maxCreditLimitInclude)
	{
		isMaxCreditLimitInclude = maxCreditLimitInclude;
	}

	public static final Form FORM = createForm();
	public static final Form CONDITION_FORM = createConditionForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		NumericRangeValidator rangeValidator = new NumericRangeValidator();
		rangeValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "9999999999.99");
		rangeValidator.setMessage("Некорректо заполнен минимальный доход. Формат для денежных величин: #.##.");

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("minIncome");
		fieldBuilder.setDescription("Минимальный доход");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.addValidators(rangeValidator);
		formBuilder.addField(fieldBuilder.build());

		rangeValidator = new NumericRangeValidator();
		rangeValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "9999999999.99");
		rangeValidator.setMessage("Некорректо заполнен максимальный доход. Формат для денежных величин: #.##.");

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("maxIncome");
		fieldBuilder.setDescription("Максимальный доход");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.addValidators(rangeValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("isMaxIncomeInclude");
		fieldBuilder.setDescription("Включить максимальный доход");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	private static Form createConditionForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("currencyNumber");
		fieldBuilder.setDescription("Валюта продукта");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("minCreditLimitId");
		fieldBuilder.setDescription("Мин. кредитный лимит");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("maxCreditLimitId");
		fieldBuilder.setDescription("Макс. кредитный лимит");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("isMaxCreditLimitInclude");
		fieldBuilder.setDescription("Включить максимальный кредитный лимит");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
