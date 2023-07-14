package com.rssl.phizic.web.ext.sbrf.persons.formBuilders;

import com.rssl.phizic.web.persons.formBuilders.EmpoweredFullFormBuilder;
import com.rssl.common.forms.validators.RequiredFieldValidator;

/**
 * @author eMakarov
 * @ created 03.09.2008
 * @ $Author$
 * @ $Revision$
 */
public class SBRFEmpoweredFullFormBuilder extends EmpoweredFullFormBuilder
{
	protected void initializeValidators()
    {
		super.initializeValidators();

		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
		fieldValidators.get(MOBILE_PHONE_FIELD).add(requiredFieldValidator);
		fieldValidators.get(MOBILE_OPERATOR_FIELD).add(requiredFieldValidator);
    }
}
