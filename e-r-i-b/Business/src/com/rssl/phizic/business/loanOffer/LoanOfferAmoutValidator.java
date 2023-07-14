package com.rssl.phizic.business.loanOffer;

import com.rssl.ikfl.crediting.OfferId;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.offers.LoanOffer;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;

/**
 * @author Mescheryakova
 * @ created 22.06.2011
 * @ $Author$
 * @ $Revision$
 * Проверка, что введенная пользователем сумма кредита меньше либо равна разрешенной
 */

public class LoanOfferAmoutValidator
{
	public static final String LOAN_PRODUCT_ID  = "loan";
	public static final String AMOUNT_FIELD     = "amount";
	public static final String DURATION = "duration";

	/**
	 * @param loanOfferId id предодобренного предложения
	 * @param duration срок
	 * @param amount сумма
	 * @return
	 * @throws BusinessException
	 */
	public boolean validate(String loanOfferId, Long duration,Money amount) throws BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		OfferId offerId = OfferId.fromString(loanOfferId);
		LoanOffer loanOffer = personData.findLoanOfferById(offerId);
		if (loanOffer == null || !loanOffer.getMaxLimit().getCurrency().equals(amount.getCurrency())
							  || LoanOfferHelper.getAmountForDuration(loanOffer, duration).compareTo(amount.getDecimal()) == -1)
			return false;

		return true;
	}


}
