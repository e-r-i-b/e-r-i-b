package com.rssl.phizic.web.departments;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.EnumParser;
import com.rssl.common.forms.parsers.SqlTimeParser;
import com.rssl.common.forms.types.*;
import com.rssl.common.forms.validators.EnumFieldValidator;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.dictionaries.offices.extended.DepartmentAutoType;
import com.rssl.phizic.web.common.EditFormBase;

import java.sql.Time;
import java.text.SimpleDateFormat;

/**
 * @author akrenev
 * @ created 19.02.2014
 * @ $Author$
 * @ $Revision$
 *
 * ����� �������������� �������������
 */

public class EditDepartmentForm extends EditFormBase
{
	public static final Form EDIT_FORM = createForm();

	public static final String EXTERNAL_SYSTEM_ID_FIELD_NAME = "externalSystemId";
	public static final String EXTERNAL_SYSTEM_NAME_FIELD_NAME = "externalSystemName";
	public static final String EXTERNAL_SYSTEM_UUID_FIELD_NAME = "externalSystemUUID";
	public static final String IS_EXTERNAL_SYSTEM_OFFICE_FIELD_NAME = "externalSystemOffice";
	public static final String OFFICE_ID_FIELD_NAME = "officeId";
	public static final String OFFICE_NAME_FIELD_NAME = "officeName";

	public static final String ESB_SUPPORTED = "esbSupported";
	public static final String AUTOMATION = "automation";
	public static final String CREDIT_CARD_OFFICE = "creditCardOffice";
	public static final String OPEN_IMA_OFFICE = "openIMAOffice";

	public static final String REGION = "region";
	public static final String BRANCH = "branch";
	public static final String OFFICE = "office";

	public static final String TIME_ZONE = "timeZone";
	public static final String SERVICE_FIELD_NAME = "service";
	public static final String POSSIBLE_LOANS_OPERATION_FIELD_NAME = "possibleLoansOperation";
	public static final String MAIN_DEPARTMENT_FIELD_NAME = "mainDepartment";
	public static final String MONTHLY_CHARGE_FIELD_NAME = "monthlyCharge";
	public static final String CONNECTION_CHARGE_FIELD_NAME = "connectionCharge";
	public static final String NOTIFY_CONTRACT_CANCELLATION_FIELD_NAME = "notifyContractCancellation";
	public static final String TIME_SCALE_FIELD_NAME = "timeScale";
	public static final String NAME_FIELD_NAME = "name";
	public static final String CITY_FIELD_NAME = "city";
	public static final String ADDRESS_FIELD_NAME = "address";
	public static final String LOCATION_FIELD_NAME = "location";
	public static final String TELEPHONE_FIELD_NAME = "telephone";
	public static final String WEEK_OPERATION_TIME_BEGIN_FIELD_NAME = "weekOperationTimeBegin";
	public static final String WEEK_OPERATION_TIME_END_FIELD_NAME = "weekOperationTimeEnd";
	public static final String BILLING_ID_FIELD_NAME = "billingId";
	public static final String BILLING_NAME_FIELD_NAME = "billingName";
	public static final String NOT_ACTIVE = "notActive";

	private String mode;      // "branch" ��� "department"
	private Department department;
	private Department parentDepartment;    //������������ �����������, ���� ����
	private Long parentId;    //id ������������� �������������

	private static final String TIME_FORMAT = "HH:mm";

	/**
	 * ����� ����������� � ������ ��:��
	 * @param time �����
	 * @return ��������� ������������� �������
	 */
	public static String formatTime(Time time)
	{
		if (time == null)
			return null;
		return new SimpleDateFormat(TIME_FORMAT).format(time);
	}

	public Department getDepartment()
	{
		return department;
	}

	public void setDepartment(Department department)
	{
		this.department = department;
	}

	public String getMode()
	{
		return mode;
	}

	public void setMode(String mode)
	{
		this.mode = mode;
	}

	public Long getParentId()
	{
		return parentId;
	}

	public void setParentId(Long parentId)
	{
		this.parentId = parentId;
	}

	public Department getParentDepartment()
	{
		return parentDepartment;
	}

	public void setParentDepartment(Department parentDepartment)
	{
		this.parentDepartment = parentDepartment;
	}

