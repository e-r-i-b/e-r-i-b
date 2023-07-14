package com.rssl.phizic.web.persons.formBuilders;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.phizic.business.persons.PersonHasDebtsValidator;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Omeliyanchuk
 * @ created 05.12.2006
 * @ $Author$
 * @ $Revision$
 */

/**
 * Форма для расторжения договора
 */
public class ClientCancelationFormBuilder extends PersonFormBuilderBase
{
	private Boolean delete;

	public Boolean getDelete()
	{
		return delete;
	}

	public void setDelete(Boolean delete)
	{
		this.delete = delete;
	}

	public Form buildForm()
	{
		List<Field> fields = new ArrayList<Field>();
		fields.add(buildField(PROLONGATION_REJECTION_DATE_FIELD, PROLONGATION_REJECTION_DATE_FIELD_DESCRIPTION, DATE_TYPE_NAME));
		fields.add(buildField(CONTRACT_CANCELLATION_COUSE_FIELD, CONTRACT_CANCELLATION_COUSE_FIELD_DESCRIPTION, STRING_TYPE_NAME));

		FormBuilder builder     = new FormBuilder();
        Field[]     fieldsArray = new Field[fields.size()];

		fields.toArray(fieldsArray);
        builder.setFields( fieldsArray );

		if(!getDelete())
        {
	        PersonHasDebtsValidator payAccountValidator = new PersonHasDebtsValidator();
			payAccountValidator.setBinding(PersonHasDebtsValidator.CLIENT_ID, CLIENT_ID_FIELD);
			payAccountValidator.setMessage("Для того, чтобы удалить пользователя с долгами, необходимо подтверждение.");
			builder.setFormValidators(payAccountValidator);
        }

        return builder.build();
	}

	protected void initializeValidators()
	{
		List<FieldValidator> validators;
        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
        DateFieldValidator dateValidator          = new DateFieldValidator();

		//дата отказа от обслуживания
	    validators = new ArrayList<FieldValidator>();
        validators.add( requiredFieldValidator);
        validators.add( dateValidator );
        fieldValidators.put(PROLONGATION_REJECTION_DATE_FIELD, validators);

		//причина отказа от обслуживания
	    validators = new ArrayList<FieldValidator>();
        validators.add( requiredFieldValidator);
        fieldValidators.put(CONTRACT_CANCELLATION_COUSE_FIELD, validators);
	}
}
