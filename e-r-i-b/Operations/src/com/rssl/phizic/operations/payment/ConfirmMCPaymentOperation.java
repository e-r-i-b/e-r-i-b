package com.rssl.phizic.operations.payment;

import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizic.auth.modes.ConfirmStrategyResult;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.security.SecurityLogicException;

/**
 * Подтверждение платежа системой
 *
 * @author gladishev
 * @ created 25.06.2013
 * @ $Author$
 * @ $Revision$
 */
public class ConfirmMCPaymentOperation extends ConfirmFormPaymentOperation
{
	@Override
	public void validateConfirm() throws BusinessException, SecurityLogicException, SecurityException
	{

	}

	@Override
	public boolean isSignatureSaveRequired()
	{
		return false;
	}

	@Override
	protected CreationType getClientOperationChannel()
	{
		return CreationType.internet;
	}
}
