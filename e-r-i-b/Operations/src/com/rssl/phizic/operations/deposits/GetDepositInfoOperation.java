package com.rssl.phizic.operations.deposits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.resources.external.DepositLink;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ViewEntityOperation;

/**
 * Created by IntelliJ IDEA. User: Novikov_A Date: 28.12.2006 Time: 19:25:43 To change this template use File
 * | Settings | File Templates.
 */
public class GetDepositInfoOperation extends OperationBase implements ViewEntityOperation
{
	protected DepositLink  deposit;

	public void initialize (Long depositId) throws BusinessException
	{
		if (depositId == null)
		  throw new BusinessException("Не установлен идентификатор депозита");
		PersonData data = PersonContext.getPersonDataProvider().getPersonData();
		deposit = data.getDeposit(depositId);
	}

    public DepositLink getEntity()
    {
	    return deposit;
    }
}
