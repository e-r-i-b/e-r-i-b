package com.rssl.phizic.web.client.cards;

import com.rssl.phizic.utils.CardsConfig;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.card.GetCardInfoOperation;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.web.common.ViewActionBase;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author Krenev
 * @ created 15.10.2007
 * @ $Author$
 * @ $Revision$
 */
public class PrintCardInfoAction extends ViewActionBase
{
	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException
	{
		GetCardInfoOperation cardInfoOperation = createOperation("CardInfoPrintOperation");
		cardInfoOperation.initialize(new java.lang.Long(currentRequest().getParameter("cardId")));

		return cardInfoOperation;
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase form)
			throws BusinessException, BusinessLogicException
	{
		PrintCardInfoForm frm = (PrintCardInfoForm) form;
		GetCardInfoOperation cardInfoOperation = (GetCardInfoOperation) operation;

		frm.setCardLink(cardInfoOperation.getEntity());
		frm.setCardAccountClient(cardInfoOperation.getCardAccountClient());

		CardsConfig cardsConfig = ConfigFactory.getConfig(CardsConfig.class);
		frm.setWarningPeriod(cardsConfig.getWarningPeriod());

		ActivePerson user = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		frm.setUser(user);
	}
}
