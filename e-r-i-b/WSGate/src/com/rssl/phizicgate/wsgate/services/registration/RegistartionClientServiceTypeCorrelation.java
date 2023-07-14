package com.rssl.phizicgate.wsgate.services.registration;


import java.util.Map;
import java.util.HashMap;

/**
 * @author Omeliyanchuk
 * @ created 07.08.2009
 * @ $Author$
 * @ $Revision$
 */

public class RegistartionClientServiceTypeCorrelation
{
	public static final Map<Class, Class> toGeneratedTypes = new HashMap<Class,Class>();   // для перевода гейтовых типов в сгенерированные
	public static final Map<Class, Class> toGateTypes      = new HashMap<Class,Class>();   // наоборот

	static
	{
		toGeneratedTypes.put(com.rssl.phizic.gate.clients.Client.class, com.rssl.phizicgate.wsgate.services.registration.generated.Client.class);
		toGeneratedTypes.put(com.rssl.phizic.gate.clients.Address.class, com.rssl.phizicgate.wsgate.services.registration.generated.Address.class);
		toGeneratedTypes.put(com.rssl.phizic.gate.clients.ClientDocument.class, com.rssl.phizicgate.wsgate.services.registration.generated.ClientDocument.class);
		toGeneratedTypes.put(com.rssl.phizic.common.types.client.ClientDocumentType.class, null);
        toGeneratedTypes.put(com.rssl.phizic.gate.dictionaries.officies.Office.class, com.rssl.phizicgate.wsgate.services.registration.generated.Office.class);
		toGeneratedTypes.put(com.rssl.phizic.gate.dictionaries.officies.Code.class, com.rssl.phizicgate.wsgate.services.registration.generated.Code.class);
		toGeneratedTypes.put(com.rssl.phizic.gate.clients.ClientState.class, com.rssl.phizicgate.wsgate.services.registration.generated.ClientState.class);
		toGeneratedTypes.put(com.rssl.phizic.gate.clients.ClientStateCategory.class, null); //прописан в BeanFormatterMap
		toGeneratedTypes.put(com.rssl.phizic.gate.clients.UserCategory.class, null);  //прописан в BeanFormatterMap


        toGateTypes.put(com.rssl.phizicgate.wsgate.services.registration.generated.Client.class, com.rssl.phizicgate.wsgate.services.types.ClientImpl.class);
        toGateTypes.put(com.rssl.phizicgate.wsgate.services.registration.generated.Office.class, com.rssl.phizicgate.wsgate.services.types.OfficeImpl.class);
		toGateTypes.put(com.rssl.phizicgate.wsgate.services.registration.generated.Address.class, com.rssl.phizicgate.wsgate.services.types.AddressImpl.class);
		toGateTypes.put(com.rssl.phizicgate.wsgate.services.registration.generated.ClientDocument.class, com.rssl.phizicgate.wsgate.services.types.ClientDocumentImpl.class);
		toGateTypes.put(com.rssl.phizicgate.wsgate.services.registration.generated.Code.class, com.rssl.phizicgate.wsgate.services.types.CodeImpl.class);
		toGateTypes.put(com.rssl.phizicgate.wsgate.services.registration.generated.CancelationCallBackImpl.class, com.rssl.phizic.gate.clients.CancelationCallBack.class);
		toGateTypes.put(com.rssl.phizic.gate.clients.UserCategory.class, null);  //прописан в BeanFormatterMap
		toGateTypes.put(com.rssl.phizic.common.types.SegmentCodeType.class, null);
	}
}
