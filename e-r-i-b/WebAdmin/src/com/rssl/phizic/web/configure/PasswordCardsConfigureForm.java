package com.rssl.phizic.web.configure;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.validators.NumericRangeValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;
import static com.rssl.phizic.security.config.Constants.*;
/**
 * User: IIvanova
 * Date: 14.02.2006
 * Time: 13:36:45
 */
public class PasswordCardsConfigureForm  extends EditPropertiesFormBase
{
	private static final Form FORM = createForm();

	@Override
	public Form getForm()
	{
		return FORM;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		Field[]      fields = new Field[3];
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(CARDS_COUNT);
		fieldBuilder.setDescription("Карты");

		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
		requiredFieldValidator.setMessage("Укажите значение в поле: " + fieldBuilder.getDescription());
		RegexpFieldValidator regexpFieldValidatorCardsCount = new RegexpFieldValidator("\\d{1,3}");
		regexpFieldValidatorCardsCount.setMessage("В настройке \\u0022Создание карт\\u0022 в поле \\u0022" + fieldBuilder.getDescription()+ " \\u0022 введите количество карт в диапазоне 1..100");
		NumericRangeValidator numericRangeValidatorCardsCount = new NumericRangeValidator();
		numericRangeValidatorCardsCount.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "1");
		numericRangeValidatorCardsCount.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "100");
		numericRangeValidatorCardsCount.setMessage("В настройке \\u0022Создание карт\\u0022 в поле \\u0022" + fieldBuilder.getDescription()+ " \\u0022 введите количество карт в диапазоне 1..100");

		fieldBuilder.setValidators(new FieldValidator[]{requiredFieldValidator, regexpFieldValidatorCardsCount, numericRangeValidatorCardsCount});


		fields[0] = fieldBuilder.build();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(CARDPASSWORDS_COUNT);
		fieldBuilder.setType("integer");
		fieldBuilder.setDescription("Ключи");

		RequiredFieldValidator requiredFieldValidatorKeysCount = new RequiredFieldValidator();
		requiredFieldValidatorKeysCount.setMessage("Укажите значение в поле: "+ fieldBuilder.getDescription());
		RegexpFieldValidator regexpFieldValidatorKeysCount = new RegexpFieldValidator("\\d{1,3}");
		regexpFieldValidatorKeysCount.setMessage("В настройке \\u0022Создание карт\\u0022 в поле \\u0022" + fieldBuilder.getDescription()+ " \\u0022 введите количество ключей в диапазоне 1..500");
		NumericRangeValidator numericRangeValidatorKeysCount = new NumericRangeValidator();
		numericRangeValidatorKeysCount.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "1");
		numericRangeValidatorKeysCount.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "500");
		numericRangeValidatorKeysCount.setMessage("В настройке \\u0022Создание карт\\u0022 в поле \\u0022" + fieldBuilder.getDescription()+ " \\u0022 введите количество ключей в диапазоне 1..500");

		fieldBuilder.setValidators(new FieldValidator[]{requiredFieldValidatorKeysCount, regexpFieldValidatorKeysCount, numericRangeValidatorKeysCount});

		fields[1] = fieldBuilder.build();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(CARDPASSWORD_LENGTH);
		fieldBuilder.setType("integer");
		fieldBuilder.setDescription("Длина ключа");

		RequiredFieldValidator requiredFieldValidatorCardPasswordLength = new RequiredFieldValidator();
		requiredFieldValidatorCardPasswordLength.setMessage("Укажите значение в поле: " + fieldBuilder.getDescription());
		RegexpFieldValidator regexpFieldValidatorCardPasswordLength = new RegexpFieldValidator("\\d{1,2}");
		regexpFieldValidatorCardPasswordLength.setMessage("В поле \\u0022" + fieldBuilder.getDescription() + "\\u0022  введите длину ключей в диапазоне 1..20");
		NumericRangeValidator numericRangeValidatorCardPasswordLength = new NumericRangeValidator();
		numericRangeValidatorCardPasswordLength.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "1");
		numericRangeValidatorCardPasswordLength.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "20");
		numericRangeValidatorCardPasswordLength.setMessage("В поле \\u0022" + fieldBuilder.getDescription() + "\\u0022  введите длину ключей в диапазоне 1..20");
		fieldBuilder.setValidators(new FieldValidator[]{requiredFieldValidatorCardPasswordLength, regexpFieldValidatorCardPasswordLength, numericRangeValidatorCardPasswordLength});

		//сardPasswordAllowedChars
		fields[2] = fieldBuilder.build();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(CARDPASSWORD_ALLOWED_CHARS);
		fieldBuilder.setDescription("Набор допустимых символов");

		RequiredFieldValidator requiredFieldValidatorCardPasswordAllowedChars = new RequiredFieldValidator();
		requiredFieldValidatorCardPasswordAllowedChars.setMessage("Укажите значение в поле: " + fieldBuilder.getDescription());
		fieldBuilder.setValidators(new FieldValidator[]{requiredFieldValidatorCardPasswordAllowedChars});

//		fields[3] = fieldBuilder.getField();
//
//		//confirmAttempts
//		fieldBuilder = new FieldBuilder();
//		fieldBuilder.setDictionaryName("confirmAttempts");
//		fieldBuilder.setType("integer");
//		fieldBuilder.setDescription("Количество разрешенных попыток для подтверждения");
//
//		RequiredFieldValidator requiredFieldValidatorConfirmAttempts = new RequiredFieldValidator();
//		requiredFieldValidator.setMessage("Укажите значение в поле: " + fieldBuilder.getDescription());
//		RegexpFieldValidator regexpFieldValidatorСonfirmAttempts = new RegexpFieldValidator("\\d{1,2}");
//		regexpFieldValidatorСonfirmAttempts.setMessage("Неверный формат данных в поле: " + fieldBuilder.getDescription() + ", введите числовое значение в диапазоне 1...10.");
//		NumericRangeValidator numericRangeValidatorСonfirmAttempts = new NumericRangeValidator();
//		numericRangeValidatorСonfirmAttempts.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "1");
//		numericRangeValidatorСonfirmAttempts.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "10");
//		numericRangeValidatorСonfirmAttempts.setMessage("Неверный формат данных в поле: " + fieldBuilder.getDescription() + ", введите числовое значение в диапазоне 1...10.");
//		fieldBuilder.setValidators(new FieldValidator[]{requiredFieldValidatorConfirmAttempts, regexpFieldValidatorCardPasswordLength, numericRangeValidatorCardPasswordLength});

		formBuilder.setFields(fields);

		return formBuilder.build();
	}
}
