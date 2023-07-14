package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.state.StateObject;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizic.business.documents.InternalTransfer;

/**
 * Хэндлер для валидации документа в гейте
 * @author Pankin
 * @ created 18.12.2012
 * @ $Author$
 * @ $Revision$
 */

public class InternalTransferGateValidationHandler extends DefaultGateValidationHandler
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof InternalTransfer))
			throw new DocumentException("Некорректный тип документа. Ожидается InternalTransfer.");

		// Валидацию нужно проводить только для длительных поручений
		if (((InternalTransfer) document).isLongOffer())
			super.process(document, stateMachineEvent);
	}
}
