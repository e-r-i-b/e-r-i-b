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
		fieldBuilder.setDescription("��������� ����");
		fieldBuilder.addValidators(	new RequiredFieldValidator("������� ��������� ����"),
									new DateFieldValidator(DATESTAMP_FORMAT, "���� ������ ���� � ������� ��.��.����"));
		fieldBuilder.setParser(dateParser);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName("fromTime");
		fieldBuilder.setDescription("��������� �����");
		fieldBuilder.setValidators(new DateFieldValidator(TIMESTAMP_FORMAT, "����� ������ ���� � ������� ��:��:CC"));
		fieldBuilder.setParser(timeParser);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName("toDate");
		fieldBuilder.setDescription("�������� ����");
		fieldBuilder.addValidators( new RequiredFieldValidator("������� �������� ����"),
									new DateFieldValidator(DATESTAMP_FORMAT, "���� ������ ���� � ������� ��.��.����"));
		fieldBuilder.setParser(dateParser);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName("toTime");
		fieldBuilder.setDescription("�������� �����");
		fieldBuilder.setValidators(new DateFieldValidator(TIMESTAMP_FORMAT, "����� ������ ���� � ������� ��:��:CC"));
		fieldBuilder.setParser(timeParser);
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

		DateTimeCompareValidator dateTimeCompareValidator = new DateTimeCompareValidator();
		dateTimeCompareValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.LESS_EQUAL);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_DATE, "fromDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_TIME, "fromTime");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_DATE, "toDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_TIME, "toTime");
		dateTimeCompareValidator.setMessage("�������� ���� ������ ���� ������ ���� ����� ���������!");

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
		fieldBuilder.setName(LOGIN_ID_FIELD_NAME);
		fieldBuilder.setDescription("������������� ������������");
		fieldBuilder.setValidators(new RegexpFieldValidator("^\\d*$", "������������� ������������ ����� ��������� ������ �����"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(CLIENT_FIO_FIELD_NAME);
		fieldBuilder.setDescription("������");
		fieldBuilder.addValidators(new RegexpFieldValidator("[�-��-߸�a-zA-Z- ]{3,255}","����������, ������� ��� ������� �� ����� 3 ��������, ���������� ����� �������� ��� ���������� ��������, ����� ��� ������"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������ ������������");
		fieldBuilder.setName("departmentName");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());		

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("departmentId");
		fieldBuilder.setDescription("�������������");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("TB");
		fieldBuilder.setDescription("�������������");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("OSB");
		fieldBuilder.setDescription("�������������");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("VSP");
		fieldBuilder.setDescription("�������������");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("birthday");
		fieldBuilder.setType("date");
		fieldBuilder.setDescription("���� ��������");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(DOCUMENT_SERIES_FIELD_NAME);
		fieldBuilder.setDescription("�����");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,16}", "���� ����� ��������� �� ������ ��������� 16 ��������"),
				new RegexpFieldValidator("[^<>!#$%^&*{}|\\]\\[''\"~=]*","���� ����� ��������� �� ������ ��������� ������������")
				);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(DOCUMENT_NUMBER_FIELD_NAME);
		fieldBuilder.setDescription("�����");
		fieldBuilder.addValidators(
	            new RegexpFieldValidator("\\d*", "���� ����� ��������� ������ ��������� ������ �����"),
	            new RegexpFieldValidator(".{0,16}", "���� ����� ��������� �� ������ ��������� 16 ����")
				);
		formBuilder.addField(fieldBuilder.build());

		//���� ��������� ���� �� ���� �� �����, �� ����������� � ���� ����
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
		dayPeriodMultiFieldValidator.setMessage("����������, ������� ������ �� ����� 1 ���");

		//���� ��� ���� �� ���������, �� ����������� ���� ���
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
		hourPeriodMultiFieldValidator.setMessage("����������, ������� ������ �� ����� 1 ����");

		RequiredMultiFieldValidator requiredMultiFieldValidator = new RequiredMultiFieldValidator();
		requiredMultiFieldValidator.setBinding(CLIENT_FIO_FIELD_NAME, CLIENT_FIO_FIELD_NAME);
		requiredMultiFieldValidator.setBinding(DOCUMENT_SERIES_FIELD_NAME,DOCUMENT_SERIES_FIELD_NAME);
		requiredMultiFieldValidator.setBinding(DOCUMENT_NUMBER_FIELD_NAME,DOCUMENT_NUMBER_FIELD_NAME);
		requiredMultiFieldValidator.setBinding(LOGIN_ID_FIELD_NAME,LOGIN_ID_FIELD_NAME);
		requiredMultiFieldValidator.setBinding(IP_ADDRES_FIELD_NAME,IP_ADDRES_FIELD_NAME);
		requiredMultiFieldValidator.setBinding(APPLICATION_FIELD_NAME,APPLICATION_FIELD_NAME);
		requiredMultiFieldValidator.setEnabledExpression(new RhinoExpression("!form."+GUEST_LOG_NAME));
		requiredMultiFieldValidator.setMessage("����������, ��������� ���� �� ����� �������: ��� �������, Login_ID, ����� ��� ����� ���������, ��������������� ��������, ����������, IP-�����");

		RequiredMultiFieldValidator requiredFieldValidatorGuest = new RequiredMultiFieldValidator();
		requiredFieldValidatorGuest.setBinding(CLIENT_FIO_FIELD_NAME, CLIENT_FIO_FIELD_NAME);
		requiredFieldValidatorGuest.setBinding(DOCUMENT_NUMBER_FIELD_NAME, DOCUMENT_NUMBER_FIELD_NAME);
		requiredFieldValidatorGuest.setBinding(PHONE_NUMBER_NAME, PHONE_NUMBER_NAME);
		requiredFieldValidatorGuest.setEnabledExpression(new RhinoExpression("form."+GUEST_LOG_NAME));
		requiredFieldValidatorGuest.setMessage("����������, ��������� ���� �������: ��� ������� � ����� ���������, ��������������� �������� ��� ����� ��������.");

		//���+��� ������ ���� ��������� ������ ��� �����.
		RequiredAllMultiFieldStringValidator requiredFIODocGuestValidator = new RequiredAllMultiFieldStringValidator();
		requiredFIODocGuestValidator.setBinding(CLIENT_FIO_FIELD_NAME, CLIENT_FIO_FIELD_NAME);
		requiredFIODocGuestValidator.setBinding(DOCUMENT_NUMBER_FIELD_NAME,DOCUMENT_NUMBER_FIELD_NAME);
		requiredFIODocGuestValidator.setEnabledExpression(new RhinoExpression("form."+GUEST_LOG_NAME+" && (form."+PHONE_NUMBER_NAME+"=='' && (form."+DOCUMENT_NUMBER_FIELD_NAME+"!='' || form."+CLIENT_FIO_FIELD_NAME+"!=''))"));
		requiredFIODocGuestValidator.setMessage("����������, ��� ���������� ��������� ��� ���� ��� ������� � ����� ���������.");

		formBuilder.addFormValidators(dayPeriodMultiFieldValidator,
									  hourPeriodMultiFieldValidator,
									  requiredMultiFieldValidator,
									  requiredFieldValidatorGuest,
									  requiredFIODocGuestValidator);

		return formBuilder.build();
	}
}
