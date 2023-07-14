package com.rssl.phizic.web.configure;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.IntType;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.LengthFieldValidator;
import com.rssl.common.forms.validators.NumericRangeValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

import java.math.BigInteger;

import static com.rssl.phizic.config.mobile.MobileApiConfig.*;

/**
 * Настройки mAPI
 * User: Moshenko
 * Date: 17.08.2012
 * Time: 13:01:49
 */
public class MobileAPIConfigureForm extends EditPropertiesFormBase
{
	private static final BigInteger PROP_VAL_MAX_LEN = BigInteger.valueOf(500L);
	private static final Form FORM = createForm();

	public Form getForm()
	{
		return FORM;
	}

	@SuppressWarnings({"OverlyLongMethod", "ReuseOfLocalVariable", "TooBroadScope"})
	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;
		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();

		//versions
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(API_VERSIONS);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		String desc = "Поддерживаемые версии протокола";
		fieldBuilder.setDescription(desc);
		RegexpFieldValidator regexpFieldValidator = new RegexpFieldValidator("^\\d{1,2}\\.\\d{2}(,\\d{1,2}\\.\\d{2})*$",
				"Номер версии должен соответствовать виду ‘мажорная версия (цифры от 1 до 99) . минорная версия (цифры от 01 до 99)’, разделитель между номерами версий ‘,’. Пробелы не допускаются. Например: 3.00,4.00,5.10");
		fieldBuilder.addValidators(requiredFieldValidator, regexpFieldValidator);
		formBuilder.addField(fieldBuilder.build());

		//invalidVersionText
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(API_INVALID_VERSION_TEXT);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		desc = "Сообщение о несовместимости протокола";
		fieldBuilder.setDescription(desc);
		LengthFieldValidator lengthValidator = new LengthFieldValidator(PROP_VAL_MAX_LEN);
		lengthValidator.setMessage(desc + " не должно превышать " + PROP_VAL_MAX_LEN + " символов");
		fieldBuilder.addValidators(requiredFieldValidator, lengthValidator);
		formBuilder.addField(fieldBuilder.build());

		//jailbreakEnabled
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(JAILBREAK_ENABLED);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("Признак включения работы iOS с jailbreak");
		fieldBuilder.addValidators(requiredFieldValidator);
		formBuilder.addField(fieldBuilder.build());

		//jailbreakEnabledText
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(JAILBREAK_ENABLED_TEXT);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		desc = "Текст, отображаемый клиенту, если РАЗРЕШЕНА работа с устройств со взломанной iOS";
		fieldBuilder.setDescription(desc);
		lengthValidator = new LengthFieldValidator(PROP_VAL_MAX_LEN);
		lengthValidator.setMessage(desc + " должен быть не более " + PROP_VAL_MAX_LEN + " символов");
		fieldBuilder.addValidators(requiredFieldValidator, lengthValidator);
		formBuilder.addField(fieldBuilder.build());

		//jailbreakDisabledText
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(JAILBREAK_DISABLED_TEXT);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		desc = "Текст, отображаемый клиенту, если ЗАПРЕЩЕНА работа с устройств со взломанной iOS";
		fieldBuilder.setDescription(desc);
		lengthValidator = new LengthFieldValidator(PROP_VAL_MAX_LEN);
		lengthValidator.setMessage(desc + " должен быть не более " + PROP_VAL_MAX_LEN + " символов");
		fieldBuilder.addValidators(requiredFieldValidator, lengthValidator);
		formBuilder.addField(fieldBuilder.build());

        //search_period
        fieldBuilder = new FieldBuilder();
        fieldBuilder.setName(MAX_SEARCH_PERIOD_IN_DAYS);
        fieldBuilder.setType(IntType.INSTANCE.getName());
        desc = "Период поиска по умолчанию";
        fieldBuilder.setDescription(desc);
        NumericRangeValidator numericRangeValidator = new NumericRangeValidator();
        numericRangeValidator.setMessage("Значение периода поиска должно быть в диапазоне 1 - 365");
        numericRangeValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "1");
        numericRangeValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "365");
        fieldBuilder.addValidators(requiredFieldValidator, numericRangeValidator);
		formBuilder.addField(fieldBuilder.build());

		//mobilePhoneNumberSumDefault
        fieldBuilder = new FieldBuilder();
        fieldBuilder.setName(MOBILE_PHONENUMBER_SUM_DEFAULT);
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
        fieldBuilder.setDescription("Сумма по умолчанию");
        NumericRangeValidator numericRangePhoneNumSumValidator = new NumericRangeValidator();
		numericRangePhoneNumSumValidator.setMessage("Значение суммы по умолчанию должно быть в диапазоне 0 - 9 999 999.99");
		numericRangePhoneNumSumValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "0");
		numericRangePhoneNumSumValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "9999999.99");
        fieldBuilder.addValidators(numericRangePhoneNumSumValidator);
		formBuilder.addField(fieldBuilder.build());

		//mobilePhoneNumberCount
        fieldBuilder = new FieldBuilder();
        fieldBuilder.setName(MOBILE_PHONENUMBER_COUNT);
        fieldBuilder.setType(IntType.INSTANCE.getName());
        fieldBuilder.setDescription("Размер списка частооплачиваемых номеров");
		NumericRangeValidator numericRangePhoneNumCountValidator = new NumericRangeValidator();
		numericRangePhoneNumCountValidator.setMessage("Значение размера списка частооплачиваемых номеров должно быть в диапазоне 0 - 999");
		numericRangePhoneNumCountValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "0");
		numericRangePhoneNumCountValidator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, "999");
		fieldBuilder.addValidators(requiredFieldValidator, numericRangePhoneNumCountValidator);
		formBuilder.addField(fieldBuilder.build());

		//fundSenderCountDailyLimit
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(FUND_SENDERS_COUNT_DAILY_LIMIT);
		fieldBuilder.setType(IntType.INSTANCE.getName());
		fieldBuilder.setDescription("Суточный лимит на количество получателей денег для запросов на сбор средств");
		fieldBuilder.addValidators(requiredFieldValidator, new RegexpFieldValidator("\\d*", "Количество получателей денег должно содержать только цифры"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Время жизни запроса на сбор средств");
		fieldBuilder.setName(LIFETIME_REQUEST_ON_FUNDRAISER);
		fieldBuilder.setType(IntType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredFieldValidator, new RegexpFieldValidator("^[1-9]\\d{0,2}", "Время жизни должно быть положительным числом"));
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
