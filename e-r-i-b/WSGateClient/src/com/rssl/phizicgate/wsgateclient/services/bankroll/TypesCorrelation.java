package com.rssl.phizicgate.wsgateclient.services.bankroll;

import java.util.HashMap;
import java.util.Map;

/**
 * @author egorova
 * @ created 10.07.2009
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
		types.put(com.rssl.phizic.gate.bankroll.Account.class, com.rssl.phizicgate.wsgateclient.services.bankroll.generated.Account.class);
		types.put(com.rssl.phizic.common.types.Currency.class, com.rssl.phizicgate.wsgateclient.services.bankroll.generated.Currency.class);
		types.put(com.rssl.phizicgate.wsgateclient.services.bankroll.generated.Currency.class, com.rssl.phizicgate.wsgateclient.services.types.CurrencyImpl.class);
		types.put(com.rssl.phizic.common.types.Money.class, com.rssl.phizicgate.wsgateclient.services.bankroll.generated.Money.class);
		types.put(com.rssl.phizicgate.wsgateclient.services.bankroll.generated.Money.class, com.rssl.phizic.common.types.Money.class);

//		types.put(com.rssl.phizicgate.wsgateclient.services.bankroll.generated.Office.class, com.rssl.phizgate.common.services.types.OfficeImpl.class);

		types.put(com.rssl.phizgate.common.services.types.OfficeImpl.class, com.rssl.phizicgate.wsgateclient.services.bankroll.generated.Office.class);
		types.put(com.rssl.phizicgate.wsgateclient.services.bankroll.generated.Office.class, com.rssl.phizgate.common.services.types.OfficeImpl.class);
		types.put(com.rssl.phizgate.common.services.types.CodeImpl.class, com.rssl.phizicgate.wsgateclient.services.bankroll.generated.Code.class);

		types.put(com.rssl.phizgate.common.services.bankroll.ExtendedCodeGateImpl.class, com.rssl.phizicgate.wsgateclient.services.bankroll.generated.Code.class);
		types.put(com.rssl.phizgate.common.services.bankroll.ExtendedOfficeGateImpl.class, com.rssl.phizicgate.wsgateclient.services.bankroll.generated.Office.class);

		types.put(com.rssl.phizicgate.wsgateclient.services.bankroll.generated.Code.class, com.rssl.phizgate.common.services.types.CodeImpl.class);
		types.put(com.rssl.phizicgate.wsgateclient.services.bankroll.generated.Account.class, com.rssl.phizicgate.wsgateclient.services.types.AccountImpl.class);
        types.put(com.rssl.phizicgate.wsgateclient.services.bankroll.generated.Card.class, com.rssl.phizicgate.wsgateclient.services.types.CardImpl.class);
        types.put(com.rssl.phizic.gate.bankroll.CardLevel.class, null);
        types.put(com.rssl.phizic.gate.bankroll.CardBonusSign.class, null);
        types.put(com.rssl.phizic.gate.bankroll.CardType.class, null);
        types.put(com.rssl.phizic.gate.bankroll.CardState.class, null);
		types.put(com.rssl.phizic.gate.bankroll.ReportDeliveryType.class, null);
		types.put(com.rssl.phizic.gate.bankroll.ReportDeliveryLanguage.class, null);
        types.put(com.rssl.phizic.gate.bankroll.AdditionalCardType.class, null);
		types.put(com.rssl.phizic.gate.bankroll.AccountState.class, null);
	}
}
