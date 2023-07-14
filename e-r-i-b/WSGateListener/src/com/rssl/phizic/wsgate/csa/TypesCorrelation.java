package com.rssl.phizic.wsgate.csa;


import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

/**
 * @author akrenev
 * @ created 09.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * таблица коррел€ции
 */

public class TypesCorrelation
{
	private static final Map<Class, Class> types;

	static
	{
		Map<Class, Class> tempTypes = new HashMap<Class,Class>();

		tempTypes.put(com.rssl.phizic.gate.clients.Address.class,                   com.rssl.phizic.wsgate.csa.generated.Address.class);
		tempTypes.put(com.rssl.phizic.gate.clients.ClientDocument.class,            com.rssl.phizic.wsgate.csa.generated.Document.class);
		tempTypes.put(com.rssl.phizic.common.types.client.ClientDocumentType.class, null);

		tempTypes.put(com.rssl.phizic.gate.confirmation.ConfirmationInfo.class,       com.rssl.phizic.wsgate.csa.generated.ConfirmationInformation.class);
		tempTypes.put(com.rssl.phizic.gate.confirmation.CardConfirmationSource.class, com.rssl.phizic.wsgate.csa.generated.CardInformation.class);
		tempTypes.put(com.rssl.phizic.common.types.ConfirmStrategyType.class,         null);
		tempTypes.put(com.rssl.phizic.gate.ermb.ErmbInfo.class,                     com.rssl.phizic.wsgate.csa.generated.ErmbInfo.class);
		tempTypes.put(com.rssl.phizic.common.types.ermb.ErmbStatus.class,           null);

		types = Collections.unmodifiableMap(tempTypes);
	}

	/**
	 * @return таблица коррел€ции
	 */
	public static Map<Class, Class> getTypes()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return types;
	}

}
