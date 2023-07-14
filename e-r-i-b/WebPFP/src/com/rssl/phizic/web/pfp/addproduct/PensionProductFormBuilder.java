package com.rssl.phizic.web.pfp.addproduct;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.BigDecimalParser;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.MoneyFieldValidator;
import com.rssl.common.forms.validators.NumericRangeValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;
import com.rssl.phizic.business.dictionaries.pfp.common.PortfolioType;
import com.rssl.phizic.business.dictionaries.pfp.products.ProductBase;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.pension.PensionProduct;
import com.rssl.phizic.business.pfp.PersonalFinanceProfile;
import com.rssl.phizic.business.pfp.portfolio.PersonPortfolio;
import com.rssl.phizic.business.pfp.portfolio.PortfolioProduct;
import com.rssl.phizic.business.pfp.portfolio.product.PensionBaseProduct;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.util.MoneyFunctions;

/**
 * @author mihaylov
 * @ created 04.09.13
 * @ $Author$
 * @ $Revision$
 */
public class PensionProductFormBuilder
{

	/**
	 * ��������� ���������� �����
	 * @param profile - ������������ �������
	 * @param productBase - ���������� �������
	 * @return ���������� �����
	 */
	public static Form getForm(PersonalFinanceProfile profile, ProductBase productBase)
	{
		PensionProduct pensionProduct = (PensionProduct) productBase;
		FormBuilder formBuilder = new FormBuilder();

		PersonPortfolio startCapitalPortfolio = profile.getPortfolioByType(PortfolioType.START_CAPITAL);
		PersonPortfolio quarterlyInvestPortfolio = profile.getPortfolioByType(PortfolioType.QUARTERLY_INVEST);

		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();

		StringBuilder startAmountMsg = new StringBuilder();
		startAmountMsg.append("�� ������� ������������ ����� ��� ���������� ������. ����� ������ ���� �� ");
		startAmountMsg.append(MoneyFunctions.formatAmount(pensionProduct.getEntryFee()));
		startAmountMsg.append(" ���");
		startAmountMsg.append(" �� ");
		startAmountMsg.append(MoneyFunctions.getFormatAmount(startCapitalPortfolio.getFreeAmount()));

		StringBuilder quarterlyAmountMsg = new StringBuilder();
		quarterlyAmountMsg.append("�� ������� ������������ ����� ��� ��������������� ������. ����� ������ ���� �� ");
		quarterlyAmountMsg.append(MoneyFunctions.formatAmount(pensionProduct.getQuarterlyFee()));
		quarterlyAmountMsg.append(" ���");
		quarterlyAmountMsg.append(" �� ");
		quarterlyAmountMsg.append(MoneyFunctions.getFormatAmount(quarterlyInvestPortfolio.getFreeAmount()));

		MoneyFieldValidator startAmountValidator = new MoneyFieldValidator();
		startAmountValidator.setParameter(MoneyFieldValidator.PARAMETER_MIN_VALUE,pensionProduct.getEntryFee().toString());
		startAmountValidator.setParameter(MoneyFieldValidator.PARAMETER_MAX_VALUE,startCapitalPortfolio.getFreeAmount().getDecimal().toString());
		startAmountValidator.setMessage(startAmountMsg.toString());

		MoneyFieldValidator quarterlyAmountValidator = new MoneyFieldValidator();
		quarterlyAmountValidator.setParameter(MoneyFieldValidator.PARAMETER_MIN_VALUE, pensionProduct.getQuarterlyFee().toString());
		quarterlyAmountValidator.setParameter(MoneyFieldValidator.PARAMETER_MAX_VALUE, quarterlyInvestPortfolio.getFreeAmount().getDecimal().toString());
		quarterlyAmountValidator.setMessage(quarterlyAmountMsg.toString());

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.setName("pensionStartAmount");
		fieldBuilder.setDescription("��������� �����");
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredFieldValidator, startAmountValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.setName("pensionQuarterlyAmount");
		fieldBuilder.setDescription("�������������� �����");
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredFieldValidator, quarterlyAmountValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.setName("pensionPeriod");
		fieldBuilder.setDescription("����");
		fieldBuilder.clearValidators();
		NumericRangeValidator periodNumericValidator = new NumericRangeValidator();
		periodNumericValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE,pensionProduct.getMinPeriod().toString());
		periodNumericValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE,pensionProduct.getMaxPeriod().toString());
		periodNumericValidator.setMessage("������� ������ �� " + pensionProduct.getMinPeriod() + " ��������� �� " + pensionProduct.getMaxPeriod() + " ���������.");
		fieldBuilder.addValidators(requiredFieldValidator,periodNumericValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("pensionIncome");
		fieldBuilder.setDescription("����������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setParser(new BigDecimalParser());
		fieldBuilder.addValidators(new RegexpFieldValidator("^\\d+((\\.|,)\\d{0,2})?$", "����������, ������� ����������, ��������� �����, ����� � �������. ��������, 0.31."));
		if(productBase.getMinIncome() != null && productBase.getMaxIncome() != null)
		{
			NumericRangeValidator incomeNumericValidator = new NumericRangeValidator();
			incomeNumericValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, productBase.getMinIncome().toString());
			incomeNumericValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, productBase.getMaxIncome().toString());
			incomeNumericValidator.setMessage("������� ���������� �� " + productBase.getMinIncome().toString() +" ��������� �� <������������ ��������> ���������.");

			fieldBuilder.addValidators(requiredFieldValidator, incomeNumericValidator);
		}
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	public static void updateFormFields(EditFormBase frm, PortfolioProduct product)
	{
		PensionBaseProduct pensionBaseProduct = (PensionBaseProduct)product.getBaseProduct(DictionaryProductType.PENSION);
		frm.setField("pensionStartAmount",pensionBaseProduct.getAmount().getDecimal());
		frm.setField("pensionQuarterlyAmount",pensionBaseProduct.getQurterlyInvest());
		frm.setField("pensionPeriod",pensionBaseProduct.getSelectedPeriod());
		frm.setField("pensionIncome",pensionBaseProduct.getIncome());
	}

}
