package com.rssl.phizic.web.persons.formBuilders;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.Field;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.persons.PayAccountExistValidator;
import com.rssl.phizic.business.persons.AccountExistValidator;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Omeliyanchuk
 * @ created 05.12.2006
 * @ $Author$
 * @ $Revision$
 */

/**
 * Форма для регистрации договора
 */
public class ClientRegistrationFormBuilder extends PersonFormBuilderBase
{
	private Boolean needAccount;

	public Boolean getNeedAccount()
	{
		return needAccount;
	}

	public void setNeedAccount(Boolean needAccount)
	{
		this.needAccount = needAccount;
	}

	public Form buildForm()
	{
		List<Field> fields = new ArrayList<Field>();
		fields.add(buildField(CLIENT_ID_FIELD, CLIENT_ID_FIELD_DESCRIPTION, STRING_TYPE_NAME));

		FormBuilder builder     = new FormBuilder();
        Field[]     fieldsArray = new Field[fields.size()];

		fields.toArray(fieldsArray);
        builder.setFields( fieldsArray );

		AccountExistValidator accountValidator = new AccountExistValidator();
        accountValidator.setBinding(PayAccountExistValidator.FIELD_O1, CLIENT_ID_FIELD);
        accountValidator.setMessage("Для подключения клиента у него должен быть указан хотя бы один счет.");
		builder.setFormValidators(accountValidator);

        if(getNeedAccount())
        {
	        PayAccountExistValidator payAccountValidator = new PayAccountExistValidator();
			payAccountValidator.setBinding(PayAccountExistValidator.FIELD_O1, CLIENT_ID_FIELD);
			payAccountValidator.setMessage("Для подключения необходимо указать счет для списания платы");
			builder.setFormValidators(accountValidator, payAccountValidator);
        }

        return builder.build();
	}

	protected void initializeValidators()
	{
		return;
	}		
}
