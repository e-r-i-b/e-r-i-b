package com.rssl.phizic.business.loanCardOffer;

import com.rssl.phizic.business.BusinessLogicException;

/**
 * User: Moshenko
 * Date: 24.05.2011
 * Time: 12:49:17
 */
public class DublicateLoanCardOfferException extends BusinessLogicException
{
	public DublicateLoanCardOfferException()
	{
		super("ѕредложение на  кредитную карта уже существует");
	}
}