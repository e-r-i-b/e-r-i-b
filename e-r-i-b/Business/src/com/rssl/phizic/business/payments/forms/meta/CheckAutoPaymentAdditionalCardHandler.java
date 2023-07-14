package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.gate.bankroll.AdditionalCardType;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;
import com.rssl.phizic.gate.payments.autopayment.EditAutoPayment;
import com.rssl.phizic.gate.payments.autopayment.RefuseAutoPayment;

/**
 * ѕроверка типа карты, на которую оформлен автоплатеж
 * @author osminin
 * @ created 13.05.2011
 * @ $Author$
 * @ $Revision$
 *
 * ≈сли автоплатеж оформлен на дополнительную карту, то владелец дополнительной карты
 * (клиент, к чьей карте была выпущена дополнительна€) может просматривать автоплатежи
 * с этой карты только Ђна просмотрї и не может редактировать или отмен€ть автоплатеж.
 */
public class CheckAutoPaymentAdditionalCardHandler extends BusinessDocumentHandlerBase
{
	private static final ExternalResourceService externalResourceService = new ExternalResourceService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof AutoPayment))
			throw new DocumentException("ќжидалс€ наследник AutoPayment");
		try
		{
			AutoPayment autoPayment = (AutoPayment) document;

			BusinessDocument businessDocument = (BusinessDocument) document;
			BusinessDocumentOwner documentOwner = businessDocument.getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("ќпераци€ в гостевой сессии не поддерживаетс€");
			Login login = businessDocument.getOwner().getLogin();

			CardLink cardLink = externalResourceService.findLinkByNumber(login, ResourceType.CARD, autoPayment.getCardNumber());
			if (cardLink == null)
				throw new DocumentException("Ќельз€ отменить или отредактировать автоплатеж по несуществующей карте.");
			boolean isEditableAndRefusable = cardLink.isMain() || cardLink.getAdditionalCardType() != AdditionalCardType.CLIENTTOOTHER;

			if (autoPayment.getType() == EditAutoPayment.class && !isEditableAndRefusable)
				throw new DocumentLogicException("¬ы не можете отредактировать автоплатеж, созданный по дополнительной карте другого лица.");
			if (autoPayment.getType() == RefuseAutoPayment.class && !isEditableAndRefusable)
				throw new DocumentLogicException("¬ы не можете отменить автоплатеж, созданный по дополнительной карте другого лица.");
		}
		catch (SystemException e)
		{
			throw new DocumentException(e);
		}
	}
}