	private static Field getStringField(String name, String description, FieldValidator... validators)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setName(name);
		fieldBuilder.setDescription(description);
		fieldBuilder.addValidators(validators);
		return fieldBuilder.build();
	}

	private static Field getBooleanField(String name, String description)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setName(name);
		fieldBuilder.setDescription(description);
		return fieldBuilder.build();
	}

	private static Field getTimeField(String name, String description, FieldValidator... validators)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setParser(new SqlTimeParser());
		fieldBuilder.setName(name);
		fieldBuilder.setDescription(description);
		fieldBuilder.addValidators(validators);
		return fieldBuilder.build();
	}

	private static Field getLongField(String name, String description, FieldValidator... validators)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.setName(name);
		fieldBuilder.setDescription(description);
		fieldBuilder.addValidators(validators);
		return fieldBuilder.build();
	}

	private static Field getIntField(String name, String description, FieldValidator... validators)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setType(IntType.INSTANCE.getName());
		fieldBuilder.setName(name);
		fieldBuilder.setDescription(description);
		fieldBuilder.addValidators(validators);
		return fieldBuilder.build();
	}

	private static Field getMoneyField(String name, String description, FieldValidator... validators)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.setName(name);
		fieldBuilder.setDescription(description);
		fieldBuilder.addValidators(validators);
		return fieldBuilder.build();
	}

	private static RequiredFieldValidator getRequiredFieldValidator(String enabledExpression)
	{
		RequiredFieldValidator fieldValidator = new RequiredFieldValidator();
		fieldValidator.setEnabledExpression(new RhinoExpression(enabledExpression));
		return fieldValidator;
	}

	@SuppressWarnings("OverlyLongMethod")
	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		formBuilder.addField(getStringField(NAME_FIELD_NAME, "������������", new RequiredFieldValidator(), new RegexpFieldValidator(".{0,256}", "������������ ������ ���� �� ����� 256 ��������")));
		formBuilder.addField(getBooleanField(SERVICE_FIELD_NAME, "������������� � �������"));
		formBuilder.addField(getBooleanField(MAIN_DEPARTMENT_FIELD_NAME, "������� ��������� �������������"));
		formBuilder.addField(getStringField(EXTERNAL_SYSTEM_UUID_FIELD_NAME, "������� �������", new RequiredFieldValidator()));
		formBuilder.addField(getStringField(CITY_FIELD_NAME, "�����", new RegexpFieldValidator(".{0,256}", "���� ����� ������ ���� �� ����� 256 ��������")));
		formBuilder.addField(getStringField(ADDRESS_FIELD_NAME, "�������� �����", new RegexpFieldValidator(".{0,256}", "�������� ����� ������ ���� �� ����� 256 ��������")));
		formBuilder.addField(getStringField(LOCATION_FIELD_NAME, "���������������", new RegexpFieldValidator(".{0,256}", "��������������� ������ ���� �� ����� 256 ��������")));
		formBuilder.addField(getStringField(TELEPHONE_FIELD_NAME, "�������", new RegexpFieldValidator(".{1,15}", "������� ������ ���� �� ����� 15 ��������")));

		formBuilder.addField(getTimeField(WEEK_OPERATION_TIME_BEGIN_FIELD_NAME, "������������ ����� � ������� ��� (������)",
				getRequiredFieldValidator("form.service == true"), new RegexpFieldValidator("\\d{1,2}:\\d{2}", "������������ ����� � ������� ��� (������) ������ ���� � ������� ��:��")));
		formBuilder.addField(getTimeField(WEEK_OPERATION_TIME_END_FIELD_NAME, "������������ ����� � ������� ��� (����������)",
				getRequiredFieldValidator("form.service == true"), new RegexpFieldValidator("\\d{1,2}:\\d{2}", "������������ ����� � ������� ��� (����������) ������ ���� � ������� ��:��")));
		formBuilder.addField(getStringField(TIME_SCALE_FIELD_NAME, "����� �������",
				new RegexpFieldValidator(".{1,30}", "����� ������� ������ ���� �� ����� 30 ��������")));
		formBuilder.addField(getLongField(NOTIFY_CONTRACT_CANCELLATION_FIELD_NAME, "���������� � ����������� ��������",
				new RegexpFieldValidator("\\d{1,2}", "���������� � ����������� �������� ������ ������ �� 1 �� 99")));
		formBuilder.addField(getBooleanField(ESB_SUPPORTED, "������������ \"������� �������\""));

		formBuilder.addField(getStringField(REGION, "����� ��������", new RequiredFieldValidator(), new RegexpFieldValidator("[1-9]\\d*", "����������, ������� ����� �� ��� ���������� �����.")));
		formBuilder.addField(getStringField(BRANCH, "����� ���������", new RegexpFieldValidator("[1-9]\\d*", "����������, ������� ����� ��������� ��� ���������� �����.")));
		formBuilder.addField(getStringField(OFFICE, "����� �������", new RegexpFieldValidator("[1-9]\\d*", "����������, ������� ����� ������� ��� ���������� �����.")));

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������� �������������");
		fieldBuilder.setName(AUTOMATION);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setParser(new EnumParser<DepartmentAutoType>(DepartmentAutoType.class));
		fieldBuilder.addValidators(new RequiredFieldValidator(), new EnumFieldValidator<DepartmentAutoType>(DepartmentAutoType.class, "�������� �������� �������� �������������."));
		fieldBuilder.setEnabledExpression(new RhinoExpression("form." + BRANCH + " == null || form." + BRANCH + "== ''"));
		formBuilder.addField(fieldBuilder.build());

		formBuilder.addField(getStringField(OFFICE_ID_FIELD_NAME, "����", getRequiredFieldValidator("!form.region && (form.service == true || form.mainDepartment == true)")));
		formBuilder.addField(getBooleanField(CREDIT_CARD_OFFICE, "������������� ����� ��������� �����"));
		formBuilder.addField(getBooleanField(OPEN_IMA_OFFICE, "����������� �������� ��� � �����"));
		formBuilder.addField(getMoneyField(CONNECTION_CHARGE_FIELD_NAME, "����� �� ����������� ���������� ������������", getRequiredFieldValidator("form.service == true")));
		formBuilder.addField(getMoneyField(MONTHLY_CHARGE_FIELD_NAME, "����������� �����", getRequiredFieldValidator("form.service == 'true'")));
		formBuilder.addField(getIntField(TIME_ZONE, "������� ����", getRequiredFieldValidator("!form.useParentTimeZone")));
		formBuilder.addField(getLongField(BILLING_ID_FIELD_NAME, "�������"));
		formBuilder.addField(getBooleanField(POSSIBLE_LOANS_OPERATION_FIELD_NAME, "�������� �������� �� ������������ ���. ���"));
		formBuilder.addField(getBooleanField(NOT_ACTIVE, "����������"));

		return formBuilder.build();
	}
}
