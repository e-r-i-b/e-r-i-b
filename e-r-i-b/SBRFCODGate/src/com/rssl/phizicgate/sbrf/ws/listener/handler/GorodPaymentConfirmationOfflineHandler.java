package com.rssl.phizicgate.sbrf.ws.listener.handler;

import com.rssl.common.forms.doc.DocumentCommand;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizicgate.sbrf.ws.listener.InternalMessageInfoContainer;

import java.util.HashMap;

/**
 * @author Gainanov
 * @ created 07.08.2008
 * @ $Author$
 * @ $Revision$
 */
public class GorodPaymentConfirmationOfflineHandler extends JurPaymentConfirmationOfflineHandler
{

	public boolean handleMessage(InternalMessageInfoContainer messageInfoContainer, Object object) throws GateException, GateLogicException
	{
		if(!(object instanceof AccountPaymentSystemPayment))
			throw new GateException("Неверный тип документа, требуется AccountPaymentSystemPayment");

		return super.handleMessage(messageInfoContainer, object);
	}

	public DocumentCommand getUpdateCommand(InternalMessageInfoContainer messageInfoContainer)
	{
		return new DocumentCommand(DocumentEvent.PARTLY_EXECUTED, new HashMap<String, Object>());
	}

}