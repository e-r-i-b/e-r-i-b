package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.payments.forms.meta.conditions.BillingPaymentRequisitesSufficientCondition;
import com.rssl.phizic.business.payments.forms.meta.conditions.OfflineDelayedCondition;

/**
 * Хэндлер для проверки того, что биллинговый платеж подготовлен.
 * @author Pankin
 * @ created 17.12.2012
 * @ $Author$
 * @ $Revision$
 */

public class BillingPaymentRequisitesSufficientHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		try
		{
			// Если внешние системы недоступны, проверять не надо
			if (new OfflineDelayedCondition().accepted(document, stateMachineEvent))
				return;

			if (!new BillingPaymentRequisitesSufficientCondition().accepted(document, stateMachineEvent))
				throw new DocumentException("В платеже не хватает реквизитов после подготовки в шлюзе.");
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new DocumentLogicException(e);
		}
	}
}
