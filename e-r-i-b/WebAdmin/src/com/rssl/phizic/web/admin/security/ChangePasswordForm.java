package com.rssl.phizic.web.admin.security;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.CompareValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.passwords.PasswordLengthValidator;
import com.rssl.common.forms.validators.passwords.PasswordStrategyValidator;
import com.rssl.common.forms.validators.passwords.PasswordValidationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kidyaev
 * @ created 22.12.2005
 * @ $Author: bogdanov $
 * @ $Revision: 57189 $
 */
@SuppressWarnings({"JavaDoc"})
public class ChangePasswordForm extends ActionFormBase
{
    public static final Form CHANGE_PASSWORD_FORM = createForm();

	private Map<String,String> fields = new HashMap<String, String>();


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
		FormBuilder formBuilder  = new FormBuilder();
		FieldBuilder fieldBuilder = null;

		fieldBuilder = new FieldBuilder();

		PasswordStrategyValidator pswValidator = new PasswordStrategyValidator();
		pswValidator.setParameter(PasswordStrategyValidator.STRATEGY_NAME, "employee");
		fieldBuilder.setName("newPassword");
	    fieldBuilder.setDescription("Новый пароль");

		//DbConfig dc = new DbConfig(PasswordValidationConfigImpl.PASSWORD_VALIDATION_CATEGORY); // получаем здесь, т.к. PasswordLengthValidator в Forms, где нет зависимости от Security
		PasswordValidationConfig passwordValidationConfig = ConfigFactory.getConfig(PasswordValidationConfig.class);
		int length = passwordValidationConfig.getActualPasswordLength("employee");

		fieldBuilder.setValidators(new RequiredFieldValidator(), new PasswordLengthValidator(length), pswValidator);
		formBuilder.addField(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
	    fieldBuilder.setName("repeatedPassword");
	    fieldBuilder.setDescription("Подтверждение пароля");
		fieldBuilder.setValidators(new RequiredFieldValidator());
	    formBuilder.addField(fieldBuilder.build());

		CompareValidator compareValidator = new CompareValidator();
		compareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.EQUAL);
		compareValidator.setBinding(CompareValidator.FIELD_O1, "newPassword");
		compareValidator.setBinding(CompareValidator.FIELD_O2, "repeatedPassword");
		compareValidator.setMessage("Пароли не совпадают");
		formBuilder.setFormValidators(compareValidator);

	    return formBuilder.build();
	}
}
