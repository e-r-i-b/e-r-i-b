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
	private String[] terbankIds = new String[]{}; //id ��������� ���������
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
		fieldBuilder.setDescription("��� ��������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("name");
		fieldBuilder.setDescription("������������ ��������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,100}", "������������ �������� ������ ���� �� ����� 100 ��������")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("minDurationYears");
		fieldBuilder.setDescription("���. ���� ������� � �����");
		fieldBuilder.setType(IntType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("\\d{0,2}", "���� ������� � ����� ������ ��������� �� ����� 2 ����"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("minDurationMonths");
		fieldBuilder.setDescription("���. ���� ������� � �������");
		fieldBuilder.setType(IntType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("\\d{0,3}", "���� ������� � ������� ������ ��������� �� ����� 3 ����"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("maxDurationYears");
		fieldBuilder.setDescription("����. ���� ������� � �����");
		fieldBuilder.setType(IntType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("\\d{0,2}", "���� ������� � ����� ������ ��������� �� ����� 2 ����"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("maxDurationMonths");
		fieldBuilder.setDescription("����. ���� ������� � �������");
		fieldBuilder.setType(IntType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("\\d{0,3}", "���� ������� � ������� ������ ��������� �� ����� 3 ����"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("isMaxDurationInclude");
		fieldBuilder.setDescription("�������� ������������ ����");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("needInitialInstalment");
		fieldBuilder.setDescription("�������������� �����");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("minInitialInstalment");
		fieldBuilder.setDescription("���. �������������� ����� � %");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("maxInitialInstalment");
		fieldBuilder.setDescription("����. �������������� ����� � %");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("isMaxInitialInstalmentInclude");
		fieldBuilder.setDescription("�������� ������������ �������������� �����");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("needSurety");
		fieldBuilder.setDescription("�����������");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());
		
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("additionalTerms");
		fieldBuilder.setDescription("�������������� �������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		LengthFieldValidator lengthValidator = new LengthFieldValidator();
		lengthValidator.setParameter("maxlength", new BigInteger("500"));
		lengthValidator.setMessage("�������������� ������� ������ ���� �� ����� 500 ��������");
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
		fieldBuilder.setDescription("������ ��������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		MoneyFieldValidator maxAmountValidator = new MoneyFieldValidator();
		maxAmountValidator.setParameter("maxValue", "9999999999.99");
		maxAmountValidator.setMessage("���������� ��������� ����� ��������. ������ ��� �������� �������: #.##");

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("minAmount");
		fieldBuilder.setDescription("���. ����� ��������");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.addValidators(maxAmountValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("maxAmount");
		fieldBuilder.setDescription("����. ����� �������� (� ������)");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.addValidators(maxAmountValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("maxAmountPercent");
		fieldBuilder.setDescription("����. ����� �������� (� % �� ���������)");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("^(\\d{1,2}+((\\.|,)\\d{0,2})?)?$", "���������� ��������� ����� ��������. ������ ��� ���������� �������: #.##"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("amountType");
		fieldBuilder.setDescription("��� ������������ �����");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("isMaxAmountInclude");
		fieldBuilder.setDescription("�������� ������������ �����");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		NumericRangeValidator rangeValidator = new NumericRangeValidator();
		rangeValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "0");
		rangeValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "100");
		rangeValidator.setMessage("���������� ������ ������ ���� ������ � ��������� �� 0 �� 100");

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("minInterestRate");
		fieldBuilder.setDescription("���. % ������");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RegexpFieldValidator("^\\s*(((\\d{0,3}(\\.\\d{0,2})?))|((\\d{0,3}(\\,\\d{0,2})?)))\\s*$", "���������� ��������� ���������� ������. ������ ��� ���������� �������: ###.##"),
				rangeValidator
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("maxInterestRate");
		fieldBuilder.setDescription("����. % ������");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RegexpFieldValidator("^\\s*(((\\d{0,3}(\\.\\d{0,2})?))|((\\d{0,3}(\\,\\d{0,2})?)))\\s*$", "���������� ��������� ���������� ������. ������ ��� ���������� �������: ###.##"),
				rangeValidator
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("isMaxInterestRateInclude");
		fieldBuilder.setDescription("�������� ������������ % ������");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
