package com.rssl.phizic.web.creditcards.products;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.BigDecimalParser;
import com.rssl.common.forms.types.*;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.cardAmountStep.CardAmountStep;
import com.rssl.phizic.business.creditcards.conditions.CreditCardCondition;
import com.rssl.phizic.business.creditcards.validators.GracePeriodValidator;
import com.rssl.phizic.business.payments.forms.validators.UniqueDefaultCreditCardProductValidator;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.web.common.EditFormBase;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dorzhinov
 * @ created 15.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class EditCreditCardProductForm extends EditFormBase
{
	private List<Currency> currencies = new ArrayList<Currency>();
	private List<CreditCardCondition> conditions = new ArrayList<CreditCardCondition>();
	private List<CardAmountStep> creditLimits = new ArrayList<CardAmountStep>();
	private String[] conditionId;
	private String[] currencyNumber;
	private String[] minCreditLimitId;
	private String[] maxCreditLimitId;
	private String[] isMaxCreditLimitInclude;
	private String[] interestRate;
	private String[] offerInterestRate;  
	private String[] firstYearPayment;
	private String[] nextYearPayment;

	public List<Currency> getCurrencies()
	{
		return currencies;
	}

	public void setCurrencies(List<Currency> currencies)
	{
		this.currencies = currencies;
	}

	public List<CreditCardCondition> getConditions()
	{
		return conditions;
	}

	public void setConditions(List<CreditCardCondition> conditions)
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

	public String[] getInterestRate()
	{
		return interestRate;
	}

	public void setInterestRate(String[] interestRate)
	{
		this.interestRate = interestRate;
	}

	public String[] getOfferInterestRate()
	{
		return offerInterestRate;
	}

	public void setOfferInterestRate(String[] offerInterestRate)
	{
		this.offerInterestRate = offerInterestRate;
	}

	public String[] getFirstYearPayment()
	{
		return firstYearPayment;
	}

	public void setFirstYearPayment(String[] firstYearPayment)
	{
		this.firstYearPayment = firstYearPayment;
	}

	public String[] getNextYearPayment()
	{
		return nextYearPayment;
	}

	public void setNextYearPayment(String[] nextYearPayment)
	{
		this.nextYearPayment = nextYearPayment;
	}

	public static final Form FORM = createForm();
	public static final Form CONDITION_FORM = createConditionForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("name");
		fieldBuilder.setDescription("������������ ��������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,100}", "������������ �������� ������ ���� �� ����� 100 ��������"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("allowGracePeriod");
		fieldBuilder.setDescription("���� �������� ������");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("productId");
		fieldBuilder.setDescription("ID");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("useForPreapprovedOffers");
		fieldBuilder.setDescription("������������ ��� �����������");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("defaultForPreapprovedOffers");
		fieldBuilder.setDescription("������������ �� ��������� � ������������");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("gracePeriodDuration");
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.allowGracePeriod == true"));
		fieldBuilder.setDescription("�������� ������ ��");
		fieldBuilder.setType(IntType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("^(\\d{1,3})?$", "�������� ������ ������ ��������� �� ����� 3 ����"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("gracePeriodInterestRate");
		fieldBuilder.setDescription("���������� ������ � �������� ������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setParser(new BigDecimalParser());
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.allowGracePeriod == true"));
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator("^\\d{1,3}(\\.\\d{0,2}){0,1}$", "���������� ��������� ���������� ������ � �������� ������. ������ ��� ���������� ������� ###.##"));
		NumericRangeValidator rangeValidator = new NumericRangeValidator();
		rangeValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "0");
		rangeValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "100");
		rangeValidator.setMessage("���������� ������ � �������� ������ ������ ���� ������ � ��������� �� 0 �� 100");
		fieldBuilder.addValidators(rangeValidator);
		formBuilder.addField(fieldBuilder.build());


		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("cardTypeCode");
		fieldBuilder.setDescription("��� �� ������");
		fieldBuilder.setType(IntType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(
			new RequiredFieldValidator(),
			new RegexpFieldValidator("^(\\d{1,3})?$", "��� �� ������ ������ ��������� �� ����� 3 ����"));
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

		GracePeriodValidator gpvalidator = new GracePeriodValidator();
		gpvalidator.setBinding(GracePeriodValidator.FIELD_ALLOW_GRACE_PERIOD, "allowGracePeriod");
		gpvalidator.setBinding(GracePeriodValidator.FIELD_GRACE_PERIOD_DURATION, "gracePeriodDuration");
		gpvalidator.setBinding(GracePeriodValidator.FIELD_GRACE_PERIOD_INTEREST_RATE, "gracePeriodInterestRate");
		gpvalidator.setMessage("������� �������� ������������ ��������� ������� � ���������� ������ � �������� ������");
		gpvalidator.setEnabledExpression(new RhinoExpression("form.allowGracePeriod == true"));
		formBuilder.addFormValidators(gpvalidator);

		MultiFieldsValidator validator = new UniqueDefaultCreditCardProductValidator();
		validator.setBinding(UniqueDefaultCreditCardProductValidator.IS_DEFAULT,"defaultForPreapprovedOffers");
		validator.setBinding(UniqueDefaultCreditCardProductValidator.IS_PRE_APPROVED,"useForPreapprovedOffers");
		validator.setBinding(UniqueDefaultCreditCardProductValidator.PRODUCT_ID,"productId");
		validator.setMessage("� �������� ������������� �� ��������� � �������������� ������������ ����� ���� ������ ������ ���� �������");
		formBuilder.addFormValidators(validator);

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

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("minCreditLimitId");
		fieldBuilder.setDescription("���. ��������� �����");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("maxCreditLimitId");
		fieldBuilder.setDescription("����. ��������� �����");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("isMaxCreditLimitInclude");
		fieldBuilder.setDescription("�������� ������������ ��������� �����");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("interestRate");
		fieldBuilder.setDescription("���������� ������");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		NumericRangeValidator rangeValidator = new NumericRangeValidator();
		rangeValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "0");
		rangeValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "100");
		rangeValidator.setMessage("���������� ������ ������ ���� ������ � ��������� �� 0 �� 100");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator("^\\s*(((\\d{1,3}(\\.\\d{0,2})?))|((\\d{1,3}(\\,\\d{0,2})?)))\\s*$", "���������� ��������� ���������� ������. ������ ��� ���������� �������: ###.##"),
				rangeValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("offerInterestRate");
		fieldBuilder.setDescription("���������� ������ �� �������������� �����");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		rangeValidator = new NumericRangeValidator();
		rangeValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "0");
		rangeValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "100");
		rangeValidator.setMessage("���������� ������  �� �������������� ����� ������ ���� ������ � ��������� �� 0 �� 100");			
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator("^\\s*(((\\d{1,3}(\\.\\d{0,2})?))|((\\d{1,3}(\\,\\d{0,2})?)))\\s*$", "���������� ��������� ���������� ������ �� �������������� �����. ������ ��� ���������� �������: ###.##"),
				rangeValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("firstYearPayment");
		fieldBuilder.setDescription("����� �� ������� ������������: ������ ���");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());

		rangeValidator = new NumericRangeValidator();
		rangeValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "9999999999.99");
		rangeValidator.setMessage("���������� ��������� ����� �� ������� ������������: ������ ���. ������ ��� �������� �������: #.##");
		fieldBuilder.addValidators(new RequiredFieldValidator(), rangeValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("nextYearPayment");
		fieldBuilder.setDescription("����� �� ������� ������������: ����������� ����");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		rangeValidator = new NumericRangeValidator();
		rangeValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "9999999999.99");
		rangeValidator.setMessage("���������� ��������� ����� �� ������� ������������: ������ ���. ������ ��� �������� �������: #.##");
		fieldBuilder.addValidators(new RequiredFieldValidator(), rangeValidator);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
