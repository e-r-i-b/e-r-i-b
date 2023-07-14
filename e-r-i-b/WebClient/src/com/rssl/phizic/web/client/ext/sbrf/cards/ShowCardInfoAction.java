package com.rssl.phizic.web.client.ext.sbrf.cards;

/**
 * @ author: filimonova
 * @ created: 24.05.2010
 * @ $Author$
 * @ $Revision$
 */

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.AbstractStoredResource;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.StoredResourceMessages;
import com.rssl.phizic.gate.bankroll.CardAbstract;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.card.GetCardAbstractOperation;
import com.rssl.phizic.operations.card.GetCardInfoOperation;
import com.rssl.phizic.operations.card.GetCardsOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.client.cards.ShowCardInfoForm;
import com.rssl.phizic.web.common.client.cards.CardInfoFormHelper;
import com.rssl.phizic.web.common.client.cards.ShowCardInfoBaseAction;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Просмотр информации о 10 последних операциях по карте
 */
public class ShowCardInfoAction extends ShowCardInfoBaseAction
{
	private static final Long MAX_COUNT_OF_TRANSACTIONS = 10L;

	protected void updateFormData(ViewEntityOperation operation, EditFormBase form)
			throws BusinessException, BusinessLogicException
	{
		ShowCardInfoForm frm = (ShowCardInfoForm) form;
		GetCardInfoOperation cardInfoOperation = (GetCardInfoOperation) operation;

		updateCommonData(cardInfoOperation, frm);

		CardInfoFormHelper.setAdditionalCards(frm, createOperation(GetCardsOperation.class));

		updateCardAbstractData(frm);

		setDefaultFilter(frm);
	}

	private void setDefaultFilter(ShowCardInfoForm frm)
	{
		if (frm.getFilter("fromAbstract") == null)
		{
			Calendar startDate = new GregorianCalendar();
			startDate.add(Calendar.MONTH, -1);
			frm.setFilter("fromAbstract", startDate.getTime());
		}
		if (frm.getFilter("toAbstract") == null)
			frm.setFilter("toAbstract", new GregorianCalendar().getTime());
		if (StringHelper.isEmpty((String) frm.getFilter("typeAbstract")))
			frm.setFilter("typeAbstract", "month");
	}

	private void updateCardAbstractData(ShowCardInfoForm frm) throws BusinessException, BusinessLogicException
	{
		GetCardAbstractOperation operation = createOperation(GetCardAbstractOperation.class);
		operation.initialize(frm.getId());

		CardLink cardLink = operation.getCard();
		CardAbstract cardAbstract = operation.getCardAbstract(MAX_COUNT_OF_TRANSACTIONS).get(cardLink);
		frm.setCardAbstract(cardAbstract);
		frm.setAbstractMsgError(operation.getCardAbstractMsgErrorMap().get(cardLink));
		frm.setExtendedCardAbstractAvailable(operation.isExtendedCardAbstractAvailable());

		if (operation.isUseStoredResource())
		{
			frm.setAbstractMsgError( StoredResourceMessages.getUnreachableStatement() );
			saveInactiveESMessage(currentRequest(), StoredResourceMessages.getUnreachableMessageSystem((AbstractStoredResource) cardLink.getCard()));
		}
	}
}
