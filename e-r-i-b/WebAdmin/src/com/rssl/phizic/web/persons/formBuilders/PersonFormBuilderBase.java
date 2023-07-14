package com.rssl.phizic.web.persons.formBuilders;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.EnumParser;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.phizic.gate.clients.IncognitoState;
import com.rssl.phizic.utils.StringHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Omeliyanchuk
 * @ created 05.12.2006
 * @ $Author$
 * @ $Revision$
 */

public abstract class PersonFormBuilderBase
{
		// Типы полей
    public static final String STRING_TYPE_NAME = StringType.INSTANCE.getName();
    public static final String DATE_TYPE_NAME   = DateType.INSTANCE.getName();

	// Поля форм
    protected static final String CLIENT_ID_FIELD                               = "clientId";
    protected static final String CLIENT_ID_FIELD_DESCRIPTION                   = "Идентификатор";

    public static final String SUR_NAME_FIELD                                   = "surName";
    public static final String SUR_NAME_FIELD_DESCRIPTION                       = "Фамилия";

    public static final String FIRST_NAME_FIELD                                 = "firstName";
    public static final String FIRST_NAME_FIELD_DESCRIPTION                     = "Имя";

    public static final String PATR_NAME_FIELD                                  = "patrName";
    public static final String PATR_NAME_FIELD_DESCRIPTION                      = "Отчество";

	public static final String LOGIN_FIELD                                      = "login";
    public static final String LOGIN_FIELD_DESCRIPTION                          = "Логин";

	public static final String DOCUMENT_TYPE_FIELD                              = "documentType";
    public static final String DOCUMENT_TYPE_FIELD_DESCRIPTION                  = "Вид документа";

    public static final String DOCUMENT_NAME_FIELD                              = "documentName";
    public static final String DOCUMENT_NAME_FIELD_DESCRIPTION                  = "Наименование документа";

    public static final String DOCUMENT_SERIES_FIELD                            = "documentSeries";
    public static final String DOCUMENT_SERIES_FIELD_DESCRIPTION                = "Серия";

    public static final String DOCUMENT_NUMBER_FIELD                            = "documentNumber";
    public static final String DOCUMENT_NUMBER_FIELD_DESCRIPTION                = "Номер";

	public static final String BIRTH_DAY_FIELD                                  = "birthDay";
	public static final String BIRTH_DAY_FIELD_DESCRIPTION                      = "Дата рождения";

	public static final String REGION_FIELD                                     = "region";
	public static final String REGION_FIELD_DESCRIPTION                         = "Номер тербанка";

	public static final String CARD_NUMBER_FIELD                                = "cardNumber";
	public static final String CARD_NUMBER_FIELD_DESCRIPTION                    = "Номер карты";

	public static final String EMAIL_FIELD                                   = "email";
	public static final String EMAIL_FIELD_DESCRIPTION                       = "Адрес электронной почты";

	public static final String INCOGNITO_FIELD                               = "incognito";
	public static final String INCOGNITO_FIELD_DESCRIPTION                   = "Статус приватности";

	public static final String MOBILE_PHONE_FIELD                            = "mobilePhone";
	public static final String MOBILE_PHONE_FIELD_DESCRIPTION                = "Мобильный телефон";

	public static final String MOBILE_OPERATOR_FIELD                         = "mobileOperator";
	public static final String MOBILE_OPERATOR_FIELD_DESCRIPTION             = "Мобильный оператор";

	public static final String PHONE_FIELD                            = "phone";
	public static final String PHONE_FIELD_DESCRIPTION                = "Номер телефона";

	//----------------------Для POS терминалов----------------------------------------------------------------
	public static final String CARD_TYPE_FIELD                                  = "cardType";
	public static final String CARD_TYPE_FIELD_DESCRIPTION                      = "Тип карты";

	public static final String TRANSACTION_DATE_FIELD                           = "transactionDate";
	public static final String TRANSACTION_DATE_FIELD_DESCRIPTION               = "Дата тарнзакции";

	public static final String TRANSACTION_TIME_FIELD                           = "transactionTime";
	public static final String TRANSACTION_TIME_FIELD_DESCRIPTION               = "Время тарнзакции";

	public static final String TERMINAL_NUMBER_FIELD                            = "terminalNumber";
	public static final String TERMINAL_NUMBER_FIELD_DESCRIPTION                = "Номер терминала";
	//--------------------------------------------------------------------------------------------------------

    protected static final String GENDER_FIELD                                  = "gender";
    protected static final String GENDER_FIELD_DESCRIPTION                      = "Пол";

    protected static final String RESIDENT_FIELD                                = "resident";
    protected static final String RESIDENT_FIELD_DESCRIPTION                    = "Резидент";

