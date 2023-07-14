package com.rssl.phizic.wsgate.clients;

import java.util.Map;
import java.util.HashMap;

/**
 * @author hudyakov
 * @ created 09.07.2009
 * @ $Author$
 * @ $Revision$
 */

public class PersonServiceTypesCorrelation
{
	public static final Map<Class, Class> toGeneratedTypes = new HashMap<Class,Class>();   // для перевода гейтовых типов в сгенерированные
	public static final Map<Class, Class> toGateTypes      = new HashMap<Class,Class>();   // наоборот

	static
	{
		toGeneratedTypes.put(com.rssl.phizic.gate.clients.ClientState.class, com.rssl.phizic.wsgate.clients.generated.ClientState.class);
		toGeneratedTypes.put(com.rssl.phizic.gate.clients.ClientStateCategory.class, null);
		toGeneratedTypes.put(com.rssl.phizic.common.types.Currency.class, com.rssl.phizic.wsgate.clients.generated.Currency.class);
		toGeneratedTypes.put(com.rssl.phizic.common.types.Money.class, com.rssl.phizic.wsgate.clients.generated.Money.class);
		toGeneratedTypes.put(com.rssl.phizic.gate.clients.CancelationCallBack.class, com.rssl.phizic.wsgate.types.CancelationCallBackType.class);

		toGateTypes.put(com.rssl.phizic.wsgate.clients.generated.ClientState.class, com.rssl.phizic.gate.clients.ClientState.class);
		toGateTypes.put(com.rssl.phizic.wsgate.clients.generated.Currency.class, com.rssl.phizic.wsgate.types.CurrencyImpl.class);
		toGateTypes.put(com.rssl.phizic.wsgate.clients.generated.Money.class, com.rssl.phizic.common.types.Money.class);
		toGateTypes.put(com.rssl.phizic.wsgate.types.CancelationCallBackType.class, com.rssl.phizic.gate.clients.CancelationCallBack.class);
	}
}
