package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizic.gate.bankroll.AccountState;
import com.rssl.phizicgate.esberibgate.ws.generated.AccountStatusEnum_Type;

import java.util.HashMap;
import java.util.Map;

/**
 * ќбЄртка преобразовани€ состо€ни€ счета из типа шины в гейтовый формат
 *
 * @author egorova
 * @ created 08.10.2010
 * @ $Author$
 * @ $Revision$
 */

public class AccountStateWrapper
{
	private static final Map<AccountStatusEnum_Type, AccountState> accountState = new HashMap<AccountStatusEnum_Type, AccountState>();

	static
	{
		accountState.put(AccountStatusEnum_Type.value1, AccountState.OPENED);
		accountState.put(AccountStatusEnum_Type.value2, AccountState.CLOSED);
		accountState.put(AccountStatusEnum_Type.value3, AccountState.ARRESTED);
		accountState.put(AccountStatusEnum_Type.value4, AccountState.LOST_PASSBOOK);
	}

	public static AccountState getAccountState(AccountStatusEnum_Type accountStatus)
	{
		// ≈сли статус из внешней системы не возвращаетс€, считаем счет открытым
		if (accountStatus == null)
			return AccountState.OPENED;
		return accountState.get(accountStatus);
	}
}
