package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.BusinessDocumentBase;
import com.rssl.phizic.gate.utils.ExternalSystemGateService;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.common.types.bankroll.BankProductType;

/**
 * Хэндлер, проверяющий доступность ЦОД для тербанка, в котором производится операция
 * @author Pankin
 * @ created 05.02.2013
 * @ $Author$
 * @ $Revision$
 */

public class CODOperationsHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof BusinessDocumentBase))
		{
			throw new DocumentException("Неверный тип платежа id=" + ((BusinessDocument) document).getId() +
					" (Ожидается BusinessDocumentBase)");
		}

		BusinessDocumentBase businessDocument = (BusinessDocumentBase) document;

		ExternalSystemGateService externalSystemGateService = GateSingleton.getFactory().service(ExternalSystemGateService.class);
		try
		{
			// Проверяем доступность ЦОД для ТБ, в котором производится операция
			ExternalSystemHelper.check(externalSystemGateService.findByProduct(businessDocument.getDepartment(), BankProductType.Deposit));
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
