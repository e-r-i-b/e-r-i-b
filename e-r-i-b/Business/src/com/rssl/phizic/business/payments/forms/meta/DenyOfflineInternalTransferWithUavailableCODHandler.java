package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.InternalTransfer;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.common.types.bankroll.BankProductType;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.utils.ExternalSystem;
import com.rssl.phizic.gate.utils.ExternalSystemGateService;

import java.util.List;

/**
 * @author Balovtsev
 * @version 21.06.13 16:37
 */
public class DenyOfflineInternalTransferWithUavailableCODHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof InternalTransfer))
		{
			throw new DocumentException("Некорректный тип документа. Ожидается InternalTransfer.");
		}

		InternalTransfer doc = (InternalTransfer) document;

		if ((doc.getChargeOffResourceType() == ResourceType.CARD    && doc.getDestinationResourceType() == ResourceType.ACCOUNT) ||
			(doc.getChargeOffResourceType() == ResourceType.ACCOUNT && doc.getDestinationResourceType() == ResourceType.ACCOUNT) ||
			(doc.getChargeOffResourceType() == ResourceType.ACCOUNT && doc.getDestinationResourceType() == ResourceType.CARD))
		{
			ExternalSystemGateService externalSystemGateService = GateSingleton.getFactory().service(ExternalSystemGateService.class);
			try
			{
				List<? extends ExternalSystem> e = externalSystemGateService.findByProduct(doc.getDepartment(), BankProductType.Deposit);
				for (ExternalSystem system : e)
				{
					externalSystemGateService.getTechnoBreakToDateWithAllowPayments(system.getUUID());
				}
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
}
