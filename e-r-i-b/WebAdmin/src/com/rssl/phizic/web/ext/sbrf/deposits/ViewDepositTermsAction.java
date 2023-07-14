package com.rssl.phizic.web.ext.sbrf.deposits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ext.sbrf.deposits.ViewDepositTermsOperation;
import com.rssl.phizic.web.ext.sbrf.payments.ViewDepositTermsActionBase;

/**
 * @author Pankin
 * @ created 07.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class ViewDepositTermsAction extends ViewDepositTermsActionBase
{
	protected ViewDepositTermsOperation getViewDepositTermsOperation() throws BusinessException, BusinessLogicException
	{
		if (checkAccess(ViewDepositTermsOperation.class, "ViewPaymentList"))
			return createOperation(ViewDepositTermsOperation.class, "ViewPaymentList");
		else
			return createOperation(ViewDepositTermsOperation.class, "ViewPaymentListUseClientForm");
	}
}
