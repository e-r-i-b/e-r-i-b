package com.rssl.phizic.business.dictionaries.promoCodesDeposit;

import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.config.promoCodesDeposit.PromoCodesMessage;

/**
 * Логическое исключение при проверке промокодов
 * @author Jatsky
 * @ created 13.01.15
 * @ $Author$
 * @ $Revision$
 */

public class WrongPromoCodeLogicException extends BusinessLogicException
{
	private PromoCodesMessage promoCodesMessage;

	public WrongPromoCodeLogicException(PromoCodesMessage promoCodesMessage)
	{
		super(promoCodesMessage.getTitle());
		this.promoCodesMessage = promoCodesMessage;
	}

	public PromoCodesMessage getPromoCodesMessage()
	{
		return promoCodesMessage;
	}
}
