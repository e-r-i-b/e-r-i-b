package com.rssl.phizic.web.atm.deposits;

import com.rssl.phizic.web.ext.sbrf.payments.ViewDepositTermsActionBase;
import com.rssl.phizic.operations.ext.sbrf.deposits.ViewDepositTermsOperation;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author Pankin
 * @ created 24.01.2012
 * @ $Author$
 * @ $Revision$
 */

public class ViewDepositTermsATMAction extends ViewDepositTermsActionBase
{
	protected ViewDepositTermsOperation getViewDepositTermsOperation() throws BusinessException, BusinessLogicException
	{
		return createOperation(ViewDepositTermsOperation.class, "AccountOpeningClaim");
	}
}
