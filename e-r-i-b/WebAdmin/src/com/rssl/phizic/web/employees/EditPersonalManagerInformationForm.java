package com.rssl.phizic.web.employees;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.EmailFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.dictionaries.pfp.channel.Channel;
import com.rssl.phizic.business.dictionaries.pfp.channel.validators.ChannelValidator;
import com.rssl.phizic.gate.employee.Employee;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.List;

/**
 * @author komarov
 * @ created 11.09.2012
 * @ $Author$
 * @ $Revision$
 *
 * �������������� �������� ���������
 */

public class EditPersonalManagerInformationForm extends EditFormBase
{
    private Employee employee=null;
	private Long     employeeId;
	private List<Channel> channels;

	public static final String ID_FIELD_NAME         = "managerId";
	public static final String PHONE_FIELD_NAME      = "emplPhone";
	public static final String EMAIL_FIELD_NAME      = "emplEMail";
	public static final String LEAD_EMAIL_FIELD_NAME = "emplLeadEMail";
	public static final String CHANNEL_FIELD_NAME    = "managerChannel";

	public static final Form EDIT_PERSONAL_MANAGER_INFORMATION_FORM = createForm();
	public static final Form EDIT_SELF_MANAGER_INFORMATION_FORM     = createSelfEditForm();

	/**
	 * @return ������������� ���������
	 */
	@SuppressWarnings("UnusedDeclaration") // ���������� � ������ ��������� ������� (jsp)
	public Employee getEmployee()
	{
		return employee;
	}

	/**
	 * ������ �������������� ����������
	 * @param employee ���������
	 */
	public void setEmployee(Employee employee)
	{
		this.employee = employee;
	}

	/**
	 * @return ������������� ����������
	 */
	public Long getEmployeeId()
	{
		return employeeId;
	}

	/**
	 * ������ ������������� ����������
	 * @param employeeId -- ������������� ����������
	 */
	@SuppressWarnings("UnusedDeclaration") // ���������� � ������ ��������� ������� (struts)
	public void setEmployeeId(Long employeeId)
	{
		this.employeeId = employeeId;
	}

	/**
	 * @return ������ �������
	 */
	@SuppressWarnings("UnusedDeclaration") // ���������� � ������ ��������� ������� (jsp)
	public List<Channel> getChannels()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return channels;
	}

	/**
	 * @param channels ������
	 */
	public void setChannels(List<Channel> channels)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.channels = channels;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		formBuilder.addField(FieldBuilder.buildStringField(ID_FIELD_NAME, "ID ���������",
				new RegexpFieldValidator("\\d{0,14}", "���� ID ��������� ����� ��������� �� ����� 14 ����.")));
		formBuilder.addField(getPhoneField());
		formBuilder.addField(getEmailField(false));
		formBuilder.addField(getLeadEmailField(false));
		formBuilder.addField(getChannelField());

		return formBuilder.build();
	}

	private static Form createSelfEditForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		formBuilder.addField(getChannelField());
		formBuilder.addField(getPhoneField());
		formBuilder.addField(getEmailField(true));
		formBuilder.addField(getLeadEmailField(true));

		return formBuilder.build();
	}

	private static Field getChannelField()
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(CHANNEL_FIELD_NAME);
		fieldBuilder.setDescription("�����");
		fieldBuilder.addValidators(new ChannelValidator());
		return fieldBuilder.build();
	}

	private static Field getPhoneField()
	{
		return FieldBuilder.buildStringField(PHONE_FIELD_NAME, "����� ��������, ������������ ��������",
				new RequiredFieldValidator(),
				new RegexpFieldValidator("[+][0-9][(][0-9]{3}[)][0-9]{3}[-][0-9]{2}[-][0-9]{2}",
						"������ ���� \"����� ��������, ������������ ��������\" ������ ����� ��� +X(XXX)XXX-XX-XX")
		);
	}

	private static Field getEmailField(boolean required)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����� ����������� �����, ������������ ��������");
		fieldBuilder.setName(EMAIL_FIELD_NAME);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		if (required)
			fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.addValidators(
				new EmailFieldValidator()
		);

		return fieldBuilder.build();
	}

	private static Field getLeadEmailField(boolean required)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setName(LEAD_EMAIL_FIELD_NAME);
		fieldBuilder.setDescription("���������� ����� ����������� ����� ������������ ������� ����������");
		if (required)
			fieldBuilder.addValidators(new RequiredFieldValidator());

		fieldBuilder.addValidators(
				new EmailFieldValidator()
		);
		return fieldBuilder.build();
	}
}
