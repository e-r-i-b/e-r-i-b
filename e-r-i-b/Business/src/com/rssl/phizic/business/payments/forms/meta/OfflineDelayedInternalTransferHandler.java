package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.documents.InternalTransfer;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.payments.InternalCardsTransfer;
import com.rssl.phizicgate.manager.config.AdaptersConfig;
import com.rssl.phizicgate.manager.routing.Adapter;

/**
 * Хэндлер для определения недоступности внешних систем при переводе между своими счетами
 * @author Pankin
 * @ created 18.12.2012
 * @ $Author$
 * @ $Revision$
 */

public class OfflineDelayedInternalTransferHandler extends OfflineDelayedHandlerBase
{
	public void innerProcess(BusinessDocument document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof InternalTransfer))
			throw new DocumentException("Некорректный тип документа. Ожидается InternalTransfer.");

		InternalTransfer payment = (InternalTransfer) document;

		if (payment.getType().equals(InternalCardsTransfer.class))
		{
			// Для перевода между картами проверяем только IQWave
			AdaptersConfig config = ConfigFactory.getConfig(AdaptersConfig.class);
			Adapter iqwAdapter = config.getCardTransfersAdapter();
			if (checkSystemByUUID(iqwAdapter.getUUID(), payment) || checkResource(payment.getChargeOffResourceLink(), payment))
				return;
		}
		// Проверяем доступность шины в целом
		else if (checkESB(payment))
			return;

		// Проверяем ресурсы списания-зачисления и адаптер, маршрутизирующий подразделение документа
		else if (checkResource(payment.getChargeOffResourceLink(), payment) || checkResource(payment.getDestinationResourceLink(), payment) ||
				checkDocumentOffice(payment))
			return;
	}
}
