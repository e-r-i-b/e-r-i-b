package com.rssl.phizic.web.ext.sevb.persons.formBuilders;

import com.rssl.phizic.web.persons.formBuilders.PartiallyPersonFormBuilder;
import com.rssl.common.forms.validators.RequiredFieldValidator;

/**
 * @author Egorova
 * @ created 30.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class SBRFPartiallyPersonFormBuilder extends PartiallyPersonFormBuilder
{
	 protected void initializeValidators()
    {
        super.initializeValidators();

        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
	    // מעקוסעגמ
	    fieldValidators.get(PATR_NAME_FIELD).add(requiredFieldValidator);
    }
}
