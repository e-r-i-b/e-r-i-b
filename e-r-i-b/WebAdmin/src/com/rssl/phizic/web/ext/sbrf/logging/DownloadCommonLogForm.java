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
	 * 7 ���� � �������������
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
		fieldBuilder.setName("withChildren");
		fieldBuilder.setDescription("������ ������ � ������������");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(APPLICATION_FIELD_NAME);
		fieldBuilder.setDescription("����������");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(SESSION_ID_FIELD_NAME);
		fieldBuilder.setDescription("������������� ������");
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
		fieldBuilder.setDescription("�������� ������ �� ������� �������� ������������");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("showSystemLog");
		fieldBuilder.setType("boolean");
		fieldBuilder.setDescription("�������� ������ �� ���������� �������");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("showMessageLog");
		fieldBuilder.setType("boolean");
		fieldBuilder.setDescription("�������� ������ �� ������� ������ �����������");
		formBuilder.addField(fieldBuilder.build());
	}

	private static void addUserFilters(FormBuilder formBuilder)
	{
		@SuppressWarnings({"TooBroadScope"})
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(CLIENT_FIO_FIELD_NAME);
		fieldBuilder.setDescription("������");
		fieldBuilder.addValidators(new RegexpFieldValidator("[�-��-߸�a-zA-Z- ]{3,255}","����������, ������� ��� ������� �� ����� 3 ��������, ���������� ����� �������� ��� ���������� ��������, ����� ��� ������"));
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

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(LOGIN_ID_FIELD_NAME);
		fieldBuilder.setDescription("������������� ������������");
		fieldBuilder.addValidators(new RegexpFieldValidator("^\\d*$","������������� ������������ ����� ��������� ������ �����"));
		formBuilder.addField(fieldBuilder.build());

		//���� ��������� ������ ���� �� �����, �� ����������� � ���� ����
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
		dayPeriodMultiFieldValidator.setMessage("����������, ������� ������ �� ����� 1 ���");

		//���� ��� ���� �� ��������� ����� ����������, �� ����������� ���� ���
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
		hourPeriodMultiFieldValidator.setMessage("����������, ������� ������ �� ����� 1 ����");

		RequiredMultiFieldValidator requiredMultiFieldValidator = new RequiredMultiFieldValidator();
		requiredMultiFieldValidator.setBinding(CLIENT_FIO_FIELD_NAME, CLIENT_FIO_FIELD_NAME);
		requiredMultiFieldValidator.setBinding(DOCUMENT_SERIES_FIELD_NAME,DOCUMENT_SERIES_FIELD_NAME);
		requiredMultiFieldValidator.setBinding(DOCUMENT_NUMBER_FIELD_NAME,DOCUMENT_NUMBER_FIELD_NAME);
		requiredMultiFieldValidator.setBinding(LOGIN_ID_FIELD_NAME,LOGIN_ID_FIELD_NAME);
		requiredMultiFieldValidator.setBinding(APPLICATION_FIELD_NAME,APPLICATION_FIELD_NAME);
		requiredMultiFieldValidator.setBinding(SESSION_ID_FIELD_NAME,SESSION_ID_FIELD_NAME);
		requiredMultiFieldValidator.setMessage("����������, ��������� ���� �� ����� �������: ��� �������, LOGIN_ID, ����� ��� ����� ���������, ��������������� ��������, ����������");

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
		fieldBuilder.setDescription("��������� ���� �������");
		// ������� ������������� ��������� ���� ���� dd.MM.yy
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(
				new RequiredFieldValidator("������� ��������� ���� �������"),
				new DateFieldValidator(DATESTAMP_FORMAT, "���� ������ ������� ������ ���� � ������� ��.��.����"));
		fieldBuilder.setParser(dateParser);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName("fromTime");
		fieldBuilder.setDescription("��������� ����� �������");
		// ������� ������������� ��������� ���� ���� dd.MM.yy
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new DateFieldValidator(TIMESTAMP_FORMAT, "����� ������ ������� ������ ���� � ������� ��:��:CC"));
		fieldBuilder.setParser(timeParser);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName("toDate");
		fieldBuilder.setDescription("�������� ���� �������");
		// ������� ������������� ��������� ���� ���� dd.MM.yy
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(
				new RequiredFieldValidator("������� �������� ���� �������"),
				new DateFieldValidator(DATESTAMP_FORMAT, "���� ��������� ������� ������ ���� � ������� ��.��.����"));
		fieldBuilder.setParser(dateParser);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName("toTime");
		fieldBuilder.setDescription("�������� ����� �������");
		// ������� ������������� ��������� ���� ���� dd.MM.yy
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new DateFieldValidator(TIMESTAMP_FORMAT, "����� ��������� ������� ������ ���� � ������� ��:��:CC"));
		fieldBuilder.setParser(timeParser);
		formBuilder.addField(fieldBuilder.build());

		DateTimeCompareValidator dateTimeCompareValidator = new DateTimeCompareValidator();
		dateTimeCompareValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.LESS_EQUAL);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_DATE, "fromDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_TIME, "fromTime");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_DATE, "toDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_TIME, "toTime");
		dateTimeCompareValidator.setMessage("�������� ���� ������ ���� ������ ���� ����� ���������!");

		formBuilder.addFormValidators(dateTimeCompareValidator);
	}
}
