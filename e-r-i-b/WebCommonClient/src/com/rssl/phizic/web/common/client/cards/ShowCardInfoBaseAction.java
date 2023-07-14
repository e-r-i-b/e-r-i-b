package com.rssl.phizic.web.common.client.cards;

import com.rssl.phizic.business.resources.external.AbstractStoredResource;
import com.rssl.phizic.business.resources.external.StoredResourceMessages;
import com.rssl.phizic.utils.CardsConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.card.GetCardInfoOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;

/**
 * @author Krenev
 * @ created 09.10.2007
 * @ $Author$
 * @ $Revision$
 */
public class ShowCardInfoBaseAction extends ViewActionBase
{
	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException
	{
		GetCardInfoOperation cardInfoOperation = createOperation(GetCardInfoOperation.class);
		cardInfoOperation.initialize(frm.getId());

		if (cardInfoOperation.isUseStoredResource())
		{
			saveInactiveESMessage(currentRequest(), StoredResourceMessages.getUnreachableMessageSystem((AbstractStoredResource) cardInfoOperation.getEntity().getCard()));
		}

		return cardInfoOperation;
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase form)
			throws BusinessException, BusinessLogicException
	{
		ShowCardInfoForm frm = (ShowCardInfoForm) form;
		GetCardInfoOperation cardInfoOperation = (GetCardInfoOperation) operation;
		updateCommonData(cardInfoOperation, frm);
		frm.setCardAccountClient(cardInfoOperation.getCardAccountClient());
		
		CardsConfig cardsConfig = ConfigFactory.getConfig(CardsConfig.class);
		frm.setWarningPeriod(cardsConfig.getWarningPeriod());
	}

	protected void updateCommonData(GetCardInfoOperation operation, ShowCardInfoForm form)
			throws BusinessException
	{
		form.setCardLink(operation.getEntity());
	}
}
