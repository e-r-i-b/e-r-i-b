package com.rssl.phizic.web.persons.formBuilders;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.Field;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.persons.forms.DocumentSeriesAndNumberValidator;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Omeliyanchuk
 * @ created 05.12.2006
 * @ $Author$
 * @ $Revision$
 */

public class EmpoweredPartiallyFormBuilder extends PersonFormBuilderBase
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
        fields.add(buildField(CITIZEN_FIELD, CITIZEN_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(INN_FIELD, INN_FIELD_DESCRIPTION, STRING_TYPE_NAME));		

		fields.add(buildField(DOCUMENT_ID_FIELD, DOCUMENT_ID_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(DOCUMENT_TYPE_FIELD, DOCUMENT_TYPE_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(DOCUMENT_NAME_FIELD, DOCUMENT_NAME_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(DOCUMENT_SERIES_FIELD, DOCUMENT_SERIES_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(DOCUMENT_NUMBER_FIELD, DOCUMENT_NUMBER_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(DOCUMENT_ISSUE_BY_CODE_FIELD, DOCUMENT_ISSUE_BY_CODE_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(DOCUMENT_ISSUE_DATE_FIELD, DOCUMENT_ISSUE_DATE_FIELD_DESCRIPTION, DATE_TYPE_NAME));
        fields.add(buildField(DOCUMENT_ISSUE_BY_FIELD, DOCUMENT_ISSUE_BY_FIELD_DESCRIPTION, STRING_TYPE_NAME));

		fields.add(buildField(DOCUMENT_PROVE_ID_FIELD, DOCUMENT_ID_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(DOCUMENT_PROVE_TYPE_FIELD, DOCUMENT_TYPE_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(DOCUMENT_PROVE_NAME_FIELD, DOCUMENT_NAME_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(DOCUMENT_PROVE_SERIES_FIELD, DOCUMENT_SERIES_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(DOCUMENT_PROVE_NUMBER_FIELD, DOCUMENT_NUMBER_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(DOCUMENT_PROVE_ISSUE_DATE_FIELD, DOCUMENT_ISSUE_DATE_FIELD_DESCRIPTION, DATE_TYPE_NAME));
        fields.add(buildField(DOCUMENT_PROVE_ISSUE_BY_FIELD, DOCUMENT_PROVE_ISSUE_BY_FIELD_DESCRIPTION, STRING_TYPE_NAME));

        fields.add(buildField(RESIDENT_FIELD, RESIDENT_FIELD_DESCRIPTION, STRING_TYPE_NAME));
		fields.add(buildField(MIGRATORY_CARD_NUMBER_FIELD, MIGRATORY_CARD_NUMBER_FIELD_DESCRIPTION, STRING_TYPE_NAME));
		fields.add(buildField(MIGRATORY_CARD_FROM_DATE_FIELD, MIGRATORY_CARD_FROM_DATE_FIELD_DESCRIPTION, DATE_TYPE_NAME));
		fields.add(buildField(MIGRATORY_CARD_TIME_UP_DATE_FIELD, MIGRATORY_CARD_TIME_UP_DATE_FIELD_DESCRIPTION, DATE_TYPE_NAME));

		fields.add(buildField(PENSION_CERTIFICATE_NUMBER_FIELD, PENSION_CERTIFICATE_NUMBER_FIELD_DESCRIPTION, STRING_TYPE_NAME));		

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
	    fields.add(buildField(PIN_ENVELOPE_NUMBER_FIELD, PIN_ENVELOPE_NUMBER_FIELD_DESCRIPTION, STRING_TYPE_NAME));
	    fields.add(buildField(PIN_ENVELOPE_OLD_NUMBER_FIELD, PIN_ENVELOPE_NUMBER_FIELD_DESCRIPTION, STRING_TYPE_NAME));
        fields.add(buildField(SERVICE_INSERTION_DATE_FIELD, SERVICE_INSERTION_DATE_FIELD_DESCRIPTION, DATE_TYPE_NAME));
        fields.add(buildField(MESSAGE_SERVICE_FIELD, MESSAGE_SERVICE_FIELD_DESCRIPTION, STRING_TYPE_NAME));
		fields.add(buildField(AGREEMENT_NUMBER_FIELD, AGREEMENT_NUMBER_FIELD_DESCRIPTION, STRING_TYPE_NAME));
	    fields.add(buildField(REGION_CODE_FIELD, REGION_CODE_FIELD_DESCRIPTION, STRING_TYPE_NAME));
	    fields.add(buildField(BRANCH_CODE_FIELD, BRANCH_CODE_FIELD_DESCRIPTION, STRING_TYPE_NAME));
	    fields.add(buildField(DEPARTMENT_CODE_FIELD, DEPARTMENT_CODE_FIELD_DESCRIPTION, STRING_TYPE_NAME));

		FormBuilder builder     = new FormBuilder();
        Field[]     fieldsArray = new Field[fields.size()];

		fields.toArray(fieldsArray);
        builder.setFields( fieldsArray );

		DocumentSeriesAndNumberValidator documentValidator = new DocumentSeriesAndNumberValidator();
	    documentValidator.setBinding("documentType", DOCUMENT_TYPE_FIELD);
		documentValidator.setBinding("documentSeries", DOCUMENT_SERIES_FIELD);
		documentValidator.setBinding("documentNumber", DOCUMENT_NUMBER_FIELD);
		documentValidator.setBinding("documentName", DOCUMENT_NAME_FIELD);
		builder.addFormValidators(documentValidator);

		documentValidator = new DocumentSeriesAndNumberValidator();
	    documentValidator.setBinding("documentType", DOCUMENT_PROVE_TYPE_FIELD);
		documentValidator.setBinding("documentSeries", DOCUMENT_PROVE_SERIES_FIELD);
		documentValidator.setBinding("documentNumber", DOCUMENT_PROVE_NUMBER_FIELD);
		builder.addFormValidators(documentValidator);

        return builder.build();
	}

    protected void initializeValidators()
    {
        List<FieldValidator> validators;
        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
        DateFieldValidator dateValidator          = new DateFieldValidator();

        // �������
	    validators = new ArrayList<FieldValidator>();
	    validators.add( requiredFieldValidator);
        validators.add( new RegexpFieldValidator(".{0,42}", "���� [" + SUR_NAME_FIELD_DESCRIPTION + "] �� ������ ��������� 42 ��������"));
        fieldValidators.put(SUR_NAME_FIELD, validators);
        // ���
	    validators = new ArrayList<FieldValidator>();
	    validators.add( requiredFieldValidator);
        validators.add( new RegexpFieldValidator(".{0,42}", "���� [" + FIRST_NAME_FIELD_DESCRIPTION + "] �� ������ ��������� 42 ��������"));
        fieldValidators.put(FIRST_NAME_FIELD, validators);
        // ��������
	    validators = new ArrayList<FieldValidator>();
        validators.add( new RegexpFieldValidator(".{0,42}", "���� [" + PATR_NAME_FIELD_DESCRIPTION + "] �� ������ ��������� 42 ��������"));
        fieldValidators.put(PATR_NAME_FIELD, validators);
        // ���� ��������
	    validators = new ArrayList<FieldValidator>();
        validators.add( dateValidator);
        fieldValidators.put(BIRTH_DAY_FIELD, validators);
        // ����� ��������
	    validators = new ArrayList<FieldValidator>();
        validators.add( new RegexpFieldValidator(".{0,128}", "���� [" + BIRTH_PLACE_FIELD_DESCRIPTION + "] �� ������ ��������� 128 ��������"));
        fieldValidators.put(BIRTH_PLACE_FIELD, validators);
        // ��� ���������
	    validators = new ArrayList<FieldValidator>();
        validators.add( new RegexpFieldValidator(".{0,32}", "���� [" + DOCUMENT_TYPE_FIELD_DESCRIPTION + "] �� ������ ��������� 32 ��������"));
        fieldValidators.put(DOCUMENT_TYPE_FIELD, validators);
        // ������������ ���������
	    validators = new ArrayList<FieldValidator>();
        validators.add( new RegexpFieldValidator(".{0,128}", "���� [" + DOCUMENT_NAME_FIELD_DESCRIPTION + "] �� ������ ��������� 128 ��������"));
        fieldValidators.put(DOCUMENT_NAME_FIELD, validators);
        // �����
	    validators = new ArrayList<FieldValidator>();
        validators.add( new RegexpFieldValidator(".{0,16}", "���� [" + DOCUMENT_SERIES_FIELD_DESCRIPTION + "] �� ������ ��������� 16 ��������"));
        fieldValidators.put(DOCUMENT_SERIES_FIELD, validators);
        // �����
	    validators = new ArrayList<FieldValidator>();
        validators.add( new RegexpFieldValidator("\\d*", "���� [" + DOCUMENT_NUMBER_FIELD_DESCRIPTION + "] ������ ��������� ������ �����"));
        validators.add( new RegexpFieldValidator(".{0,16}", "���� [" + DOCUMENT_NUMBER_FIELD_DESCRIPTION + "] �� ������ ��������� 16 ����"));
        fieldValidators.put(DOCUMENT_NUMBER_FIELD, validators);
        // ��� �������������
	    validators = new ArrayList<FieldValidator>();
        validators.add( new RegexpFieldValidator(".{0,16}", "���� [" + DOCUMENT_ISSUE_BY_FIELD_DESCRIPTION + "] �� ������ ��������� 16 ��������"));
        fieldValidators.put(DOCUMENT_ISSUE_BY_FIELD, validators);
        // ���� ������
	    validators = new ArrayList<FieldValidator>();
        validators.add( dateValidator);
        fieldValidators.put(DOCUMENT_ISSUE_DATE_FIELD, validators);
        // ��� �����
	    validators = new ArrayList<FieldValidator>();
        validators.add( new RegexpFieldValidator(".{0,128}", "���� [" + DOCUMENT_ISSUE_BY_FIELD_DESCRIPTION + "] �� ������ ��������� 128 ��������"));
        fieldValidators.put(DOCUMENT_ISSUE_BY_FIELD, validators);
		// ��� ���������
	    validators = new ArrayList<FieldValidator>();
        validators.add( new RegexpFieldValidator(".{0,32}", "���� [" + DOCUMENT_PROVE_TYPE_FIELD_DESCRIPTION + "] �� ������ ��������� 32 ��������"));
        fieldValidators.put(DOCUMENT_PROVE_TYPE_FIELD, validators);
        // �����
	    validators = new ArrayList<FieldValidator>();
        validators.add( new RegexpFieldValidator(".{0,16}", "���� [" + DOCUMENT_PROVE_SERIES_FIELD_DESCRIPTION + "] �� ������ ��������� 16 ��������"));
        fieldValidators.put(DOCUMENT_PROVE_SERIES_FIELD, validators);
        // �����
	    validators = new ArrayList<FieldValidator>();
        validators.add( new RegexpFieldValidator("\\d*", "���� [" + DOCUMENT_PROVE_NUMBER_FIELD_DESCRIPTION + "] ������ ��������� ������ �����"));
        validators.add( new RegexpFieldValidator(".{0,16}", "���� [" + DOCUMENT_PROVE_NUMBER_FIELD_DESCRIPTION + "] �� ������ ��������� 16 ����"));
        fieldValidators.put(DOCUMENT_PROVE_NUMBER_FIELD, validators);
        // ���� ������
	    validators = new ArrayList<FieldValidator>();
        validators.add( dateValidator);
        fieldValidators.put(DOCUMENT_PROVE_ISSUE_DATE_FIELD, validators);
        // ��� �����
	    validators = new ArrayList<FieldValidator>();
        validators.add( new RegexpFieldValidator(".{0,128}", "���� [" + DOCUMENT_PROVE_ISSUE_BY_FIELD_DESCRIPTION + "] �� ������ ��������� 128 ��������"));
        fieldValidators.put(DOCUMENT_PROVE_ISSUE_BY_FIELD, validators);
	    // ����� ������������ �����
	    validators = new ArrayList<FieldValidator>();
	    validators.add( new RegexpFieldValidator("\\d{11}", "���� [" + MIGRATORY_CARD_NUMBER_FIELD_DESCRIPTION  + "] ������ ��������� 11 ����"));
	    fieldValidators.put(MIGRATORY_CARD_NUMBER_FIELD , validators);
	    // ���� ������ ����� ����������
	    validators = new ArrayList<FieldValidator>();
        validators.add( dateValidator);
        fieldValidators.put(MIGRATORY_CARD_FROM_DATE_FIELD, validators);
	    // ���� ��������� ����� ����������
	    validators = new ArrayList<FieldValidator>();
        validators.add( dateValidator);
        fieldValidators.put(MIGRATORY_CARD_TIME_UP_DATE_FIELD, validators);
        // �����������
	    validators = new ArrayList<FieldValidator>();
        validators.add( new RegexpFieldValidator(".{0,70}", "���� [" + CITIZEN_FIELD_DESCRIPTION + "] �� ������ ��������� 70 ��������"));
        fieldValidators.put(CITIZEN_FIELD, validators);
        // ���
	    validators = new ArrayList<FieldValidator>();
        validators.add( new RegexpFieldValidator("\\d{12}", "���� [" + INN_FIELD_DESCRIPTION + "] ������ �������� �� 12 ����"));
        fieldValidators.put(INN_FIELD, validators);
        // ����� �����������
	    validators = new ArrayList<FieldValidator>();
        validators.add( new RegexpFieldValidator(".{0,255}", "���� [" + REGISTRATION_ADDRESS_FIELD_DESCRIPTION  + "] �� ������ ��������� 255 ��������"));
        fieldValidators.put(REGISTRATION_ADDRESS_FIELD , validators);
	    // ����� ����������� - �������� ������
	    validators = new ArrayList<FieldValidator>();
        validators.add( new RegexpFieldValidator(".{0,6}", "���� [" + REGISTRATION_ADDRESS_POSTAL_CODE_FIELD_DESCRIPTION  + "] �� ������ ��������� 6 ��������"));
        fieldValidators.put(REGISTRATION_ADDRESS_POSTAL_CODE_FIELD , validators);
	    // ����� ����������� - �������
	    validators = new ArrayList<FieldValidator>();
	    validators.add( new RegexpFieldValidator(".{0,100}", "���� [" + REGISTRATION_ADDRESS_PROVINCE_FIELD_DESCRIPTION  + "] �� ������ ��������� 100 ��������"));
	    fieldValidators.put(REGISTRATION_ADDRESS_PROVINCE_FIELD , validators);
	    // ����� ����������� - �����
	    validators = new ArrayList<FieldValidator>();
	    validators.add( new RegexpFieldValidator(".{0,100}", "���� [" + REGISTRATION_ADDRESS_DISTRICT_FIELD_DESCRIPTION  + "] �� ������ ��������� 100 ��������"));
	    fieldValidators.put(REGISTRATION_ADDRESS_DISTRICT_FIELD , validators);
	    // ����� ����������� - �����
	    validators = new ArrayList<FieldValidator>();
	    validators.add( new RegexpFieldValidator(".{0,100}", "���� [" + REGISTRATION_ADDRESS_CITY_FIELD_DESCRIPTION  + "] �� ������ ��������� 100 ��������"));
	    fieldValidators.put(REGISTRATION_ADDRESS_CITY_FIELD , validators);
	    // ����� ����������� - �����
	    validators = new ArrayList<FieldValidator>();
	    validators.add( new RegexpFieldValidator(".{0,100}", "���� [" + REGISTRATION_ADDRESS_STREET_FIELD_DESCRIPTION  + "] �� ������ ��������� 100 ��������"));
	    fieldValidators.put(REGISTRATION_ADDRESS_STREET_FIELD , validators);
	    // ����� ����������� - ���
	    validators = new ArrayList<FieldValidator>();
	    validators.add( new RegexpFieldValidator(".{0,10}", "���� [" + REGISTRATION_ADDRESS_HOUSE_FIELD_DESCRIPTION  + "] �� ������ ��������� 10 ��������"));
	    fieldValidators.put(REGISTRATION_ADDRESS_HOUSE_FIELD , validators);
	    // ����� ����������� - ������
	    validators = new ArrayList<FieldValidator>();
	    validators.add( new RegexpFieldValidator(".{0,10}", "���� [" + REGISTRATION_ADDRESS_BUILDING_FIELD_DESCRIPTION  + "] �� ������ ��������� 10 ��������"));
	    fieldValidators.put(REGISTRATION_ADDRESS_BUILDING_FIELD , validators);
	    // ����� ����������� - ��������
	    validators = new ArrayList<FieldValidator>();
	    validators.add( new RegexpFieldValidator(".{0,10}", "���� [" + REGISTRATION_ADDRESS_FLAT_FIELD_DESCRIPTION  + "] �� ������ ��������� 10 ��������"));
	    fieldValidators.put(REGISTRATION_ADDRESS_FLAT_FIELD , validators);
        // ����� ������������ ����������
	    validators = new ArrayList<FieldValidator>();
        validators.add( new RegexpFieldValidator(".{0,255}", "���� [" + RESIDENCE_ADDRESS_FIELD_DESCRIPTION + "] �� ������ ��������� 255 ��������"));
        fieldValidators.put(RESIDENCE_ADDRESS_FIELD, validators);
	    // ����� ������������ ���������� - �������� ������
	    validators = new ArrayList<FieldValidator>();
        validators.add( new RegexpFieldValidator(".{0,6}", "���� [" + RESIDENCE_ADDRESS_POSTAL_CODE_FIELD_DESCRIPTION  + "] �� ������ ��������� 6 ��������"));
        fieldValidators.put(RESIDENCE_ADDRESS_POSTAL_CODE_FIELD , validators);
	    // ����� ������������ ���������� - �������
	    validators = new ArrayList<FieldValidator>();
	    validators.add( new RegexpFieldValidator(".{0,100}", "���� [" + RESIDENCE_ADDRESS_PROVINCE_FIELD_DESCRIPTION  + "] �� ������ ��������� 100 ��������"));
	    fieldValidators.put(RESIDENCE_ADDRESS_PROVINCE_FIELD , validators);
	    // ����� ������������ ���������� - �����
	    validators = new ArrayList<FieldValidator>();
	    validators.add( new RegexpFieldValidator(".{0,100}", "���� [" + RESIDENCE_ADDRESS_DISTRICT_FIELD_DESCRIPTION  + "] �� ������ ��������� 100 ��������"));
	    fieldValidators.put(RESIDENCE_ADDRESS_DISTRICT_FIELD , validators);
	    // ����� ������������ ���������� - �����
	    validators = new ArrayList<FieldValidator>();
	    validators.add( new RegexpFieldValidator(".{0,100}", "���� [" + RESIDENCE_ADDRESS_CITY_FIELD_DESCRIPTION  + "] �� ������ ��������� 100 ��������"));
	    fieldValidators.put(RESIDENCE_ADDRESS_CITY_FIELD , validators);
	    // ����� ������������ ���������� - �����
	    validators = new ArrayList<FieldValidator>();
	    validators.add( new RegexpFieldValidator(".{0,100}", "���� [" + RESIDENCE_ADDRESS_STREET_FIELD_DESCRIPTION  + "] �� ������ ��������� 100 ��������"));
	    fieldValidators.put(RESIDENCE_ADDRESS_STREET_FIELD , validators);
	    // ����� ������������ ���������� - ���
	    validators = new ArrayList<FieldValidator>();
	    validators.add( new RegexpFieldValidator(".{0,10}", "���� [" + RESIDENCE_ADDRESS_HOUSE_FIELD_DESCRIPTION  + "] �� ������ ��������� 10 ��������"));
	    fieldValidators.put(RESIDENCE_ADDRESS_HOUSE_FIELD , validators);
	    // ����� ������������ ���������� - ������
	    validators = new ArrayList<FieldValidator>();
	    validators.add( new RegexpFieldValidator(".{0,10}", "���� [" + RESIDENCE_ADDRESS_BUILDING_FIELD_DESCRIPTION  + "] �� ������ ��������� 10 ��������"));
	    fieldValidators.put(RESIDENCE_ADDRESS_BUILDING_FIELD , validators);
	    // ����� ������������ ���������� - ��������
	    validators = new ArrayList<FieldValidator>();
	    validators.add( new RegexpFieldValidator(".{0,10}", "���� [" + RESIDENCE_ADDRESS_FLAT_FIELD_DESCRIPTION  + "] �� ������ ��������� 10 ��������"));
	    fieldValidators.put(RESIDENCE_ADDRESS_FLAT_FIELD , validators);
        // ����� ����������� �����
	    validators = new ArrayList<FieldValidator>();
        validators.add( new RegexpFieldValidator(".{0,70}", "���� [" + EMAIL_FIELD_DESCRIPTION + "] �� ������ ��������� 70 ��������"));
        fieldValidators.put(EMAIL_FIELD, validators);
	    // �������� �������
	    validators = new ArrayList<FieldValidator>();
        validators.add( new RegexpFieldValidator("[0-9]{2,3}[-][0-9]{2}[-][0-9]{2}", "���� [" + HOME_PHONE_FIELD_DESCRIPTION + "] ������ ����� ������ XXX-XX-XX ��� XX-XX-XX"));
        fieldValidators.put(HOME_PHONE_FIELD, validators);
        // ������� �������
	    validators = new ArrayList<FieldValidator>();
        validators.add( new RegexpFieldValidator("[0-9]{2,3}[-][0-9]{2}[-][0-9]{2}", "���� [" + JOB_PHONE_FIELD_DESCRIPTION + "] ������ ����� ������ XXX-XX-XX ��� XX-XX-XX"));
        fieldValidators.put(JOB_PHONE_FIELD, validators);
        // ��������� �������
	    validators = new ArrayList<FieldValidator>();
	    validators.add( new PhoneNumberValidator(MOBILE_PHONE_FIELD_DESCRIPTION));
        fieldValidators.put(MOBILE_PHONE_FIELD, validators);
        // ��������� ��������
	    validators = new ArrayList<FieldValidator>();
        validators.add( new RegexpFieldValidator(".{0,70}", "���� [" + MOBILE_OPERATOR_FIELD_DESCRIPTION + "] �� ������ ��������� 70 ��������"));
        fieldValidators.put(MOBILE_OPERATOR_FIELD, validators);
        // ���� ������ ������������
	    validators = new ArrayList<FieldValidator>();
        validators.add( dateValidator);
        fieldValidators.put(SERVICE_INSERTION_DATE_FIELD, validators);
        // ����� ��������
	    validators = new ArrayList<FieldValidator>();
        validators.add( new RegexpFieldValidator(".{0,16}", "���� [" + AGREEMENT_NUMBER_FIELD_DESCRIPTION + "] �� ������ ��������� 16 ��������"));
        fieldValidators.put(AGREEMENT_NUMBER_FIELD, validators);
        // ��������� ��������
	    validators = new ArrayList<FieldValidator>();
        validators.add( new RegexpFieldValidator(".{0,4}", "���� [" + AGREEMENT_OWNER_FIELD_DESCRIPTION + "] �� ������ ��������� 4 ��������"));
        fieldValidators.put(AGREEMENT_OWNER_FIELD, validators);
    }
}
