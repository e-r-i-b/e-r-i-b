package com.rssl.phizic.web.cards.limits;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.validators.ChooseValueValidator;
import com.rssl.common.forms.validators.NumericRangeValidator;
import com.rssl.common.forms.validators.RegexpMoneyFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * User: Moshenko
 * Date: 07.06.2011
 * Time: 12:56:32
 * Формбин для редактирования лимитов по картам
 */
public class EditCardLimitsForm extends EditFormBase {
    public static Form CARDLIMIT_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		NumericRangeValidator rangeValidator = new NumericRangeValidator();
		rangeValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "1");
		rangeValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "99999999");
		rangeValidator.setMessage("Укажите значение в поле: Сумма лимита.");

		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("decimal");
		fieldBuilder.setDescription("Сумма лимита");
        fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredFieldValidator,
				new RegexpMoneyFieldValidator("^\\d{1,8}$","Пожалуйста, укажите значение в поле \"" + fieldBuilder.getDescription() + "\". Например, 320."),
				rangeValidator);
		formBuilder.addField(fieldBuilder.build());

        //money currency
        fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("currency");
		fieldBuilder.setDescription("Валюта продукта");
        fieldBuilder.setType("string");
		fieldBuilder.addValidators(requiredFieldValidator,
				new ChooseValueValidator(ListUtil.fromArray(new String[]{"RUB", "USD", "EUR"})));
        formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

}
