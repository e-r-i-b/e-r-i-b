package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;

/**
 * @author Evgrafov
 * @ created 30.11.2005
 * @ $Author: khudyakov $
 * @ $Revision: 32866 $
 */

public class UserAccountValidator extends FieldValidatorBase
{
    public boolean validate(String value) throws TemporalDocumentException
    {
	    try
	    {
		    if( isValueEmpty(value) )
		        return true;

		    PersonData data = PersonContext.getPersonDataProvider().getPersonData();

		    AccountLink account = data.findAccount(value);

		    return account != null;

	    }
	    catch (BusinessException e)
	    {
		    throw new RuntimeException(e); 
	    }
	    catch (BusinessLogicException e)
	    {
		    throw new RuntimeException(e);
	    }
    }
}
