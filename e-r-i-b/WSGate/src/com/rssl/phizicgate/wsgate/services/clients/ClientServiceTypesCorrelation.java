package com.rssl.phizicgate.wsgate.services.clients;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Pakhomova
 * @created: 01.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class ClientServiceTypesCorrelation
{
	public static final Map<Class, Class> toGeneratedTypes = new HashMap<Class,Class>();   // для перевода гейтовых типов в сгенерированные
	public static final Map<Class, Class> toGateTypes      = new HashMap<Class,Class>();   // наоборот

	static
	{
		toGeneratedTypes.put(com.rssl.phizic.gate.clients.Client.class, com.rssl.phizicgate.wsgate.services.clients.generated.Client.class);
		toGeneratedTypes.put(com.rssl.phizic.gate.clients.Address.class, com.rssl.phizicgate.wsgate.services.clients.generated.Address.class);
		toGeneratedTypes.put(com.rssl.phizic.gate.clients.ClientDocument.class, com.rssl.phizicgate.wsgate.services.clients.generated.ClientDocument.class);
		toGeneratedTypes.put(com.rssl.phizic.common.types.client.ClientDocumentType.class, null);
		toGeneratedTypes.put(com.rssl.phizic.gate.clients.ClientStateCategory.class, null); //прописан в BeanFormatterMap
		toGeneratedTypes.put(com.rssl.phizic.common.types.SegmentCodeType.class, null);
        toGeneratedTypes.put(com.rssl.phizic.gate.dictionaries.officies.Office.class, com.rssl.phizicgate.wsgate.services.clients.generated.Office.class);
		toGeneratedTypes.put(com.rssl.phizic.gate.dictionaries.officies.Code.class, com.rssl.phizicgate.wsgate.services.clients.generated.Code.class);
		toGeneratedTypes.put(com.rssl.phizic.gate.clients.ClientState.class, com.rssl.phizicgate.wsgate.services.clients.generated.ClientState.class);

        toGateTypes.put(com.rssl.phizicgate.wsgate.services.clients.generated.Client.class, com.rssl.phizicgate.wsgate.services.types.ClientImpl.class);
        toGateTypes.put(com.rssl.phizicgate.wsgate.services.clients.generated.Office.class, com.rssl.phizicgate.wsgate.services.types.OfficeImpl.class);
        toGateTypes.put(com.rssl.phizicgate.wsgate.services.clients.generated.Code.class, com.rssl.phizicgate.wsgate.services.types.CodeImpl.class);
		toGateTypes.put(com.rssl.phizicgate.wsgate.services.clients.generated.Address.class, com.rssl.phizicgate.wsgate.services.types.AddressImpl.class);
		toGateTypes.put(com.rssl.phizicgate.wsgate.services.clients.generated.ClientDocument.class, com.rssl.phizicgate.wsgate.services.types.ClientDocumentImpl.class);
		toGateTypes.put(com.rssl.phizicgate.wsgate.services.clients.generated.ClientState.class, com.rssl.phizic.gate.clients.ClientState.class);
	}

}
