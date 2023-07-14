package com.rssl.phizic.web.configure;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.validators.NumericRangeValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.config.mobile.MobileApiConfig;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

import static com.rssl.common.forms.validators.passwords.SmsPasswordConfig.*;

/**
 * @author eMakarov
 * @ created 17.06.2008
 * @ $Author$
 * @ $Revision$
 */
public class SmsPasswordConfigureForm extends EditPropertiesFormBase
{
	public static final Form SMS_FORM = createForm();

	@Override
	public Form getForm()
	{
		return SMS_FORM;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		Field[]      fields = new Field[5];
		FieldBuilder fieldBuilder;

		// smsPasswordLength
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(SMS_PASSWORD_LENGTH);
		fieldBuilder.setType("integer");
		fieldBuilder.setDescription("Длина SMS-пароля");

		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
		requiredFieldValidator.setMessage("Укажите значение в поле: " + fieldBuilder.getDescription());
		RegexpFieldValidator regexpFieldValidatorSmsPasswordLength = new RegexpFieldValidator("\\d{1,2}");
		regexpFieldValidatorSmsPasswordLength.setMessage("Неверный формат данных в поле: "+ fieldBuilder.getDescription()+" введите числовое значение в диапазоне 1..99.");
		NumericRangeValidator numericRangeValidatorSmsPasswordLength = new NumericRangeValidator();
		numericRangeValidatorSmsPasswordLength.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "1");
		numericRangeValidatorSmsPasswordLength.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "99");
		numericRangeValidatorSmsPasswordLength.setMessage("Неверный формат данных в поле: "+ fieldBuilder.getDescription()+" , введите числовое значение в диапазоне 1..99.");

		fieldBuilder.addValidators(new FieldValidator[]{requiredFieldValidator, regexpFieldValidatorSmsPasswordLength, numericRangeValidatorSmsPasswordLength});

		fields[0] = fieldBuilder.build();

		// smsPasswordConfirmAttempts
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(SMS_PASSWORD_CONFIRMATTEMTS);
		fieldBuilder.setType("integer");
		fieldBuilder.setDescription("Количество попыток ввода SMS-пароля Клиентом");

		RequiredFieldValidator requiredFieldValidatorKeysCount = new RequiredFieldValidator();
		requiredFieldValidatorKeysCount.setMessage("Укажите значение в поле: "+ fieldBuilder.getDescription());
		RegexpFieldValidator regexpFieldValidatorKeysCount = new RegexpFieldValidator("\\d{1,2}");
		regexpFieldValidatorKeysCount.setMessage("Неверный формат данных в поле: "+fieldBuilder.getDescription()+", введите числовое значение в диапазоне 1..12.");
		NumericRangeValidator numericRangeValidatorKeysCount = new NumericRangeValidator();
		numericRangeValidatorKeysCount.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "1");
		numericRangeValidatorKeysCount.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "12");
		numericRangeValidatorKeysCount.setMessage("Неверный формат данных в поле: "+fieldBuilder.getDescription()+", введите числовое значение в диапазоне 1..12.");

		fieldBuilder.addValidators(new FieldValidator[]{requiredFieldValidatorKeysCount, regexpFieldValidatorKeysCount, numericRangeValidatorKeysCount});

		fields[1] = fieldBuilder.build();

		// smsPasswordLifeTime
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(SMS_PASSWORD_LIFETIME);
		fieldBuilder.setType("integer");
		fieldBuilder.setDescription("Время действия SMS-пароля");

		RequiredFieldValidator requiredFieldValidatorSmsPasswordLifeTime = new RequiredFieldValidator();
		requiredFieldValidatorSmsPasswordLifeTime.setMessage("Укажите значение в поле: " + fieldBuilder.getDescription());
		RegexpFieldValidator regexpFieldValidatorSmsPasswordLifeTime = new RegexpFieldValidator("\\d{1,5}");
		regexpFieldValidatorSmsPasswordLifeTime.setMessage("Неверный формат данных в поле: " + fieldBuilder.getDescription() + ", введите числовое значение в диапазоне 1..99999.");
		fieldBuilder.addValidators(new FieldValidator[]{requiredFieldValidatorSmsPasswordLifeTime, regexpFieldValidatorSmsPasswordLifeTime});

		// smsPasswordAllowedChars
		fields[2] = fieldBuilder.build();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(SMS_PASSWORD_ALLOWEDCHARS);
		fieldBuilder.setDescription("Набор используемых символов");

		RequiredFieldValidator requiredFieldValidatorSmsPasswordAllowedChars = new RequiredFieldValidator();
		requiredFieldValidatorSmsPasswordAllowedChars.setMessage("Укажите значение в поле: " + fieldBuilder.getDescription());
		fieldBuilder.addValidators(new FieldValidator[]{requiredFieldValidatorSmsPasswordAllowedChars});

		fields[3] = fieldBuilder.build();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(MobileApiConfig.AUTO_SWITCH_PUSH_KEY);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fields[4] = fieldBuilder.build();

		formBuilder.setFields(fields);

		return formBuilder.build();
	}
}