    protected static final String BIRTH_PLACE_FIELD                             = "birthPlace";
    protected static final String BIRTH_PLACE_FIELD_DESCRIPTION                 = "Место рождения";

//Документ, удостоверяющий личность
    protected static final String DOCUMENT_ID_FIELD                             = "documentId";
    protected static final String DOCUMENT_ID_FIELD_DESCRIPTION                 = "ID документа";


    protected static final String DOCUMENT_ISSUE_BY_CODE_FIELD                  = "documentIssueByCode";
    protected static final String DOCUMENT_ISSUE_BY_CODE_FIELD_DESCRIPTION      = "Код подразделения";

    protected static final String DOCUMENT_ISSUE_DATE_FIELD                     = "documentIssueDate";
    protected static final String DOCUMENT_ISSUE_DATE_FIELD_DESCRIPTION         = "Дата выдачи";

    protected static final String DOCUMENT_ISSUE_BY_FIELD                       = "documentIssueBy";
    protected static final String DOCUMENT_ISSUE_BY_FIELD_DESCRIPTION           = "Кем выдан";

//Документ, подтверждающий право на пребывание (проживание) в Российской Федерации
	protected static final String DOCUMENT_PROVE_ID_FIELD                             = "documentProveId";
    protected static final String DOCUMENT_PROVE_ID_FIELD_DESCRIPTION                 = "ID документа, подтверждающего право пребывание";

	protected static final String DOCUMENT_PROVE_TYPE_FIELD                           = "documentProveType";
    protected static final String DOCUMENT_PROVE_TYPE_FIELD_DESCRIPTION               = "Вид документа, подтверждающего право пребывание";

    protected static final String DOCUMENT_PROVE_NAME_FIELD                           = "documentProveName";
    protected static final String DOCUMENT_PROVE_NAME_FIELD_DESCRIPTION               = "Наименование документа, подтверждающего право пребывание";

    protected static final String DOCUMENT_PROVE_SERIES_FIELD                         = "documentProveSeries";
    protected static final String DOCUMENT_PROVE_SERIES_FIELD_DESCRIPTION             = "Серия документа, подтверждающего право пребывание";

    protected static final String DOCUMENT_PROVE_NUMBER_FIELD                         = "documentProveNumber";
    protected static final String DOCUMENT_PROVE_NUMBER_FIELD_DESCRIPTION             = "Номер документа, подтверждающего право пребывание";

    protected static final String DOCUMENT_PROVE_ISSUE_DATE_FIELD                     = "documentProveIssueDate";
    protected static final String DOCUMENT_PROVE_ISSUE_DATE_FIELD_DESCRIPTION         = "Дата выдачи, подтверждающего право пребывание";

	protected static final String DOCUMENT_PROVE_ISSUE_BY_FIELD                       = "documentProveIssueBy";
    protected static final String DOCUMENT_PROVE_ISSUE_BY_FIELD_DESCRIPTION           = "Кем выдан документ, подтверждающий право пребывание";

    protected static final String CITIZEN_FIELD                                 = "citizen";
    protected static final String CITIZEN_FIELD_DESCRIPTION                     = "Гражданство";

//Миграционная карта	
	protected static final String MIGRATORY_CARD_NUMBER_FIELD                   = "migratoryCardNumber";
	protected static final String MIGRATORY_CARD_NUMBER_FIELD_DESCRIPTION       = "Номер миграционной карты";

	protected static final String PENSION_CERTIFICATE_NUMBER_FIELD                   = "pensionCertificate";
	protected static final String PENSION_CERTIFICATE_NUMBER_FIELD_DESCRIPTION       = "Номер пенсионного удостоверения";

	protected static final String MIGRATORY_CARD_FROM_DATE_FIELD                = "migratoryCardFromDate";
	protected static final String MIGRATORY_CARD_FROM_DATE_FIELD_DESCRIPTION    = "Дата начала срока пребывания миграционной карты";

	protected static final String MIGRATORY_CARD_TIME_UP_DATE_FIELD                = "migratoryCardTimeUpDate";
	protected static final String MIGRATORY_CARD_TIME_UP_DATE_FIELD_DESCRIPTION    = "Дата окончания срока пребывания миграционной карты";

    protected static final String INN_FIELD                                     = "inn";
    protected static final String INN_FIELD_DESCRIPTION                         = "ИНН";

	protected static final String REGISTRATION_ADDRESS_FIELD                            = "registrationAddress";
    protected static final String REGISTRATION_ADDRESS_FIELD_DESCRIPTION                = "Адрес регистрации";

	protected static final String REGISTRATION_ADDRESS_POSTAL_CODE_FIELD                = "registrationPostalCode";
    protected static final String REGISTRATION_ADDRESS_POSTAL_CODE_FIELD_DESCRIPTION    = "Почтовый индекс";

