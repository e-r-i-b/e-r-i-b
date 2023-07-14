package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.bki.CreditProfileService;
import com.rssl.phizic.business.bki.CreditReportStatus;
import com.rssl.phizic.business.bki.PersonCreditProfile;
import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.documents.payments.CreditReportPayment;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bki.CreditBureauService;
import com.rssl.phizic.gate.exceptions.GateException;

import java.util.Calendar;

/**
 * Хендлер, отрабатывающий при переходе платежа за кредитный отчет в статус "Исполнен"
 * @author Rtischeva
 * @ created 16.10.14
 * @ $Author$
 * @ $Revision$
 */
public class CreditReportPaymentExecuteHandler extends BusinessDocumentHandlerBase
{
	private final CreditProfileService creditProfileService = new CreditProfileService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		try
		{
			AbstractPaymentDocument doc = (AbstractPaymentDocument) document;

			if (!(doc instanceof CreditReportPayment))
				throw new DocumentException("Ожидается класс, реализующий CreditReportPayment");

			BusinessDocumentOwner documentOwner = doc.getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
			ActivePerson client = documentOwner.getPerson();
			PersonCreditProfile creditProfile = creditProfileService.findByPerson(client);
			if (creditProfile == null)
				throw new DocumentException("Не найден кредитный профиль клиента");

			creditProfile.setLastPayment(Calendar.getInstance());
			creditProfileService.save(creditProfile);

			CreditBureauService creditBureauService = GateSingleton.getFactory().service(CreditBureauService.class);
			creditBureauService.sendGetCreditHistory(client.asClient(), doc.asGateDocument());

			CreditReportPayment creditReportPayment = (CreditReportPayment) doc;
			creditReportPayment.setCreditReportStatus(CreditReportStatus.WAITING.toString());
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
	}
}
