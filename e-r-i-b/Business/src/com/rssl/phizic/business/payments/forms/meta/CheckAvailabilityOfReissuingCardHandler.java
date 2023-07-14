package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.resources.ReissuedCardFilter;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.payments.ReIssueCardClaim;
import com.rssl.phizic.utils.StringHelper;

/**
 * Хендлер для проверки возможности перевыпускать указанную карту.
 *
 * @author bogdanov
 * @ created 23.05.2013
 * @ $Author$
 * @ $Revision$
 */

public class CheckAvailabilityOfReissuingCardHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof ReIssueCardClaim))
		{
			throw new DocumentException("Неверный тип платежа id=" + ((BusinessDocument) document).getId() +
					" (Ожидается ReIssueCardClaim)");
		}

		ReIssueCardClaim reissueCardClaim = (ReIssueCardClaim) document;

		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		if (personData == null)
			throw new DocumentException("Не задана personData");

		try
		{
			final String id = reissueCardClaim.getCardId();
			final String cardNumber = reissueCardClaim.getCardNumber();
			CardLink link = null;
			if (StringHelper.isNotEmpty(cardNumber))
			{
				link = personData.findCard(cardNumber);
			}
			else if (StringHelper.isNotEmpty(id))
			{
				link = personData.getCard(Long.parseLong(id.substring(id.indexOf(":")+1)));
			}
			else
			{
				return;
			}
			ReissuedCardFilter filter = new ReissuedCardFilter();
			if (link != null && !filter.evaluate(link))
				stateMachineEvent.addMessage("Для выбранной карты Вы не можете создать заявку на перевыпуск.");
		}
		catch(BusinessException ex)
		{
			throw new DocumentException(ex);
		}
	}
}
