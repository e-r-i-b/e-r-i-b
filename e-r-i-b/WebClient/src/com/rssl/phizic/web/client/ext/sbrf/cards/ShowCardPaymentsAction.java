package com.rssl.phizic.web.client.ext.sbrf.cards;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.card.GetCardInfoOperation;
import com.rssl.phizic.operations.card.GetCardsOperation;
import com.rssl.phizic.operations.payment.ListTemplatesOperation;
import com.rssl.phizic.web.common.client.cards.ShowCardInfoForm;
import com.rssl.phizic.web.common.client.cards.CardInfoFormHelper;
import com.rssl.phizic.web.common.client.cards.ShowCardInfoBaseAction;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.common.forms.doc.CreationType;

import java.util.List;
import java.util.Collections;

/**
 * @author lukina
 * @ created 31.05.2011
 * @ $Author$
 * @ $Revision$
 */
/*
* Просмотр шаблонов и платежей с карты
 */

public class ShowCardPaymentsAction extends ShowCardInfoBaseAction
{
	protected void updateFormData(ViewEntityOperation operation, EditFormBase form)
			throws BusinessException, BusinessLogicException
	{
		ShowCardInfoForm frm = (ShowCardInfoForm) form;
		GetCardInfoOperation cardInfoOperation = (GetCardInfoOperation) operation;
		frm.setCardOperation(true);

		frm.setCardLink(cardInfoOperation.getEntity());

		CardInfoFormHelper.setAdditionalCards(frm, createOperation(GetCardsOperation.class));

		frm.setTemplates(getDocumentTemplates(frm.getCardLink().getNumber()));
	}

	private List<TemplateDocument> getDocumentTemplates(String payerAccount) throws BusinessException, BusinessLogicException
	{
		if (checkAccess(ListTemplatesOperation.class))
		{
			ListTemplatesOperation templateOperation = createOperation(ListTemplatesOperation.class);
			templateOperation.initialize(CreationType.internet, payerAccount);
			return templateOperation.getEntity();
		}
		return Collections.emptyList();
	}
}
