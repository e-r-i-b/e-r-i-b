package com.rssl.phizic.web.persons.formBuilders;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.Field;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Omeliyanchuk
 * @ created 05.12.2006
 * @ $Author$
 * @ $Revision$
 */

public class StopListFormBuilder extends PersonFormBuilderBase
{
	public Form buildForm()
	{
		List<Field> fields = new ArrayList<Field>();

        fields.add(buildField(CLIENT_ID_FIELD, CLIENT_ID_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(SUR_NAME_FIELD, SUR_NAME_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(FIRST_NAME_FIELD, FIRST_NAME_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(PATR_NAME_FIELD, PATR_NAME_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(GENDER_FIELD, GENDER_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(BIRTH_DAY_FIELD, BIRTH_DAY_FIELD_DESCRIPTION, DATE_TYPE_NAME));
        fields.add(buildField(BIRTH_PLACE_FIELD, BIRTH_PLACE_FIELD_DESCRIPTION, STRING_TYPE_NAME));
		fields.add(buildField(DOCUMENT_ID_FIELD, DOCUMENT_ID_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(DOCUMENT_TYPE_FIELD, DOCUMENT_TYPE_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(DOCUMENT_NAME_FIELD, DOCUMENT_NAME_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(DOCUMENT_SERIES_FIELD, DOCUMENT_SERIES_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(DOCUMENT_NUMBER_FIELD, DOCUMENT_NUMBER_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(DOCUMENT_ISSUE_BY_CODE_FIELD, DOCUMENT_ISSUE_BY_CODE_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(DOCUMENT_ISSUE_DATE_FIELD, DOCUMENT_ISSUE_DATE_FIELD_DESCRIPTION, DATE_TYPE_NAME));
        fields.add(buildField(DOCUMENT_ISSUE_BY_FIELD, DOCUMENT_ISSUE_BY_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(CITIZEN_FIELD, CITIZEN_FIELD_DESCRIPTION, STRING_TYPE_NAME));
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

		FormBuilder builder     = new FormBuilder();
        Field[]     fieldsArray = new Field[fields.size()];

		fields.toArray(fieldsArray);
        builder.setFields( fieldsArray );

        return builder.build();
	}

    protected void initializeValidators()
    {
        List<FieldValidator> validators;
        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
        DateFieldValidator dateValidator          = new DateFieldValidator();

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
        validators.add( requiredFieldValidator);
        validators.add( dateValidator);
        fieldValidators.put(BIRTH_DAY_FIELD, validators);
        // место рождения
	    validators = new ArrayList<FieldValidator>();
        validators.add( new RegexpFieldValidator(".{0,128}", "Поле [" + BIRTH_PLACE_FIELD_DESCRIPTION + "] не должно превышать 128 символов"));
        fieldValidators.put(BIRTH_PLACE_FIELD, validators);
        // Вид документа
	    validators = new ArrayList<FieldValidator>();
        validators.add( requiredFieldValidator);
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
        validators.add( requiredFieldValidator);
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
    }
}
