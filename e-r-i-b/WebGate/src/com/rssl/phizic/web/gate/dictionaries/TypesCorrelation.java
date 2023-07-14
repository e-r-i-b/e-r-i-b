package com.rssl.phizic.web.gate.dictionaries;

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
	public static final Map types = new HashMap<Class,Class>();

	static
	{
		types.put(com.rssl.phizic.common.types.Currency.class, com.rssl.phizic.web.gate.dictionaries.generated.Currency.class);
		types.put(com.rssl.phizic.gate.dictionaries.Country.class, com.rssl.phizic.web.gate.dictionaries.generated.Country.class);
		types.put(com.rssl.phizic.gate.dictionaries.ResidentBank.class, com.rssl.phizic.web.gate.dictionaries.generated.Bank.class);
		types.put(com.rssl.phizic.gate.dictionaries.KBKRecord.class, com.rssl.phizic.web.gate.dictionaries.generated.KBKRecord.class);
		types.put(com.rssl.phizic.gate.dictionaries.CurrencyOperationType.class, com.rssl.phizic.web.gate.dictionaries.generated.CurrencyOperationType.class);
		types.put(com.rssl.phizic.gate.dictionaries.ContactMember.class, com.rssl.phizic.web.gate.dictionaries.generated.ContactMember.class);
	}
}
