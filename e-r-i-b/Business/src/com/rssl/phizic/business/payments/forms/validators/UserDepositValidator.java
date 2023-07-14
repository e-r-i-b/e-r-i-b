package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;

/**
 * @author Omeliyanchuk
 * @ created 02.10.2007
 * @ $Author$
 * @ $Revision$
 */

/**
 * проверка пренадлежит ли вклад текущему пользователю. 
 */
public class UserDepositValidator extends FieldValidatorBase
{
    public boolean validate(String value) throws TemporalDocumentException
    {
	    try
	    {
		    if( isValueEmpty(value) )
		        return true;

		    PersonData data = PersonContext.getPersonDataProvider().getPersonData();
			return data.getDeposit(Long.valueOf(value))!=null;
	    }
	    catch (BusinessException e)
	    {
		    throw new TemporalDocumentException(e);
	    }
    }
}
