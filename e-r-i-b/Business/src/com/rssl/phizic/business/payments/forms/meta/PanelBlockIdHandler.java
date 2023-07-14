package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.quick.pay.PanelBlock;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.business.quick.pay.QuickPaymentPanelUtil;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * Сохраняет идентификатор блока ПБО(с которого перешили на оплату услуги) в документ
 * @author komarov
 * @ created 22.11.2012
 * @ $Author$
 * @ $Revision$
 */

public class PanelBlockIdHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		List<Long> clickedIds   = LogThreadContext.getClickedBlockIds();
		if(CollectionUtils.isEmpty(clickedIds))
			return;

		if (!(document instanceof JurPayment))
			return;

		JurPayment payment = (JurPayment) document;

		Long providerId = payment.getReceiverInternalId();
		List<PanelBlock> blocks = QuickPaymentPanelUtil.getQuickPaymentPanel();

		for(PanelBlock block : blocks)
		{
			Long blockId = block.getId();
			if(clickedIds.contains(blockId) && block.getProviderId().equals(providerId))
			{
				payment.setPanelBlockId(blockId);
				clickedIds.remove(blockId);
				return;
			}
		}
	}
}
