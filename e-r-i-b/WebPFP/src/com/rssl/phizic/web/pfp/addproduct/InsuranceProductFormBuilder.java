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
import com.rssl.phizic.business.dictionaries.pfp.products.insurance.InsuranceDatePeriod;
import com.rssl.phizic.business.dictionaries.pfp.products.insurance.InsuranceProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.ProductBase;
import com.rssl.phizic.business.pfp.PersonalFinanceProfile;
import com.rssl.phizic.business.pfp.portfolio.PersonPortfolio;
import com.rssl.phizic.business.pfp.portfolio.PortfolioProduct;
import com.rssl.phizic.business.dictionaries.pfp.common.PortfolioType;
import com.rssl.phizic.business.pfp.portfolio.PortfolioState;
import com.rssl.phizic.business.pfp.portfolio.product.InsuranceBaseProduct;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.util.MoneyFunctions;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mihaylov
 * @ created 04.05.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * ����� ��� ������ � ������ �������������� ���������� ��������
 */
public class InsuranceProductFormBuilder
{

	/**
	 * ��������� ���������� �����
	 * @param portfolio - �������� �������
	 * @param productBase - ���������� �������
	 * @return
	 */
	public static Form getForm(PersonalFinanceProfile profile, PersonPortfolio portfolio, ProductBase productBase)
	{
		InsuranceProduct insuranceProduct = (InsuranceProduct) productBase;

		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		MoneyFieldValidator maxSumFieldValidator = new MoneyFieldValidator();
		BigDecimal freeAmount = portfolio.getFreeAmount().getDecimal();
		maxSumFieldValidator.setParameter(MoneyFieldValidator.PARAMETER_MAX_VALUE, freeAmount.toString());
		maxSumFieldValidator.setMessage("�������� ���� ����� ���������� ������ ��������� ��������� �������� ��� �������������� ��������.");

		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();

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

		List<FieldValidator> fieldValidatorList = new ArrayList<FieldValidator>();
		fieldValidatorList.add(requiredFieldValidator);

		PersonPortfolio quarterlyInvest = profile.getPortfolioByType(PortfolioType.QUARTERLY_INVEST);
		quarterlyInvest.setPortfolioState(PortfolioState.EDIT);
		Money freeQuarterInvest = quarterlyInvest.getFreeAmount();

		for(InsuranceDatePeriod insurancePeriod : insuranceProduct.getPeriods())
		{
			if(insurancePeriod.getMaxSum() != null)
			{
				MoneyFieldValidator moneyFieldValidator = new MoneyFieldValidator();
				moneyFieldValidator.setEnabledExpression(new RhinoExpression("form.selectedPeriodId=="+insurancePeriod.getId()));
				StringBuilder messageBuilder = new StringBuilder("�� ������� ������������ ����� ������ ��� �������� �������������. ����� ������ ����");
				moneyFieldValidator.setParameter(MoneyFieldValidator.PARAMETER_MAX_VALUE, insurancePeriod.getMaxSum().toString());
				messageBuilder.append(" �� ").append(MoneyFunctions.formatAmount(insurancePeriod.getMaxSum())).append(" ���");
				moneyFieldValidator.setMessage(messageBuilder.toString());
				fieldValidatorList.add(moneyFieldValidator);
			}

			if(insurancePeriod.getMinSum() != null)
			{
				MoneyFieldValidator moneyFieldValidator = new MoneyFieldValidator();
				moneyFieldValidator.setEnabledExpression(new RhinoExpression("form.selectedPeriodId=="+insurancePeriod.getId()));
				moneyFieldValidator.setParameter(MoneyFieldValidator.PARAMETER_MIN_VALUE,insurancePeriod.getMinSum().toString());
				moneyFieldValidator.setMessage("�������� ���� ����� ���������� ������ ������ ����������� �����, ��������������� ���������� �����������, ������� �� �������.");
				fieldValidatorList.add(moneyFieldValidator);
			}

			if(insurancePeriod.getType().getMonths() != null)
			{
				MoneyFieldValidator maxQuarterInvestValidator = new MoneyFieldValidator();
				maxQuarterInvestValidator.setMessage("��������� ����� ���������� ������ ��������� ���� ��������� �������� ��� �������������� ��������. ����������, ������� ������ �����");
				maxQuarterInvestValidator.setEnabledExpression(new RhinoExpression("form.selectedPeriodId=="+insurancePeriod.getId()));
				BigDecimal maxQuarterInvestValue = freeQuarterInvest.getDecimal().multiply(BigDecimal.valueOf(insurancePeriod.getType().getMonths()))
													.divide(BigDecimal.valueOf(PortfolioType.QUARTERLY_INVEST.getMonthCount()), 2 ,RoundingMode.UP);
				maxQuarterInvestValidator.setParameter(MoneyFieldValidator.PARAMETER_MAX_VALUE,maxQuarterInvestValue.toString());
				fieldValidatorList.add(maxQuarterInvestValidator);
			}
		}			

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("insuranceIncome");
		fieldBuilder.setDescription("����������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setParser(new BigDecimalParser());
		fieldBuilder.addValidators(new RegexpFieldValidator("^\\d+((\\.|,)\\d{0,2})?$", "����������, ������� ����������, ��������� �����, ����� � �������. ��������, 0.31."));
		if(productBase.getMinIncome() != null && productBase.getMaxIncome() != null)
		{
			NumericRangeValidator numericMinValidator = new NumericRangeValidator();
			numericMinValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE,productBase.getMinIncome().toString());
			numericMinValidator.setMessage("�� ������� ���������� ������ �����������. ����������, ������� ������ ��������.");
			NumericRangeValidator numericMaxValidator = new NumericRangeValidator();
			numericMaxValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE,productBase.getMaxIncome().toString());
			numericMaxValidator.setMessage("�� ������� ���������� ������ ������������. ����������, ������� ������ ��������.");
			fieldBuilder.addValidators(requiredFieldValidator,numericMinValidator,numericMaxValidator);
		}
		formBuilder.addField(fieldBuilder.build());
		fieldValidatorList.add(maxSumFieldValidator);

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�����");
		fieldBuilder.setName("insuranceAmount");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.addValidators(fieldValidatorList.toArray(new FieldValidator[fieldValidatorList.size()]));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� �����");
		fieldBuilder.setName("insuranceSum");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	/**
	 * ��������� ���� ����� ������� ������������ ��������
	 * @param frm - �����
	 * @param product - ������� �� ��������
	 */
	public static void updateFormFields(EditFormBase frm, PortfolioProduct product)
	{
		InsuranceBaseProduct insuranceProduct = (InsuranceBaseProduct)product.getBaseProduct(DictionaryProductType.INSURANCE);
		frm.setField("insuranceAmount",insuranceProduct.getAmount().getDecimal());
		frm.setField("selectedPeriodId",insuranceProduct.getSelectedPeriodId());
		frm.setField("selectedTerm",insuranceProduct.getSelectedTermValue());
		frm.setField("insuranceIncome",insuranceProduct.getIncome());
		frm.setField("insuranceSum", insuranceProduct.getInsuranceSum());
	}

}
