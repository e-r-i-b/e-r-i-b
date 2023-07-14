package com.rssl.phizic.web.persons.formBuilders;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.persons.forms.DocumentSeriesAndNumberValidator;
import com.rssl.phizic.business.persons.forms.SmsFormatValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 05.12.2006
 * @ $Author$
 * @ $Revision$
 */

public class PartiallyPersonFormBuilder extends PersonFormBuilderBase
{
	public Form buildForm()
	{
		List<Field> fields = new ArrayList<Field>();

        fields.add(buildField(CLIENT_ID_FIELD, CLIENT_ID_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(SUR_NAME_FIELD, SUR_NAME_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(FIRST_NAME_FIELD, FIRST_NAME_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(PATR_NAME_FIELD, PATR_NAME_FIELD_DESCRIPTION, STRING_TYPE_NAME));
		fields.add(buildField(RESIDENT_FIELD, RESIDENT_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(GENDER_FIELD, GENDER_FIELD_DESCRIPTION, STRING_TYPE_NAME));
		fields.add(buildField(RESIDENT_FIELD, RESIDENT_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(BIRTH_DAY_FIELD, BIRTH_DAY_FIELD_DESCRIPTION, DATE_TYPE_NAME));
        fields.add(buildField(BIRTH_PLACE_FIELD, BIRTH_PLACE_FIELD_DESCRIPTION, STRING_TYPE_NAME));

		fields.add(buildField(DOCUMENT_ID_FIELD, DOCUMENT_ID_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(DOCUMENT_TYPE_FIELD, DOCUMENT_TYPE_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(DOCUMENT_NAME_FIELD, DOCUMENT_NAME_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(DOCUMENT_SERIES_FIELD, DOCUMENT_SERIES_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(DOCUMENT_NUMBER_FIELD, DOCUMENT_NUMBER_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(DOCUMENT_ISSUE_BY_CODE_FIELD, DOCUMENT_ISSUE_BY_CODE_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(DOCUMENT_ISSUE_DATE_FIELD, DOCUMENT_ISSUE_DATE_FIELD_DESCRIPTION, DATE_TYPE_NAME,"form.documentType != 'PASSPORT_WAY'"));
        fields.add(buildField(DOCUMENT_ISSUE_BY_FIELD, DOCUMENT_ISSUE_BY_FIELD_DESCRIPTION, STRING_TYPE_NAME));

		fields.add(buildField(DOCUMENT_PROVE_ID_FIELD, DOCUMENT_ID_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(DOCUMENT_PROVE_TYPE_FIELD, DOCUMENT_TYPE_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(DOCUMENT_PROVE_NAME_FIELD, DOCUMENT_NAME_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(DOCUMENT_PROVE_SERIES_FIELD, DOCUMENT_SERIES_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(DOCUMENT_PROVE_NUMBER_FIELD, DOCUMENT_NUMBER_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(DOCUMENT_PROVE_ISSUE_DATE_FIELD, DOCUMENT_ISSUE_DATE_FIELD_DESCRIPTION, DATE_TYPE_NAME));
        fields.add(buildField(DOCUMENT_PROVE_ISSUE_BY_FIELD, DOCUMENT_PROVE_ISSUE_BY_FIELD_DESCRIPTION, STRING_TYPE_NAME));		

		fields.add(buildField(CITIZEN_FIELD, CITIZEN_FIELD_DESCRIPTION, STRING_TYPE_NAME));
		fields.add(buildField(MIGRATORY_CARD_NUMBER_FIELD, MIGRATORY_CARD_NUMBER_FIELD_DESCRIPTION, STRING_TYPE_NAME));
		fields.add(buildField(MIGRATORY_CARD_FROM_DATE_FIELD, MIGRATORY_CARD_FROM_DATE_FIELD_DESCRIPTION, DATE_TYPE_NAME));
		fields.add(buildField(MIGRATORY_CARD_TIME_UP_DATE_FIELD, MIGRATORY_CARD_TIME_UP_DATE_FIELD_DESCRIPTION, DATE_TYPE_NAME));

		fields.add(buildField(PENSION_CERTIFICATE_NUMBER_FIELD, PENSION_CERTIFICATE_NUMBER_FIELD_DESCRIPTION, STRING_TYPE_NAME));

        fields.add(buildField(INN_FIELD, INN_FIELD_DESCRIPTION, STRING_TYPE_NAME));

        fields.add(buildField(REGISTRATION_ADDRESS_FIELD, REGISTRATION_ADDRESS_FIELD_DESCRIPTION, STRING_TYPE_NAME));
		fields.add(buildField(REGISTRATION_ADDRESS_POSTAL_CODE_FIELD , REGISTRATION_ADDRESS_POSTAL_CODE_FIELD_DESCRIPTION , STRING_TYPE_NAME));
        fields.add(buildField(REGISTRATION_ADDRESS_PROVINCE_FIELD , REGISTRATION_ADDRESS_PROVINCE_FIELD_DESCRIPTION , STRING_TYPE_NAME));
		fields.add(buildField(REGISTRATION_ADDRESS_DISTRICT_FIELD , REGISTRATION_ADDRESS_DISTRICT_FIELD_DESCRIPTION , STRING_TYPE_NAME));
		fields.add(buildField(REGISTRATION_ADDRESS_CITY_FIELD , REGISTRATION_ADDRESS_CITY_FIELD_DESCRIPTION , STRING_TYPE_NAME));
		fields.add(buildField(REGISTRATION_ADDRESS_STREET_FIELD , REGISTRATION_ADDRESS_STREET_FIELD_DESCRIPTION , STRING_TYPE_NAME));
		fields.add(buildField(REGISTRATION_ADDRESS_HOUSE_FIELD , REGISTRATION_ADDRESS_HOUSE_FIELD_DESCRIPTION , STRING_TYPE_NAME));
		fields.add(buildField(REGISTRATION_ADDRESS_BUILDING_FIELD , REGISTRATION_ADDRESS_BUILDING_FIELD_DESCRIPTION , STRING_TYPE_NAME));
		fields.add(buildField(REGISTRATION_ADDRESS_FLAT_FIELD , REGISTRATION_ADDRESS_FLAT_FIELD_DESCRIPTION , STRING_TYPE_NAME));

		fields.add(buildField(RESIDENCE_ADDRESS_FIELD, RESIDENCE_ADDRESS_FIELD_DESCRIPTION, STRING_TYPE_NAME));
		fields.add(buildField(RESIDENCE_ADDRESS_POSTAL_CODE_FIELD , RESIDENCE_ADDRESS_POSTAL_CODE_FIELD_DESCRIPTION , STRING_TYPE_NAME));
		fields.add(buildField(RESIDENCE_ADDRESS_PROVINCE_FIELD , RESIDENCE_ADDRESS_PROVINCE_FIELD_DESCRIPTION , STRING_TYPE_NAME));
		fields.add(buildField(RESIDENCE_ADDRESS_DISTRICT_FIELD , RESIDENCE_ADDRESS_DISTRICT_FIELD_DESCRIPTION , STRING_TYPE_NAME));
		fields.add(buildField(RESIDENCE_ADDRESS_CITY_FIELD , RESIDENCE_ADDRESS_CITY_FIELD_DESCRIPTION , STRING_TYPE_NAME));
		fields.add(buildField(RESIDENCE_ADDRESS_STREET_FIELD , RESIDENCE_ADDRESS_STREET_FIELD_DESCRIPTION , STRING_TYPE_NAME));
		fields.add(buildField(RESIDENCE_ADDRESS_HOUSE_FIELD , RESIDENCE_ADDRESS_HOUSE_FIELD_DESCRIPTION , STRING_TYPE_NAME));
		fields.add(buildField(RESIDENCE_ADDRESS_BUILDING_FIELD , RESIDENCE_ADDRESS_BUILDING_FIELD_DESCRIPTION , STRING_TYPE_NAME));
		fields.add(buildField(RESIDENCE_ADDRESS_FLAT_FIELD , RESIDENCE_ADDRESS_FLAT_FIELD_DESCRIPTION , STRING_TYPE_NAME));

        fields.add(buildField(EMAIL_FIELD, EMAIL_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(HOME_PHONE_FIELD, HOME_PHONE_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(JOB_PHONE_FIELD, JOB_PHONE_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(MOBILE_PHONE_FIELD, MOBILE_PHONE_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(MOBILE_OPERATOR_FIELD, MOBILE_OPERATOR_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(SMSFORMAT_FIELD, SMSFORMAT_FIELD_DESCRIPTION, STRING_TYPE_NAME));
	    fields.add(buildField(PIN_ENVELOPE_NUMBER_FIELD, PIN_ENVELOPE_NUMBER_FIELD_DESCRIPTION, STRING_TYPE_NAME));
	    fields.add(buildField(PIN_ENVELOPE_OLD_NUMBER_FIELD, PIN_ENVELOPE_NUMBER_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(SERVICE_INSERTION_DATE_FIELD, SERVICE_INSERTION_DATE_FIELD_DESCRIPTION, DATE_TYPE_NAME));
        fields.add(buildField(PROLONGATION_REJECTION_DATE_FIELD, PROLONGATION_REJECTION_DATE_FIELD_DESCRIPTION, DATE_TYPE_NAME));
        fields.add(buildField(MESSAGE_SERVICE_FIELD, MESSAGE_SERVICE_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(AGREEMENT_NUMBER_FIELD, AGREEMENT_NUMBER_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(AGREEMENT_OWNER_FIELD, AGREEMENT_OWNER_FIELD_DESCRIPTION, STRING_TYPE_NAME));
	    fields.add(buildField(REGION_CODE_FIELD, REGION_CODE_FIELD_DESCRIPTION, STRING_TYPE_NAME));
	    fields.add(buildField(BRANCH_CODE_FIELD, BRANCH_CODE_FIELD_DESCRIPTION, STRING_TYPE_NAME));
	    fields.add(buildField(DEPARTMENT_CODE_FIELD, DEPARTMENT_CODE_FIELD_DESCRIPTION, STRING_TYPE_NAME));
		fields.add(buildField(SECRET_WORD_FIELD, SECRET_WORD_DESCRIPTION, STRING_TYPE_NAME));
		fields.add(buildField(CONTRACT_CANCELLATION_COUSE_FIELD, CONTRACT_CANCELLATION_COUSE_FIELD_DESCRIPTION, STRING_TYPE_NAME));
		fields.add(buildField(DEPARTMENT_ID_FIELD, DEPARTMENT_ID_FIELD_DESCRIPTION, "integer"));

		fields.add(buildIncognitoField());

		FormBuilder builder     = new FormBuilder();
        Field[]     fieldsArray = new Field[fields.size()];

		fields.toArray(fieldsArray);
        builder.setFields( fieldsArray );

        SmsFormatValidator smsFormatValidator = new SmsFormatValidator();
        smsFormatValidator.setBinding(MESSAGE_SERVICE_FIELD, MESSAGE_SERVICE_FIELD);
        smsFormatValidator.setBinding(SMSFORMAT_FIELD, SMSFORMAT_FIELD);
        smsFormatValidator.setMessage("Установите значение в поле [" + SMSFORMAT_FIELD_DESCRIPTION + "]");
        builder.addFormValidators(smsFormatValidator);

		DocumentSeriesAndNumberValidator documentValidator = new DocumentSeriesAndNumberValidator();
	    documentValidator.setBinding("documentType", DOCUMENT_TYPE_FIELD);
		documentValidator.setBinding("documentSeries", DOCUMENT_SERIES_FIELD);
		documentValidator.setBinding("documentNumber", DOCUMENT_NUMBER_FIELD);
		documentValidator.setBinding("documentName", DOCUMENT_NAME_FIELD);
		builder.addFormValidators(documentValidator);

        return builder.build();
	}

    protected void initializeValidators()
    {
        List<FieldValidator> validators;
        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
        DateFieldValidator dateValidator          = new DateFieldValidator("dd.MM.yyyy");

        // фамилия
	    validators = new ArrayList<FieldValidator>();
        validators.add( requiredFieldValidator);
        validators.add( new RegexpFieldValidator(".{0,42}", "Поле [" + SUR_NAME_FIELD_DESCRIPTION + "] не должно превышать 42 символов"));
        fieldValidators.put(SUR_NAME_FIELD, validators);
        // имя
	    validators = new ArrayList<FieldValidator>();
        validators.add( requiredFieldValidator);
        validators.add( new RegexpFieldValidator(".{0,42}", "Поле [" + FIRST_NAME_FIELD_DESCRIPTION + "] не должно превышать 42 символов"));
        fieldValidators.put(FIRST_NAME_FIELD, validators);
        // отчество
	    validators = new ArrayList<FieldValidator>();
        validators.add( new RegexpFieldValidator(".{0,42}", "Поле [" + PATR_NAME_FIELD_DESCRIPTION + "] не должно превышать 42 символов"));
        fieldValidators.put(PATR_NAME_FIELD, validators);
        // дата рождения
	    validators = new ArrayList<FieldValidator>();
        validators.add( dateValidator);
        fieldValidators.put(BIRTH_DAY_FIELD, validators);
        // место рождения
	    validators = new ArrayList<FieldValidator>();
        validators.add( new RegexpFieldValidator(".{0,128}", "Поле [" + BIRTH_PLACE_FIELD_DESCRIPTION + "] не должно превышать 128 символов"));
        fieldValidators.put(BIRTH_PLACE_FIELD, validators);
        // Вид документа
	    validators = new ArrayList<FieldValidator>();
        validators.add( new RegexpFieldValidator(".{0,32}", "Поле [" + DOCUMENT_TYPE_FIELD_DESCRIPTION + "] не должно превышать 32 символов"));
        fieldValidators.put(DOCUMENT_TYPE_FIELD, validators);
        // Наименование документа
	    validators = new ArrayList<FieldValidator>();
        validators.add( new RegexpFieldValidator(".{0,128}", "Поле [" + DOCUMENT_NAME_FIELD_DESCRIPTION + "] не должно превышать 128 символов"));
        fieldValidators.put(DOCUMENT_NAME_FIELD, validators);
        // Серия
	    validators = new ArrayList<FieldValidator>();
        validators.add( new RegexpFieldValidator(".{0,16}", "Поле [" + DOCUMENT_SERIES_FIELD_DESCRIPTION + "] не должно превышать 16 символов"));
        fieldValidators.put(DOCUMENT_SERIES_FIELD, validators);
        // Номер
	    validators = new ArrayList<FieldValidator>();
        validators.add( new RegexpFieldValidator("\\d*", "Поле [" + DOCUMENT_NUMBER_FIELD_DESCRIPTION + "] должно содержать только цифры"));
        validators.add( new RegexpFieldValidator(".{0,16}", "Поле [" + DOCUMENT_NUMBER_FIELD_DESCRIPTION + "] не должно превышать 16 цифр"));
        fieldValidators.put(DOCUMENT_NUMBER_FIELD, validators);
        // Код подразделения
	    validators = new ArrayList<FieldValidator>();
        validators.add( new RegexpFieldValidator(".{0,16}", "Поле [" + DOCUMENT_ISSUE_BY_CODE_FIELD_DESCRIPTION + "] не должно превышать 16 символов"));
        fieldValidators.put(DOCUMENT_ISSUE_BY_CODE_FIELD, validators);
        // Дата выдачи
	    validators = new ArrayList<FieldValidator>();
	    validators.add( requiredFieldValidator);
        validators.add( dateValidator);
        fieldValidators.put(DOCUMENT_ISSUE_DATE_FIELD, validators);
        // Кем выдан
	    validators = new ArrayList<FieldValidator>();
	    validators.add( requiredFieldValidator);
        validators.add( new RegexpFieldValidator(".{0,128}", "Поле [" + DOCUMENT_ISSUE_BY_FIELD_DESCRIPTION + "] не должно превышать 128 символов"));
        fieldValidators.put(DOCUMENT_ISSUE_BY_FIELD, validators);
		// Вид документа
	    validators = new ArrayList<FieldValidator>();
        validators.add( new RegexpFieldValidator(".{0,32}", "Поле [" + DOCUMENT_PROVE_TYPE_FIELD_DESCRIPTION + "] не должно превышать 32 символов"));
        fieldValidators.put(DOCUMENT_PROVE_TYPE_FIELD, validators);
        // Серия
	    validators = new ArrayList<FieldValidator>();
        validators.add( new RegexpFieldValidator(".{0,16}", "Поле [" + DOCUMENT_PROVE_SERIES_FIELD_DESCRIPTION + "] не должно превышать 16 символов"));
        fieldValidators.put(DOCUMENT_PROVE_SERIES_FIELD, validators);
        // Номер
	    validators = new ArrayList<FieldValidator>();
        validators.add( new RegexpFieldValidator("\\d*", "Поле [" + DOCUMENT_PROVE_NUMBER_FIELD_DESCRIPTION + "] должно содержать только цифры"));
        validators.add( new RegexpFieldValidator(".{0,16}", "Поле [" + DOCUMENT_PROVE_NUMBER_FIELD_DESCRIPTION + "] не должно превышать 16 цифр"));
        fieldValidators.put(DOCUMENT_PROVE_NUMBER_FIELD, validators);
        // Дата выдачи
	    validators = new ArrayList<FieldValidator>();
        validators.add( dateValidator);
        fieldValidators.put(DOCUMENT_PROVE_ISSUE_DATE_FIELD, validators);
        // Кем выдан
	    validators = new ArrayList<FieldValidator>();
        validators.add( new RegexpFieldValidator(".{0,128}", "Поле [" + DOCUMENT_PROVE_ISSUE_BY_FIELD_DESCRIPTION + "] не должно превышать 128 символов"));
        fieldValidators.put(DOCUMENT_PROVE_ISSUE_BY_FIELD, validators);
	    // Номер миграционной карты
	    validators = new ArrayList<FieldValidator>();
	    validators.add( new RegexpFieldValidator("\\d{11}", "Поле [" + MIGRATORY_CARD_NUMBER_FIELD_DESCRIPTION  + "] должно содержать 11 цифр"));
	    fieldValidators.put(MIGRATORY_CARD_NUMBER_FIELD , validators);
	    // Дата начала срока пребывания
	    validators = new ArrayList<FieldValidator>();
        validators.add( dateValidator);
        fieldValidators.put(MIGRATORY_CARD_FROM_DATE_FIELD, validators);
	    // Дата окончания срока пребывания
	    validators = new ArrayList<FieldValidator>();
        validators.add( dateValidator);
        fieldValidators.put(MIGRATORY_CARD_TIME_UP_DATE_FIELD, validators);
        // Гражданство
	    validators = new ArrayList<FieldValidator>();
        validators.add( new RegexpFieldValidator(".{0,70}", "Поле [" + CITIZEN_FIELD_DESCRIPTION + "] не должно превышать 70 символов"));
        fieldValidators.put(CITIZEN_FIELD, validators);
        // ИНН
	    validators = new ArrayList<FieldValidator>();
        validators.add( new RegexpFieldValidator("\\d{12}", "Поле [" + INN_FIELD_DESCRIPTION + "] должно состоять из 12 цифр"));
        fieldValidators.put(INN_FIELD, validators);
	    // Адрес регистрации
		validators = new ArrayList<FieldValidator>();
	    validators.add( new RegexpFieldValidator(".{0,255}", "Поле [" + REGISTRATION_ADDRESS_FIELD_DESCRIPTION  + "] не должно превышать 255 символов"));
	    fieldValidators.put(REGISTRATION_ADDRESS_FIELD , validators);
		// Адрес регистрации - почтовый индекс
		validators = new ArrayList<FieldValidator>();
	    validators.add( new RegexpFieldValidator(".{0,6}", "Поле [" + REGISTRATION_ADDRESS_POSTAL_CODE_FIELD_DESCRIPTION  + "] не должно превышать 6 символов"));
	    fieldValidators.put(REGISTRATION_ADDRESS_POSTAL_CODE_FIELD , validators);
		// Адрес регистрации - область
		validators = new ArrayList<FieldValidator>();
		validators.add( new RegexpFieldValidator(".{0,100}", "Поле [" + REGISTRATION_ADDRESS_PROVINCE_FIELD_DESCRIPTION  + "] не должно превышать 100 символов"));
		fieldValidators.put(REGISTRATION_ADDRESS_PROVINCE_FIELD , validators);
		// Адрес регистрации - район
		validators = new ArrayList<FieldValidator>();
		validators.add( new RegexpFieldValidator(".{0,100}", "Поле [" + REGISTRATION_ADDRESS_DISTRICT_FIELD_DESCRIPTION  + "] не должно превышать 100 символов"));
		fieldValidators.put(REGISTRATION_ADDRESS_DISTRICT_FIELD , validators);
		// Адрес регистрации - город
		validators = new ArrayList<FieldValidator>();
		validators.add( new RegexpFieldValidator(".{0,100}", "Поле [" + REGISTRATION_ADDRESS_CITY_FIELD_DESCRIPTION  + "] не должно превышать 100 символов"));
		fieldValidators.put(REGISTRATION_ADDRESS_CITY_FIELD , validators);
		// Адрес регистрации - улица
		validators = new ArrayList<FieldValidator>();
		validators.add( new RegexpFieldValidator(".{0,100}", "Поле [" + REGISTRATION_ADDRESS_STREET_FIELD_DESCRIPTION  + "] не должно превышать 100 символов"));
		fieldValidators.put(REGISTRATION_ADDRESS_STREET_FIELD , validators);
		// Адрес регистрации - дом
		validators = new ArrayList<FieldValidator>();
		validators.add( new RegexpFieldValidator(".{0,10}", "Поле [" + REGISTRATION_ADDRESS_HOUSE_FIELD_DESCRIPTION  + "] не должно превышать 10 символов"));
		fieldValidators.put(REGISTRATION_ADDRESS_HOUSE_FIELD , validators);
		// Адрес регистрации - корпус
		validators = new ArrayList<FieldValidator>();
		validators.add( new RegexpFieldValidator(".{0,10}", "Поле [" + REGISTRATION_ADDRESS_BUILDING_FIELD_DESCRIPTION  + "] не должно превышать 10 символов"));
		fieldValidators.put(REGISTRATION_ADDRESS_BUILDING_FIELD , validators);
		// Адрес регистрации - квартира
		validators = new ArrayList<FieldValidator>();
		validators.add( new RegexpFieldValidator(".{0,10}", "Поле [" + REGISTRATION_ADDRESS_FLAT_FIELD_DESCRIPTION  + "] не должно превышать 10 символов"));
		fieldValidators.put(REGISTRATION_ADDRESS_FLAT_FIELD , validators);
	    // Адрес фактического проживания
		validators = new ArrayList<FieldValidator>();
	    validators.add( new RegexpFieldValidator(".{0,255}", "Поле [" + RESIDENCE_ADDRESS_FIELD_DESCRIPTION + "] не должно превышать 255 символов"));
	    fieldValidators.put(RESIDENCE_ADDRESS_FIELD, validators);
		// Адрес фактического проживания - почтовый индекс
		validators = new ArrayList<FieldValidator>();
	    validators.add( new RegexpFieldValidator(".{0,6}", "Поле [" + RESIDENCE_ADDRESS_POSTAL_CODE_FIELD_DESCRIPTION  + "] не должно превышать 6 символов"));
	    fieldValidators.put(RESIDENCE_ADDRESS_POSTAL_CODE_FIELD , validators);
		// Адрес фактического проживания - область
		validators = new ArrayList<FieldValidator>();
		validators.add( new RegexpFieldValidator(".{0,100}", "Поле [" + RESIDENCE_ADDRESS_PROVINCE_FIELD_DESCRIPTION  + "] не должно превышать 100 символов"));
		fieldValidators.put(RESIDENCE_ADDRESS_PROVINCE_FIELD , validators);
		// Адрес фактического проживания - район
		validators = new ArrayList<FieldValidator>();
		validators.add( new RegexpFieldValidator(".{0,100}", "Поле [" + RESIDENCE_ADDRESS_DISTRICT_FIELD_DESCRIPTION  + "] не должно превышать 100 символов"));
		fieldValidators.put(RESIDENCE_ADDRESS_DISTRICT_FIELD , validators);
		// Адрес фактического проживания - город
		validators = new ArrayList<FieldValidator>();
		validators.add( new RegexpFieldValidator(".{0,100}", "Поле [" + RESIDENCE_ADDRESS_CITY_FIELD_DESCRIPTION  + "] не должно превышать 100 символов"));
		fieldValidators.put(RESIDENCE_ADDRESS_CITY_FIELD , validators);
		// Адрес фактического проживания - улица
		validators = new ArrayList<FieldValidator>();
		validators.add( new RegexpFieldValidator(".{0,100}", "Поле [" + RESIDENCE_ADDRESS_STREET_FIELD_DESCRIPTION  + "] не должно превышать 100 символов"));
		fieldValidators.put(RESIDENCE_ADDRESS_STREET_FIELD , validators);
		// Адрес фактического проживания - дом
		validators = new ArrayList<FieldValidator>();
		validators.add( new RegexpFieldValidator(".{0,10}", "Поле [" + RESIDENCE_ADDRESS_HOUSE_FIELD_DESCRIPTION  + "] не должно превышать 10 символов"));
		fieldValidators.put(RESIDENCE_ADDRESS_HOUSE_FIELD , validators);
		// Адрес фактического проживания - корпус
		validators = new ArrayList<FieldValidator>();
		validators.add( new RegexpFieldValidator(".{0,10}", "Поле [" + RESIDENCE_ADDRESS_BUILDING_FIELD_DESCRIPTION  + "] не должно превышать 10 символов"));
		fieldValidators.put(RESIDENCE_ADDRESS_BUILDING_FIELD , validators);
		// Адрес фактического проживания - квартира
		validators = new ArrayList<FieldValidator>();
		validators.add( new RegexpFieldValidator(".{0,10}", "Поле [" + RESIDENCE_ADDRESS_FLAT_FIELD_DESCRIPTION  + "] не должно превышать 10 символов"));
		fieldValidators.put(RESIDENCE_ADDRESS_FLAT_FIELD , validators);

        // Адрес электронной почты
	    validators = new ArrayList<FieldValidator>();
        validators.add( new RegexpFieldValidator(".{0,70}", "Поле [" + EMAIL_FIELD_DESCRIPTION + "] не должно превышать 70 символов"));
        fieldValidators.put(EMAIL_FIELD, validators);
        // Домашний телефон
	    validators = new ArrayList<FieldValidator>();
        validators.add( new RegexpFieldValidator(".{0,16}", "Поле [" + HOME_PHONE_FIELD_DESCRIPTION + "] не должно превышать 16 символов"));
	    validators.add(new RegexpFieldValidator("[0-9]*","Поле [" + HOME_PHONE_FIELD_DESCRIPTION + "] должно содержать только цифры"));
        fieldValidators.put(HOME_PHONE_FIELD, validators);
        // Рабочий телефон
	    validators = new ArrayList<FieldValidator>();
        validators.add( new RegexpFieldValidator(".{0,16}", "Поле [" + JOB_PHONE_FIELD_DESCRIPTION + "] не должно превышать 16 символов"));
	    validators.add(new RegexpFieldValidator("[0-9]*","Поле [" + JOB_PHONE_FIELD_DESCRIPTION + "] должно содержать только цифры"));
        fieldValidators.put(JOB_PHONE_FIELD, validators);
        // Мобильный телефон
	    validators = new ArrayList<FieldValidator>();
	    validators.add( new PhoneNumberValidator(MOBILE_PHONE_FIELD_DESCRIPTION));
        fieldValidators.put(MOBILE_PHONE_FIELD, validators);
        // Мобильный оператор
	    validators = new ArrayList<FieldValidator>();
        validators.add( new RegexpFieldValidator(".{0,70}", "Поле [" + MOBILE_OPERATOR_FIELD_DESCRIPTION + "] не должно превышать 70 символов"));
        fieldValidators.put(MOBILE_OPERATOR_FIELD, validators);
        // Дата начала обслуживания
	    validators = new ArrayList<FieldValidator>();
        validators.add( dateValidator);
        fieldValidators.put(SERVICE_INSERTION_DATE_FIELD, validators);
        // Дата отказа от обслуживания
	    validators = new ArrayList<FieldValidator>();
        validators.add( dateValidator);
        fieldValidators.put(PROLONGATION_REJECTION_DATE_FIELD, validators);
        // Способ доставки подтверждений
	    validators = new ArrayList<FieldValidator>();
        validators.add( new RegexpFieldValidator(".{0,20}", "Поле [" + MESSAGE_SERVICE_FIELD_DESCRIPTION + "] не должно превышать 20 символов"));
        fieldValidators.put(MESSAGE_SERVICE_FIELD, validators);
        // Номер договора
	    validators = new ArrayList<FieldValidator>();
        validators.add( new RegexpFieldValidator(".{0,16}", "Поле [" + AGREEMENT_NUMBER_FIELD_DESCRIPTION + "] не должно превышать 16 символов"));
        fieldValidators.put(AGREEMENT_NUMBER_FIELD, validators);
        // Отделение договора
	    validators = new ArrayList<FieldValidator>();
        validators.add( new RegexpFieldValidator(".{0,4}", "Поле [" + AGREEMENT_OWNER_FIELD_DESCRIPTION + "] не должно превышать 4 символов"));
        fieldValidators.put(AGREEMENT_OWNER_FIELD, validators);
	    //Секретное слово
	    validators = new ArrayList<FieldValidator>();
	    validators.add(new RegexpFieldValidator("[а-яА-Я0-9ёЁa-zA-Z]*","Поле [" + SECRET_WORD_DESCRIPTION + "] должно содержать только буквы и цифры"));
        validators.add( new RegexpFieldValidator(".{0,128}", "Поле [" + SECRET_WORD_DESCRIPTION + "] не должно превышать 128 символов"));
        fieldValidators.put(SECRET_WORD_FIELD, validators);
	    //подразделение
	    validators = new ArrayList<FieldValidator>();
	    validators.add(new RequiredFieldValidator());
	    fieldValidators.put(DEPARTMENT_ID_FIELD, validators);
    }	
}
