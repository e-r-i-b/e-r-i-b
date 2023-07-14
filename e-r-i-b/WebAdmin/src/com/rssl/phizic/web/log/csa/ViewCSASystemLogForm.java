package com.rssl.phizic.web.log.csa;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.claims.forms.validators.RequiredMultiFieldValidator;
import com.rssl.phizic.web.log.DownloadLogFilterBaseForm;

/**
 * @author vagin
 * @ created 10.10.2012
 * @ $Author$
 * @ $Revision$
 * ����� ��������� ������� ��������� �������� ���
 */
public class ViewCSASystemLogForm extends DownloadLogFilterBaseForm
{
	public static final String CLIENT_FIO_FIELD_NAME = "fio";
	public static final String DOCUMENT_NUMBER_FIELD_NAME = "number";
	public static final String LOGIN_FIELD_NAME = "login";
	public static final String IP_ADDRES_FIELD_NAME = "ipAddres";
	public static final String APPLICATION_FIELD_NAME = "application";
	public static final String GUEST_LOG_NAME = "isGuestLog";
	public static final String PHONE_NUMBER_NAME = "phoneNumber";

	public static final Form SYSTEM_LOG_FILTER_FORM = createFilterForm();

	protected static final FormBuilder getPartlyFormBuilder()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;

		DateParser dateParser = new DateParser(DATESTAMP_FORMAT);
		DateParser timeParser = new DateParser(TIMESTAMP_FORMAT);
		Expression logUIDExpression = new RhinoExpression("form.logUID == null");

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName("fromDate");
		fieldBuilder.setDescription("��������� ����");
		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator("������� ��������� ����");
		requiredFieldValidator.setEnabledExpression(logUIDExpression);
		DateFieldValidator dateFieldValidator = new DateFieldValidator(DATESTAMP_FORMAT, "���� ������ ���� � ������� ��.��.����");
		dateFieldValidator.setEnabledExpression(logUIDExpression);
		fieldBuilder.addValidators(requiredFieldValidator, dateFieldValidator);
		fieldBuilder.setParser(dateParser);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName("fromTime");
		fieldBuilder.setDescription("��������� �����");
		dateFieldValidator = new DateFieldValidator(TIMESTAMP_FORMAT, "����� ������ ���� � ������� ��:��:CC");
		dateFieldValidator.setEnabledExpression(logUIDExpression);
		fieldBuilder.setValidators(dateFieldValidator);
		fieldBuilder.setParser(timeParser);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName("toDate");
		fieldBuilder.setDescription("�������� ����");
		requiredFieldValidator = new RequiredFieldValidator("������� �������� ����");
		requiredFieldValidator.setEnabledExpression(logUIDExpression);
		dateFieldValidator = new DateFieldValidator(DATESTAMP_FORMAT, "���� ������ ���� � ������� ��.��.����");
		dateFieldValidator.setEnabledExpression(logUIDExpression);
		fieldBuilder.addValidators( requiredFieldValidator , dateFieldValidator);
		fieldBuilder.setParser(dateParser);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName("toTime");
		fieldBuilder.setDescription("�������� �����");
		dateFieldValidator = new DateFieldValidator(TIMESTAMP_FORMAT, "����� ������ ���� � ������� ��:��:CC");
		dateFieldValidator.setEnabledExpression(logUIDExpression);
		fieldBuilder.setValidators(dateFieldValidator);
		fieldBuilder.setParser(timeParser);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(LOGIN_FIELD_NAME);
		fieldBuilder.setDescription("�����");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(CLIENT_FIO_FIELD_NAME);
		fieldBuilder.setDescription("������");
		fieldBuilder.addValidators(new RegexpFieldValidator("[�-��-߸�a-zA-Z- ]{3,255}", "����������, ������� ��� ������� �� ����� 3 ��������, ���������� ����� �������� ��� ���������� ��������, ����� ��� ������"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("departmentCode");
		fieldBuilder.setDescription("��� ��");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("birthday");
		fieldBuilder.setType("date");
		fieldBuilder.setDescription("���� ��������");

		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(DOCUMENT_NUMBER_FIELD_NAME);
		fieldBuilder.setDescription("�����");
		fieldBuilder.addValidators(
            new RegexpFieldValidator("\\d*", "���� ����� ��������� ������ ��������� ������ �����"),
            new RegexpFieldValidator(".{0,16}", "���� ����� ��������� �� ������ ��������� 16 ����")
				);
		formBuilder.addField(fieldBuilder.build());

		DateTimeCompareValidator dateTimeCompareValidator = new DateTimeCompareValidator();
		dateTimeCompareValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.LESS_EQUAL);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_DATE, "fromDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_TIME, "fromTime");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_DATE, "toDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_TIME, "toTime");
		dateTimeCompareValidator.setMessage("�������� ���� ������ ���� ������ ���� ����� ���������!");
		dateTimeCompareValidator.setEnabledExpression(logUIDExpression);

