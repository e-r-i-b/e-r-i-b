package com.rssl.phizic.operations.pfp.recommendation.show;

import com.rssl.phizic.operations.pfp.EditPfpOperationBase;
import com.rssl.phizic.business.pfp.PersonalFinanceProfile;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.products.card.recommendation.UseCreditCardRecommendationService;
import com.rssl.phizic.business.dictionaries.pfp.products.card.recommendation.UseCreditCardRecommendation;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.common.forms.doc.DocumentEvent;

/**
 * Отображает рекомендации по использованию кредитной карты.
 * @author komarov
 * @ created 28.06.2013 
 * @ $Author$
 * @ $Revision$
 */

public class ShowRecommendationOperation extends EditPfpOperationBase
{
	private static final State SHOW_RECOMMENDATIONS_FORM = new State("SHOW_RECOMMENDATIONS");
	private static final UseCreditCardRecommendationService recommendationService = new UseCreditCardRecommendationService();


	protected void additionalCheckPersonalFinanceProfile(PersonalFinanceProfile profile) throws BusinessException, BusinessLogicException
	{
		checkState(SHOW_RECOMMENDATIONS_FORM);
	}

	/**
	 * Возвращает рекоммендаций по использованию кредитной карты
	 * @return рекоммендаций по использованию кредитной карты
	 * @throws BusinessException
	 */
	public UseCreditCardRecommendation getRecommendation() throws BusinessException
	{
		return recommendationService.getRecommendation();
	}

	/**
	 * Сохранение профиля
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void nextStep() throws BusinessException, BusinessLogicException
	{
		fireEvent(DocumentEvent.CHOOSE_CARD);
		pfpService.addOrUpdateProfile(personalFinanceProfile);
	}
}
