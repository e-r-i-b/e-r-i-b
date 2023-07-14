package com.rssl.phizic.web.blockingrules;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.claims.forms.validators.IsCheckedMultiFieldValidator;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author vagin
 * @ created 21.08.2012
 * @ $Author$
 * @ $Revision$
 * Форма редактирования/создания правила блокировки
 */
public class EditBlockingRulesForm extends EditFormBase
{
	public static final Form FORM = createForm();

	private static Form createForm()
	{
		DateParser dateParser = new DateParser(EditConstants.DATESTAMP_FORMAT);
		DateParser timeParser = new DateParser(EditConstants.TIMESTAMP_FORMAT);

		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.NAME_FIELD);
		fieldBuilder.setDescription("Название");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator(), new RegexpFieldValidator("^.{0,100}$", "Вы неправильно указали название правила. Пожалуйста, введите не более 100 символов."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.DEPARTMENTS_FIELD);
		fieldBuilder.setDescription("Подразделение");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator(),
				new RegexpFieldValidator("^(\\d|\\?|\\*|\\,)*$", "Вы неправильно указали название подразделения. Пожалуйста, введите цифры или следующие символы: *,?,’,’."),
				new RegexpFieldValidator("^.{0,100}$", "Вы неправильно указали название подразделения. Пожалуйста, введите не более 100 символов."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.STATE_FIELD);
		fieldBuilder.setDescription("Состояние");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.RESUMING_TIME_FIELD);
		fieldBuilder.setDescription("Время восстановления доступа");
		fieldBuilder.setType("date");
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(),new DateFieldValidator(EditConstants.TIMESTAMP_FORMAT, "Время должно быть в формате ЧЧ:ММ"));
		fieldBuilder.setParser(timeParser);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.RESUMING_DATE_FIELD);
		fieldBuilder.setDescription("Дата восстановления доступа");
		fieldBuilder.setType("date");
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(),new DateFieldValidator(EditConstants.DATESTAMP_FORMAT, "Дата должна быть в формате ДД.ММ.ГГГГ"));
		fieldBuilder.setParser(dateParser);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.CONFIGURE_NOTIFICATION_FIELD);
		fieldBuilder.setDescription("Настроить уведомление");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.FROM_PUBLISH_FIELD);
		fieldBuilder.setDescription("Опубликовать с");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.configureNotification == true"));

		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.FROM_PUBLISH_DATE_FIELD);
		fieldBuilder.setDescription("Опубликовать с");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(
				new RequiredFieldValidator(EditConstants.WRONG_FROM_DATETIME_PUBLISH),
				new DateFieldValidator(EditConstants.DATESTAMP_FORMAT, EditConstants.WRONG_FROM_DATETIME_PUBLISH)
		);
		fieldBuilder.setParser(dateParser);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.fromPublish == true"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.FROM_PUBLISH_TIME_FIELD);
		fieldBuilder.setDescription("Опубликовать с");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(
				new RequiredFieldValidator(EditConstants.WRONG_FROM_TIME_PUBLISH),
				new DateFieldValidator(EditConstants.TIMESTAMP_FORMAT, EditConstants.WRONG_FROM_TIME_PUBLISH)
		);
		fieldBuilder.setParser(timeParser);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.fromPublish == true"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.TO_PUBLISH_FIELD);
		fieldBuilder.setDescription("Снять с публикации");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.configureNotification == true"));

		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.TO_PUBLISH_DATE_FIELD);
		fieldBuilder.setDescription("Снять с публикации");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(
				new RequiredFieldValidator(EditConstants.WRONG_TO_DATETIME_PUBLISH),
				new DateFieldValidator(EditConstants.DATESTAMP_FORMAT, EditConstants.WRONG_TO_DATETIME_PUBLISH)
		);
		fieldBuilder.setParser(dateParser);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.toPublish == true"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.TO_PUBLISH_TIME_FIELD);
		fieldBuilder.setDescription("Снять с публикации");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(
				new RequiredFieldValidator(EditConstants.WRONG_TO_TIME_PUBLISH),
				new DateFieldValidator(EditConstants.TIMESTAMP_FORMAT, EditConstants.WRONG_TO_TIME_PUBLISH)
		);
		fieldBuilder.setParser(timeParser);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.toPublish == true"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.FROM_REGISTRATION_DATE_FIELD);
		fieldBuilder.setDescription("Доступ будет ограничен с");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(),new DateFieldValidator(EditConstants.DATESTAMP_FORMAT, "Дата должна быть в формате ДД.ММ.ГГГГ"));
		fieldBuilder.setParser(dateParser);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.configureNotification == true"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.FROM_REGISTRATION_TIME_FIELD);
		fieldBuilder.setDescription("Доступ будет ограничен с");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(),new DateFieldValidator(EditConstants.TIMESTAMP_FORMAT, "Время должно быть в формате ЧЧ:ММ"));
		fieldBuilder.setParser(timeParser);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.configureNotification == true"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.TO_REGISTRATION_DATE_FIELD);
		fieldBuilder.setDescription("Доступ будет ограничен по");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(),new DateFieldValidator(EditConstants.DATESTAMP_FORMAT, "Дата должна быть в формате ДД.ММ.ГГГГ"));
		fieldBuilder.setParser(dateParser);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.configureNotification == true"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.TO_REGISTRATION_TIME_FIELD);
		fieldBuilder.setDescription("Доступ будет ограничен по");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(),new DateFieldValidator(EditConstants.TIMESTAMP_FORMAT, "Время должно быть в формате ЧЧ:ММ"));
		fieldBuilder.setParser(timeParser);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.configureNotification == true"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.APPLY_TO_ERIB_FIELD);
		fieldBuilder.setDescription("Правило действует в ЕРИБ");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.APPLY_TO_MAPI_FIELD);
		fieldBuilder.setDescription("Правило действует в mApi");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.APPLY_TO_ATM_FIELD);
		fieldBuilder.setDescription("Правило действует в atmApi");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.APPLY_TO_ERMB_FIELD);
		fieldBuilder.setDescription("Правило действует в ЕРМБ");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();

		RequiredFieldValidator useMapiRequiredValidator = new RequiredFieldValidator();
		useMapiRequiredValidator.setEnabledExpression(new RhinoExpression("form.applyToMAPI"));

		fieldBuilder.setName(EditConstants.USE_MAPI_MESSAGE_FIELD);
		fieldBuilder.setDescription("Причина блокировки mApi");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(useMapiRequiredValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();

		RequiredFieldValidator useAtmRequiredValidator = new RequiredFieldValidator();
		useAtmRequiredValidator.setEnabledExpression(new RhinoExpression("form.applyToATM"));

		fieldBuilder.setName(EditConstants.USE_ATM_MESSAGE_FIELD);
		fieldBuilder.setDescription("Причина блокировки atmApi");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(useAtmRequiredValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();

		RequiredFieldValidator useErmbRequiredValidator = new RequiredFieldValidator();
		useErmbRequiredValidator.setEnabledExpression(new RhinoExpression("form.applyToERMB"));

		fieldBuilder.setName(EditConstants.USE_ERMB_MESSAGE_FIELD);
		fieldBuilder.setDescription("Причина блокировки ЕРМБ");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(useErmbRequiredValidator);
		formBuilder.addField(fieldBuilder.build());

		RequiredFieldValidator eribRequiredValidator = new RequiredFieldValidator();
		eribRequiredValidator.setEnabledExpression(new RhinoExpression("form.applyToERIB"));

		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.ERIB_MESSAGE_FIELD);
		fieldBuilder.setDescription("Причина блокировки ЕРИБ");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(eribRequiredValidator, new RegexpFieldValidator("^(.|\\s){1,1024}$", "Вы неправильно указали причину блокировки ЕРИБ. Пожалуйста, введите не более 1024 символов."));
		formBuilder.addField(fieldBuilder.build());

		RequiredFieldValidator mapiRequiredValidator = new RequiredFieldValidator();
		mapiRequiredValidator.setEnabledExpression(new RhinoExpression("form.applyToMAPI && form.useMapiMessage"));

		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.MAPI_MESSAGE_FIELD);
		fieldBuilder.setDescription("Текст причины блокировки mApi");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(mapiRequiredValidator, new RegexpFieldValidator("^(.|\\s){1,1024}$", "Вы неправильно указали причину блокировки mApi. Пожалуйста, введите не более 1024 символов."));
		formBuilder.addField(fieldBuilder.build());

		RequiredFieldValidator atmRequiredValidator = new RequiredFieldValidator();
		atmRequiredValidator.setEnabledExpression(new RhinoExpression("form.applyToATM && form.useAtmMessage"));

		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.ATM_MESSAGE_FIELD);
		fieldBuilder.setDescription("Текст причины блокировки atmApi");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(atmRequiredValidator, new RegexpFieldValidator("^(.|\\s){1,1024}$", "Вы неправильно указали причину блокировки atmApi. Пожалуйста, введите не более 1024 символов."));
		formBuilder.addField(fieldBuilder.build());

		RequiredFieldValidator ermbRequiredValidator = new RequiredFieldValidator();
		ermbRequiredValidator.setEnabledExpression(new RhinoExpression("form.applyToERMB && form.useErmbMessage"));

		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.ERMB_MESSAGE_FIELD);
		fieldBuilder.setDescription("Текст причины блокировки ЕРМБ");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(ermbRequiredValidator, new RegexpFieldValidator("^(.|\\s){1,1024}$", "Вы неправильно указали причину блокировки ЕРМБ. Пожалуйста, введите не более 1024 символов."));
		formBuilder.addField(fieldBuilder.build());

		CurrentDateTimeCompareValidator dateTimeValidator = new CurrentDateTimeCompareValidator();
		dateTimeValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.GREATE_EQUAL);
		dateTimeValidator.setBinding("date", EditConstants.RESUMING_DATE_FIELD);
		dateTimeValidator.setBinding("time", EditConstants.RESUMING_TIME_FIELD);
		dateTimeValidator.setMessage("Дата восстановления доступа должна быть больше либо равна текущей.");

		DateTimeCompareValidator dateTimeCompareValidator = new DateTimeCompareValidator();
		dateTimeCompareValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.LESS);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_DATE, EditConstants.FROM_PUBLISH_DATE_FIELD);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_TIME, EditConstants.FROM_PUBLISH_TIME_FIELD);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_DATE, EditConstants.TO_PUBLISH_DATE_FIELD);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_TIME, EditConstants.TO_PUBLISH_TIME_FIELD);
		dateTimeCompareValidator.setMessage("Дата и время публикации уведомления должны быть меньше даты и времени снятия его с публикации. Пожалуйста, введите другое значение.");
		dateTimeCompareValidator.setEnabledExpression(new RhinoExpression("form.toPublish == true && form.fromPublish == true"));

		CurrentDateTimeCompareValidator currentDateTimeCompareValidator = new CurrentDateTimeCompareValidator();
		currentDateTimeCompareValidator.setParameter( DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.GREATE_EQUAL);
		currentDateTimeCompareValidator.setBinding("date", EditConstants.TO_PUBLISH_DATE_FIELD);
		currentDateTimeCompareValidator.setBinding("time", EditConstants.TO_PUBLISH_TIME_FIELD);
		currentDateTimeCompareValidator.setMessage("Дата и время снятия с публикации уведомления должны быть больше текущей даты и времени. Пожалуйста, введите другое значение.");
		currentDateTimeCompareValidator.setEnabledExpression( new RhinoExpression( "form.toPublish == true" ));

		IsCheckedMultiFieldValidator requiredChannelsValidator = new IsCheckedMultiFieldValidator();
		requiredChannelsValidator.setBinding(EditConstants.APPLY_TO_ATM_FIELD, EditConstants.APPLY_TO_ATM_FIELD);
		requiredChannelsValidator.setBinding(EditConstants.APPLY_TO_ERIB_FIELD, EditConstants.APPLY_TO_ERIB_FIELD);
		requiredChannelsValidator.setBinding(EditConstants.APPLY_TO_ERMB_FIELD, EditConstants.APPLY_TO_ERMB_FIELD);
		requiredChannelsValidator.setBinding(EditConstants.APPLY_TO_MAPI_FIELD, EditConstants.APPLY_TO_MAPI_FIELD);
		requiredChannelsValidator.setMessage("Укажите хотя бы один канал для настройки правила блокировки.");

		formBuilder.addFormValidators( dateTimeValidator, dateTimeCompareValidator, currentDateTimeCompareValidator, requiredChannelsValidator);

		return formBuilder.build();
	}
}
