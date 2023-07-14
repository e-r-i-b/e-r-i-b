package com.rssl.phizicgate.manager.ext.sbrf.documents;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.DocumentUpdater;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.StateUpdateInfo;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * @author gladishev
 * @ created 17.10.13
 * @ $Author$
 * @ $Revision$
 *
 * апдейтер-селектор.
 */
public class ESBERIBPaymetUpdaterSelector extends ESBERIBPaymetDelegateSelector<DocumentUpdater> implements DocumentUpdater
{
	public ESBERIBPaymetUpdaterSelector(GateFactory factory)
	{
		super(factory);
	}

	public StateUpdateInfo execute(GateDocument document) throws GateException, GateLogicException
	{
		return getDelegate(document).execute(document);
	}
}
