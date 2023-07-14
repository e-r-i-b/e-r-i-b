package com.rssl.phizic.web.log.csa;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.claims.forms.validators.RequiredMultiFieldValidator;
import com.rssl.phizic.web.log.MessageLogForm;

/**
 * @author vagin
 * @ created 18.10.2012
 * @ $Author$
 * @ $Revision$
 * Форма журнала сообщений ЦСА
 */
public class ViewCSAMessageLogForm extends MessageLogForm
{
	public static final Form CSA_MESSAGE_LOG_FILTER_FORM = createFilterForm();

	protected static Form createFilterForm()
	{
		FormBuilder formBuilder = getPartlyFormBuilder();

		FieldBuilder fieldBuilder;

		DateParser dateParser = new DateParser(DATESTAMP_FORMAT);
		DateParser timeParser = new DateParser(TIMESTAMP_FORMAT);

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("logUID");
		fieldBuilder.setDescription("Идентификатор операции логирования.");

		formBuilder.addField(fieldBuilder.build());

		Expression logUIDExpression = new RhinoExpression("form.logUID == null");

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName("fromDate");
		fieldBuilder.setDescription("Начальная дата");
		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator("Введите начальную дату");
		requiredFieldValidator.setEnabledExpression(logUIDExpression);
		DateFieldValidator dateFieldValidator = new DateFieldValidator(DATESTAMP_FORMAT, "Дата должна быть в формате ДД.ММ.ГГГГ");
		dateFieldValidator.setEnabledExpression(logUIDExpression);
		fieldBuilder.addValidators(requiredFieldValidator, dateFieldValidator);
		fieldBuilder.setParser(dateParser);

		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName("fromTime");
		fieldBuilder.setDescription("Начальное время");
		dateFieldValidator = new DateFieldValidator(TIMESTAMP_FORMAT, "Время должно быть в формате ЧЧ:ММ");
		dateFieldValidator.setEnabledExpression(logUIDExpression);
		fieldBuilder.setValidators(new FieldValidator[]{dateFieldValidator});
		fieldBuilder.setParser(timeParser);

		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName("toDate");
		fieldBuilder.setDescription("Конечная дата");
		requiredFieldValidator = new RequiredFieldValidator("Введите конечную дату");
		requiredFieldValidator.setEnabledExpression(logUIDExpression);
		dateFieldValidator = new DateFieldValidator(DATESTAMP_FORMAT, "Дата должна быть в формате ДД.ММ.ГГГГ");
		dateFieldValidator.setEnabledExpression(logUIDExpression);
		fieldBuilder.addValidators(requiredFieldValidator, dateFieldValidator);
		fieldBuilder.setParser(dateParser);

		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName("toTime");
		fieldBuilder.setDescription("Конечное время");
		dateFieldValidator = new DateFieldValidator(TIMESTAMP_FORMAT, "Время должно быть в формате ЧЧ:ММ");
		dateFieldValidator.setEnabledExpression(logUIDExpression);
		fieldBuilder.setValidators(new FieldValidator[]{dateFieldValidator});
		fieldBuilder.setParser(timeParser);

		formBuilder.addField(fieldBuilder.build());

		DateTimeCompareValidator dateTimeCompareValidator = new DateTimeCompareValidator();
		dateTimeCompareValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.LESS_EQUAL);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_DATE, "fromDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_TIME, "fromTime");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_DATE, "toDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_TIME, "toTime");
		dateTimeCompareValidator.setMessage("Конечная дата должна быть больше либо равна начальной!");
		dateTimeCompareValidator.setEnabledExpression(logUIDExpression);

		formBuilder.setFormValidators(dateTimeCompareValidator);

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
		fieldBuilder.setName(DOCUMENT_NUMBER_FIELD_NAME);
		fieldBuilder.setDescription("Номер");
		fieldBuilder.addValidators(
            new RegexpFieldValidator("\\d*", "Поле номер документа должно содержать только цифры"),
            new RegexpFieldValidator(".{0,16}", "Поле номер документа не должно превышать 16 цифр")
				);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("departmentCode");
		fieldBuilder.setDescription("Код ТБ");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("promoterId");
		fieldBuilder.setDescription("Идентификатор промоутера");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("mGUID");
		fieldBuilder.setDescription("mGUID");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("requestState");
		fieldBuilder.setDescription("Результат");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("ipAddress");
		fieldBuilder.setDescription("IP-адрес");
		fieldBuilder.setValidators(new RegexpFieldValidator("^\\d{0,3}.\\d{0,3}.\\d{0,3}.\\d{0,3}$","Неверный формат ip-адреса"));
		formBuilder.addField(fieldBuilder.build());

		//вычисляемое вспомогательное поле
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("searchInMonthInterval");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("Разрешен ли поиск в интервале месяца");
		fieldBuilder.setValueExpression(new RhinoExpression("(form." + CLIENT_FIO_FIELD_NAME + " !='' && form.birthday != null && form." + DOCUMENT_NUMBER_FIELD_NAME + " !='')" +
				"|| (form." + GUEST_LOG_NAME + "&& form." + PHONE_NUMBER_NAME + "!='')"));
		formBuilder.addField(fieldBuilder.build());

		//вычисляемое вспомогательное поле
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("searchInDayInterval");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("Разрешен ли поиск в интервале дня");
		fieldBuilder.setValueExpression(new RhinoExpression("form." + CLIENT_FIO_FIELD_NAME + " !='' || form." + DOCUMENT_NUMBER_FIELD_NAME + " !=''"));
		formBuilder.addField(fieldBuilder.build());

		//вычисляемое вспомогательное поле
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("searchInHourInterval");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("Разрешен ли поиск в интервале часа");
		fieldBuilder.setValueExpression(new RhinoExpression("form." + CLIENT_FIO_FIELD_NAME + " =='' && form." + DOCUMENT_NUMBER_FIELD_NAME + " ==''"));
		formBuilder.addField(fieldBuilder.build());

		DateTimePeriodMultiFieldValidator monthPeriodMultiFieldValidator = new DateTimePeriodMultiFieldValidator();
		monthPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_DATE_FROM,"fromDate");
		monthPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_TIME_FROM,"fromTime");
		monthPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_DATE_TO,"toDate");
		monthPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_TIME_TO,"toTime");
		monthPeriodMultiFieldValidator.setTypePeriod(DateTimePeriodMultiFieldValidator.MONTH_TYPE_PERIOD);
		monthPeriodMultiFieldValidator.setEnabledExpression(new RhinoExpression("Boolean(form.searchInMonthInterval) && form.logUID == null"));
		monthPeriodMultiFieldValidator.setLengthPeriod(1);
		monthPeriodMultiFieldValidator.setMessage("Пожалуйста, укажите период не более 30 дней.");

		//Если заполнено хотябы одно из полей, то ограничение в один день
		Expression requiredMultiFieldNotNull = new RhinoExpression("!Boolean(form.searchInMonthInterval) && Boolean(form.searchInDayInterval) && form.logUID == null");
		DateTimePeriodMultiFieldValidator dayPeriodMultiFieldValidator = new DateTimePeriodMultiFieldValidator();
		dayPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_DATE_FROM,"fromDate");
		dayPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_TIME_FROM,"fromTime");
		dayPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_DATE_TO,"toDate");
		dayPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_TIME_TO,"toTime");
		dayPeriodMultiFieldValidator.setTypePeriod(DateTimePeriodMultiFieldValidator.DAY_TYPE_PERIOD);
		dayPeriodMultiFieldValidator.setEnabledExpression(requiredMultiFieldNotNull);
		dayPeriodMultiFieldValidator.setMessage("Пожалуйста, укажите период не более одного дня или заполните поля: клиент, паспорт, дата рождения, период.");

		//Если все поля не заполнены, то ограничение один час
		Expression requiredMultiFieldNull = new RhinoExpression("Boolean(form.searchInHourInterval) && form.logUID == null");
		DateTimePeriodMultiFieldValidator hourPeriodMultiFieldValidator = new DateTimePeriodMultiFieldValidator();
		hourPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_DATE_FROM,"fromDate");
		hourPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_TIME_FROM,"fromTime");
		hourPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_DATE_TO,"toDate");
		hourPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_TIME_TO,"toTime");
		hourPeriodMultiFieldValidator.setTypePeriod(DateTimePeriodMultiFieldValidator.HOUR_TYPE_PERIOD);
		hourPeriodMultiFieldValidator.setEnabledExpression(requiredMultiFieldNull);
		hourPeriodMultiFieldValidator.setMessage("Пожалуйста, укажите период не более 1 часа.");

		RequiredMultiFieldValidator requiredMultiFieldValidator = new RequiredMultiFieldValidator();
		requiredMultiFieldValidator.setBinding(CLIENT_FIO_FIELD_NAME, CLIENT_FIO_FIELD_NAME);
		requiredMultiFieldValidator.setBinding(DOCUMENT_NUMBER_FIELD_NAME,DOCUMENT_NUMBER_FIELD_NAME);
		requiredMultiFieldValidator.setBinding(APPLICATION_FIELD_NAME,APPLICATION_FIELD_NAME);
		requiredMultiFieldValidator.setEnabledExpression(new RhinoExpression("!form."+GUEST_LOG_NAME));
		requiredMultiFieldValidator.setMessage("Пожалуйста, заполните одно из полей фильтра: ФИО клиента, номер документа, удостоверяющего личность, приложение.");
		//гостевой журнал
		RequiredMultiFieldValidator requiredFieldValidatorGuest = new RequiredMultiFieldValidator();
		requiredFieldValidatorGuest.setBinding(CLIENT_FIO_FIELD_NAME, CLIENT_FIO_FIELD_NAME);
		requiredFieldValidatorGuest.setBinding(DOCUMENT_NUMBER_FIELD_NAME, DOCUMENT_NUMBER_FIELD_NAME);
		requiredFieldValidatorGuest.setBinding(PHONE_NUMBER_NAME, PHONE_NUMBER_NAME);
		requiredFieldValidatorGuest.setEnabledExpression(new RhinoExpression("form."+GUEST_LOG_NAME));
		requiredFieldValidatorGuest.setMessage("Пожалуйста, заполните поля фильтра: ФИО клиента и номер документа, удостоверяющего личность или номер телефона.");

		//ФИО+ДУЛ должны быть заполнены вместе для гостя.
		RequiredAllMultiFieldStringValidator requiredFIODocGuestValidator = new RequiredAllMultiFieldStringValidator();
		requiredFIODocGuestValidator.setBinding(CLIENT_FIO_FIELD_NAME, CLIENT_FIO_FIELD_NAME);
		requiredFIODocGuestValidator.setBinding(DOCUMENT_NUMBER_FIELD_NAME, DOCUMENT_NUMBER_FIELD_NAME);
		requiredFIODocGuestValidator.setEnabledExpression(new RhinoExpression("form."+GUEST_LOG_NAME+" && (form."+PHONE_NUMBER_NAME+"=='' && (form."+DOCUMENT_NUMBER_FIELD_NAME+"!='' || form."+CLIENT_FIO_FIELD_NAME+"!=''))"));
		requiredFIODocGuestValidator.setMessage("Пожалуйста, для фильтрации заполните оба поля ФИО клиента и номер документа.");

		formBuilder.addFormValidators(monthPeriodMultiFieldValidator, dayPeriodMultiFieldValidator,
									  hourPeriodMultiFieldValidator, requiredMultiFieldValidator,
									  requiredFieldValidatorGuest, requiredFIODocGuestValidator);

		return formBuilder.build();
	}
}
