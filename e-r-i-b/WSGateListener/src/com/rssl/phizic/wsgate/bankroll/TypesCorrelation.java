package com.rssl.phizic.wsgate.bankroll;

import java.util.Map;
import java.util.HashMap;

/**
 * @author egorova
 * @ created 01.07.2009
 * @ $Author$
 * @ $Revision$
 */
public class TypesCorrelation
{
	public static final Map<Class, Class> types = new HashMap<Class, Class>();

	static
	{
		types.put(com.rssl.phizic.wsgate.bankroll.generated.Currency.class, com.rssl.phizic.common.types.Currency.class);
		types.put(com.rssl.phizic.common.types.Currency.class, com.rssl.phizic.wsgate.bankroll.generated.Currency.class);
		types.put(com.rssl.phizic.wsgate.bankroll.generated.Office.class, com.rssl.phizic.wsgate.types.OfficeImpl.class);
		types.put(com.rssl.phizic.wsgate.bankroll.generated.Code.class, com.rssl.phizic.wsgate.types.CodeImpl.class);
		types.put(com.rssl.phizic.wsgate.bankroll.generated.Account.class, com.rssl.phizic.gate.bankroll.Account.class);

		types.put(com.rssl.phizic.gate.bankroll.AccountState.class, null);
		types.put(com.rssl.phizic.common.types.Money.class, com.rssl.phizic.wsgate.bankroll.generated.Money.class);
		types.put(com.rssl.phizic.wsgate.bankroll.generated.Money.class, com.rssl.phizic.common.types.Money.class);
		types.put(com.rssl.phizic.gate.dictionaries.officies.Office.class, com.rssl.phizic.wsgate.bankroll.generated.Office.class);
		types.put(com.rssl.phizic.gate.dictionaries.officies.Code.class, com.rssl.phizic.wsgate.bankroll.generated.Code.class);
        types.put(com.rssl.phizic.wsgate.bankroll.generated.Card.class, com.rssl.phizic.gate.bankroll.Card.class);
        types.put(com.rssl.phizic.gate.bankroll.CardLevel.class, null);
        types.put(com.rssl.phizic.gate.bankroll.CardBonusSign.class, null);
        types.put(com.rssl.phizic.gate.bankroll.CardType.class, null);
        types.put(com.rssl.phizic.gate.bankroll.CardState.class, null);
        types.put(com.rssl.phizic.gate.bankroll.AdditionalCardType.class, null);

	}
}
