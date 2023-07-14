package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * Абстрактный класс сендера для шинных оффлайн заявок
 * @author gladishev
 * @ created 17.10.13
 * @ $Author$
 * @ $Revision$
 */
public abstract class AbstractOfflineClaimSenderBase extends AbstractClaimSenderBase
{
	public AbstractOfflineClaimSenderBase(GateFactory factory)
	{
		super(factory);
	}

	@Override
	public void send(GateDocument document) throws GateException, GateLogicException
	{
		super.send(document);

		addOfflineDocumentInfo(document);
	}
}
