package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.AccountState;
import com.rssl.phizic.utils.MockHelper;

/**
 * @author Gainanov
 * @ created 30.03.2007
 * @ $Author$
 * @ $Revision$
 */
public class AccountIsNotBlockedValidator extends FieldValidatorBase
{
    public AccountIsNotBlockedValidator()
    {
        this("Счет заблокирован");
    }

    public AccountIsNotBlockedValidator(String message)
    {
        setMessage(message);
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

		    Account account = accountLink.getAccount();

		    if(MockHelper.isMockObject(account))
		        throw new TemporalDocumentException("Ошибка при получении информации по счету №"+account.getNumber());

		    return account.getAccountState() != AccountState.CLOSED;

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

