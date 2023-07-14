package com.rssl.phizic.web.ext.sbrf.logging;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.claims.forms.validators.RequiredMultiFieldValidator;
import com.rssl.phizic.web.log.DownloadLogFilterBaseForm;

/**
 * @author Erkin
 * @ created 04.04.2011
 * @ $Author$
 * @ $Revision$
 */
public class DownloadCommonLogForm extends DownloadLogFilterBaseForm
{
	/**
	 * 7 дней в миллисекундах
	 */
	public static final String CLIENT_FIO_FIELD_NAME = "fio";
	public static final String DOCUMENT_SERIES_FIELD_NAME = "series";
	public static final String DOCUMENT_NUMBER_FIELD_NAME = "number";
	public static final String LOGIN_ID_FIELD_NAME = "loginId";
	public static final String APPLICATION_FIELD_NAME = "application";
	public static final String SESSION_ID_FIELD_NAME = "sessionId";

	private static final int MAX_DATE_SPAN = 7 * 24 * 3600 * 1000;

	static final Form FILTER_FORM = createFilterForm();

	private Long clientId = null;

	///////////////////////////////////////////////////////////////////////////
	
	public Long getClientId()
	{
		return clientId;
	}

	public void setClientId(Long clientId)
	{
		this.clientId = clientId;
	}

	private static Form createFilterForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		@SuppressWarnings({"TooBroadScope"})
		FieldBuilder fieldBuilder;

		addLogTypeFilters(formBuilder);

		addUserFilters(formBuilder);

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
		fieldBuilder.setName("withChildren");
		fieldBuilder.setDescription("Выбран вместе с подчиненными");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(APPLICATION_FIELD_NAME);
		fieldBuilder.setDescription("Приложение");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(SESSION_ID_FIELD_NAME);
		fieldBuilder.setDescription("Идентификатор сессии");
		formBuilder.addField(fieldBuilder.build());

		addDateFilters(formBuilder);

