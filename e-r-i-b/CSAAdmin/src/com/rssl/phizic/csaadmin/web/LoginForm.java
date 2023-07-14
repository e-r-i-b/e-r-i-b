package com.rssl.phizic.csaadmin.web;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.CompareValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.passwords.PasswordStrategyValidator;
import org.apache.struts.action.ActionForm;

import java.util.HashMap;
import java.util.Map;

/**
 * @author akrenev
 * @ created 27.09.13
 * @ $Author$
 * @ $Revision$
 *
 * ����� ����� ���������� � �������
 */

public class LoginForm extends ActionForm
{
	private static final String MESSAGE = "������� ����� ������������ � ������.";
	public static final Form LOGIN_FORM = createLoginForm();
	public static final Form CHANGE_PASSWORD_FORM = createChangePasswordForm();

	private Map<String,Object> fields = new HashMap<String, Object>();

	/**
	 * @return �������� ����� �����
	 */
	public Map<String, Object> getFields()
	{
		return fields;
	}

	/**
	 * ���������� �������� ����� �����
	 * @param fields - �������� �����
	 */
	public void setFields(Map<String, Object> fields)
	{
		this.fields = fields;
	}

	/**
	 * �������� �������� ���� ����� �� �����
	 * @param key - ����
	 * @return �������� ���� �����
	 */
	public Object getField(String key)
    {
        return fields.get(key);
    }

	/**
	 * ���������� �������� �� �����
	 * @param key - ����
	 * @param obj - ��������
	 */
    public void setField(String key, Object obj)
    {
        fields.put(key, obj);
    }

	private static Form createLoginForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fb = new FieldBuilder();
		fb.setName("login");
		fb.setDescription("�����");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator(MESSAGE));
		formBuilder.addField(fb.build());

		//noinspection ReuseOfLocalVariable
		fb = new FieldBuilder();
		fb.setName("password");
		fb.setDescription("������");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator(MESSAGE));
		formBuilder.addField(fb.build());

		//noinspection ReuseOfLocalVariable
		fb = new FieldBuilder();
		fb.setName("clientRandom");
		fb.setDescription("������");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator(MESSAGE));
		formBuilder.addField(fb.build());

		return formBuilder.build();
	}

	private static Form createChangePasswordForm()
	{
		PasswordStrategyValidator passwordValidator = new PasswordStrategyValidator();
		passwordValidator.setParameter(PasswordStrategyValidator.STRATEGY_NAME, "employee");

		FormBuilder formBuilder  = new FormBuilder();
		FieldBuilder fb = new FieldBuilder();
		fb.setName("newPassword");
		fb.setDescription("����� ������");
		fb.addValidators(new RequiredFieldValidator(), passwordValidator);
		formBuilder.addField(fb.build());

		//noinspection ReuseOfLocalVariable
		fb = new FieldBuilder();
		fb.setName("repeatedPassword");
		fb.setDescription("������������� ������");
		fb.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fb.build());

		CompareValidator compareValidator = new CompareValidator();
		compareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.EQUAL);
		compareValidator.setBinding(CompareValidator.FIELD_O1, "newPassword");
		compareValidator.setBinding(CompareValidator.FIELD_O2, "repeatedPassword");
		compareValidator.setMessage("������ �� ���������");
		formBuilder.setFormValidators(compareValidator);

		return formBuilder.build();
	}
}
