package com.rssl.phizic.web.log.csa;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.DateTimeCompareValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.common.types.csa.CSAOperationKind;
import com.rssl.phizic.common.types.csa.IdentificationType;
import com.rssl.phizic.web.log.DownloadLogFilterBaseForm;

/**
 * @author vagin
 * @ created 23.10.13
 * @ $Author$
 * @ $Revision$
 * ����� ������� ������ � ���.
 */
public class ViewCSAActionLogForm extends DownloadLogFilterBaseForm
{
	public static final String EMPLOYEE_FIO_FIELD_NAME = "employeeFio";
	public static final String EMPLOYEE_LOGIN_FIELD_NAME = "employeeLogin";
	public static final String IDENTIFICATION_TYPE_FIEDL_NAME = "identificationType";
	public static final String OPERATION_TYPE_FIELD_NAME = "operationType";
	public static final String CONFIRMATION_TYPE_FIELD_NAME = "confirmationType";
	public static final String IP_ADDRESS_FIELD_NAME = "ipAddress";

	private Long id;
	private ActivePerson activePerson;
	private String passport;

	public static final Form CSA_ACTION_FILTER_FORM = createFilterForm();

	private static Form createFilterForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(EMPLOYEE_FIO_FIELD_NAME);
		fieldBuilder.setDescription("���������");
		fieldBuilder.addValidators(new RegexpFieldValidator(".{0,150}","�������� ���� \"���������\" �� ������ ��������� 150 ��������."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(EMPLOYEE_LOGIN_FIELD_NAME);
		fieldBuilder.setDescription("����� ����������");
		fieldBuilder.addValidators(new RegexpFieldValidator(".{0,50}","�������� ���� \"����� ����������\" �� ������ ��������� 50 ��������."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(IP_ADDRESS_FIELD_NAME);
		fieldBuilder.setDescription("IP-�����");
		fieldBuilder.setValidators(new RegexpFieldValidator("^\\d{0,3}.\\d{0,3}.\\d{0,3}.\\d{0,3}$","�������� ������ ip-������"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(IDENTIFICATION_TYPE_FIEDL_NAME);
		fieldBuilder.setDescription("������ ��������������");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(OPERATION_TYPE_FIELD_NAME);
		fieldBuilder.setDescription("��������");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(CONFIRMATION_TYPE_FIELD_NAME);
		fieldBuilder.setDescription("�������������");
		formBuilder.addField(fieldBuilder.build());

		DateParser dateParser = new DateParser(DATESTAMP_FORMAT);
		DateParser timeParser = new DateParser(TIMESTAMP_FORMAT);

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

		DateTimeCompareValidator dateTimeCompareValidator = new DateTimeCompareValidator();
		dateTimeCompareValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.LESS_EQUAL);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_DATE, "fromDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_TIME, "fromTime");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_DATE, "toDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_TIME, "toTime");
		dateTimeCompareValidator.setMessage("�������� ���� ������ ���� ������ ���� ����� ���������!");

		formBuilder.setFormValidators(dateTimeCompareValidator);

		return formBuilder.build();
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public IdentificationType[] getIdentificationTypes()
	{
		return IdentificationType.values();
	}

	public CSAOperationKind[] getOperationTypes()
	{
		return CSAOperationKind.values();
	}

	/**
	 * @return ������������� ������
	 */
	public ActivePerson getActivePerson()
	{
		return activePerson;
	}

	/**
	 * ������ �������������� �������
	 * @param activePerson ������������� ������
	 */
	public void setActivePerson(ActivePerson activePerson)
	{
		this.activePerson = activePerson;
	}

	/**
	 * @return ����� /WEB-INF/jsp-sbrf/persons/personData.jsp
	 */
	public boolean isModified()
	{
		return false;
	}

	public String getPassport()
	{
		return passport;
	}

	public void setPassport(String passport)
	{
		this.passport = passport;
	}
}
