package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.AccountChangeInterestDestinationClaim;
import com.rssl.phizic.business.documents.payments.BusinessDocument;

/**
 * При инициализации заявки проверяем, существует ли счёт и принадлежит ли он клиенту
 *
 * @author EgorovaA
 * @ created 25.03.14
 * @ $Author$
 * @ $Revision$
 */
public class AccountChangeInterestDestinationClaimInitHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof AccountChangeInterestDestinationClaim))
		{
			throw new DocumentException("Неверный тип платежа id=" + ((BusinessDocument) document).getId() + " (Ожидается AccountChangeInterestDestinationClaim)");
		}

		AccountChangeInterestDestinationClaim claim = (AccountChangeInterestDestinationClaim) document;

		if (claim.getModifiableLink() == null)
			throw new DocumentException("Изменяемый счёт не принадлежит клиенту или не существует.");
	}
}
