package com.rssl.phizic.web.pfp.addproduct;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.BigDecimalParser;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;
import com.rssl.phizic.business.dictionaries.pfp.products.*;
import com.rssl.phizic.business.dictionaries.pfp.products.complex.ComplexIMAInvestmentProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.complex.ComplexInvestmentProductBase;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.IMAProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.FundProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.AccountProduct;
import com.rssl.phizic.business.pfp.portfolio.PersonPortfolio;
import com.rssl.phizic.business.pfp.portfolio.PortfolioProduct;
import com.rssl.phizic.business.pfp.portfolio.product.BaseProduct;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.util.MoneyFunctions;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mihaylov
 * @ created 10.05.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * ����� ��� ���������� ���������� ���� ����������� �������������� ���������
 */
public class ComplexInvestmentProductFormBuilder
{

	/**
	 * ���������� ���������� ����� ��� ������������ �������� ������� + ���
	 * @param portfolio - �������� �������, � ������� ��������� �������
	 * @param productBase - ������� �� �����������, ������� ��������� � ��������
	 * @return
	 */
	public static Form getFundForm(PersonPortfolio portfolio, ProductBase productBase)
	{
		FormBuilder formBuilder = getFundFormBuilder(portfolio,productBase);

		ComplexInvestmentProductBase investmentProduct = (ComplexInvestmentProductBase) productBase;
		BigDecimal minSum = investmentProduct.getParameters(portfolio.getType()).getMinSum();
		BigDecimal maxSum = portfolio.getFreeAmount().getDecimal();

		MultiFieldSumValidator minProductSumValidator = new MultiFieldSumValidator();
		minProductSumValidator.setParameter(MultiFieldSumValidator.OPERATOR, MultiFieldSumValidator.GREATE_EQUAL);
		minProductSumValidator.setParameter(MultiFieldSumValidator.SUM_PARAMETER_NAME, minSum.toString());
		minProductSumValidator.setMessage("��������� ������������ �������� ������ ����������� ����������. ����������, ������� ����� �� " +
				MoneyFunctions.formatAmount(minSum)+ " ���.");
		minProductSumValidator.setBinding("fundAmount","fundAmount");
		minProductSumValidator.setBinding("accountAmount","accountAmount");
		formBuilder.addFormValidators(minProductSumValidator);

		MultiFieldSumValidator maxProductSumValidator = new MultiFieldSumValidator();
		maxProductSumValidator.setParameter(MultiFieldSumValidator.OPERATOR, MultiFieldSumValidator.LESS_EQUAL);
		maxProductSumValidator.setParameter(MultiFieldSumValidator.SUM_PARAMETER_NAME, maxSum.toString());
		maxProductSumValidator.setMessage("C���� ��������� ���� ��������� �������� � ��������. ����������, ������� ����� �� " +
				MoneyFunctions.formatAmount(maxSum)+ " ���.");
		maxProductSumValidator.setBinding("fundAmount","fundAmount");
		maxProductSumValidator.setBinding("accountAmount","accountAmount");
		formBuilder.addFormValidators(maxProductSumValidator);

		return formBuilder.build();
	}