    protected static final String REGISTRATION_ADDRESS_PROVINCE_FIELD                   = "registrationProvince";
    protected static final String REGISTRATION_ADDRESS_PROVINCE_FIELD_DESCRIPTION       = "Область";

	protected static final String REGISTRATION_ADDRESS_DISTRICT_FIELD                   = "registrationDistrict";
	protected static final String REGISTRATION_ADDRESS_DISTRICT_FIELD_DESCRIPTION       = "Район";

	protected static final String REGISTRATION_ADDRESS_CITY_FIELD                       = "registrationCity";
    protected static final String REGISTRATION_ADDRESS_CITY_FIELD_DESCRIPTION           = "Город";

 	protected static final String REGISTRATION_ADDRESS_STREET_FIELD                     = "registrationStreet";
	protected static final String REGISTRATION_ADDRESS_STREET_FIELD_DESCRIPTION         = "Улица";

	protected static final String REGISTRATION_ADDRESS_HOUSE_FIELD                      = "registrationHouse";
    protected static final String REGISTRATION_ADDRESS_HOUSE_FIELD_DESCRIPTION          = "Дом";

	protected static final String REGISTRATION_ADDRESS_BUILDING_FIELD                   = "registrationBuilding";
    protected static final String REGISTRATION_ADDRESS_BUILDING_FIELD_DESCRIPTION       = "Подъезд";

 	protected static final String REGISTRATION_ADDRESS_FLAT_FIELD                       = "registrationFlat";
	protected static final String REGISTRATION_ADDRESS_FLAT_FIELD_DESCRIPTION           = "Квартира";

	protected static final String RESIDENCE_ADDRESS_FIELD                               = "residenceAddress";
	protected static final String RESIDENCE_ADDRESS_FIELD_DESCRIPTION                   = "Адрес проживания";

	protected static final String RESIDENCE_ADDRESS_POSTAL_CODE_FIELD                   = "residencePostalCode";
    protected static final String RESIDENCE_ADDRESS_POSTAL_CODE_FIELD_DESCRIPTION       = "Почтовый ндекс";

    protected static final String RESIDENCE_ADDRESS_PROVINCE_FIELD                      = "residenceProvince";
    protected static final String RESIDENCE_ADDRESS_PROVINCE_FIELD_DESCRIPTION          = "Область";

	protected static final String RESIDENCE_ADDRESS_DISTRICT_FIELD                      = "residenceDistrict";
	protected static final String RESIDENCE_ADDRESS_DISTRICT_FIELD_DESCRIPTION          = "Район";

	protected static final String RESIDENCE_ADDRESS_CITY_FIELD                          = "residenceCity";
    protected static final String RESIDENCE_ADDRESS_CITY_FIELD_DESCRIPTION              = "Город";

 	protected static final String RESIDENCE_ADDRESS_STREET_FIELD                        = "residenceStreet";
	protected static final String RESIDENCE_ADDRESS_STREET_FIELD_DESCRIPTION            = "Улица";

	protected static final String RESIDENCE_ADDRESS_HOUSE_FIELD                         = "residenceHouse";
    protected static final String RESIDENCE_ADDRESS_HOUSE_FIELD_DESCRIPTION             = "Дом";

	protected static final String RESIDENCE_ADDRESS_BUILDING_FIELD                      = "residenceBuilding";
    protected static final String RESIDENCE_ADDRESS_BUILDING_FIELD_DESCRIPTION          = "Подъезд";

 	protected static final String RESIDENCE_ADDRESS_FLAT_FIELD                          = "residenceFlat";
	protected static final String RESIDENCE_ADDRESS_FLAT_FIELD_DESCRIPTION              = "Квартира";

    protected static final String HOME_PHONE_FIELD                              = "homePhone";
    protected static final String HOME_PHONE_FIELD_DESCRIPTION                  = "Домашний телефон";

    protected static final String JOB_PHONE_FIELD                               = "jobPhone";
    protected static final String JOB_PHONE_FIELD_DESCRIPTION                   = "Рабочий телефон";

    protected static final String SMSFORMAT_FIELD                               = "SMSFormat";
    protected static final String SMSFORMAT_FIELD_DESCRIPTION                   = "Формат SMS сообщений";

	protected static final String PIN_ENVELOPE_NUMBER_FIELD                     = "pinEnvelopeNumber";
	protected static final String PIN_ENVELOPE_OLD_NUMBER_FIELD                 = "oldPinEnvelopeNumber";
	protected static final String PIN_ENVELOPE_NUMBER_FIELD_DESCRIPTION         = "Номер ПИН-конверта";

    protected static final String SERVICE_INSERTION_DATE_FIELD                  = "serviceInsertionDate";
    protected static final String SERVICE_INSERTION_DATE_FIELD_DESCRIPTION      = "Дата начала обслуживания";

