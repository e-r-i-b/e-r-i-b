package com.rssl.phizic.business.payments.forms.meta.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.CardReportDeliveryClaim;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;

/**
 * @author akrenev
 * @ created 13.05.2014
 * @ $Author$
 * @ $Revision$
 *
 * Хендлер обновляющий линк после исполнения заявки
 */

public class CardReportDeliveryClaimCardLinkUpdateHandler extends BusinessDocumentHandlerBase
{
	private static final ExternalResourceService service = new ExternalResourceService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof CardReportDeliveryClaim))
			throw new DocumentException("Неверный тип платежа id=" + ((BusinessDocument) document).getId() + " (Ожидается CardReportDeliveryClaim)");

		CardReportDeliveryClaim claim = (CardReportDeliveryClaim) document;

		Long cardLinkId = claim.getCardIdReportDelivery();
		try
		{
			BusinessDocumentOwner documentOwner = claim.getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
			CardLink link = service.findInSystemLinkById(documentOwner.getLogin(), CardLink.class, cardLinkId);
			if (link == null)
				return;

			link.setUseReportDelivery(claim.isUseReportDelivery());
			link.setEmailAddress(claim.getEmailReportDelivery());
			link.setReportDeliveryType(claim.getReportDeliveryType());
			link.setReportDeliveryLanguage(claim.getReportDeliveryLanguage());
			service.updateLink(link);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
