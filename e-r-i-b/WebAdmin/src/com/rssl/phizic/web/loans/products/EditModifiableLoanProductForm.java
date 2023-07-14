package com.rssl.phizic.web.loans.products;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.*;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.loans.conditions.LoanCondition;
import com.rssl.phizic.business.loans.kinds.LoanKind;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.web.common.EditFormBase;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dorzhinov
 * @ created 25.05.2011
 * @ $Author$
 * @ $Revision$
 */
public class EditModifiableLoanProductForm extends EditFormBase
{
	private Boolean needInitialInstalment;
	private List<LoanKind> loanKinds;
	private List<Department> allTerbanks;
	private String[] terbankIds = new String[]{}; //id выбранных тербанков
	private List<Currency> currencies = new ArrayList<Currency>();
	private List<LoanCondition> conditions = new ArrayList<LoanCondition>();
	private String[] conditionId;
	private String[] currencyNumber;
	private String[] minAmount;
	private String[] maxAmount;
	private String[] maxAmountPercent;
	private String[] isMaxAmountInclude;
	private String[] amountType;
	private String[] minInterestRate;
	private String[] maxInterestRate;
	private String[] isMaxInterestRateInclude;

	public Boolean getNeedInitialInstalment()
	{
		return needInitialInstalment;
	}

	public void setNeedInitialInstalment(Boolean needInitialInstalment)
	{
		this.needInitialInstalment = needInitialInstalment;
	}

	public List<LoanKind> getLoanKinds()
	{
		return loanKinds;
	}

	public void setLoanKinds(List<LoanKind> loanKinds)
	{
		this.loanKinds = loanKinds;
	}

	public List<Department> getAllTerbanks()
	{
		return allTerbanks;
	}

	public void setAllTerbanks(List<Department> allTerbanks)
	{
		this.allTerbanks = allTerbanks;
	}

	public String[] getTerbankIds()
	{
		return terbankIds;
	}

	public void setTerbankIds(String[] terbankIds)
	{
		this.terbankIds = terbankIds;
	}

	public List<Currency> getCurrencies()
	{
		return currencies;
	}

	public void setCurrencies(List<Currency> currencies)
	{
		this.currencies = currencies;
	}

	public List<LoanCondition> getConditions()
	{
		return conditions;
	}

