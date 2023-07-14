package com.rssl.phizic.web.client.ext.sbrf.security;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.payments.forms.validators.AgeValidator;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Gulov
 * @ created 07.05.15
 * @ $Author$
 * @ $Revision$
 */
/**
 * ����� ����� ���������� ������ �������
 */
public class GuestPersonDataForm extends ActionFormBase
{
	public static final Form FORM = createForm();

	private Map<String,String> fields = new HashMap<String, String>();
	private String phone;

	public Map<String,String> getFields()
	{
		return fields;
	}

	public String getField(String key)
	{
		return fields.get(key);
	}

	public void setField(String key, String obj)
	{
		fields.put(key, obj);
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder = new FieldBuilder();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������");
		fieldBuilder.setName("surName");
		fieldBuilder.setType("string");
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(), new RegexpFieldValidator(".{0,120}", "���� ������� �� ������ ��������� 120 ��������"),
			new RegexpFieldValidator("^[�-��-ߨ�]+$", "������������ ������� � ���� ���������. ������� ������� ���������� (�������� �������)"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("firstName");
		fieldBuilder.setType("string");
		fieldBuilder.setDescription("���");
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(), new RegexpFieldValidator(".{0,120}", "���� ��� �� ������ ��������� 120 ��������"),
			new RegexpFieldValidator("^[�-��-ߨ�]+$", "������������ ������� � ���� �����. ������� ��� ���������� (�������� �������)"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������");
		fieldBuilder.setName("patrName");
		fieldBuilder.setType("string");
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RegexpFieldValidator(".{0,120}", "���� �������� �� ������ ��������� 120 ��������"),
			new RegexpFieldValidator("^[�-��-ߨ�]+$", "������������ ������� � ���� ���������. ������� �������� ���������� (�������� �������)"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���� ��������");
		fieldBuilder.setName("birthday");
		fieldBuilder.setType("date");
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(), new AgeValidator(21, 100, "��� ������� �� ������������� �������� �������"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������");
		fieldBuilder.setName("document");
		fieldBuilder.setType("string");
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(), new RegexpFieldValidator(".{0,16}", "���� �������� �� ������ ��������� 16 ��������"),
			new RegexpFieldValidator("^[0-9]+$", "������� ����� � ����� �������� �������"));
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
	private String operation;

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getPhone()
	{
		return phone;
	}

	public String getOperation()
	{
		return operation;
	}

	public void setOperation(String operation)
	{
		this.operation = operation;
	}
}
