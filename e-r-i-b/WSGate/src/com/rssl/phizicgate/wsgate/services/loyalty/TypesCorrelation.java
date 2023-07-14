package com.rssl.phizicgate.wsgate.services.loyalty;

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
	private static final Map<Class, Class> types= new HashMap<Class,Class>();

	public static Map<Class, Class> getTypes()
	{
		return types;
	}

	static
	{
		types.put(com.rssl.phizicgate.wsgate.services.loyalty.generated.Currency.class, com.rssl.phizic.business.dictionaries.currencies.CurrencyImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.loyalty.generated.Money.class, com.rssl.phizic.common.types.Money.class);

		types.put(com.rssl.phizicgate.wsgate.services.loyalty.generated.LoyaltyOffer.class, com.rssl.phizicgate.wsgate.services.loyalty.types.LoyaltyOfferImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.loyalty.generated.LoyaltyProgram.class, com.rssl.phizicgate.wsgate.services.loyalty.types.LoyaltyProgramImpl.class);
		types.put(com.rssl.phizicgate.wsgate.services.loyalty.generated.LoyaltyProgramOperation.class, com.rssl.phizicgate.wsgate.services.loyalty.types.LoyaltyProgramOperationImpl.class);

		//обратное преобразование
		types.put(com.rssl.phizic.business.dictionaries.currencies.CurrencyImpl.class, com.rssl.phizicgate.wsgate.services.loyalty.generated.Currency.class);
		types.put(com.rssl.phizic.common.types.Money.class, com.rssl.phizicgate.wsgate.services.loyalty.generated.Money.class);
		types.put(com.rssl.phizic.gate.loyalty.LoyaltyProgram.class, com.rssl.phizicgate.wsgate.services.loyalty.generated.LoyaltyProgram.class);

		types.put(com.rssl.phizic.gate.loyalty.LoyaltyOperationKind.class, null);
		types.put(com.rssl.phizic.gate.bankroll.CardType.class, null);
		types.put(com.rssl.phizic.gate.bankroll.CardBonusSign.class, null);
		types.put(com.rssl.phizic.gate.bankroll.CardLevel.class, null);
		types.put(com.rssl.phizic.gate.bankroll.CardState.class, null);
		types.put(com.rssl.phizic.gate.bankroll.AdditionalCardType.class, null);
		types.put(com.rssl.phizic.gate.bankroll.ReportDeliveryType.class, null);
		types.put(com.rssl.phizic.gate.bankroll.ReportDeliveryLanguage.class, null);
	}
}
