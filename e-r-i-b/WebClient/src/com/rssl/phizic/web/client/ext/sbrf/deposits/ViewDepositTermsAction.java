package com.rssl.phizic.web.client.ext.sbrf.deposits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ext.sbrf.deposits.ViewDepositTermsOperation;
import com.rssl.phizic.web.ext.sbrf.payments.ViewDepositTermsActionBase;

/**
 * ќтображение условий размещени€ средств во вклад
 * @author Pankin
 * @ created 06.06.2011
 * @ $Author$
 * @ $Revision$
 */

public class ViewDepositTermsAction extends ViewDepositTermsActionBase
{
	protected ViewDepositTermsOperation getViewDepositTermsOperation() throws BusinessException, BusinessLogicException
	{
		return createOperation(ViewDepositTermsOperation.class, "AccountOpeningClaim");
	}
}
