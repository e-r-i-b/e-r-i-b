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
 * Форма входа сотрудника в систему
 */

public class LoginForm extends ActionForm
{
	private static final String MESSAGE = "Введите логин пользователя и пароль.";
	public static final Form LOGIN_FORM = createLoginForm();
	public static final Form CHANGE_PASSWORD_FORM = createChangePasswordForm();

	private Map<String,Object> fields = new HashMap<String, Object>();

	/**
	 * @return значения полей формы
	 */
	public Map<String, Object> getFields()
	{
		return fields;
	}

	/**
	 * Установить значение полей формы
	 * @param fields - значение полей
	 */
	public void setFields(Map<String, Object> fields)
	{
		this.fields = fields;
	}

	/**
	 * Получить значение поля формы по ключу
	 * @param key - ключ
	 * @return значение поля формы
	 */
	public Object getField(String key)
    {
        return fields.get(key);
    }

	/**
	 * Установить значение на форму
	 * @param key - ключ
	 * @param obj - значение
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
		fb.setDescription("логин");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator(MESSAGE));
		formBuilder.addField(fb.build());

		//noinspection ReuseOfLocalVariable
		fb = new FieldBuilder();
		fb.setName("password");
		fb.setDescription("Пароль");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator(MESSAGE));
		formBuilder.addField(fb.build());

		//noinspection ReuseOfLocalVariable
		fb = new FieldBuilder();
		fb.setName("clientRandom");
		fb.setDescription("Пароль");
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
		fb.setDescription("Новый пароль");
		fb.addValidators(new RequiredFieldValidator(), passwordValidator);
		formBuilder.addField(fb.build());

		//noinspection ReuseOfLocalVariable
		fb = new FieldBuilder();
		fb.setName("repeatedPassword");
		fb.setDescription("Подтверждение пароля");
		fb.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fb.build());

		CompareValidator compareValidator = new CompareValidator();
		compareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.EQUAL);
		compareValidator.setBinding(CompareValidator.FIELD_O1, "newPassword");
		compareValidator.setBinding(CompareValidator.FIELD_O2, "repeatedPassword");
		compareValidator.setMessage("Пароли не совпадают");
		formBuilder.setFormValidators(compareValidator);

		return formBuilder.build();
	}
}
