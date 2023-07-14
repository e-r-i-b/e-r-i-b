package com.rssl.phizic.web.ext.sbrf.payments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.deposits.GetDepositAgreementWithCapitalizationOperation;

/**
 * @author EgorovaA
 * @ created 05.08.14
 * @ $Author$
 * @ $Revision$
 */
public class AccountChangeInterestDestinationAgreementAdminAction extends AccountChangeInterestDestinationAgreementAction
{
	protected GetDepositAgreementWithCapitalizationOperation getOperation() throws BusinessException
	{
		if (checkAccess(GetDepositAgreementWithCapitalizationOperation.class, "ViewPaymentList"))
			return createOperation(GetDepositAgreementWithCapitalizationOperation.class, "ViewPaymentList");
		else
			return createOperation(GetDepositAgreementWithCapitalizationOperation.class, "ViewPaymentListUseClientForm");
	}
}
