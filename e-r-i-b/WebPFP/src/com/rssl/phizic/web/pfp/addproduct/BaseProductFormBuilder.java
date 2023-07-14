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
 * Отдельный класс для формирования логической формы для добавления простых продуктов(ОМС,ПИФ, вклад) в портфель
 */
public class BaseProductFormBuilder
{

	/**
	 * Формируем логическую форму
	 * @param portfolio - портфель клиента
	 * @param productBase - добавляемы продукт
	 * @return логическая форма
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
		fieldBuilder.setDescription("Доходность");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setParser(new BigDecimalParser());
		fieldBuilder.addValidators(new RegexpFieldValidator("^\\d+((\\.|,)\\d{0,2})?$", "Пожалуйста, укажите доходность, используя цифры, точку и запятую. Например, 0.31."));
		if(productBase.getMinIncome() != null && productBase.getMaxIncome() != null)
		{
			NumericRangeValidator numericMinValidator = new NumericRangeValidator();
			numericMinValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE,productBase.getMinIncome().toString());
			numericMinValidator.setMessage("Вы указали доходность меньше минимальной. Пожалуйста, укажите другое значение.");

			NumericRangeValidator numericMaxValidator = new NumericRangeValidator();
			numericMaxValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE,productBase.getMaxIncome().toString());
			numericMaxValidator.setMessage("Вы указали доходность больше максимальной. Пожалуйста, укажите другое значение.");

			fieldBuilder.addValidators(requiredFieldValidator,numericMinValidator,numericMaxValidator);
		}
		formBuilder.addField(fieldBuilder.build());


		MoneyFieldValidator maxSumFieldValidator = new MoneyFieldValidator();
		BigDecimal freeAmount = portfolio.getFreeAmount().getDecimal();
		maxSumFieldValidator.setParameter(MoneyFieldValidator.PARAMETER_MAX_VALUE, freeAmount.toString());
		if(portfolio.getType() == PortfolioType.START_CAPITAL)
		{
			maxSumFieldValidator.setMessage("Введённая Вами сумма стартового взноса превышает  доступные средства для первоначальных вложений. Пожалуйста, введите другую сумму.");
		}
		else
		{
			maxSumFieldValidator.setMessage("Указанная сумма  взноса превышает Ваши свободные средства для ежеквартальных вложений. Пожалуйста, введите другую сумму.");
		}

		MoneyFieldValidator minSumFieldValidator = new MoneyFieldValidator();
		BigDecimal productAmount = simpleProduct.getParameters(portfolio.getType()).getMinSum();
		minSumFieldValidator.setParameter(MoneyFieldValidator.PARAMETER_MIN_VALUE, productAmount.toString());
		minSumFieldValidator.setMessage("Вы неправильно указали сумму. Пожалуйста, введите сумму от " +
				MoneyFunctions.formatAmount(productAmount) +" руб. до " + MoneyFunctions.formatAmount(freeAmount) +" руб.");

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Сумма");
		fieldBuilder.setName(productType + "Amount");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator(), minSumFieldValidator, maxSumFieldValidator);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	/**
	 * Обновляем поля формы данными сохраненного продукта
	 * @param frm - форма
	 * @param product - продукт из портфеля
	 */
	public static void updateFormFields(EditFormBase frm, PortfolioProduct product)
	{
		BaseProduct baseProduct = product.getBaseProductList().get(0);
		String productType = baseProduct.getProductType().name().toLowerCase();
		frm.setField(productType + "Amount",baseProduct.getAmount().getDecimal());
		frm.setField(productType + "Income",baseProduct.getIncome());
	}

}
