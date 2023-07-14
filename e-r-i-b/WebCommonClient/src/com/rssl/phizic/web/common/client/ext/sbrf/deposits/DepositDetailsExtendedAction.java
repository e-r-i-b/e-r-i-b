package com.rssl.phizic.web.common.client.ext.sbrf.deposits;

import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.deposits.DepositDetailsOperation;
import com.rssl.phizic.web.common.client.deposits.DepositDetailsAction;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.business.BusinessException;

/**
 * @author Rydvanskiy
 * @ created 02.03.2011
 * @ $Author$
 * @ $Revision$
 */

public class DepositDetailsExtendedAction  extends DepositDetailsAction
{
	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException
	{
		DepositDetailsOperation operation = createOperation("DepositDetailsOperation", "AccountOpeningClaim");
		operation.initialize(frm.getId());

		return operation;
	}
	
	protected boolean getEmptyErrorPage()
	{
		return true;
	}
}
