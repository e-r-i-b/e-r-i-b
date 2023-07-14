package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.ext.sbrf.dictionaries.offices.SBRFOfficeCodeAdapter;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.payments.AbstractJurTransfer;
import com.rssl.phizic.gate.payments.ReceiverNameService;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author osminin
 * @ created 06.04.2011
 * @ $Author$
 * @ $Revision$
 * Хандлер для установления наименования получателя из системы БАРС
 */
public class BarsReceiverNameHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if (!(document instanceof AbstractJurTransfer))
			throw new DocumentException("document должен реализовывать AbstractJurTransfer");

		AbstractJurTransfer transfer = (AbstractJurTransfer) document;

		try
		{
			ReceiverNameService receiverNameService = GateSingleton.getFactory().service(ReceiverNameService.class);
			String tb = new SBRFOfficeCodeAdapter(transfer.getOffice().getCode()).getRegion();
			String receiverName = receiverNameService.getReceiverName(transfer.getReceiverAccount(), transfer.getReceiverBank().getBIC(), tb);
			if (!StringHelper.isEmpty(receiverName))
				transfer.setReceiverName(receiverName);
		}
		catch (Exception e)
		{
			//ошибки взаимодействия с системой БАРС не должны отразиться на платеже
			log.error("Ошибка при установлении наименования получателя", e);
		}
	}
}