    protected static final String PROLONGATION_REJECTION_DATE_FIELD             = "prolongationRejectionDate";
    protected static final String PROLONGATION_REJECTION_DATE_FIELD_DESCRIPTION = "Дата расторжения договора";

    protected static final String MESSAGE_SERVICE_FIELD                         = "messageService";
    protected static final String MESSAGE_SERVICE_FIELD_DESCRIPTION             = "Способ доставки подтверждений";

    protected static final String AGREEMENT_NUMBER_FIELD                        = "agreementNumber";
    protected static final String AGREEMENT_NUMBER_FIELD_DESCRIPTION            = "Номер договора";

    protected static final String AGREEMENT_OWNER_FIELD                         = "agreementOwner";
    protected static final String AGREEMENT_OWNER_FIELD_DESCRIPTION             = "Отделение договора";

	protected static final String REGION_CODE_FIELD                             = "region";
	protected static final String REGION_CODE_FIELD_DESCRIPTION                 = "Отделение";

	protected static final String BRANCH_CODE_FIELD                             = "branchCode";
	protected static final String BRANCH_CODE_FIELD_DESCRIPTION                 = "Отделение";

	protected static final String DEPARTMENT_CODE_FIELD                         = "departmentCode";
	protected static final String DEPARTMENT_CODE_FIELD_DESCRIPTION             = "Отделение";

	protected static final String DEPARTMENT_ID_FIELD                         = "departmentId";
	protected static final String DEPARTMENT_ID_FIELD_DESCRIPTION             = "Подразделение";

    protected static final String CONTRACT_CANCELLATION_COUSE_FIELD             = "contractCancellationCouse";
    protected static final String CONTRACT_CANCELLATION_COUSE_FIELD_DESCRIPTION = "Причина расторжения договора";

	protected static final String SECRET_WORD_FIELD                             = "secretWord";
	protected static final String SECRET_WORD_DESCRIPTION                       = "Кодовое слово";

	protected static final String SNILS_FIELD                                   = "SNILS";
	protected static final String SNILS_FIELD_DESCRIPTION                       = "Страховой Номер Индивидуального Лицевого Счёта";

	protected static final String SEGMENT_CODE_FIELD                            = "segmentCodeType";
	protected static final String SEGMENT_CODE_FIELD_DESCRIPTION                = "Статус клиента";

	protected static final String TARIF_PLAN_CODE_FIELD                         = "tarifPlanCodeType";
	protected static final String TARIF_PLAN_CODE_FIELD_DESCRIPTION             = "Тарифный план";

	protected static final String TARIF_PLAN_CONNECT_DATE_FIELD                 = "tarifPlanConnectionDate";
	protected static final String TARIF_PLAN_CONNECT_DATE_FIELD_DESCRIPTION     = "Дата подключения тарифного плана";

	protected static final String MANAGER_ID_FIELD                              = "managerId";
	protected static final String MANAGER_ID_FIELD_DESCRIPTION                  = "ID менеджера";

    // Map< имя поля, List<валидатор> >
    protected Map<String,List<FieldValidator>> fieldValidators = new HashMap<String,List<FieldValidator>>();

	public abstract Form buildForm();
	protected abstract void initializeValidators();

	PersonFormBuilderBase()
	{
		initializeValidators();		
	}

	protected Field buildIncognitoField()
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(INCOGNITO_FIELD);
		fieldBuilder.setDescription(INCOGNITO_FIELD_DESCRIPTION);
		fieldBuilder.setType(STRING_TYPE_NAME);
		fieldBuilder.setParser(new EnumParser<IncognitoState>(IncognitoState.class));
		return fieldBuilder.build();
	}

	protected Field buildField(String name, String description, String type)
	{
		return buildField(name, description, type, null);
	}

	protected Field buildField(String name, String description, String type, String enabledExpression)
    {
        FieldBuilder fieldBuilder = new FieldBuilder();

        fieldBuilder.setName(name);
        fieldBuilder.setDescription(description);
        fieldBuilder.setType(type);
	    if(getValidator(name)!=null)
            fieldBuilder.setValidators( getValidator(name) );
	    else
	        fieldBuilder.setValidators( new FieldValidator[0] );

	    if (!StringHelper.isEmpty(enabledExpression))
	        fieldBuilder.setEnabledExpression(new RhinoExpression(enabledExpression));

        return fieldBuilder.build();
    }

	protected FieldValidator[] getValidator(String name)
	{
		List<FieldValidator> list = fieldValidators.get(name);
		if( (list != null) && (list.size()!=0) )
		{
			FieldValidator[] fields= new FieldValidator[list.size()];
			Object[] arr = fieldValidators.get(name).toArray();
			int i=0;
			for (Object o : arr)
			{
				fields[i] = (FieldValidator)o;
				i++;
			}

			return fields;
		}
		else return null;

	}
}
