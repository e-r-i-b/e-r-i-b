package com.rssl.phizic.operations.dictionaries.pfp.products.card.recommendation;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.products.card.Card;
import com.rssl.phizic.business.dictionaries.pfp.products.card.CardService;
import com.rssl.phizic.business.dictionaries.pfp.products.card.recommendation.UseCreditCardRecommendation;
import com.rssl.phizic.business.dictionaries.pfp.products.card.recommendation.UseCreditCardRecommendationService;
import com.rssl.phizic.operations.dictionaries.synchronization.EditDictionaryEntityOperationBase;
import org.apache.commons.lang.ArrayUtils;

import java.util.List;

/**
 * @author akrenev
 * @ created 04.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Операция редактирования настроек рекоммендаций
 */

public class EditUseCreditCardRecommendationOperation extends EditDictionaryEntityOperationBase
{
	public static final int MAX_STEP_COUNT = UseCreditCardRecommendationService.MAX_STEP_COUNT;
	private static final UseCreditCardRecommendationService service = new UseCreditCardRecommendationService();
	private static final CardService cardService = new CardService();

	private UseCreditCardRecommendation recommendation;

	/**
	 * проинициализировать операцию
	 * @throws BusinessException
	 */
	public void initialize() throws BusinessException
	{
		recommendation = service.getRecommendation(getInstanceName());
	}

	public Object getEntity()
	{
		return recommendation;
	}

	/**
	 * задать карты для настройки
	 * @param cardProductIds идентификаторы карт
	 * @throws BusinessException
	 */
	public void setCards(Long[] cardProductIds) throws BusinessException, BusinessLogicException
	{
		if (ArrayUtils.isEmpty(cardProductIds))
			throw new BusinessLogicException("Необходимо добавить хотя бы одну кредитную карту.");
		List<Card> cards = recommendation.getCards();
		cards.clear();
		cards.addAll(cardService.getListByIds(cardProductIds, getInstanceName()));
	}

	protected void doSave() throws BusinessException
	{
		service.saveRecommendation(recommendation, getInstanceName());
	}
}
