package com.rssl.phizic.web.gate.services.loyalty;

import java.util.Map;
import java.util.HashMap;

/**
 * @author gladishev
 * @ created 02.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class TypesCorrelation
{
	private static final Map<Class, Class> types = new HashMap<Class,Class>();

	public static Map<Class, Class> getTypes()
	{
		return types;
	}

	static
	{
		types.put(com.rssl.phizic.web.gate.services.loyalty.generated.Money.class, com.rssl.phizic.common.types.Money.class);
		types.put(com.rssl.phizic.web.gate.services.loyalty.generated.Currency.class, com.rssl.phizic.web.gate.services.types.CurrencyImpl.class);
		types.put(com.rssl.phizic.web.gate.services.loyalty.generated.LoyaltyProgram.class, com.rssl.phizic.web.gate.services.types.LoyaltyProgramImpl.class);


		//обратное преобразование
		types.put(com.rssl.phizic.gate.loyalty.LoyaltyProgram.class, com.rssl.phizic.web.gate.services.loyalty.generated.LoyaltyProgram.class);
		types.put(com.rssl.phizic.gate.loyalty.LoyaltyProgramOperation.class, com.rssl.phizic.web.gate.services.loyalty.generated.LoyaltyProgramOperation.class);
		types.put(com.rssl.phizic.gate.loyalty.LoyaltyOffer.class, com.rssl.phizic.web.gate.services.loyalty.generated.LoyaltyOffer.class);

		types.put(com.rssl.phizic.common.types.Currency.class, com.rssl.phizic.web.gate.services.loyalty.generated.Currency.class);
		types.put(com.rssl.phizic.common.types.Money.class, com.rssl.phizic.web.gate.services.loyalty.generated.Money.class);

		types.put(com.rssl.phizic.gate.loyalty.LoyaltyOperationKind.class, null); //прописан в BeanFormatterMap
	}
}
