package com.rssl.phizic.business.payments.forms.meta.handlers;

import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizic.business.documents.AccountOpeningClaim;
import com.rssl.phizic.business.documents.BusinessDocumentBase;
import com.rssl.phizic.business.finances.targets.AccountTarget;
import com.rssl.phizic.business.finances.targets.AccountTargetService;
import com.rssl.phizic.business.BusinessException;

/**
 * @author akrenev
 * @ created 04.06.2013
 * @ $Author$
 * @ $Revision$
 *
 * Хендлер, проставляющий номер счета в цель, ради которой создавался вкляд
 */

public class UpdateAccountTargetHandler extends BusinessDocumentHandlerBase
{
	private static final AccountTargetService targetService = new AccountTargetService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof AccountOpeningClaim))
			throw new DocumentException("Неверный тип платежа id=" + ((BusinessDocumentBase) document).getId() + " (Ожидается AccountOpeningClaim)");

		try
		{
			AccountOpeningClaim accountOpeningClaim = (AccountOpeningClaim) document;
			AccountTarget target = targetService.findByClaim(accountOpeningClaim);

			if (target == null)
				return;

			target.setAccountNum(accountOpeningClaim.getReceiverAccount());
			targetService.addOrUpdate(target);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
