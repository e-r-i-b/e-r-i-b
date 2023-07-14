package com.rssl.phizic.business.documents;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.ConvertableToGateDocument;
import com.rssl.phizic.gate.documents.GateDocument;

/**
 * Адаптер бизнеса к гейту
 *
 * @author khudyakov
 * @ created 17.03.15
 * @ $Author$
 * @ $Revision$
 */
public class ConvertibleToGateDocumentAdapter implements ConvertableToGateDocument
{
	private BusinessDocument document;

	public ConvertibleToGateDocumentAdapter(BusinessDocument document)
	{
		this.document = document;
	}

	public GateDocument asGateDocument() throws NotConvertibleToGateBusinessException
	{
		if (document instanceof GateExecutableDocument)
		{
			GateExecutableDocument executable = (GateExecutableDocument) document;
			return executable.asGateDocument();
		}
		throw new NotConvertibleToGateBusinessException(document);
	}
}
