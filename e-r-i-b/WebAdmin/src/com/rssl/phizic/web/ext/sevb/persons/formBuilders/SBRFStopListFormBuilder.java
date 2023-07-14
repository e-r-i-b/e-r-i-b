package com.rssl.phizic.web.ext.sevb.persons.formBuilders;

import com.rssl.phizic.web.persons.formBuilders.StopListFormBuilder;
import com.rssl.common.forms.validators.RequiredFieldValidator;

/**
 * @author Egorova
 * @ created 30.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class SBRFStopListFormBuilder extends StopListFormBuilder
{
	protected void initializeValidators()
    {
        super.initializeValidators();

        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
	    fieldValidators.get(PATR_NAME_FIELD).add(requiredFieldValidator);

    }
}
