package com.rssl.phizic.business.offers;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CreditProductCondition;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CreditProductConditionHelper;
import com.rssl.phizic.business.persons.ActivePerson;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Nady
 * @ created 19.03.2015
 * @ $Author$
 * @ $Revision$
 */

/**
 * ¬озвращает предложени€, доступные в кредитной за€вке.
 * ѕредложение доступно в кредитной за€вке, если по нему определЄн код продукта, код субпродукта или код типа продукта (в кодировке TransactSM).
 */
public class ETSMLoanOfferFilterService extends AbstractFilterOfferService
{
	public ETSMLoanOfferFilterService(OfferService delegate)
	{
		super(delegate);
	}

	public List<LoanOffer> getLoanOfferByPersonData(Integer number, ActivePerson person, Boolean isViewed) throws BusinessException
	{
		List<LoanOffer> loanOffers = super.getLoanOfferByPersonData(number, person, isViewed);

		List<LoanOffer> filteredLoanOffers = new ArrayList<LoanOffer>();
		if (loanOffers != null)
		{
			for (LoanOffer loanOffer : loanOffers)
			{
				CreditProductCondition creditProductCondition = CreditProductConditionHelper.getCreditProductConditionByLoanOffer(loanOffer);
				String productTypeCode = loanOffer.getProductTypeCode();
				if (creditProductCondition != null)
					productTypeCode = creditProductCondition.getCreditProductType().getCode();

				if (productTypeCode != null)
					filteredLoanOffers.add(loanOffer);
			}
		}

		return filteredLoanOffers;
	}
}
