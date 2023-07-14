package com.rssl.phizic.web.pfp.addproduct;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.BigDecimalParser;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.MoneyFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.NumericRangeValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.business.dictionaries.pfp.common.PortfolioType;
import com.rssl.phizic.business.dictionaries.pfp.products.ProductBase;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.SimpleProductBase;
import com.rssl.phizic.business.pfp.portfolio.PersonPortfolio;
import com.rssl.phizic.business.pfp.portfolio.PortfolioProduct;
import com.rssl.phizic.business.pfp.portfolio.product.BaseProduct;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.util.MoneyFunctions;

import java.math.BigDecimal;

/**
 * @author mihaylov
 * @ created 03.05.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��������� ����� ��� ������������ ���������� ����� ��� ���������� ������� ���������(���,���, �����) � ��������
 */
public class BaseProductFormBuilder
{

	/**
	 * ��������� ���������� �����
	 * @param portfolio - �������� �������
	 * @param productBase - ���������� �������
	 * @return ���������� �����
	 */
	public static Form getForm(PersonPortfolio portfolio, ProductBase productBase)
	{
		SimpleProductBase simpleProduct = (SimpleProductBase) productBase;
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
		String productType = productBase.getProductType().name().toLowerCase();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(productType + "Income");
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


		MoneyFieldValidator maxSumFieldValidator = new MoneyFieldValidator();
		BigDecimal freeAmount = portfolio.getFreeAmount().getDecimal();
		maxSumFieldValidator.setParameter(MoneyFieldValidator.PARAMETER_MAX_VALUE, freeAmount.toString());
		if(portfolio.getType() == PortfolioType.START_CAPITAL)
		{
			maxSumFieldValidator.setMessage("�������� ���� ����� ���������� ������ ���������  ��������� �������� ��� �������������� ��������. ����������, ������� ������ �����.");
		}
		else
		{
			maxSumFieldValidator.setMessage("��������� �����  ������ ��������� ���� ��������� �������� ��� �������������� ��������. ����������, ������� ������ �����.");
		}

		MoneyFieldValidator minSumFieldValidator = new MoneyFieldValidator();
		BigDecimal productAmount = simpleProduct.getParameters(portfolio.getType()).getMinSum();
		minSumFieldValidator.setParameter(MoneyFieldValidator.PARAMETER_MIN_VALUE, productAmount.toString());
		minSumFieldValidator.setMessage("�� ����������� ������� �����. ����������, ������� ����� �� " +
				MoneyFunctions.formatAmount(productAmount) +" ���. �� " + MoneyFunctions.formatAmount(freeAmount) +" ���.");

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�����");
		fieldBuilder.setName(productType + "Amount");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator(), minSumFieldValidator, maxSumFieldValidator);
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
		BaseProduct baseProduct = product.getBaseProductList().get(0);
		String productType = baseProduct.getProductType().name().toLowerCase();
		frm.setField(productType + "Amount",baseProduct.getAmount().getDecimal());
		frm.setField(productType + "Income",baseProduct.getIncome());
	}

}
