package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.clients.ClientDocumentImpl;
import com.rssl.phizic.business.documents.AccountOpeningClaim;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.person.PersonDocument;

import java.util.Set;

/**
 * Хендлер устанавливающий главный документ
 * Должен выполняться перед отправкой заявки
 * @author komarov
 * @ created 15.06.2015
 * @ $Author$
 * @ $Revision$
 */
public class AccountOpeningMainDocumentHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if (!(document instanceof AccountOpeningClaim))
		{
			throw new DocumentException("Неверный тип платежа id=" + document.getId() +
					" (Ожидается AccountOpeningClaim)");
		}
		AccountOpeningClaim accountOpeningClaim = (AccountOpeningClaim) document;

		try
		{
			ActivePerson person = accountOpeningClaim.getOwner().getPerson();
			Set<PersonDocument> documents    = person.getPersonDocuments();
			ClientDocumentImpl mainDocument = new ClientDocumentImpl(documents.size() == 1 ? documents.iterator().next() : getMainDocument(documents));
			accountOpeningClaim.setMainDocumentInfo(mainDocument);
		}
		catch (BusinessException ex)
		{
			throw new DocumentException(ex);
		}

	}

	private PersonDocument getMainDocument(Set<PersonDocument> docs)
	{
		for (PersonDocument doc : docs)
		{
			if (doc.getDocumentMain())
			{
				return doc;
			}
		}

		return null;
	}
}
