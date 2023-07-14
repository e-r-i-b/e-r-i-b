package com.rssl.phizicgate.manager.services.selectors.way;

import com.rssl.phizic.gate.config.ExternalSystemIntegrationMode;
import com.rssl.phizic.gate.documents.GateDocument;

/**
 * @author akrenev
 * @ created 10.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * —електор метода интеграции дл€ документа
 */

class DocumentWaySelector<T extends GateDocument> extends WaySelector<T>
{
	@Override
	protected ExternalSystemIntegrationMode getMode(T source, String serviceName)
	{
		ExternalSystemIntegrationMode documentMode = getDocumentMode(source);
		if (documentMode != null)
			return documentMode;

		return ServiceNameWaySelector.getServiceMode(serviceName);
	}

	static <S extends GateDocument> ExternalSystemIntegrationMode getDocumentMode(S source)
	{
		return source.getIntegrationMode();
	}
}
