package com.rssl.phizic.web.pfp.addproduct;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.BigDecimalParser;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;
import com.rssl.phizic.business.dictionaries.pfp.products.insurance.InsuranceProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.insurance.InsuranceDatePeriod;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.AccountProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.complex.ComplexInsuranceProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.ProductBase;
import com.rssl.phizic.business.dictionaries.pfp.common.PortfolioType;
import com.rssl.phizic.business.pfp.portfolio.PersonPortfolio;
import com.rssl.phizic.business.pfp.portfolio.PortfolioProduct;
import com.rssl.phizic.business.pfp.portfolio.PortfolioState;
import com.rssl.phizic.business.pfp.portfolio.product.BaseProduct;
import com.rssl.phizic.business.pfp.portfolio.product.InsuranceBaseProduct;
import com.rssl.phizic.business.pfp.PersonalFinanceProfile;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.util.MoneyFunctions;
import com.rssl.phizic.common.types.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.ArrayList;

/**
 * @author mihaylov
 * @ created 10.05.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * ����� ��� ������ � ������ �������������� ������������ ���������� ��������
 */
public class ComplexInsuranceProductFormBuilder
{

	/**
	 * ��������� ���������� �����
	 * @param portfolio - �������� �������
	 * @param productBase - ���������� �������
	 * @return
	 */
	public static Form getForm(PersonalFinanceProfile profile, PersonPortfolio portfolio, ProductBase productBase)
	{
		ComplexInsuranceProduct complexInsuranceProduct = (ComplexInsuranceProduct) productBase;
		AccountProduct accountProduct = complexInsuranceProduct.getAccount();

		BigDecimal minSumInsurance = complexInsuranceProduct.getMinSumInsurance();
		BigDecimal minSumAccount = complexInsuranceProduct.getAccount().getParameters(portfolio.getType()).getMinSum();

		BigDecimal minSumComplex = complexInsuranceProduct.getMinSum();
		BigDecimal maxSumComplex = portfolio.getFreeAmount().getDecimal();

		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();

		MoneyFieldValidator minInsuranceSumFieldValidator = new MoneyFieldValidator();
		minInsuranceSumFieldValidator.setParameter(MoneyFieldValidator.PARAMETER_MIN_VALUE, minSumInsurance.toString());
		minInsuranceSumFieldValidator.setMessage("�� ������� ����� ���������� ������ ������ ���������� ����������. ����������, ������� ����� �� " +
				MoneyFunctions.formatAmount(minSumInsurance) + " ���.");

		MoneyFieldValidator minAccountSumFieldValidator = new MoneyFieldValidator();
		minAccountSumFieldValidator.setParameter(MoneyFieldValidator.PARAMETER_MIN_VALUE, minSumAccount.toString());
		minAccountSumFieldValidator.setMessage("�� ������� ����� ������ ���������� ����������. ����������, ������� ����� �� " +
				MoneyFunctions.formatAmount(minSumAccount) + " ���.");

		MultiFieldSumValidator maxComplexSumValidator = new MultiFieldSumValidator();
		maxComplexSumValidator.setParameter(MultiFieldSumValidator.OPERATOR, MultiFieldSumValidator.LESS_EQUAL);
		maxComplexSumValidator.setParameter(MultiFieldSumValidator.SUM_PARAMETER_NAME, maxSumComplex.toString());
		maxComplexSumValidator.setMessage("C���� ��������� ���� ��������� �������� � ��������. ����������, ������� ����� �� " +
				MoneyFunctions.formatAmount(maxSumComplex) + " ���.");

		MultiFieldSumValidator minComplexSumValidator = new MultiFieldSumValidator();
		minComplexSumValidator.setParameter(MultiFieldSumValidator.OPERATOR, MultiFieldSumValidator.GREATE_EQUAL);
		minComplexSumValidator.setParameter(MultiFieldSumValidator.SUM_PARAMETER_NAME, minSumComplex.toString());
		minComplexSumValidator.setMessage("��������� ������������ ���������� �������� ������ ����������� ����������. ����������, ������� ����� �� " +
				MoneyFunctions.formatAmount(minSumComplex)+ " ���.");

		CompareSumValidator compareSumValidator = new CompareSumValidator();
		compareSumValidator.setBinding(CompareSumValidator.FIELD_O1,"accountAmount");
		compareSumValidator.setBinding(CompareSumValidator.FIELD_O2,"insuranceAmount");
		compareSumValidator.setParameter(CompareSumValidator.FIELD_O2_FACTOR,complexInsuranceProduct.getAccount().getSumFactor().toString());
		compareSumValidator.setParameter(CompareSumValidator.OPERATOR,CompareSumValidator.LESS_EQUAL);
		compareSumValidator.setMessage("����� ������ ��� ��������� ����� ���������� ������  ������ ����������� ����������. ����������, ��������� ����� ������ �� %s ���. ��� ��������� ����� ���������� ������.");
		compareSumValidator.setParameter(CompareSumValidator.NEED_UPDATE_MESSAGE,"true");

		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("accountIncome");
		fieldBuilder.setDescription("���������� ������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setParser(new BigDecimalParser());
		fieldBuilder.addValidators(new RegexpFieldValidator("^\\d+((\\.|,)\\d{0,2})?$", "����������, ������� ����������, ��������� �����, ����� � �������. ��������, 0.31."));
		if(accountProduct.getMinIncome() != null && accountProduct.getMaxIncome() != null)
		{
			NumericRangeValidator numericMinValidator = new NumericRangeValidator();
			numericMinValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE,accountProduct.getMinIncome().toString());
			numericMinValidator.setMessage("�� ������� ���������� ������ �����������. ����������, ������� ������ ��������.");
			NumericRangeValidator numericMaxValidator = new NumericRangeValidator();
			numericMaxValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE,accountProduct.getMaxIncome().toString());
			numericMaxValidator.setMessage("�� ������� ���������� ������ ������������. ����������, ������� ������ ��������.");
			fieldBuilder.addValidators(requiredFieldValidator,numericMinValidator,numericMaxValidator);
		}
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�����: ����� ������");
		fieldBuilder.setName("accountAmount");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredFieldValidator,minAccountSumFieldValidator);
		formBuilder.addField(fieldBuilder.build());
		maxComplexSumValidator.setBinding(fieldBuilder.getName(),fieldBuilder.getName());
		minComplexSumValidator.setBinding(fieldBuilder.getName(),fieldBuilder.getName());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� ���������: �������");
		fieldBuilder.setName("insuranceProductId");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredFieldValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������������");
		fieldBuilder.setName("selectedPeriodId");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredFieldValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���� ���������");
		fieldBuilder.setName("selectedTerm");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredFieldValidator);
		formBuilder.addField(fieldBuilder.build());

		List<FieldValidator> insuranceIncomeValidatorList = new ArrayList<FieldValidator>();
		List<FieldValidator> insuranceAmountValidatorList = new ArrayList<FieldValidator>();
		insuranceAmountValidatorList.add(requiredFieldValidator);
		insuranceAmountValidatorList.add(minInsuranceSumFieldValidator);

		PersonPortfolio quarterlyInvest = profile.getPortfolioByType(PortfolioType.QUARTERLY_INVEST);
		quarterlyInvest.setPortfolioState(PortfolioState.EDIT);
		Money freeQuarterInvest = quarterlyInvest.getFreeAmount();

		for(InsuranceProduct insuranceProduct: complexInsuranceProduct.getInsuranceProducts())
		{
			if(insuranceProduct.getMinIncome() != null && insuranceProduct.getMaxIncome() != null)
			{
				RhinoExpression enableExpression = new RhinoExpression("form.insuranceProductId=="+insuranceProduct.getId());
				NumericRangeValidator numericMinValidator = new NumericRangeValidator();
				numericMinValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE,insuranceProduct.getMinIncome().toString());
				numericMinValidator.setMessage("�� ������� ���������� ������ �����������. ����������, ������� ������ ��������.");
				NumericRangeValidator numericMaxValidator = new NumericRangeValidator();
				numericMaxValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE,insuranceProduct.getMaxIncome().toString());
				numericMaxValidator.setMessage("�� ������� ���������� ������ ������������. ����������, ������� ������ ��������.");
				numericMaxValidator.setEnabledExpression(enableExpression);
				insuranceIncomeValidatorList.add(numericMinValidator);
				insuranceIncomeValidatorList.add(numericMaxValidator);
				RequiredFieldValidator rfValidator = new RequiredFieldValidator();
				rfValidator.setEnabledExpression(enableExpression);
				insuranceIncomeValidatorList.add(rfValidator);
			}
			for(InsuranceDatePeriod insurancePeriod : insuranceProduct.getPeriods())
			{
				if(insurancePeriod.getType().getMonths() != null)
				{
					MoneyFieldValidator maxQuarterInvestValidator = new MoneyFieldValidator();
					maxQuarterInvestValidator.setMessage("��������� ����� ���������� ������ ��������� ���� ��������� �������� ��� �������������� ��������. ����������, ������� ������ �����");
					maxQuarterInvestValidator.setEnabledExpression(new RhinoExpression("form.insuranceProductId=="+insuranceProduct.getId() + " && form.selectedPeriodId=="+insurancePeriod.getId()));
					BigDecimal maxQuarterInvestValue = freeQuarterInvest.getDecimal().multiply(BigDecimal.valueOf(insurancePeriod.getType().getMonths()))
														.divide(BigDecimal.valueOf(PortfolioType.QUARTERLY_INVEST.getMonthCount()), 2 , RoundingMode.UP);
					maxQuarterInvestValidator.setParameter(MoneyFieldValidator.PARAMETER_MAX_VALUE,maxQuarterInvestValue.toString());
					insuranceAmountValidatorList.add(maxQuarterInvestValidator);
				}
			}
		}

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("insuranceIncome");
		fieldBuilder.setDescription("���������� ���������� ��������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setParser(new BigDecimalParser());
		fieldBuilder.addValidators(insuranceIncomeValidatorList.toArray(new FieldValidator[insuranceIncomeValidatorList.size()]));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� �����");
		fieldBuilder.setName("insuranceSum");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� ���������: ����� ������");
		fieldBuilder.setName("insuranceAmount");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.addValidators(insuranceAmountValidatorList.toArray(new FieldValidator[insuranceAmountValidatorList.size()]));
		formBuilder.addField(fieldBuilder.build());

		maxComplexSumValidator.setBinding(fieldBuilder.getName(),fieldBuilder.getName());
		minComplexSumValidator.setBinding(fieldBuilder.getName(),fieldBuilder.getName());

		formBuilder.addFormValidators(maxComplexSumValidator, minComplexSumValidator, compareSumValidator);

		return formBuilder.build();
	}

	/**
	 * ��������� ���� ����� ������� ������������ ��������
	 * @param frm - �����
	 * @param product - ������� �� ��������
	 */
	public static void updateFormFields(EditFormBase frm, PortfolioProduct product)
	{
		BaseProduct accountProduct = product.getBaseProduct(DictionaryProductType.ACCOUNT);
		frm.setField("accountIncome",accountProduct.getIncome());
		frm.setField("accountAmount",accountProduct.getAmount().getDecimal());

		InsuranceBaseProduct insuranceProduct = (InsuranceBaseProduct)product.getBaseProduct(DictionaryProductType.INSURANCE);
		frm.setField("insuranceProductId",insuranceProduct.getDictionaryProductId());
		frm.setField("insuranceIncome",insuranceProduct.getIncome());
		frm.setField("insuranceAmount",insuranceProduct.getAmount().getDecimal());
		frm.setField("selectedPeriodId",insuranceProduct.getSelectedPeriodId());
		frm.setField("selectedTerm",insuranceProduct.getSelectedTermValue());
		frm.setField("insuranceSum", insuranceProduct.getInsuranceSum());
	}

}
