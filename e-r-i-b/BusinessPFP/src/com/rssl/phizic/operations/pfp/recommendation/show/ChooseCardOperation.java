package com.rssl.phizic.operations.pfp.recommendation.show;

import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.pfp.PersonalFinanceProfile;
import com.rssl.phizic.common.types.documents.State;

/**
 * Операция выбора кредитной карты
 * @author komarov
 * @ created 07.08.13 
 * @ $Author$
 * @ $Revision$
 */

public class ChooseCardOperation extends ShowRecommendationOperation
{
	private static final State CHOOSE_CARD_FORM = new State("CHOOSE_CARD");


	protected void additionalCheckPersonalFinanceProfile(PersonalFinanceProfile profile) throws BusinessException, BusinessLogicException
	{
		checkState(CHOOSE_CARD_FORM);
	}

	/**
	 * @param id идентификатор карты
	 */
	public void setCardId(Long id)
	{
		personalFinanceProfile.setCardId(id);
	}

	/**
		 * Сохранение профиля
		 * @throws BusinessException
		 * @throws BusinessLogicException
		 */
	public void nextStep() throws BusinessException, BusinessLogicException
	{
		fireEvent(DocumentEvent.EDIT_RISK_PROFILE);
		pfpService.addOrUpdateProfile(personalFinanceProfile);
	}
}