	/**
	 * ���������� ���������� ����� ��� ������������ �������� ������� + ��� + ���
	 * @param portfolio - �������� �������, � ������� ��������� �������
	 * @param productBase - ������� �� �����������, ������� ��������� � ��������
	 * @return
	 */
	public static Form getFundImaForm(PersonPortfolio portfolio, ProductBase productBase)
	{
		FormBuilder formBuilder = getFundFormBuilder(portfolio,productBase);
		FieldBuilder fieldBuilder;

		ComplexIMAInvestmentProduct imaInvestmentProduct = (ComplexIMAInvestmentProduct) productBase;

		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
		List<FieldValidator> imaAmountValidatorList = new ArrayList<FieldValidator>();
		List<FieldValidator> imaIncomeValidatorList = new ArrayList<FieldValidator>();
		imaAmountValidatorList.add(requiredFieldValidator);
		for(IMAProduct imaProduct : imaInvestmentProduct.getImaProducts())
		{
			RhinoExpression enableExpression = new RhinoExpression("form.imaProductId=="+imaProduct.getId());
			MoneyFieldValidator minImaSumValidator = new MoneyFieldValidator();
			BigDecimal minSum = imaProduct.getParameters(portfolio.getType()).getMinSum();
			minImaSumValidator.setParameter(MoneyFieldValidator.PARAMETER_MIN_VALUE, minSum.toString());
			minImaSumValidator.setMessage("�� ������� ����� ������ ���������� ����������. ����������, ������� ����� �� " +
					MoneyFunctions.formatAmount(minSum)+ " ���.");
			minImaSumValidator.setEnabledExpression(enableExpression);
			imaAmountValidatorList.add(minImaSumValidator);

			if(imaProduct.getMinIncome() != null && imaProduct.getMaxIncome() != null)
			{
				NumericRangeValidator numericMinValidator = new NumericRangeValidator();
				numericMinValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE,imaProduct.getMinIncome().toString());
				numericMinValidator.setEnabledExpression(enableExpression);
				numericMinValidator.setMessage("�� ������� ���������� ������ �����������. ����������, ������� ������ ��������.");
				NumericRangeValidator numericMaxValidator = new NumericRangeValidator();
				numericMaxValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE,imaProduct.getMaxIncome().toString());
				numericMaxValidator.setEnabledExpression(enableExpression);
				numericMaxValidator.setMessage("�� ������� ���������� ������ ������������. ����������, ������� ������ ��������.");
				imaIncomeValidatorList.add(numericMinValidator);
				imaIncomeValidatorList.add(numericMaxValidator);
				RequiredFieldValidator rfValidator = new RequiredFieldValidator();
				rfValidator.setEnabledExpression(enableExpression);
				imaIncomeValidatorList.add(rfValidator);
			}
		}

		MultiFieldSumValidator minProductSumValidator = new MultiFieldSumValidator();
		minProductSumValidator.setParameter(MultiFieldSumValidator.OPERATOR, MultiFieldSumValidator.GREATE_EQUAL);
		BigDecimal minSum = imaInvestmentProduct.getParameters(portfolio.getType()).getMinSum();
		minProductSumValidator.setParameter(MultiFieldSumValidator.SUM_PARAMETER_NAME, minSum.toString());
		minProductSumValidator.setMessage("�� ������� ����� ������ ����������� ����� ��� ���������� ��������. ����������, ������� ����� �� " +
				MoneyFunctions.formatAmount(minSum)+ " ���.");
		minProductSumValidator.setBinding("fundAmount","fundAmount");
		minProductSumValidator.setBinding("imaAmount","imaAmount");
		minProductSumValidator.setBinding("accountAmount","accountAmount");
		formBuilder.addFormValidators(minProductSumValidator);

		MultiFieldSumValidator maxProductSumValidator = new MultiFieldSumValidator();
		maxProductSumValidator.setParameter(MultiFieldSumValidator.OPERATOR, MultiFieldSumValidator.LESS_EQUAL);
		BigDecimal maxSum = portfolio.getFreeAmount().getDecimal();
		maxProductSumValidator.setParameter(MultiFieldSumValidator.SUM_PARAMETER_NAME, maxSum.toString());
		maxProductSumValidator.setMessage("C���� ��������� ���� ��������� �������� � ��������. ����������, ������� ����� �� " +
				MoneyFunctions.formatAmount(maxSum)+ " ���.");
		maxProductSumValidator.setBinding("fundAmount","fundAmount");
		maxProductSumValidator.setBinding("imaAmount","imaAmount");
		maxProductSumValidator.setBinding("accountAmount","accountAmount");
		formBuilder.addFormValidators(maxProductSumValidator);

		CompareValidator compareValidator = new CompareValidator();
		compareValidator.setParameter(CompareValidator.OPERATOR,CompareValidator.LESS_EQUAL);
		compareValidator.setBinding(CompareValidator.FIELD_O1, "accountAmount");
		compareValidator.setBinding(CompareValidator.FIELD_O2, "imaAmount");
		compareValidator.setMessage("����� ������ ������ ���� �� ������ ����� ���������� � ���");
		formBuilder.addFormValidators(compareValidator);

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���: ������");
		fieldBuilder.setName("imaProductId");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredFieldValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("imaIncome");
		fieldBuilder.setDescription("���������� ���");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setParser(new BigDecimalParser());
		fieldBuilder.addValidators(imaIncomeValidatorList.toArray(new FieldValidator[imaIncomeValidatorList.size()]));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���: �����");
		fieldBuilder.setName("imaAmount");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.addValidators(imaAmountValidatorList.toArray(new FieldValidator[imaAmountValidatorList.size()]));
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	/**
	 * ���������� ���� ������ � ������ ������� ��� ����������� �������������� ���������.
	 * @param portfolio - �������� �������
	 * @param productBase - ������� �� �����������, ����������� � ��������.
	 * @return
	 */
	private static FormBuilder getFundFormBuilder(PersonPortfolio portfolio, ProductBase productBase)
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		ComplexInvestmentProductBase investmentProduct = (ComplexInvestmentProductBase) productBase;
		AccountProduct accountProduct = investmentProduct.getAccount();

		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();

		List<FieldValidator> fundAmountValidatorList = new ArrayList<FieldValidator>();
		List<FieldValidator> fundIncomeValidatorList = new ArrayList<FieldValidator>();
		fundAmountValidatorList.add(requiredFieldValidator);
		for(FundProduct fundProduct : investmentProduct.getFundProducts())
		{
			RhinoExpression enableExpression = new RhinoExpression("form.fundProductId=="+fundProduct.getId());
			MoneyFieldValidator fundSumValidator = new MoneyFieldValidator();
			BigDecimal minSum = fundProduct.getParameters(portfolio.getType()).getMinSum();
			fundSumValidator.setParameter(MoneyFieldValidator.PARAMETER_MIN_VALUE, minSum.toString());
			BigDecimal maxSum = portfolio.getFreeAmount().getDecimal().subtract(fundProduct.getSumFactor()).setScale(2, RoundingMode.UP);
			StringBuilder messageBuilder = new StringBuilder("�� ����������� ������� �����. ����������, ������� ����� �� ");
			messageBuilder.append(MoneyFunctions.formatAmount(minSum));
			messageBuilder.append(" ���.");
			if(maxSum.compareTo(minSum) > 0)
			{
				fundSumValidator.setParameter(MoneyFieldValidator.PARAMETER_MAX_VALUE, maxSum.toString());
				messageBuilder.append(" �� ");
				messageBuilder.append(MoneyFunctions.formatAmount(maxSum));
				messageBuilder.append(" ���.");
			}
			fundSumValidator.setEnabledExpression(enableExpression);
			fundSumValidator.setMessage(messageBuilder.toString());
			fundAmountValidatorList.add(fundSumValidator);

			if(fundProduct.getMinIncome() != null && fundProduct.getMaxIncome() != null)
			{
				NumericRangeValidator numericMinValidator = new NumericRangeValidator();
				numericMinValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE,fundProduct.getMinIncome().toString());
				numericMinValidator.setEnabledExpression(enableExpression);
				numericMinValidator.setMessage("�� ������� ���������� ������ �����������. ����������, ������� ������ ��������.");
				NumericRangeValidator numericMaxValidator = new NumericRangeValidator();
				numericMaxValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE,fundProduct.getMaxIncome().toString());
				numericMaxValidator.setEnabledExpression(enableExpression);
				numericMaxValidator.setMessage("�� ������� ���������� ������ ������������. ����������, ������� ������ ��������.");
				fundIncomeValidatorList.add(numericMinValidator);
				fundIncomeValidatorList.add(numericMaxValidator);
				RequiredFieldValidator rfValidator = new RequiredFieldValidator();
				rfValidator.setEnabledExpression(enableExpression);
				fundIncomeValidatorList.add(rfValidator);
			}
		}

		MoneyFieldValidator minAccountSumValidator = new MoneyFieldValidator();
		BigDecimal minSum = accountProduct.getParameters(portfolio.getType()).getMinSum();
		minAccountSumValidator.setParameter(MoneyFieldValidator.PARAMETER_MIN_VALUE, minSum.toString());
		minAccountSumValidator.setMessage("�� ������� ����� ������ ���������� ����������. ����������, ������� ����� �� " +
				MoneyFunctions.formatAmount(minSum)+ " ���.");

		CompareValidator compareValidator = new CompareValidator();
		compareValidator.setParameter(CompareValidator.OPERATOR,CompareValidator.LESS_EQUAL);
		compareValidator.setBinding(CompareValidator.FIELD_O1, "accountAmount");
		compareValidator.setBinding(CompareValidator.FIELD_O2, "fundAmount");
		compareValidator.setMessage("����� ������ ������ ���� �� ������ ����� ���������� � ���");
		formBuilder.addFormValidators(compareValidator);

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������ �������������� ����: ����");
		fieldBuilder.setName("fundProductId");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredFieldValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("fundIncome");
		fieldBuilder.setDescription("���������� ���");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setParser(new BigDecimalParser());
		fieldBuilder.addValidators(fundIncomeValidatorList.toArray(new FieldValidator[fundIncomeValidatorList.size()]));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������ �������������� ����: �����");
		fieldBuilder.setName("fundAmount");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.addValidators(fundAmountValidatorList.toArray(new FieldValidator[fundAmountValidatorList.size()]));
		formBuilder.addField(fieldBuilder.build());

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
		fieldBuilder.addValidators(new RequiredFieldValidator(),minAccountSumValidator);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder;
	}

	/**
	 * ��������� ���� ����� ������� ������������ ��������
	 * @param frm - �����
	 * @param product - ������� �� ��������
	 */
	public static void updateFormFields(EditFormBase frm, PortfolioProduct product)
	{
		BaseProduct accountProduct = product.getBaseProduct(DictionaryProductType.ACCOUNT);
		if(accountProduct != null)
		{
			frm.setField("accountIncome",accountProduct.getIncome());
			frm.setField("accountAmount",accountProduct.getAmount().getDecimal());
		}
		BaseProduct fundProduct = product.getBaseProduct(DictionaryProductType.FUND);
		if(fundProduct != null)
		{
			frm.setField("fundProductId",fundProduct.getDictionaryProductId());
			frm.setField("fundIncome",fundProduct.getIncome());
			frm.setField("fundAmount",fundProduct.getAmount().getDecimal());
		}
		BaseProduct imaProduct = product.getBaseProduct(DictionaryProductType.IMA);
		if(imaProduct != null)
		{
			frm.setField("imaProductId",imaProduct.getDictionaryProductId());
			frm.setField("imaIncome",imaProduct.getIncome());
			frm.setField("imaAmount",imaProduct.getAmount().getDecimal());
		}
	}

}
