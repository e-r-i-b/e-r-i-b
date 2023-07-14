package com.rssl.phizic.business.loanOffer;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * User: Moshenko
 * Date: 24.05.2011
 * Time: 12:49:17
 */
public class DublicateLoanOfferException extends BusinessLogicException
{
	public DublicateLoanOfferException()
	{
		super("ѕредложение на  кредит уже существует");
	}
}
