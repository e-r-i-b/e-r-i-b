package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.TemporalBusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.ActiveAccountFilter;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;

/**
 * @author Egorova
 * @ created 08.02.2008
 * @ $Author$
 * @ $Revision$
 */
public class AccountIsOpenValidator extends FieldValidatorBase
{
	public AccountIsOpenValidator()
    {
       setMessage("—чет закрыт");
    }


	public boolean validate(String value) throws TemporalDocumentException
    {
	    try
	    {
		    if( isValueEmpty(value) )
		        return true;

		    PersonData data = PersonContext.getPersonDataProvider().getPersonData();

		    AccountLink accountLink = data.findAccount(value);
		    if (accountLink == null)
			    return false;

		    try
		    {
			    return accept(accountLink);
		    }
		    catch (TemporalBusinessException e)
		    {
			    throw new TemporalDocumentException(e);
		    }

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

	protected boolean accept(AccountLink accountLink) throws TemporalBusinessException
	{
		return (new ActiveAccountFilter()).accept(accountLink.getAccount());
	}
}