		return formBuilder.build();
	}

	protected static void addLogTypeFilters(FormBuilder formBuilder)
	{
		@SuppressWarnings({"TooBroadScope"})
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("showUserLog");
		fieldBuilder.setType("boolean");
		fieldBuilder.setDescription("Показать записи из журнала действий пользователя");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("showSystemLog");
		fieldBuilder.setType("boolean");
		fieldBuilder.setDescription("Показать записи из системного журнала");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("showMessageLog");
		fieldBuilder.setType("boolean");
		fieldBuilder.setDescription("Показать записи из журнала обмена сообщениями");
		formBuilder.addField(fieldBuilder.build());
	}

	private static void addUserFilters(FormBuilder formBuilder)
	{
		@SuppressWarnings({"TooBroadScope"})
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(CLIENT_FIO_FIELD_NAME);
		fieldBuilder.setDescription("Клиент");
		fieldBuilder.addValidators(new RegexpFieldValidator("[а-яА-ЯёЁa-zA-Z- ]{3,255}","Пожалуйста, укажите ФИО клиента не менее 3 символов, содержащее буквы русского или латинского алфавита, дефис или пробел"));
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

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(LOGIN_ID_FIELD_NAME);
		fieldBuilder.setDescription("Идентификатор пользователя");
		fieldBuilder.addValidators(new RegexpFieldValidator("^\\d*$","Идентификатор пользователя может содержать только числа"));
		formBuilder.addField(fieldBuilder.build());

		//Если заполнено хотябы одно из полей, то ограничение в один день
		Expression requiredMultiFieldNotNull = new RhinoExpression("form."+SESSION_ID_FIELD_NAME+" == '' && (" +
																   "form."+CLIENT_FIO_FIELD_NAME+" != '' || "+
														           "form."+DOCUMENT_SERIES_FIELD_NAME+" != '' || "+
				                                                   "form."+DOCUMENT_NUMBER_FIELD_NAME+" != '' || "+
										                           "form."+LOGIN_ID_FIELD_NAME+" != '')");
		DateTimePeriodMultiFieldValidator dayPeriodMultiFieldValidator = new DateTimePeriodMultiFieldValidator();
		dayPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_DATE_FROM,"fromDate");
		dayPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_TIME_FROM,"fromTime");
		dayPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_DATE_TO,"toDate");
		dayPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_TIME_TO,"toTime");
		dayPeriodMultiFieldValidator.setTypePeriod(DateTimePeriodMultiFieldValidator.DAY_TYPE_PERIOD);
		dayPeriodMultiFieldValidator.setEnabledExpression(requiredMultiFieldNotNull);
		dayPeriodMultiFieldValidator.setMessage("Пожалуйста, укажите период не более 1 дня");

		//Если все поля не заполнены кроме приложения, то ограничение один час
		Expression requiredMultiFieldNull = new RhinoExpression("form."+SESSION_ID_FIELD_NAME+" == '' && "+
																"form."+CLIENT_FIO_FIELD_NAME+" == '' && "+
   												                "form."+DOCUMENT_SERIES_FIELD_NAME+" == '' && "+
				                                                "form."+DOCUMENT_NUMBER_FIELD_NAME+" == '' && "+
										                        "form."+LOGIN_ID_FIELD_NAME+" == '' && "+
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
		requiredMultiFieldValidator.setBinding(APPLICATION_FIELD_NAME,APPLICATION_FIELD_NAME);
		requiredMultiFieldValidator.setBinding(SESSION_ID_FIELD_NAME,SESSION_ID_FIELD_NAME);
		requiredMultiFieldValidator.setMessage("Пожалуйста, заполните одно из полей фильтра: ФИО клиента, LOGIN_ID, серию или номер документа, удостоверяющего личность, приложение");

		formBuilder.addFormValidators(dayPeriodMultiFieldValidator,
									  hourPeriodMultiFieldValidator,
									  requiredMultiFieldValidator);

	}

	@SuppressWarnings({"OverlyLongMethod"})
	protected static void addDateFilters(FormBuilder formBuilder)
	{
		DateParser dateParser = new DateParser(DATESTAMP_FORMAT);
		DateParser timeParser = new DateParser(TIMESTAMP_FORMAT);

		@SuppressWarnings({"TooBroadScope"})
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName("fromDate");
		fieldBuilder.setDescription("Начальная дата периода");
		// Убираем умолчательный валидатор даты типа dd.MM.yy
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(
				new RequiredFieldValidator("Введите начальную дату периода"),
				new DateFieldValidator(DATESTAMP_FORMAT, "Дата начала периода должна быть в формате ДД.ММ.ГГГГ"));
		fieldBuilder.setParser(dateParser);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName("fromTime");
		fieldBuilder.setDescription("Начальное время периода");
		// Убираем умолчательный валидатор даты типа dd.MM.yy
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new DateFieldValidator(TIMESTAMP_FORMAT, "Время начала периода должно быть в формате ЧЧ:ММ:CC"));
		fieldBuilder.setParser(timeParser);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName("toDate");
		fieldBuilder.setDescription("Конечная дата периода");
		// Убираем умолчательный валидатор даты типа dd.MM.yy
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(
				new RequiredFieldValidator("Введите конечную дату периода"),
				new DateFieldValidator(DATESTAMP_FORMAT, "Дата окончания периода должна быть в формате ДД.ММ.ГГГГ"));
		fieldBuilder.setParser(dateParser);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName("toTime");
		fieldBuilder.setDescription("Конечное время периода");
		// Убираем умолчательный валидатор даты типа dd.MM.yy
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new DateFieldValidator(TIMESTAMP_FORMAT, "Время окончания периода должно быть в формате ЧЧ:ММ:CC"));
		fieldBuilder.setParser(timeParser);
		formBuilder.addField(fieldBuilder.build());

		DateTimeCompareValidator dateTimeCompareValidator = new DateTimeCompareValidator();
		dateTimeCompareValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.LESS_EQUAL);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_DATE, "fromDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_TIME, "fromTime");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_DATE, "toDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_TIME, "toTime");
		dateTimeCompareValidator.setMessage("Конечная дата должна быть больше либо равна начальной!");

		formBuilder.addFormValidators(dateTimeCompareValidator);
	}
}
