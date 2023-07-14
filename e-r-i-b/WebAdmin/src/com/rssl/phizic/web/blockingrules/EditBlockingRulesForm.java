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
 * ����� ��������������/�������� ������� ����������
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
		fieldBuilder.setDescription("��������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator(), new RegexpFieldValidator("^.{0,100}$", "�� ����������� ������� �������� �������. ����������, ������� �� ����� 100 ��������."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.DEPARTMENTS_FIELD);
		fieldBuilder.setDescription("�������������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator(),
				new RegexpFieldValidator("^(\\d|\\?|\\*|\\,)*$", "�� ����������� ������� �������� �������������. ����������, ������� ����� ��� ��������� �������: *,?,�,�."),
				new RegexpFieldValidator("^.{0,100}$", "�� ����������� ������� �������� �������������. ����������, ������� �� ����� 100 ��������."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.STATE_FIELD);
		fieldBuilder.setDescription("���������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.RESUMING_TIME_FIELD);
		fieldBuilder.setDescription("����� �������������� �������");
		fieldBuilder.setType("date");
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(),new DateFieldValidator(EditConstants.TIMESTAMP_FORMAT, "����� ������ ���� � ������� ��:��"));
		fieldBuilder.setParser(timeParser);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.RESUMING_DATE_FIELD);
		fieldBuilder.setDescription("���� �������������� �������");
		fieldBuilder.setType("date");
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(),new DateFieldValidator(EditConstants.DATESTAMP_FORMAT, "���� ������ ���� � ������� ��.��.����"));
		fieldBuilder.setParser(dateParser);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.CONFIGURE_NOTIFICATION_FIELD);
		fieldBuilder.setDescription("��������� �����������");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.FROM_PUBLISH_FIELD);
		fieldBuilder.setDescription("������������ �");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.configureNotification == true"));

		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.FROM_PUBLISH_DATE_FIELD);
		fieldBuilder.setDescription("������������ �");
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
		fieldBuilder.setDescription("������������ �");
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
		fieldBuilder.setDescription("����� � ����������");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.configureNotification == true"));

		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.TO_PUBLISH_DATE_FIELD);
		fieldBuilder.setDescription("����� � ����������");
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
		fieldBuilder.setDescription("����� � ����������");
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
		fieldBuilder.setDescription("������ ����� ��������� �");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(),new DateFieldValidator(EditConstants.DATESTAMP_FORMAT, "���� ������ ���� � ������� ��.��.����"));
		fieldBuilder.setParser(dateParser);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.configureNotification == true"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.FROM_REGISTRATION_TIME_FIELD);
		fieldBuilder.setDescription("������ ����� ��������� �");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(),new DateFieldValidator(EditConstants.TIMESTAMP_FORMAT, "����� ������ ���� � ������� ��:��"));
		fieldBuilder.setParser(timeParser);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.configureNotification == true"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.TO_REGISTRATION_DATE_FIELD);
		fieldBuilder.setDescription("������ ����� ��������� ��");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(),new DateFieldValidator(EditConstants.DATESTAMP_FORMAT, "���� ������ ���� � ������� ��.��.����"));
		fieldBuilder.setParser(dateParser);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.configureNotification == true"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.TO_REGISTRATION_TIME_FIELD);
		fieldBuilder.setDescription("������ ����� ��������� ��");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(),new DateFieldValidator(EditConstants.TIMESTAMP_FORMAT, "����� ������ ���� � ������� ��:��"));
		fieldBuilder.setParser(timeParser);
		fieldBuilder.setEnabledExpression(new RhinoExpression("form.configureNotification == true"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.APPLY_TO_ERIB_FIELD);
		fieldBuilder.setDescription("������� ��������� � ����");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.APPLY_TO_MAPI_FIELD);
		fieldBuilder.setDescription("������� ��������� � mApi");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.APPLY_TO_ATM_FIELD);
		fieldBuilder.setDescription("������� ��������� � atmApi");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.APPLY_TO_ERMB_FIELD);
		fieldBuilder.setDescription("������� ��������� � ����");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();

		RequiredFieldValidator useMapiRequiredValidator = new RequiredFieldValidator();
		useMapiRequiredValidator.setEnabledExpression(new RhinoExpression("form.applyToMAPI"));

		fieldBuilder.setName(EditConstants.USE_MAPI_MESSAGE_FIELD);
		fieldBuilder.setDescription("������� ���������� mApi");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(useMapiRequiredValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();

		RequiredFieldValidator useAtmRequiredValidator = new RequiredFieldValidator();
		useAtmRequiredValidator.setEnabledExpression(new RhinoExpression("form.applyToATM"));

		fieldBuilder.setName(EditConstants.USE_ATM_MESSAGE_FIELD);
		fieldBuilder.setDescription("������� ���������� atmApi");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(useAtmRequiredValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();

		RequiredFieldValidator useErmbRequiredValidator = new RequiredFieldValidator();
		useErmbRequiredValidator.setEnabledExpression(new RhinoExpression("form.applyToERMB"));

		fieldBuilder.setName(EditConstants.USE_ERMB_MESSAGE_FIELD);
		fieldBuilder.setDescription("������� ���������� ����");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(useErmbRequiredValidator);
		formBuilder.addField(fieldBuilder.build());

		RequiredFieldValidator eribRequiredValidator = new RequiredFieldValidator();
		eribRequiredValidator.setEnabledExpression(new RhinoExpression("form.applyToERIB"));

		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.ERIB_MESSAGE_FIELD);
		fieldBuilder.setDescription("������� ���������� ����");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(eribRequiredValidator, new RegexpFieldValidator("^(.|\\s){1,1024}$", "�� ����������� ������� ������� ���������� ����. ����������, ������� �� ����� 1024 ��������."));
		formBuilder.addField(fieldBuilder.build());

		RequiredFieldValidator mapiRequiredValidator = new RequiredFieldValidator();
		mapiRequiredValidator.setEnabledExpression(new RhinoExpression("form.applyToMAPI && form.useMapiMessage"));

		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.MAPI_MESSAGE_FIELD);
		fieldBuilder.setDescription("����� ������� ���������� mApi");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(mapiRequiredValidator, new RegexpFieldValidator("^(.|\\s){1,1024}$", "�� ����������� ������� ������� ���������� mApi. ����������, ������� �� ����� 1024 ��������."));
		formBuilder.addField(fieldBuilder.build());

		RequiredFieldValidator atmRequiredValidator = new RequiredFieldValidator();
		atmRequiredValidator.setEnabledExpression(new RhinoExpression("form.applyToATM && form.useAtmMessage"));

		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.ATM_MESSAGE_FIELD);
		fieldBuilder.setDescription("����� ������� ���������� atmApi");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(atmRequiredValidator, new RegexpFieldValidator("^(.|\\s){1,1024}$", "�� ����������� ������� ������� ���������� atmApi. ����������, ������� �� ����� 1024 ��������."));
		formBuilder.addField(fieldBuilder.build());

		RequiredFieldValidator ermbRequiredValidator = new RequiredFieldValidator();
		ermbRequiredValidator.setEnabledExpression(new RhinoExpression("form.applyToERMB && form.useErmbMessage"));

		fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(EditConstants.ERMB_MESSAGE_FIELD);
		fieldBuilder.setDescription("����� ������� ���������� ����");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(ermbRequiredValidator, new RegexpFieldValidator("^(.|\\s){1,1024}$", "�� ����������� ������� ������� ���������� ����. ����������, ������� �� ����� 1024 ��������."));
		formBuilder.addField(fieldBuilder.build());

		CurrentDateTimeCompareValidator dateTimeValidator = new CurrentDateTimeCompareValidator();
		dateTimeValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.GREATE_EQUAL);
		dateTimeValidator.setBinding("date", EditConstants.RESUMING_DATE_FIELD);
		dateTimeValidator.setBinding("time", EditConstants.RESUMING_TIME_FIELD);
		dateTimeValidator.setMessage("���� �������������� ������� ������ ���� ������ ���� ����� �������.");

		DateTimeCompareValidator dateTimeCompareValidator = new DateTimeCompareValidator();
		dateTimeCompareValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.LESS);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_DATE, EditConstants.FROM_PUBLISH_DATE_FIELD);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_TIME, EditConstants.FROM_PUBLISH_TIME_FIELD);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_DATE, EditConstants.TO_PUBLISH_DATE_FIELD);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_TIME, EditConstants.TO_PUBLISH_TIME_FIELD);
		dateTimeCompareValidator.setMessage("���� � ����� ���������� ����������� ������ ���� ������ ���� � ������� ������ ��� � ����������. ����������, ������� ������ ��������.");
		dateTimeCompareValidator.setEnabledExpression(new RhinoExpression("form.toPublish == true && form.fromPublish == true"));

		CurrentDateTimeCompareValidator currentDateTimeCompareValidator = new CurrentDateTimeCompareValidator();
		currentDateTimeCompareValidator.setParameter( DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.GREATE_EQUAL);
		currentDateTimeCompareValidator.setBinding("date", EditConstants.TO_PUBLISH_DATE_FIELD);
		currentDateTimeCompareValidator.setBinding("time", EditConstants.TO_PUBLISH_TIME_FIELD);
		currentDateTimeCompareValidator.setMessage("���� � ����� ������ � ���������� ����������� ������ ���� ������ ������� ���� � �������. ����������, ������� ������ ��������.");
		currentDateTimeCompareValidator.setEnabledExpression( new RhinoExpression( "form.toPublish == true" ));

		IsCheckedMultiFieldValidator requiredChannelsValidator = new IsCheckedMultiFieldValidator();
		requiredChannelsValidator.setBinding(EditConstants.APPLY_TO_ATM_FIELD, EditConstants.APPLY_TO_ATM_FIELD);
		requiredChannelsValidator.setBinding(EditConstants.APPLY_TO_ERIB_FIELD, EditConstants.APPLY_TO_ERIB_FIELD);
		requiredChannelsValidator.setBinding(EditConstants.APPLY_TO_ERMB_FIELD, EditConstants.APPLY_TO_ERMB_FIELD);
		requiredChannelsValidator.setBinding(EditConstants.APPLY_TO_MAPI_FIELD, EditConstants.APPLY_TO_MAPI_FIELD);
		requiredChannelsValidator.setMessage("������� ���� �� ���� ����� ��� ��������� ������� ����������.");

		formBuilder.addFormValidators( dateTimeValidator, dateTimeCompareValidator, currentDateTimeCompareValidator, requiredChannelsValidator);

		return formBuilder.build();
	}
}
