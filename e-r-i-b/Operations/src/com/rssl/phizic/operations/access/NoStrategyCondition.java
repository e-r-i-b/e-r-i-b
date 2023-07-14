package com.rssl.phizic.operations.access;

import com.rssl.phizic.auth.modes.StrategyCondition;
import com.rssl.phizic.business.documents.AccountChangeInterestDestinationClaim;
import com.rssl.phizic.business.documents.BusinessDocumentBase;
import com.rssl.phizic.business.documents.ChangeDepositMinimumBalanceClaim;
import com.rssl.phizic.business.documents.payments.LoanClaimBase;
import com.rssl.phizic.business.documents.payments.ShortLoanClaim;
import com.rssl.phizic.common.types.commission.WriteDownOperation;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.utils.ListUtil;
import org.apache.commons.collections.ListUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author Mescheryakova
 * @ created 22.06.2011
 * @ $Author$
 * @ $Revision$
 */

public class NoStrategyCondition implements StrategyCondition
{
	public boolean checkCondition(ConfirmableObject object)
	{
		/** не подтверждаем заявки на:
		*   кредитные продукты
		*   кредитные карты
		*   предложения по кредитам
		*   предложения по кредитным картам
		 *  изменение неснижаемого остатка
		 *  Короткие заявки на кредит(для случаем в когда не разрешена обработка персональных данных. )
		*/
		if (object instanceof LoanClaimBase || object instanceof ChangeDepositMinimumBalanceClaim || object instanceof AccountChangeInterestDestinationClaim
			|| (object instanceof ShortLoanClaim && !((ShortLoanClaim)object).getProcessingEnabled()))
		{
			return false;
		}
		return true;
	}

	public String getWarning()
	{
		return null;
	}
}