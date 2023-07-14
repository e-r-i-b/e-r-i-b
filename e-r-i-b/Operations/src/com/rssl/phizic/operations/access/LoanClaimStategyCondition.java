package com.rssl.phizic.operations.access;

import com.rssl.phizic.auth.modes.StrategyCondition;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.documents.payments.ShortLoanClaim;
import com.rssl.phizic.security.ConfirmableObject;

/**
 * @author Moshenko
 * @ created 30.01.2014
 * @ $Author$
 * @ $Revision$
 * ƒанное услови€ нужно дл€ того чтобы не включать карточную стратегию дл€ ShortLoanOffer, ExtendedLoanClaim
 * ƒанное услови€ нужно дл€ того чтобы не включать карточную стратегию дл€ ShortLoanOffer
 */
public class LoanClaimStategyCondition implements StrategyCondition
{
	public String getWarning()
	{
		return null;
	}

	public boolean checkCondition(ConfirmableObject object)
	{
		/**
		 *   ороткие за€вки на кредит(когда разрешение обработка персональных данных)
		 */
		if (object instanceof ShortLoanClaim && ((ShortLoanClaim)object).getProcessingEnabled())
			return false;

		/**
		 * –асширенной за€вке на кредит не доступно подтверждение по паролю с чека
		 */
		if (object instanceof ExtendedLoanClaim)
			return false;

		return true;
	}
}
