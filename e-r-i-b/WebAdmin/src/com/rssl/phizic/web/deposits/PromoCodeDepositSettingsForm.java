package com.rssl.phizic.web.deposits;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.IntType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.CompareValidator;
import com.rssl.common.forms.validators.NumericRangeValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.config.promoCodesDeposit.PromoCodesDepositConfig;
import com.rssl.phizic.config.promoCodesDeposit.PromoCodesMessage;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

import java.util.HashMap;
import java.util.Map;

/**
 * Форма настроек промо - кодов при открытии вклада
 *
 * @ author: Gololobov
 * @ created: 15.12.14
 * @ $Author$
 * @ $Revision$
 */
public class PromoCodeDepositSettingsForm extends EditPropertiesFormBase
{
	//Сообщения промо - кодов
	private Map<String, PromoCodesMessage> promoCodesMessagesMap = new HashMap<String, PromoCodesMessage>();

	private static final Form EDIT_FORM = createForm();

	public Form getForm()
	{
		return EDIT_FORM;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;
		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(PromoCodesDepositConfig.ACCESSIBLE_SYMBOLS);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Разрешенные символы");
		fieldBuilder.addValidators(requiredFieldValidator,
				new RegexpFieldValidator(".{1,150}", "Длина поля \"разрешенные символы\" должна быть не более 150"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(PromoCodesDepositConfig.MIN_COUNT_SYMBOLS);
		fieldBuilder.setType(IntType.INSTANCE.getName());
		fieldBuilder.setDescription("Минимальное количество символов в промо-коде");
		NumericRangeValidator numericRangeValidator = new NumericRangeValidator();
		numericRangeValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "1");
		numericRangeValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "99");
		fieldBuilder.addValidators(requiredFieldValidator, numericRangeValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(PromoCodesDepositConfig.MAX_COUNT_SYMBOLS);
		fieldBuilder.setType(IntType.INSTANCE.getName());
		fieldBuilder.setDescription("Максимальное количество символов в промо-коде");
		numericRangeValidator = new NumericRangeValidator();
		numericRangeValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "1");
		numericRangeValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "99");
		fieldBuilder.addValidators(requiredFieldValidator, numericRangeValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(PromoCodesDepositConfig.MAX_UNSUCCESSFULL_ITERATIONS);
		fieldBuilder.setType(IntType.INSTANCE.getName());
		fieldBuilder.setDescription("Лимит неудачных попыток");
		numericRangeValidator = new NumericRangeValidator();
		numericRangeValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "0");
		numericRangeValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "99");
		fieldBuilder.addValidators(numericRangeValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(PromoCodesDepositConfig.BLOCKING_TIMEMINUTES);
		fieldBuilder.setType(IntType.INSTANCE.getName());
		fieldBuilder.setDescription("Время блокировки");
		numericRangeValidator = new NumericRangeValidator();
		numericRangeValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "0");
		numericRangeValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "99999999");
		fieldBuilder.addValidators(numericRangeValidator);
		formBuilder.addField(fieldBuilder.build());

		CompareValidator compareValidator = new CompareValidator();
		compareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS);
		compareValidator.setBinding(CompareValidator.FIELD_O1, PromoCodesDepositConfig.MIN_COUNT_SYMBOLS);
		compareValidator.setBinding(CompareValidator.FIELD_O2, PromoCodesDepositConfig.MAX_COUNT_SYMBOLS);
		compareValidator.setMessage("Максимальное количество символов в промо - коде должно быть больше либо равно минимальному!");
		formBuilder.addFormValidators(compareValidator);

		return formBuilder.build();
	}

	public Map<String, PromoCodesMessage> getPromoCodesMessagesMap()
	{
		return promoCodesMessagesMap;
	}
}
