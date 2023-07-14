package com.rssl.phizic.operations.account;

import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.AccountFilter;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ViewEntityOperation;

/**
 * Created by IntelliJ IDEA. User: Novikov_A Date: 28.12.2006 Time: 16:38:04 To change this template use File
 * | Settings | File Templates.
 */
public class GetAccountInfoOperation extends OperationBase<AccountFilter> implements ViewEntityOperation
{
	protected AccountLink accountLink;

	public void initialize (Long accountId) throws BusinessException, BusinessLogicException
	{
		PersonData  personData = PersonContext.getPersonDataProvider().getPersonData();
		AccountLink temp = personData.getAccount(accountId);

		if (temp == null)
			throw new BusinessException("информация по счету с id " + accountId + " не найдена");

		accountLink = temp;
	}

	public AccountLink getEntity() throws BusinessException
    {  
	    return accountLink;
    }
}
