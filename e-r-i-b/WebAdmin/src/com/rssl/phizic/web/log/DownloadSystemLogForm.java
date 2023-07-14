package com.rssl.phizic.web.log;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.claims.forms.validators.RequiredMultiFieldValidator;

/**
 * @author Omeliyanchuk
 * @ created 27.06.2007
 * @ $Author$
 * @ $Revision$
 */

public class DownloadSystemLogForm extends DownloadLogFilterBaseForm
{
	public static final String CLIENT_FIO_FIELD_NAME = "fio";
	public static final String DOCUMENT_SERIES_FIELD_NAME = "series";
	public static final String DOCUMENT_NUMBER_FIELD_NAME = "number";
	public static final String LOGIN_ID_FIELD_NAME = "loginId";
	public static final String IP_ADDRES_FIELD_NAME = "ipAddres";
	public static final String APPLICATION_FIELD_NAME = "application";
	public static final String GUEST_LOG_NAME = "isGuestLog";
	public static final String PHONE_NUMBER_NAME = "phoneNumber";

	public static Form SYSTEM_LOG_FILTER_FORM = createFilterForm();

	protected static final FormBuilder getPartlyFormBuilder()
	{
		FormBuilder formBuilder = new FormBuilder();

		DateParser dateParser = new DateParser(DATESTAMP_FORMAT);
		DateParser timeParser = new DateParser(TIMESTAMP_FORMAT);
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName("fromDate");
		fieldBuilder.setDescription("Начальная дата");
		fieldBuilder.addValidators(	new RequiredFieldValidator("Введите начальную дату"),
									new DateFieldValidator(DATESTAMP_FORMAT, "Дата должна быть в формате ДД.ММ.ГГГГ"));
		fieldBuilder.setParser(dateParser);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName("fromTime");
		fieldBuilder.setDescription("Начальное время");
		fieldBuilder.setValidators(new DateFieldValidator(TIMESTAMP_FORMAT, "Время должно быть в формате ЧЧ:ММ:CC"));
		fieldBuilder.setParser(timeParser);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName("toDate");
		fieldBuilder.setDescription("Конечная дата");
		fieldBuilder.addValidators( new RequiredFieldValidator("Введите конечную дату"),
									new DateFieldValidator(DATESTAMP_FORMAT, "Дата должна быть в формате ДД.ММ.ГГГГ"));
		fieldBuilder.setParser(dateParser);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName("toTime");
		fieldBuilder.setDescription("Конечное время");
		fieldBuilder.setValidators(new DateFieldValidator(TIMESTAMP_FORMAT, "Время должно быть в формате ЧЧ:ММ:CC"));
		fieldBuilder.setParser(timeParser);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(APPLICATION_FIELD_NAME);
		fieldBuilder.setDescription("Приложение");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("source");
		fieldBuilder.setDescription("Модуль");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(IP_ADDRES_FIELD_NAME);
		fieldBuilder.setDescription("IP-адрес");
		fieldBuilder.setValidators(new RegexpFieldValidator("^\\d{0,3}.\\d{0,3}.\\d{0,3}.\\d{0,3}$","Неверный формат ip-адреса"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("maxRows");
		fieldBuilder.setDescription("Максимальное количество записей");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("errorId");
		fieldBuilder.setDescription("Идентификатор ошибки");
		fieldBuilder.setValidators(new RegexpFieldValidator("^\\d*$","Идентификатор ошибки может содержать только числа"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("messageType");
		fieldBuilder.setDescription("Тип сообщения");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("messageWord");
		fieldBuilder.setDescription("Слово в сообщении");
		formBuilder.addField(fieldBuilder.build());

		DateTimeCompareValidator dateTimeCompareValidator = new DateTimeCompareValidator();
		dateTimeCompareValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.LESS_EQUAL);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_DATE, "fromDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_TIME, "fromTime");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_DATE, "toDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_TIME, "toTime");
		dateTimeCompareValidator.setMessage("Конечная дата должна быть больше либо равна начальной!");

		formBuilder.setFormValidators(dateTimeCompareValidator);

		return formBuilder;
	}
	private static Form createFilterForm()
	{
		FormBuilder formBuilder = getPartlyFormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(GUEST_LOG_NAME);
		fieldBuilder.setType("boolean");
		fieldBuilder.setDescription("Гостевой СБОЛ");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(PHONE_NUMBER_NAME);
		fieldBuilder.setDescription("Номер телефона");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("^7.*", "Номер телефона должен начинаться с 7"),
				new RegexpFieldValidator("\\d{11}", "Номер телефона должен состоять из 11 цифр")
				);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(LOGIN_ID_FIELD_NAME);
		fieldBuilder.setDescription("Идентификатор пользователя");
		fieldBuilder.setValidators(new RegexpFieldValidator("^\\d*$", "Идентификатор пользователя может содержать только числа"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(CLIENT_FIO_FIELD_NAME);
		fieldBuilder.setDescription("Клиент");
		fieldBuilder.addValidators(new RegexpFieldValidator("[а-яА-ЯёЁa-zA-Z- ]{3,255}","Пожалуйста, укажите ФИО клиента не менее 3 символов, содержащее буквы русского или латинского алфавита, дефис или пробел"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Наименование департамента");
		fieldBuilder.setName("departmentName");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());		

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("departmentId");
		fieldBuilder.setDescription("Подразделение");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("TB");
		fieldBuilder.setDescription("Подразделение");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("OSB");
		fieldBuilder.setDescription("Подразделение");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("VSP");
		fieldBuilder.setDescription("Подразделение");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("birthday");
		fieldBuilder.setType("date");
		fieldBuilder.setDescription("Дата рождения");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(DOCUMENT_SERIES_FIELD_NAME);
		fieldBuilder.setDescription("Серия");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,16}", "Поле серия документа не должно превышать 16 символов"),
				new RegexpFieldValidator("[^<>!#$%^&*{}|\\]\\[''\"~=]*","Поле серия документа не должно содержать спецсимволов")
				);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(DOCUMENT_NUMBER_FIELD_NAME);
		fieldBuilder.setDescription("Номер");
		fieldBuilder.addValidators(
	            new RegexpFieldValidator("\\d*", "Поле номер документа должно содержать только цифры"),
	            new RegexpFieldValidator(".{0,16}", "Поле номер документа не должно превышать 16 цифр")
				);
		formBuilder.addField(fieldBuilder.build());

		//Если заполнено хотя бы одно из полей, то ограничение в один день
		Expression requiredMultiFieldNotNull = new RhinoExpression("form."+GUEST_LOG_NAME+" && ("+
																       "form."+PHONE_NUMBER_NAME+" != '' || "+
																	   "form."+DOCUMENT_NUMBER_FIELD_NAME+" != '' || " +
																	   "form."+CLIENT_FIO_FIELD_NAME+" != ''"+
																   ") || !form."+GUEST_LOG_NAME+" && ("+
																	   "form."+CLIENT_FIO_FIELD_NAME+" != '' || "+
																	   "form."+DOCUMENT_SERIES_FIELD_NAME+" != '' || "+
																	   "form."+DOCUMENT_NUMBER_FIELD_NAME+" != '' || "+
																	   "form."+LOGIN_ID_FIELD_NAME+" != '' || "+
																	   "form."+IP_ADDRES_FIELD_NAME+" != ''"+
				                                                   ")");
		DateTimePeriodMultiFieldValidator dayPeriodMultiFieldValidator = new DateTimePeriodMultiFieldValidator();
		dayPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_DATE_FROM,"fromDate");
		dayPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_TIME_FROM,"fromTime");
		dayPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_DATE_TO,"toDate");
		dayPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_TIME_TO,"toTime");
		dayPeriodMultiFieldValidator.setTypePeriod(DateTimePeriodMultiFieldValidator.DAY_TYPE_PERIOD);
		dayPeriodMultiFieldValidator.setEnabledExpression(requiredMultiFieldNotNull);
		dayPeriodMultiFieldValidator.setMessage("Пожалуйста, укажите период не более 1 дня");

		//Если все поля не заполнены, то ограничение один час
		Expression requiredMultiFieldNull = new RhinoExpression("!form."+GUEST_LOG_NAME+" &&"+
																"form."+CLIENT_FIO_FIELD_NAME+" == '' && "+
																"form."+DOCUMENT_SERIES_FIELD_NAME+" == '' && "+
																"form."+DOCUMENT_NUMBER_FIELD_NAME+" == '' && "+
																"form."+LOGIN_ID_FIELD_NAME+" == '' && "+
																"form."+IP_ADDRES_FIELD_NAME+" == '' && "+
																"form."+APPLICATION_FIELD_NAME+" != ''");
		DateTimePeriodMultiFieldValidator hourPeriodMultiFieldValidator = new DateTimePeriodMultiFieldValidator();
		hourPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_DATE_FROM,"fromDate");
		hourPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_TIME_FROM,"fromTime");
		hourPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_DATE_TO,"toDate");
		hourPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_TIME_TO,"toTime");
		hourPeriodMultiFieldValidator.setTypePeriod(DateTimePeriodMultiFieldValidator.HOUR_TYPE_PERIOD);
		hourPeriodMultiFieldValidator.setEnabledExpression(requiredMultiFieldNull);
		hourPeriodMultiFieldValidator.setMessage("Пожалуйста, укажите период не более 1 часа");

		RequiredMultiFieldValidator requiredMultiFieldValidator = new RequiredMultiFieldValidator();
		requiredMultiFieldValidator.setBinding(CLIENT_FIO_FIELD_NAME, CLIENT_FIO_FIELD_NAME);
		requiredMultiFieldValidator.setBinding(DOCUMENT_SERIES_FIELD_NAME,DOCUMENT_SERIES_FIELD_NAME);
		requiredMultiFieldValidator.setBinding(DOCUMENT_NUMBER_FIELD_NAME,DOCUMENT_NUMBER_FIELD_NAME);
		requiredMultiFieldValidator.setBinding(LOGIN_ID_FIELD_NAME,LOGIN_ID_FIELD_NAME);
		requiredMultiFieldValidator.setBinding(IP_ADDRES_FIELD_NAME,IP_ADDRES_FIELD_NAME);
		requiredMultiFieldValidator.setBinding(APPLICATION_FIELD_NAME,APPLICATION_FIELD_NAME);
		requiredMultiFieldValidator.setEnabledExpression(new RhinoExpression("!form."+GUEST_LOG_NAME));
		requiredMultiFieldValidator.setMessage("Пожалуйста, заполните одно из полей фильтра: ФИО клиента, Login_ID, серию или номер документа, удостоверяющего личность, приложение, IP-адрес");

		RequiredMultiFieldValidator requiredFieldValidatorGuest = new RequiredMultiFieldValidator();
		requiredFieldValidatorGuest.setBinding(CLIENT_FIO_FIELD_NAME, CLIENT_FIO_FIELD_NAME);
		requiredFieldValidatorGuest.setBinding(DOCUMENT_NUMBER_FIELD_NAME, DOCUMENT_NUMBER_FIELD_NAME);
		requiredFieldValidatorGuest.setBinding(PHONE_NUMBER_NAME, PHONE_NUMBER_NAME);
		requiredFieldValidatorGuest.setEnabledExpression(new RhinoExpression("form."+GUEST_LOG_NAME));
		requiredFieldValidatorGuest.setMessage("Пожалуйста, заполните поля фильтра: ФИО клиента и номер документа, удостоверяющего личность или номер телефона.");

		//ФИО+ДУЛ должны быть заполнены вместе для гостя.
		RequiredAllMultiFieldStringValidator requiredFIODocGuestValidator = new RequiredAllMultiFieldStringValidator();
		requiredFIODocGuestValidator.setBinding(CLIENT_FIO_FIELD_NAME, CLIENT_FIO_FIELD_NAME);
		requiredFIODocGuestValidator.setBinding(DOCUMENT_NUMBER_FIELD_NAME,DOCUMENT_NUMBER_FIELD_NAME);
		requiredFIODocGuestValidator.setEnabledExpression(new RhinoExpression("form."+GUEST_LOG_NAME+" && (form."+PHONE_NUMBER_NAME+"=='' && (form."+DOCUMENT_NUMBER_FIELD_NAME+"!='' || form."+CLIENT_FIO_FIELD_NAME+"!=''))"));
		requiredFIODocGuestValidator.setMessage("Пожалуйста, для фильтрации заполните оба поля ФИО клиента и номер документа.");

		formBuilder.addFormValidators(dayPeriodMultiFieldValidator,
									  hourPeriodMultiFieldValidator,
									  requiredMultiFieldValidator,
									  requiredFieldValidatorGuest,
									  requiredFIODocGuestValidator);

		return formBuilder.build();
	}
}
