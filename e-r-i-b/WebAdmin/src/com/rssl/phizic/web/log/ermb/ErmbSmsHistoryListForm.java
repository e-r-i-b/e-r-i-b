package com.rssl.phizic.web.log.ermb;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.DateTimeCompareValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.ermb.auxiliary.smslog.ErmbSmsHistoryValidator;
import com.rssl.phizic.business.ermb.auxiliary.smslog.FilterField;
import com.rssl.phizic.business.ermb.auxiliary.smslog.SmsLogEntry;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.utils.PassportTypeWrapper;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.Map;
import java.util.Set;

/**
 * @author Gulov
 * @ created 12.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class ErmbSmsHistoryListForm extends ListFormBase<SmsLogEntry>
{
	private static final String DATESTAMP_FORMAT = "dd.MM.yyyy";
	private static final String TIMESTAMP_FORMAT = "HH:mm:ss";

	public static final Form FILTER_FORM = createForm();
	public static final Form PERSON_FILTER_FORM = createPersonForm();

	private String sessionId;
	private String pagination_offset0;//��������
	private String pagination_size0;//���������� ������������ �������
	/**
	 * ������������� �������, ��� ������ �� ������ �������
	 */
	private Long personId;
	private Person activePerson;
	private boolean modified;

	public String getSessionId()
	{
		return sessionId;
	}

	public void setSessionId(String sessionId)
	{
		this.sessionId = sessionId;
	}

	//����� �������� ������� � ���, ��� �������� � ���������� ������� ��������
	// � ����� � ���������� $$pagination_offset0 � t$$pagination_size0
	public void set$$pagination_offset0(String pagination_offset0)
	{
		this.pagination_offset0 = pagination_offset0;
	}

	public String getPaginationOffset()
	{
		return pagination_offset0;
	}

	//����� �������� ������� � ���, ��� �������� � ���������� ������� ��������
	// � ����� � ���������� $$pagination_offset0 � t$$pagination_size0
	public void set$$pagination_size0(String pagination_size0)
	{
		this.pagination_size0 = pagination_size0;
	}

	public String getPaginationSize()
	{
		return pagination_size0;
	}

	public Long getPersonId()
	{
		return personId;
	}

	public void setPersonId(Long personId)
	{
		this.personId = personId;
	}

	public Set<Map.Entry<ClientDocumentType, String>> getDocumentTypes()
	{
		return PassportTypeWrapper.getAllTypes().entrySet();
	}

	public Person getActivePerson()
	{
		return activePerson;
	}

	public void setActivePerson(Person activePerson)
	{
		this.activePerson = activePerson;
	}

	public boolean isModified()
	{
		return modified;
	}

	public void setModified(boolean modified)
	{
		this.modified = modified;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		DateParser dateParser = new DateParser(DATESTAMP_FORMAT);

		@SuppressWarnings({"TooBroadScope"})
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������� ��� ��������");
		fieldBuilder.setName(FilterField.FIO.value());
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� ���������");
		fieldBuilder.setName(FilterField.DOCUMENT_TYPE.value());
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ���������");
		fieldBuilder.setName(FilterField.DOCUMENT_NUMBER.value());
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
                new RegexpFieldValidator("\\d*", "���� ����� ��������� ������ ��������� ������ �����"),
                new RegexpFieldValidator(".{0,16}", "���� ����� ��������� �� ������ ��������� 16 ����")
				);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription(FilterField.DOCUMENT_SERIES.value());
		fieldBuilder.setName("documentSeries");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,16}", "���� ����� ��������� �� ������ ��������� 16 ��������"),
				new RegexpFieldValidator("[^<>!#$%^&*{}|\\]\\[''\"~=]*","���� ����� ��������� �� ������ ��������� ������������")
				);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���� ��������");
		fieldBuilder.setName(FilterField.BIRTHDAY.value());
		fieldBuilder.setType("date");
		fieldBuilder.addValidators(new DateFieldValidator(DATESTAMP_FORMAT, "���� ������ ���� � ������� ��.��.����"));
		fieldBuilder.setParser(dateParser);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������");
		fieldBuilder.setName(FilterField.PHONE.value());
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());
		fieldBuilder.addValidators(new RegexpFieldValidator("\\d*", "���� '�������' ������ ��������� ������ �����"));

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� ��������");
		fieldBuilder.setName(FilterField.TB.value());
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		ErmbSmsHistoryValidator smsLogValidator = new ErmbSmsHistoryValidator();
		smsLogValidator.setBinding(FilterField.FIO.value(), FilterField.FIO.value());
		smsLogValidator.setBinding(FilterField.DOCUMENT_TYPE.value(), FilterField.DOCUMENT_TYPE.value());
		smsLogValidator.setBinding(FilterField.DOCUMENT_NUMBER.value(), FilterField.DOCUMENT_NUMBER.value());
		smsLogValidator.setBinding(FilterField.DOCUMENT_SERIES.value(), FilterField.DOCUMENT_SERIES.value());
		smsLogValidator.setBinding(FilterField.BIRTHDAY.value(), FilterField.BIRTHDAY.value());
		smsLogValidator.setBinding(FilterField.PHONE.value(), FilterField.PHONE.value());
		smsLogValidator.setBinding(FilterField.TB.value(), FilterField.TB.value());
		smsLogValidator.setMessage(
				"������ ������ ����� ���� �������� ���� �� ������ ��������, ���� �� �������.\\n" +
						"������������� ��� ���������� �������� ����� ���������� �����:\\n" +
						"1) �������, ���, ��� �������� � ��������\\n" +
						"2) �������, ���, ��� �������� � ���� ��������\\n" +
						"3) ����� ��������\\n" +
						"��������� �������� ������ ������� �� �����������.");
		formBuilder.addFormValidators(smsLogValidator);

		addDateFilterFields(formBuilder);

		return formBuilder.build();
	}

	private static Form createPersonForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		addDateFilterFields(formBuilder);

		return formBuilder.build();
	}

	private static void addDateFilterFields(FormBuilder formBuilder)
	{
		@SuppressWarnings({"TooBroadScope"})
		FieldBuilder fieldBuilder;

		DateParser dateParser = new DateParser(DATESTAMP_FORMAT);
		DateParser timeParser = new DateParser(TIMESTAMP_FORMAT);

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName(FilterField.FROM_DATE.value());
		fieldBuilder.setDescription("��������� ���� �������");
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(
				new RequiredFieldValidator("������� ��������� ���� �������"),
				new DateFieldValidator(DATESTAMP_FORMAT, "���� ������ ������� ������ ���� � ������� ��.��.����"));
		fieldBuilder.setParser(dateParser);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName(FilterField.FROM_TIME.value());
		fieldBuilder.setDescription("��������� ����� �������");
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(
				new RequiredFieldValidator("������� ��������� ����� �������"),
				new DateFieldValidator(TIMESTAMP_FORMAT, "����� ������ ������� ������ ���� � ������� ��:��:CC"));
		fieldBuilder.setParser(timeParser);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName(FilterField.TO_DATE.value());
		fieldBuilder.setDescription("�������� ���� �������");
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(
				new RequiredFieldValidator("������� �������� ���� �������"),
				new DateFieldValidator(DATESTAMP_FORMAT, "���� ��������� ������� ������ ���� � ������� ��.��.����"));
		fieldBuilder.setParser(dateParser);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName(FilterField.TO_TIME.value());
		fieldBuilder.setDescription("�������� ����� �������");
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(
				new RequiredFieldValidator("������� �������� ����� �������"),
				new DateFieldValidator(TIMESTAMP_FORMAT, "����� ��������� ������� ������ ���� � ������� ��:��:CC"));
		fieldBuilder.setParser(timeParser);
		formBuilder.addField(fieldBuilder.build());

		DateTimeCompareValidator dateTimeCompareValidator = new DateTimeCompareValidator();
		dateTimeCompareValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.LESS_EQUAL);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_DATE, "fromDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_TIME, "fromTime");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_DATE, "toDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_TIME, "toTime");
		dateTimeCompareValidator.setMessage("�������� ���� ������ ���� ������ ���� ����� ���������!");

		formBuilder.addFormValidators(dateTimeCompareValidator );
	}
}
