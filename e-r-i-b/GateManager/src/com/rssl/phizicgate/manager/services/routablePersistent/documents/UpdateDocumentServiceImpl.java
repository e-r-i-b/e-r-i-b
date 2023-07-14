package com.rssl.phizicgate.manager.services.routablePersistent.documents;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.manager.services.persistent.documents.DocumentManagerHelper;

/**
 * @author bogdanov
 * @ created 07.12.2012
 * @ $Author$
 * @ $Revision$
 */

public class UpdateDocumentServiceImpl extends com.rssl.phizicgate.manager.services.persistent.documents.UpdateDocumentServiceImpl
{
	public UpdateDocumentServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	@Override
	protected DocumentManagerHelper createDocumentManagerHelper(GateDocument document) throws GateLogicException, GateException
	{
		return new DocumentManagerHelper(document);
	}
}
