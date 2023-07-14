package com.rssl.phizic.business.documents.payments.stateMachine.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.documents.P2PAutoTransferClaimBase;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.gate.payments.longoffer.ChannelType;
import com.rssl.phizic.logging.LogThreadContext;

/**
 * Хендлер простановки флага канала создания для автопервода P2P
 *
 * @author khudyakov
 * @ created 15.10.14
 * @ $Author$
 * @ $Revision$
 */
public class SetChannelTypeP2PAutoTransferHandler extends BusinessDocumentHandlerBase<P2PAutoTransferClaimBase>
{
	public void process(P2PAutoTransferClaimBase claim, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		claim.setChannelType(getChannelType());
		claim.setCodeATM(LogThreadContext.getCodeATM());
	}

	private ChannelType getChannelType()
	{
		Application application = ApplicationConfig.getIt().getApplicationInfo().getApplication();

		switch (application)
		{
			case atm: return ChannelType.US;
			case mobile9: return ChannelType.IB;
			case PhizIA: return ChannelType.VSP;
			case PhizIC: return ChannelType.IB;
			default:throw new IllegalArgumentException();
		}
	}
}
