package com.rssl.phizicgate.wsgateclient.services.clients.backrefservice;

import java.util.Map;
import java.util.HashMap;

public class BackRefClientServiceTypesCorrelation
{
	public static final Map<Class, Class> types = new HashMap<Class,Class>();   // наоборот

	static
	{
		types.put(com.rssl.phizic.gate.clients.Client.class, com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.Client.class);
		types.put(com.rssl.phizic.gate.clients.Address.class, com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.Address.class);
		types.put(com.rssl.phizic.gate.clients.ClientDocument.class, com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.ClientDocument.class);
		types.put(com.rssl.phizic.common.types.client.ClientDocumentType.class, null);
		types.put(com.rssl.phizic.gate.clients.ClientStateCategory.class, null); //прописан в BeanFormatterMap
		types.put(com.rssl.phizic.gate.clients.ClientState.class, com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.ClientState.class);
		types.put(com.rssl.phizic.common.types.SegmentCodeType.class, null);

        types.put(com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.Client.class, com.rssl.phizgate.common.services.types.ClientImpl.class);
		types.put(com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.Address.class, com.rssl.phizicgate.wsgateclient.services.types.AddressImpl.class);
		types.put(com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.ClientDocument.class, com.rssl.phizgate.common.services.types.ClientDocumentImpl.class);
		types.put(com.rssl.phizicgate.wsgateclient.services.clients.backrefservice.generated.ClientState.class, com.rssl.phizic.gate.clients.ClientState.class);
	}
}