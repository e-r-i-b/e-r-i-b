package com.rssl.phizic.web.gate.services.registration;

import java.util.Map;
import java.util.HashMap;

/**
 * @author: Pakhomova
 * @created: 13.08.2009
 * @ $Author$
 * @ $Revision$
 */
public class RegistartionClientServiceTypesCorrelation
{
		public static final Map<Class, Class> types = new HashMap<Class,Class>();


	static
	{
		types.put(com.rssl.phizic.gate.clients.Client.class, com.rssl.phizic.web.gate.services.registration.generated.Client.class);
		types.put(com.rssl.phizic.gate.clients.Address.class, com.rssl.phizic.web.gate.services.registration.generated.Address.class);
		types.put(com.rssl.phizic.gate.clients.ClientDocument.class, com.rssl.phizic.web.gate.services.registration.generated.ClientDocument.class);
		types.put(com.rssl.phizic.common.types.client.ClientDocumentType.class, null);
		types.put(com.rssl.phizic.gate.clients.ClientState.class, com.rssl.phizic.web.gate.services.registration.generated.ClientState.class);
		types.put(com.rssl.phizic.gate.clients.ClientStateCategory.class, null);
		types.put(com.rssl.phizic.gate.dictionaries.officies.Code.class, com.rssl.phizic.web.gate.services.registration.generated.Code.class);
		types.put(com.rssl.phizic.gate.dictionaries.officies.Office.class, com.rssl.phizic.web.gate.services.registration.generated.Office.class);
		types.put(com.rssl.phizic.gate.clients.CancelationCallBack.class, com.rssl.phizic.web.gate.services.registration.generated.CancelationCallBackImpl.class);

        types.put(com.rssl.phizic.web.gate.services.registration.generated.Office.class, com.rssl.phizgate.common.services.types.OfficeImpl.class);
        types.put(com.rssl.phizic.web.gate.services.registration.generated.Client.class, com.rssl.phizic.web.gate.services.types.ClientImpl.class);
		types.put(com.rssl.phizic.web.gate.services.registration.generated.Code.class, com.rssl.phizgate.common.services.types.CodeImpl.class);
		types.put(com.rssl.phizic.web.gate.services.registration.generated.Address.class, com.rssl.phizic.web.gate.services.types.AddressImpl.class);
		types.put(com.rssl.phizic.web.gate.services.registration.generated.ClientDocument.class, com.rssl.phizic.web.gate.services.types.ClientDocumentImpl.class);
		types.put(com.rssl.phizic.web.gate.services.registration.generated.ClientState.class, com.rssl.phizic.gate.clients.ClientState.class);
		types.put(com.rssl.phizic.web.gate.services.registration.generated.CancelationCallBackImpl.class, com.rssl.phizic.gate.clients.CancelationCallBack.class);
		types.put(com.rssl.phizic.common.types.SegmentCodeType.class, null);
	}
}
