package com.rssl.phizic.web.gate.services.clients;

import java.util.Map;
import java.util.HashMap;

/**
 * @author: Pakhomova
 * @created: 29.05.2009
 * @ $Author$
 * @ $Revision$
 */
public class ClientServiceTypesCorrelation
{
	public static final Map<Class, Class> toGeneratedTypes = new HashMap<Class,Class>();   // для перевода гейтовых типов в сгенерированные
	public static final Map<Class, Class> toGateTypes      = new HashMap<Class,Class>();   // наоборот


	static
	{
		toGeneratedTypes.put(com.rssl.phizic.gate.clients.Client.class, com.rssl.phizic.web.gate.services.clients.generated.Client.class);
		toGeneratedTypes.put(com.rssl.phizic.gate.clients.Address.class, com.rssl.phizic.web.gate.services.clients.generated.Address.class);
		toGeneratedTypes.put(com.rssl.phizic.gate.clients.ClientDocument.class, com.rssl.phizic.web.gate.services.clients.generated.ClientDocument.class);
		toGeneratedTypes.put(com.rssl.phizic.common.types.client.ClientDocumentType.class, null);
		toGeneratedTypes.put(com.rssl.phizic.gate.clients.ClientState.class, com.rssl.phizic.web.gate.services.clients.generated.ClientState.class);
		toGeneratedTypes.put(com.rssl.phizic.gate.clients.ClientStateCategory.class, null);
		toGeneratedTypes.put(com.rssl.phizic.common.types.SegmentCodeType.class, null);
		toGeneratedTypes.put(com.rssl.phizic.gate.dictionaries.officies.Code.class, com.rssl.phizic.web.gate.services.clients.generated.Code.class);
		toGeneratedTypes.put(com.rssl.phizic.gate.dictionaries.officies.Office.class, com.rssl.phizic.web.gate.services.clients.generated.Office.class);

        toGateTypes.put(com.rssl.phizic.web.gate.services.clients.generated.Office.class, com.rssl.phizgate.common.services.types.OfficeImpl.class);
        toGateTypes.put(com.rssl.phizic.web.gate.services.clients.generated.Client.class, com.rssl.phizic.web.gate.services.types.ClientImpl.class);
		toGateTypes.put(com.rssl.phizic.web.gate.services.clients.generated.Code.class, com.rssl.phizgate.common.services.types.CodeImpl.class);
		toGateTypes.put(com.rssl.phizic.web.gate.services.clients.generated.Address.class, com.rssl.phizic.web.gate.services.types.AddressImpl.class);
		toGateTypes.put(com.rssl.phizic.web.gate.services.clients.generated.ClientDocument.class, com.rssl.phizic.web.gate.services.types.ClientDocumentImpl.class);
		toGateTypes.put(com.rssl.phizic.web.gate.services.clients.generated.ClientState.class, com.rssl.phizic.gate.clients.ClientState.class);
	}

}
