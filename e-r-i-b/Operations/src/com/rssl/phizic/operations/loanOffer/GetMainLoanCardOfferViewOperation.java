package com.rssl.phizic.operations.loanOffer;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.offers.LoanCardOffer;

import java.util.List;

/**
 * User: Moshenko
 * Date: 31.05.2011
 * Time: 12:11:41
 * Отображение  предложений по картам, в верхней части главной страницы
 */
public class GetMainLoanCardOfferViewOperation extends GetLoanCardOfferViewOperation
{
	/**
	 * возвращаем не просмотренные предложения
	 * @return
	 */
	protected List<LoanCardOffer> getOneLoanCardOffer() throws BusinessException
	{
		return personData.getLoanCardOfferByPersonData(1, false);
	}
}
