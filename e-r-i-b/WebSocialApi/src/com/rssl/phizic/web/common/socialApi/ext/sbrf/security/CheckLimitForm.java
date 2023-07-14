package com.rssl.phizic.web.common.socialApi.ext.sbrf.security;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * форма проверки баланса мобльного кошелька
 * @author Pankin
 * @ created 23.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class CheckLimitForm extends ActionFormBase
{
	private Money totalAmount;

	public Money getTotalAmount()
	{
		return totalAmount;
	}

	public void setTotalAmount(Money totalAmount)
	{
		this.totalAmount = totalAmount;
	}
}
