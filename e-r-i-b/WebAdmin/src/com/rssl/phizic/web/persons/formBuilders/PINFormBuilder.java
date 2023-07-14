package com.rssl.phizic.web.persons.formBuilders;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.Field;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.phizic.business.security.pin.validators.PINValidator;
import com.rssl.phizic.business.security.pin.validators.PINDepartmentLinkValidator;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Omeliyanchuk
 * @ created 05.12.2006
 * @ $Author$
 * @ $Revision$
 */

/**
 * Форма для PIN
 */
public class PINFormBuilder extends PersonFormBuilderBase
{
	public Form buildForm()
	{
		List<Field> fields = new ArrayList<Field>();
		fields.add(buildField(PIN_ENVELOPE_NUMBER_FIELD, PIN_ENVELOPE_NUMBER_FIELD_DESCRIPTION, STRING_TYPE_NAME));
		fields.add(buildField(PIN_ENVELOPE_OLD_NUMBER_FIELD, PIN_ENVELOPE_NUMBER_FIELD_DESCRIPTION, STRING_TYPE_NAME));
		fields.add(buildField(DEPARTMENT_ID_FIELD, DEPARTMENT_ID_FIELD_DESCRIPTION, STRING_TYPE_NAME));

		FormBuilder builder     = new FormBuilder();
        Field[]     fieldsArray = new Field[fields.size()];

		fields.toArray(fieldsArray);
        builder.setFields( fieldsArray );

		PINDepartmentLinkValidator pinDepartmentValidator = new PINDepartmentLinkValidator();

		pinDepartmentValidator.setBinding(PINDepartmentLinkValidator.DEPARTEMNT_ID_FIELD, DEPARTMENT_ID_FIELD);
		pinDepartmentValidator.setBinding(PINDepartmentLinkValidator.PIN_ENVELOPE_FIELD, PIN_ENVELOPE_NUMBER_FIELD);
		pinDepartmentValidator.setMessage("Клиент и номер ПИН-конверта должны принадлежать одному подразделению");

		builder.setFormValidators(pinDepartmentValidator);

        return builder.build();
	}

	protected void initializeValidators()
	{
        List<FieldValidator> validators;
        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();

		PINValidator pinValidator = new PINValidator();
		pinValidator.setMessage("Неверный номер ПИН-конверта.");

	    validators = new ArrayList<FieldValidator>();
        validators.add( requiredFieldValidator);
        validators.add( new RegexpFieldValidator(".{0,16}","Поле [" + PIN_ENVELOPE_NUMBER_FIELD_DESCRIPTION + "] не должно превышать 16 символов"));
		validators.add(pinValidator);
        fieldValidators.put(PIN_ENVELOPE_NUMBER_FIELD, validators);

		return;
	}
}
