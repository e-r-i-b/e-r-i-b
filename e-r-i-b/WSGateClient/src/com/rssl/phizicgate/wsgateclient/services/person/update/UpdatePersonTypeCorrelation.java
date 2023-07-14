package com.rssl.phizicgate.wsgateclient.services.person.update;

import java.util.Map;
import java.util.HashMap;

/**
 * @author Omeliyanchuk
 * @ created 03.08.2009
 * @ $Author$
 * @ $Revision$
 */

public class UpdatePersonTypeCorrelation
{
	public static final Map<Class, Class> toGeneratedTypes = new HashMap<Class,Class>();   // для перевода гейтовых типов в сгенерированные
	public static final Map<Class, Class> toGateTypes      = new HashMap<Class,Class>();   // наоборот

	static
	{
		toGeneratedTypes.put(com.rssl.phizic.gate.clients.ClientState.class, com.rssl.phizicgate.wsgateclient.services.person.update.generated.ClientState.class);
		toGeneratedTypes.put(com.rssl.phizic.gate.clients.CancelationCallBack.class, com.rssl.phizicgate.wsgateclient.services.person.update.generated.CancelationCallBackImpl.class);
		toGeneratedTypes.put(com.rssl.phizic.common.types.Money.class, com.rssl.phizicgate.wsgateclient.services.person.update.generated.Money.class);
		toGeneratedTypes.put(com.rssl.phizic.gate.clients.ClientStateCategory.class, null);
		toGeneratedTypes.put(com.rssl.phizic.common.types.Currency.class, com.rssl.phizicgate.wsgateclient.services.person.update.generated.Currency.class);



	}
}