	public void setConditions(List<LoanCondition> conditions)
	{
		this.conditions = conditions;
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

	public String[] getMinAmount()
	{
		return minAmount;
	}

	public void setMinAmount(String[] minAmount)
	{
		this.minAmount = minAmount;
	}

	public String[] getMaxAmount()
	{
		return maxAmount;
	}

	public void setMaxAmount(String[] maxAmount)
	{
		this.maxAmount = maxAmount;
	}

	public String[] getMaxAmountPercent()
	{
		return maxAmountPercent;
	}

	public void setMaxAmountPercent(String[] maxAmountPercent)
	{
		this.maxAmountPercent = maxAmountPercent;
	}

	public String[] getMaxAmountInclude()
	{
		return isMaxAmountInclude;
	}

	public void setMaxAmountInclude(String[] maxAmountInclude)
	{
		this.isMaxAmountInclude = maxAmountInclude;
	}

	public String[] getAmountType()
	{
		return amountType;
	}

	public void setAmountType(String[] amountType)
	{
		this.amountType = amountType;
	}

	public String[] getMinInterestRate()
	{
		return minInterestRate;
	}

	public void setMinInterestRate(String[] minInterestRate)
	{
		this.minInterestRate = minInterestRate;
	}

	public String[] getMaxInterestRate()
	{
		return maxInterestRate;
	}

	public void setMaxInterestRate(String[] maxInterestRate)
	{
		this.maxInterestRate = maxInterestRate;
	}

	public String[] getMaxInterestRateInclude()
	{
		return isMaxInterestRateInclude;
	}

	public void setMaxInterestRateInclude(String[] maxInterestRateInclude)
	{
		this.isMaxInterestRateInclude = maxInterestRateInclude;
	}

	public static final Form FORM = createForm();
	public static final Form CONDITION_FORM = createConditionForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("loanKind");
		fieldBuilder.setDescription("Вид продукта");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("name");
		fieldBuilder.setDescription("Наименование продукта");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,100}", "Наименование продукта должно быть не более 100 символов")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("minDurationYears");
		fieldBuilder.setDescription("Мин. срок кредита в годах");
		fieldBuilder.setType(IntType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("\\d{0,2}", "Срок кредита в годах должен содержать не более 2 цифр"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("minDurationMonths");
		fieldBuilder.setDescription("Мин. срок кредита в месяцах");
		fieldBuilder.setType(IntType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("\\d{0,3}", "Срок кредита в месяцах должен содержать не более 3 цифр"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("maxDurationYears");
		fieldBuilder.setDescription("Макс. срок кредита в годах");
		fieldBuilder.setType(IntType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("\\d{0,2}", "Срок кредита в годах должен содержать не более 2 цифр"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("maxDurationMonths");
		fieldBuilder.setDescription("Макс. срок кредита в месяцах");
		fieldBuilder.setType(IntType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("\\d{0,3}", "Срок кредита в месяцах должен содержать не более 3 цифр"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("isMaxDurationInclude");
		fieldBuilder.setDescription("Включить максимальный срок");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("needInitialInstalment");
		fieldBuilder.setDescription("Первоначальный взнос");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("minInitialInstalment");
		fieldBuilder.setDescription("Мин. первоначальный взнос в %");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("maxInitialInstalment");
		fieldBuilder.setDescription("Макс. первоначальный взнос в %");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("isMaxInitialInstalmentInclude");
		fieldBuilder.setDescription("Включить максимальный первоначальный взнос");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("needSurety");
		fieldBuilder.setDescription("Обеспечение");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());
		
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("additionalTerms");
		fieldBuilder.setDescription("Дополнительные условия");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		LengthFieldValidator lengthValidator = new LengthFieldValidator();
		lengthValidator.setParameter("maxlength", new BigInteger("500"));
		lengthValidator.setMessage("Дополнительные условия должны быть не более 500 символов");
		fieldBuilder.addValidators(lengthValidator);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	private static Form createConditionForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("conditionId");
		fieldBuilder.setDescription("conditionId");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("currencyNumber");
		fieldBuilder.setDescription("Валюта продукта");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		MoneyFieldValidator maxAmountValidator = new MoneyFieldValidator();
		maxAmountValidator.setParameter("maxValue", "9999999999.99");
		maxAmountValidator.setMessage("Некорректо заполнена сумма продукта. Формат для денежных величин: #.##");

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("minAmount");
		fieldBuilder.setDescription("Мин. сумма продукта");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.addValidators(maxAmountValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("maxAmount");
		fieldBuilder.setDescription("Макс. сумма продукта (в валюте)");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.addValidators(maxAmountValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("maxAmountPercent");
		fieldBuilder.setDescription("Макс. сумма продукта (в % от стоимости)");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("^(\\d{1,2}+((\\.|,)\\d{0,2})?)?$", "Некорректо заполнена сумма продукта. Формат для процентных величин: #.##"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("amountType");
		fieldBuilder.setDescription("Тип максимальной суммы");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("isMaxAmountInclude");
		fieldBuilder.setDescription("Включить максимальную сумму");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		NumericRangeValidator rangeValidator = new NumericRangeValidator();
		rangeValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "0");
		rangeValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "100");
		rangeValidator.setMessage("Процентная ставка должна быть задана в диапазоне от 0 до 100");

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("minInterestRate");
		fieldBuilder.setDescription("Мин. % ставка");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RegexpFieldValidator("^\\s*(((\\d{0,3}(\\.\\d{0,2})?))|((\\d{0,3}(\\,\\d{0,2})?)))\\s*$", "Некорректо заполнена процентная ставка. Формат для процентных величин: ###.##"),
				rangeValidator
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("maxInterestRate");
		fieldBuilder.setDescription("Макс. % ставка");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RegexpFieldValidator("^\\s*(((\\d{0,3}(\\.\\d{0,2})?))|((\\d{0,3}(\\,\\d{0,2})?)))\\s*$", "Некорректо заполнена процентная ставка. Формат для процентных величин: ###.##"),
				rangeValidator
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("isMaxInterestRateInclude");
		fieldBuilder.setDescription("Включить максимальную % ставку");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
