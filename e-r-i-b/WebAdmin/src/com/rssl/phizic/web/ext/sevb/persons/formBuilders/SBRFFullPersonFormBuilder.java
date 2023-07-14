package com.rssl.phizic.web.ext.sevb.persons.formBuilders;

import com.rssl.phizic.web.persons.formBuilders.FullPersonFormBuilder;
import com.rssl.common.forms.validators.RequiredFieldValidator;

/**
 * @author Egorova
 * @ created 30.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class SBRFFullPersonFormBuilder extends FullPersonFormBuilder
{
	protected void initializeValidators()
    {
        super.initializeValidators();

        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
	    fieldValidators.get(PATR_NAME_FIELD).add(requiredFieldValidator);
	    fieldValidators.get(MOBILE_PHONE_FIELD).add(requiredFieldValidator);
	    fieldValidators.get(MOBILE_OPERATOR_FIELD).add(requiredFieldValidator);

    }
}
