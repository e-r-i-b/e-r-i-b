package com.rssl.phizic.web.persons.formBuilders;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.Field;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.persons.AccountExistValidator;
import com.rssl.phizic.business.persons.PayAccountExistValidator;
import com.rssl.phizic.operations.validators.ServiceExistValidator;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Omeliyanchuk
 * @ created 05.12.2006
 * @ $Author$
 * @ $Revision$
 */

/**
 * Форма для регистрации представителя
 */
public class EmpoweredRegistrationFormBuilder extends PersonFormBuilderBase
{
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
        accountValidator.setMessage("Для подключения необходимо указать хотя бы один доступный счет");

        ServiceExistValidator serviceValidator = new ServiceExistValidator();
        serviceValidator.setBinding(ServiceExistValidator.FIELD_O1, CLIENT_ID_FIELD);
        serviceValidator.setMessage("Для подключения необходимо указать хотя бы одну доступную операцию");
        builder.setFormValidators(serviceValidator,accountValidator);

        return builder.build();
	}

	protected void initializeValidators()
	{
		return;
	}
}
