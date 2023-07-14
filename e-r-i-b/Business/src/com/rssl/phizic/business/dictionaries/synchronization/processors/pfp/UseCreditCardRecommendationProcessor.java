package com.rssl.phizic.business.dictionaries.synchronization.processors.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pfp.products.card.Card;
import com.rssl.phizic.business.dictionaries.pfp.products.card.recommendation.ProductEfficacy;
import com.rssl.phizic.business.dictionaries.pfp.products.card.recommendation.UseCreditCardRecommendation;
import com.rssl.phizic.business.dictionaries.pfp.products.card.recommendation.UseCreditCardRecommendationStep;

import java.util.ArrayList;

/**
 * @author akrenev
 * @ created 27.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Процессор записи рекоммендаций по использованию кредитной карты
 */

public class UseCreditCardRecommendationProcessor extends PFPProcessorBase<UseCreditCardRecommendation>
{
	@Override
	protected Class<UseCreditCardRecommendation> getEntityClass()
	{
		return UseCreditCardRecommendation.class;
	}

	@Override
	protected UseCreditCardRecommendation getNewEntity()
	{
		UseCreditCardRecommendation recommendation = new UseCreditCardRecommendation();
		recommendation.setSteps(new ArrayList<UseCreditCardRecommendationStep>());
		recommendation.setCards(new ArrayList<Card>());
		recommendation.setAccountEfficacy(new ProductEfficacy());
		recommendation.setThanksEfficacy(new ProductEfficacy());
		return recommendation;
	}

	private void updateEfficacy(ProductEfficacy source, ProductEfficacy destination)
	{
		destination.setDefaultIncome(source.getDefaultIncome());
		destination.setFromIncome(source.getFromIncome());
		destination.setToIncome(source.getToIncome());
		destination.setDescription(source.getDescription());
	}

	@Override
	protected void update(UseCreditCardRecommendation source, UseCreditCardRecommendation destination) throws BusinessException
	{
		super.update(source, destination);
		destination.getSteps().clear();
		destination.getSteps().addAll(source.getSteps());
		destination.getCards().clear();
		destination.getCards().addAll(getLocalVersionByGlobal(source.getCards()));
		updateEfficacy(source.getAccountEfficacy(), destination.getAccountEfficacy());
		updateEfficacy(source.getThanksEfficacy(), destination.getThanksEfficacy());
		destination.setRecommendation(source.getRecommendation());
	}
}
