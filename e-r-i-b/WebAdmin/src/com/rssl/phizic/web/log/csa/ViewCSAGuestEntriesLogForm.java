package com.rssl.phizic.web.log.csa;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.web.log.MessageLogForm;

/**
 * @author tisov
 * @ created 23.07.15
 * @ $Author$
 * @ $Revision$
 * ����� ��������� ������� � �������� ������
 */
public class ViewCSAGuestEntriesLogForm extends MessageLogForm
{

	public static final Form CSA_MESSAGE_LOG_FILTER_FORM = createFilterForm();
	private static final String BIRTH_DATE_FIELD_NAME = "birthday";

	protected static Form createFilterForm()
	{

		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		DateParser dateParser = new DateParser(DATESTAMP_FORMAT);
		DateParser timeParser = new DateParser(TIMESTAMP_FORMAT);

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("logUID");
		fieldBuilder.setDescription("������������� �������� �����������.");

		formBuilder.addField(fieldBuilder.build());


		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName("fromDate");
		fieldBuilder.setDescription("��������� ����");
		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator("������� ��������� ����");
		DateFieldValidator dateFieldValidator = new DateFieldValidator(DATESTAMP_FORMAT, "���� ������ ���� � ������� ��.��.����");
		fieldBuilder.addValidators(requiredFieldValidator, dateFieldValidator);
		fieldBuilder.setParser(dateParser);

		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName("fromTime");
		fieldBuilder.setDescription("��������� �����");
		dateFieldValidator = new DateFieldValidator(TIMESTAMP_FORMAT, "����� ������ ���� � ������� ��:��");
		fieldBuilder.setValidators(new FieldValidator[]{dateFieldValidator});
		fieldBuilder.setParser(timeParser);

		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName("toDate");
		fieldBuilder.setDescription("�������� ����");
		requiredFieldValidator = new RequiredFieldValidator("������� �������� ����");
		dateFieldValidator = new DateFieldValidator(DATESTAMP_FORMAT, "���� ������ ���� � ������� ��.��.����");
		fieldBuilder.addValidators(requiredFieldValidator, dateFieldValidator);
		fieldBuilder.setParser(dateParser);

		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName("toTime");
		fieldBuilder.setDescription("�������� �����");
		dateFieldValidator = new DateFieldValidator(TIMESTAMP_FORMAT, "����� ������ ���� � ������� ��:��");
		fieldBuilder.setValidators(new FieldValidator[]{dateFieldValidator});
		fieldBuilder.setParser(timeParser);

		formBuilder.addField(fieldBuilder.build());

		DateTimeCompareValidator dateTimeCompareValidator = new DateTimeCompareValidator();
		dateTimeCompareValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.LESS_EQUAL);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_DATE, "fromDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_TIME, "fromTime");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_DATE, "toDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_TIME, "toTime");
		dateTimeCompareValidator.setMessage("�������� ���� ������ ���� ������ ���� ����� ���������!");

		formBuilder.setFormValidators(dateTimeCompareValidator);

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
		fieldBuilder.setName(CLIENT_FIO_FIELD_NAME);
		fieldBuilder.setDescription("������");
		fieldBuilder.addValidators(new RegexpFieldValidator("[�-��-߸�a-zA-Z- ]{3,255}","����������, ������� ��� ������� �� ����� 3 ��������, ���������� ����� �������� ��� ���������� ��������, ����� ��� ������"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(BIRTH_DATE_FIELD_NAME);
		fieldBuilder.setType("date");
		fieldBuilder.setDescription("���� ��������");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("TB");
		fieldBuilder.setDescription("��� ��");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("ipAddres");
		fieldBuilder.setDescription("IP-�����");
		fieldBuilder.setValidators(new RegexpFieldValidator("^\\d{0,3}.\\d{0,3}.\\d{0,3}.\\d{0,3}$","�������� ������ ip-������"));
		formBuilder.addField(fieldBuilder.build());

		RequiredAllMultiFieldStringValidator fioAndBirthdayRequiredValidator = new RequiredAllMultiFieldStringValidator();
		fioAndBirthdayRequiredValidator.setBinding(CLIENT_FIO_FIELD_NAME, CLIENT_FIO_FIELD_NAME);
		fioAndBirthdayRequiredValidator.setBinding(BIRTH_DATE_FIELD_NAME, BIRTH_DATE_FIELD_NAME);

		RequiredAllMultiFieldStringValidator phoneRequiredValidator = new RequiredAllMultiFieldStringValidator();
		phoneRequiredValidator.setBinding(PHONE_NUMBER_NAME, PHONE_NUMBER_NAME);

		DisjunctionLogicValidator disjunctionValidator = new DisjunctionLogicValidator();
		disjunctionValidator.addValidator(fioAndBirthdayRequiredValidator);
		disjunctionValidator.addValidator(phoneRequiredValidator);

		DateTimePeriodMultiFieldValidator monthPeriodMultiFieldValidator = new DateTimePeriodMultiFieldValidator();
		monthPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_DATE_FROM,"fromDate");
		monthPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_TIME_FROM,"fromTime");
		monthPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_DATE_TO,"toDate");
		monthPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_TIME_TO,"toTime");
		monthPeriodMultiFieldValidator.setTypePeriod(DateTimePeriodMultiFieldValidator.MONTH_TYPE_PERIOD);
		monthPeriodMultiFieldValidator.setLengthPeriod(1);
		monthPeriodMultiFieldValidator.setMessage("����������, ������� ������ �� ����� 1 ������.");

		formBuilder.addFormValidators(disjunctionValidator, monthPeriodMultiFieldValidator);

		return formBuilder.build();
	}
}
