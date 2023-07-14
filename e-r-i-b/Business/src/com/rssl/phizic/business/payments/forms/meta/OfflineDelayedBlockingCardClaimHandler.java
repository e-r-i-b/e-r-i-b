package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.documents.BlockingCardClaim;
import com.rssl.phizic.business.documents.payments.BusinessDocument;

/**
 * Хэндлер для определения недоступности внешних систем при блокировке карты
 * @author Pankin
 * @ created 10.02.2013
 * @ $Author$
 * @ $Revision$
 */

public class OfflineDelayedBlockingCardClaimHandler extends OfflineDelayedHandlerBase
{
	public void innerProcess(BusinessDocument document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof BlockingCardClaim))
			throw new DocumentException("Некорректный тип документа. Ожидается BlockingCardClaim.");

		BlockingCardClaim claim = (BlockingCardClaim) document;

		// Проверяем доступность шины в целом
		if (checkESB(claim))
			return;
		// Проверяем доступность way
		else if (checkResource(claim.getBlockingCardLink(), claim))
			return;
	}
}
