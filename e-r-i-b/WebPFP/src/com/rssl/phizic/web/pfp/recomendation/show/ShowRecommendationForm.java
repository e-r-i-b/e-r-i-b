package com.rssl.phizic.web.pfp.recomendation.show;

import com.rssl.phizic.web.pfp.EditPersonalFinanceProfileForm;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.business.dictionaries.pfp.products.card.recommendation.UseCreditCardRecommendation;

/**
 * @author komarov
 * @ created 28.06.2013 
 * @ $Author$
 * @ $Revision$
 */

public class ShowRecommendationForm extends EditPersonalFinanceProfileForm 
{
	private Money outcomeMoney; // ежемесячные расходы
	private UseCreditCardRecommendation cardRecommendation;// рекомендации

	/**
	 * @return ежемесячные расходы
	 */
	public Money getOutcomeMoney()
	{
		return outcomeMoney;
	}

	/**
	 * @param outcomeMoney ежемесячные расходы
	 */
	public void setOutcomeMoney(Money outcomeMoney)
	{
		this.outcomeMoney = outcomeMoney;
	}

	/**
	 * @return рекомендации
	 */
	public UseCreditCardRecommendation getCardRecommendation()
	{
		return cardRecommendation;
	}

	/**
	 * @param cardRecommendation рекомендации
	 */
	public void setCardRecommendation(UseCreditCardRecommendation cardRecommendation)
	{
		this.cardRecommendation = cardRecommendation;
	}

}