		formBuilder.addFormValidators(dateTimeCompareValidator);

		//����������� ��������������� ����
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("searchInMonthInterval");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("�������� �� ����� � ��������� ������");
		fieldBuilder.setValueExpression(new RhinoExpression("form." + CLIENT_FIO_FIELD_NAME + " !='' && form.birthday != null && form." + DOCUMENT_NUMBER_FIELD_NAME + " !=''" ));
		formBuilder.addField(fieldBuilder.build());

		return formBuilder;
	}
	private static Form createFilterForm()
	{
		FormBuilder formBuilder = getPartlyFormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(GUEST_LOG_NAME);
		fieldBuilder.setType("boolean");
		fieldBuilder.setDescription("�������� ����");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(PHONE_NUMBER_NAME);
		fieldBuilder.setDescription("����� ��������");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator("^7.*", "����� �������� ������ ���������� � 7"),
				new RegexpFieldValidator("\\d{11}", "����� �������� ������ �������� �� 11 ����")
				);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("string");
		fieldBuilder.setName("logUID");
		fieldBuilder.setDescription("������������� �������� �����������.");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(APPLICATION_FIELD_NAME);
		fieldBuilder.setDescription("����������");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("source");
		fieldBuilder.setDescription("������");
		formBuilder.addField(fieldBuilder.build());
		
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(IP_ADDRES_FIELD_NAME);
		fieldBuilder.setDescription("IP-�����");
		fieldBuilder.setValidators(new RegexpFieldValidator("^\\d{0,3}.\\d{0,3}.\\d{0,3}.\\d{0,3}$","�������� ������ ip-������"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("maxRows");
		fieldBuilder.setDescription("������������ ���������� �������");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("errorId");
		fieldBuilder.setDescription("������������� ������");
		fieldBuilder.setValidators(new RegexpFieldValidator("^\\d*$","������������� ������ ����� ��������� ������ �����"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("messageType");
		fieldBuilder.setDescription("��� ���������");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("messageWord");
		fieldBuilder.setDescription("����� � ���������");
		formBuilder.addField(fieldBuilder.build());

		//����������� ��������������� ����
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("searchInDayInterval");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("�������� �� ����� � ��������� ���");
		fieldBuilder.setValueExpression(new RhinoExpression("form." + CLIENT_FIO_FIELD_NAME + " !='' || form." + DOCUMENT_NUMBER_FIELD_NAME + " !='' || form." + LOGIN_FIELD_NAME + " !='' || form." + IP_ADDRES_FIELD_NAME + " != ''"));
		formBuilder.addField(fieldBuilder.build());

		//����������� ��������������� ����
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("searchInHourInterval");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("�������� �� ����� � ��������� ����");
		fieldBuilder.setValueExpression(new RhinoExpression("!form." + GUEST_LOG_NAME + "&&(form." + CLIENT_FIO_FIELD_NAME + " =='' && form." + DOCUMENT_NUMBER_FIELD_NAME + " =='' && form." + LOGIN_FIELD_NAME + " =='' && form." + IP_ADDRES_FIELD_NAME + " == '')"));
		formBuilder.addField(fieldBuilder.build());

		DateTimePeriodMultiFieldValidator monthPeriodMultiFieldValidator = new DateTimePeriodMultiFieldValidator();
		monthPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_DATE_FROM,"fromDate");
		monthPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_TIME_FROM,"fromTime");
		monthPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_DATE_TO,"toDate");
		monthPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_TIME_TO,"toTime");
		monthPeriodMultiFieldValidator.setTypePeriod(DateTimePeriodMultiFieldValidator.MONTH_TYPE_PERIOD);
		monthPeriodMultiFieldValidator.setEnabledExpression(new RhinoExpression("!form." + GUEST_LOG_NAME + "&& Boolean(form.searchInMonthInterval) && form.logUID == null"));
		monthPeriodMultiFieldValidator.setLengthPeriod(1);
		monthPeriodMultiFieldValidator.setMessage("����������, ������� ������ �� ����� 30 ����.");

		String dayPeriodGuestRhinoExp = "form." + GUEST_LOG_NAME + " && (form." + PHONE_NUMBER_NAME + " != '' || form." + DOCUMENT_NUMBER_FIELD_NAME + " != '' || form." + CLIENT_FIO_FIELD_NAME + " != '')";

		//���� ��������� ������ ���� �� �����, �� ����������� � ���� ����
		DateTimePeriodMultiFieldValidator dayPeriodMultiFieldValidator = new DateTimePeriodMultiFieldValidator();
		dayPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_DATE_FROM,"fromDate");
		dayPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_TIME_FROM,"fromTime");
		dayPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_DATE_TO,"toDate");
		dayPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_TIME_TO,"toTime");
		dayPeriodMultiFieldValidator.setTypePeriod(DateTimePeriodMultiFieldValidator.DAY_TYPE_PERIOD);
		dayPeriodMultiFieldValidator.setEnabledExpression(new RhinoExpression(dayPeriodGuestRhinoExp + " || (!Boolean(form.searchInMonthInterval) && Boolean(form.searchInDayInterval) && form.logUID == null)"));
		dayPeriodMultiFieldValidator.setMessage("����������, ������� ������ �� ����� 1 ���");

		//���� ��� ���� �� ���������, �� ����������� ���� ���
		DateTimePeriodMultiFieldValidator hourPeriodMultiFieldValidator = new DateTimePeriodMultiFieldValidator();
		hourPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_DATE_FROM,"fromDate");
		hourPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_TIME_FROM,"fromTime");
		hourPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_DATE_TO,"toDate");
		hourPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_TIME_TO,"toTime");
		hourPeriodMultiFieldValidator.setTypePeriod(DateTimePeriodMultiFieldValidator.HOUR_TYPE_PERIOD);
		hourPeriodMultiFieldValidator.setEnabledExpression(new RhinoExpression("Boolean(form.searchInHourInterval) && form.logUID == null"));
		hourPeriodMultiFieldValidator.setMessage("����������, ������� ������ �� ����� 1 ����");

		RequiredMultiFieldValidator requiredMultiFieldValidator = new RequiredMultiFieldValidator();
		requiredMultiFieldValidator.setBinding(CLIENT_FIO_FIELD_NAME, CLIENT_FIO_FIELD_NAME);
		requiredMultiFieldValidator.setBinding(DOCUMENT_NUMBER_FIELD_NAME,DOCUMENT_NUMBER_FIELD_NAME);
		requiredMultiFieldValidator.setBinding(LOGIN_FIELD_NAME,LOGIN_FIELD_NAME);
		requiredMultiFieldValidator.setBinding(IP_ADDRES_FIELD_NAME,IP_ADDRES_FIELD_NAME);
		requiredMultiFieldValidator.setBinding(APPLICATION_FIELD_NAME,APPLICATION_FIELD_NAME);
		requiredMultiFieldValidator.setMessage("����������, ��������� ���� �� ����� �������: ��� �������, Login, ����� ���������, ��������������� ��������, ����������, IP-�����");
		requiredMultiFieldValidator.setEnabledExpression(new RhinoExpression("!form." + GUEST_LOG_NAME));

		RequiredMultiFieldValidator requiredFieldValidatorGuest = new RequiredMultiFieldValidator();
		requiredFieldValidatorGuest.setBinding(CLIENT_FIO_FIELD_NAME, CLIENT_FIO_FIELD_NAME);
		requiredFieldValidatorGuest.setBinding(DOCUMENT_NUMBER_FIELD_NAME, DOCUMENT_NUMBER_FIELD_NAME);
		requiredFieldValidatorGuest.setBinding(PHONE_NUMBER_NAME, PHONE_NUMBER_NAME);
		requiredFieldValidatorGuest.setEnabledExpression(new RhinoExpression("form." + GUEST_LOG_NAME));
		requiredFieldValidatorGuest.setMessage("����������, ��������� ���� �������: ��� ������� � ����� ���������, ��������������� �������� ��� ����� ��������.");

		//���+��� ������ ���� ��������� ������ ��� �����.
		RequiredAllMultiFieldStringValidator requiredFIODocGuestValidator = new RequiredAllMultiFieldStringValidator();
		requiredFIODocGuestValidator.setBinding(CLIENT_FIO_FIELD_NAME, CLIENT_FIO_FIELD_NAME);
		requiredFIODocGuestValidator.setBinding(DOCUMENT_NUMBER_FIELD_NAME,DOCUMENT_NUMBER_FIELD_NAME);
		requiredFIODocGuestValidator.setEnabledExpression(new RhinoExpression("form."+GUEST_LOG_NAME+" && (form."+PHONE_NUMBER_NAME+"=='' && (form."+DOCUMENT_NUMBER_FIELD_NAME+"!='' || form."+CLIENT_FIO_FIELD_NAME+"!=''))"));
		requiredFIODocGuestValidator.setMessage("����������, ��� ���������� ��������� ��� ���� ��� ������� � ����� ���������.");

		formBuilder.addFormValidators(
				monthPeriodMultiFieldValidator,
				dayPeriodMultiFieldValidator,
				hourPeriodMultiFieldValidator,
				requiredMultiFieldValidator,
				requiredFieldValidatorGuest,
				requiredFIODocGuestValidator);

		return formBuilder.build();
	}
}
