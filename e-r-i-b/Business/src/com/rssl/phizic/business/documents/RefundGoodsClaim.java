package com.rssl.phizic.business.documents;

import com.rssl.phizic.gate.documents.WithdrawMode;

/**
 * ¬озврат товара из интернет-магазина
 * 
 * @author gladishev
 * @ created 31.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class RefundGoodsClaim extends RollbackOrderClaim
{
	private static final String RETURNED_GOODS = "returned-goods";

	public WithdrawMode getWithdrawMode()
	{
		return WithdrawMode.Partial;
	}

	public String getReturnedGoods()
	{
		return getNullSaveAttributeStringValue(RETURNED_GOODS);
	}
}
