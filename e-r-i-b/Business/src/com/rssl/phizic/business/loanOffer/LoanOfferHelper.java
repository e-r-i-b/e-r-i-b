package com.rssl.phizic.business.loanOffer;

import com.rssl.ikfl.crediting.OfferCondition;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.ErmbHelper;
import com.rssl.phizic.business.offers.LoanOffer;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loanclaim.LoanClaimConfig;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author Mescheryakova
 * @ created 01.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class LoanOfferHelper
{
	private static final PersonService personService    = new PersonService();
    /**
     * Возвращает текст, описывающий условия предодобренных предложений
     *
     * @return List&lt;String&gt;
     */
    public static final List<String> getProfitPreapprovedCredit()
    {
        return ConfigFactory.getConfig(LoanClaimConfig.class).getProfitPreapprovedCredit();
    }

	/**
	 * Получает сумму для конкретного срока из кредитного предложения
	 * @param loanOffer - кредитное предложение
	 * @param duration  - срок
	 * @return - сумма для срока
	 */
	public static BigDecimal getAmountForDuration(LoanOffer loanOffer, long duration)
	{
		List<OfferCondition> offerConditions = loanOffer.getConditions();

		for (OfferCondition offerCondition : offerConditions)
		{
			int period = offerCondition.getPeriod();
			if (duration == period)
				return offerCondition.getAmount();
		}
		return new BigDecimal(0);
	}

	public static Set<LoanOffer> filterLoanOffersByEndDate(final List<LoanOffer> loanOffers)
	{
		if (loanOffers == null)
		{
			return Collections.emptySet();
		}

		Set<LoanOffer> offers = new HashSet<LoanOffer>();
		for (LoanOffer loanOffer: loanOffers)
		{
			if (loanOffer.getEndDate() == null || loanOffer.getEndDate().compareTo(Calendar.getInstance()) > 0)
			{
				offers.add(loanOffer);
			}
		}

		return offers;
	}

	/**
	 * @param offer Предодобренное предложение
	 * @param person клиент.
	 * @return Принадлежит ли предложение указанному клиенту.
	 */
	public static boolean checkOwnership(LoanOffer offer, ActivePerson person) throws BusinessException
	{
		if (offer == null)
			throw new IllegalArgumentException("Параметр offer равен null");

		if (!ErmbHelper.isSameFIOAndBirthday(offer.getFirstName(), offer.getSurName(), offer.getPatrName(), offer.getBirthDay(),
				person.getFirstName(), person.getSurName(), person.getPatrName(), person.getBirthDay()))
			return false;

		String offerSeriesAndNumber = (offer.getPasportSeries() + offer.getPasportNumber()).replaceAll(" ", "");
		List <String> seriesAndNumbers = PersonHelper.getPersonSeriesAndNumbers(person);

		for(String sAndN:seriesAndNumbers)
		{
			if (offerSeriesAndNumber.equalsIgnoreCase(sAndN))
				return true;
		}
		return false;
	}
}
