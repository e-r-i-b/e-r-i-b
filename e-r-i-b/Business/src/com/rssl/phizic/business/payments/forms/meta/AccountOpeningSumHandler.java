package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.common.forms.state.StateObject;
import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizic.business.documents.AccountOpeningClaim;

/**
 * Хэндлер для проверки суммы зачисления при открытии вклада
 * @author Pankin
 * @ created 20.03.2012
 * @ $Author$
 * @ $Revision$
 */

public class AccountOpeningSumHandler extends BusinessDocumentHandlerBase
{
	private static final String LESS_SUM_MESSAGE = "Сумма зачисления не может быть меньше неснижаемого " +
			"остатка по вкладу. Пожалуйста, внимательно ознакомьтесь с условиями вклада, который хотите открыть.";

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof AccountOpeningClaim))
		{
			throw new DocumentException("Неверный тип платежа id=" + ((BusinessDocument) document).getId() +
					" (Ожидается AccountOpeningClaim)");
		}

		AccountOpeningClaim accountOpeningClaim = (AccountOpeningClaim) document;

		// Сумма проверяется только для вкладов с неснижаемым остатком
		if (!accountOpeningClaim.isWithMinimumBalance())
			return;

		// Неснижаемый остаток не может быть больше суммы зачисления
		if (accountOpeningClaim.getIrreducibleAmmount().compareTo(accountOpeningClaim.getDestinationAmount().getDecimal()) > 0)
			throw new DocumentLogicException(LESS_SUM_MESSAGE);
	}
}
