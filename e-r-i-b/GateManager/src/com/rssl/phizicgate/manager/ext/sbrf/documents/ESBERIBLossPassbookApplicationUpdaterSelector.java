package com.rssl.phizicgate.manager.ext.sbrf.documents;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizicgate.manager.ext.sbrf.ESBHelper;

/**
 * @author gladishev
 * @ created 25.10.13
 * @ $Author$
 * @ $Revision$
 */
public class ESBERIBLossPassbookApplicationUpdaterSelector extends ESBERIBPaymetUpdaterSelector
{
	public ESBERIBLossPassbookApplicationUpdaterSelector(GateFactory factory)
	{
		super(factory);
	}

	@Override protected boolean esbSupported(GateDocument document) throws GateException
	{
		return super.esbSupported(document) && ESBHelper.isSACSSupported(document.getInternalOwnerId());
	}
}
