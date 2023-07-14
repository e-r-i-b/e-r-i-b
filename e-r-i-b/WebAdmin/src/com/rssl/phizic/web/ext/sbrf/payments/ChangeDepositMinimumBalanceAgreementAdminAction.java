package com.rssl.phizic.web.ext.sbrf.payments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.deposits.GetDepositCollateralAgreementsOperation;

/**
 * @author EgorovaA
 * @ created 23.12.13
 * @ $Author$
 * @ $Revision$
 */
public class ChangeDepositMinimumBalanceAgreementAdminAction extends ChangeDepositMinimumBalanceAgreementAction
{
	@Override
	protected GetDepositCollateralAgreementsOperation getDepositCollateralAgreementsOperation() throws BusinessException
	{
		if (checkAccess(GetDepositCollateralAgreementsOperation.class, "ViewPaymentList"))
			return createOperation(GetDepositCollateralAgreementsOperation.class, "ViewPaymentList");
		else
			return createOperation(GetDepositCollateralAgreementsOperation.class, "ViewPaymentListUseClientForm");
	}
}
