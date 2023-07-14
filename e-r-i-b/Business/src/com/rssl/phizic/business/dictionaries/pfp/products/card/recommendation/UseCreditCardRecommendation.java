package com.rssl.phizic.business.dictionaries.pfp.products.card.recommendation;

import com.rssl.phizic.business.dictionaries.pfp.products.card.Card;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockDictionaryRecordBase;

import java.util.List;

/**
 * @author akrenev
 * @ created 04.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Настройка рекоммендаций по использованию кредитной карты
 */

public class UseCreditCardRecommendation extends MultiBlockDictionaryRecordBase
{
	private Long id;
	private List<UseCreditCardRecommendationStep> steps;
	private ProductEfficacy accountEfficacy;
	private ProductEfficacy thanksEfficacy;
	private String recommendation;
	private List<Card> cards;

	/**
	 * @return шаги
	 */
	public List<UseCreditCardRecommendationStep> getSteps()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return steps;
	}

	/**
	 * задать шаги
	 * @param steps шаги
	 */
	public void setSteps(List<UseCreditCardRecommendationStep> steps)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.steps = steps;
	}

	/**
	 * @return параметры эффективности вклада
	 */
	public ProductEfficacy getAccountEfficacy()
	{
		return accountEfficacy;
	}

	/**
	 * задать параметры эффективности
	 * @param accountEfficacy параметры эффективности
	 */
	public void setAccountEfficacy(ProductEfficacy accountEfficacy)
	{
		this.accountEfficacy = accountEfficacy;
	}

	/**
	 * @return параметры эффективности "спасибо"
	 */
	public ProductEfficacy getThanksEfficacy()
	{
		return thanksEfficacy;
	}

	/**
	 * задать параметры эффективности "спасибо"
	 * @param thanksEfficacy параметры
	 */
	public void setThanksEfficacy(ProductEfficacy thanksEfficacy)
	{
		this.thanksEfficacy = thanksEfficacy;
	}

	/**
	 * @return рекоммендация
	 */
	public String getRecommendation()
	{
		return recommendation;
	}

	/**
	 * задать рекоммендацию
	 * @param recommendation рекоммендация
	 */
	public void setRecommendation(String recommendation)
	{
		this.recommendation = recommendation;
	}

	/**
	 * @return карты
	 */
	public List<Card> getCards()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return cards;
	}

	/**
	 * задать карты
	 * @param cards карты
	 */
	public void setCards(List<Card> cards)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.cards = cards;
	}
}
