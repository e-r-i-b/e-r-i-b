package com.rssl.phizicgate.wsgate.dictionaries;

import java.util.Map;
import java.util.HashMap;

/**
 * @author: Pakhomova
 * @created: 11.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class TypesCorrelation
{
	public static final Map<Class,Class> types = new HashMap<Class,Class>();

	static
	{
		types.put(com.rssl.phizicgate.wsgate.dictionaries.generated.Currency.class, com.rssl.phizicgate.wsgate.services.types.CurrencyImpl.class);
		types.put(com.rssl.phizicgate.wsgate.dictionaries.generated.Bank.class, com.rssl.phizic.gate.dictionaries.ResidentBank.class);
		types.put(com.rssl.phizicgate.wsgate.dictionaries.generated.Country.class, com.rssl.phizic.gate.dictionaries.Country.class);
		types.put(com.rssl.phizicgate.wsgate.dictionaries.generated.KBKRecord.class, com.rssl.phizic.gate.dictionaries.KBKRecord.class);
		types.put(com.rssl.phizicgate.wsgate.dictionaries.generated.CurrencyOperationType.class, com.rssl.phizic.gate.dictionaries.CurrencyOperationType.class);
		types.put(com.rssl.phizicgate.wsgate.dictionaries.generated.ContactMember.class, com.rssl.phizic.gate.dictionaries.ContactMember.class);
	}
}
