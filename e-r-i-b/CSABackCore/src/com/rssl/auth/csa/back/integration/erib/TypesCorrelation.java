package com.rssl.auth.csa.back.integration.erib;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

/**
 * @author akrenev
 * @ created 10.04.2013
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

		tempTypes.put(com.rssl.auth.csa.back.integration.erib.generated.ClientInformation.class,
				com.rssl.auth.csa.back.integration.erib.types.ClientInformation.class);
		tempTypes.put(com.rssl.auth.csa.back.integration.erib.generated.Address.class,
				com.rssl.auth.csa.back.integration.erib.types.Address.class);
		tempTypes.put(com.rssl.auth.csa.back.integration.erib.generated.Document.class,
				com.rssl.phizgate.common.services.types.ClientDocumentImpl.class);
		tempTypes.put(com.rssl.auth.csa.back.integration.erib.generated.Contact.class,
				com.rssl.auth.csa.back.integration.erib.types.Contact.class);

		tempTypes.put(com.rssl.auth.csa.back.integration.erib.generated.ConfirmationInformation.class,
				com.rssl.auth.csa.back.integration.erib.types.ConfirmationInformation.class);
		tempTypes.put(com.rssl.auth.csa.back.integration.erib.generated.CardInformation.class,
				com.rssl.auth.csa.back.integration.erib.types.CardInformation.class);
		tempTypes.put(com.rssl.auth.csa.back.integration.erib.generated.ErmbInfo.class,
				com.rssl.phizic.gate.ermb.ErmbInfoImpl.class);

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
