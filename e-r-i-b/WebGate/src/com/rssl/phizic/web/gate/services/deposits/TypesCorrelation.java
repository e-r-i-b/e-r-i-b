package com.rssl.phizic.web.gate.services.deposits;

import java.util.Map;
import java.util.HashMap;

/**
 * @author Krenev
 * @ created 04.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class TypesCorrelation
{
	public static final Map<Class, Class> types= new HashMap<Class,Class>();

	static
	{
		types.put(com.rssl.phizic.web.gate.services.deposits.generated.Office.class, com.rssl.phizgate.common.services.types.OfficeImpl.class);
		types.put(com.rssl.phizic.web.gate.services.deposits.generated.Code.class, com.rssl.phizgate.common.services.types.CodeImpl.class);
		types.put(com.rssl.phizic.gate.dictionaries.officies.Office.class, com.rssl.phizic.web.gate.services.deposits.generated.Office.class);
		types.put(com.rssl.phizic.gate.dictionaries.officies.Code.class, com.rssl.phizic.web.gate.services.deposits.generated.Code.class);
		types.put(com.rssl.phizic.web.gate.services.deposits.generated.ClientState.class, com.rssl.phizic.gate.clients.ClientState.class);

		//types.put(com.rssl.phizic.web.gate.services.deposits.generated.Account.class, com.rssl.phizic.web.gate.services.types.AccountImpl.class);

		types.put(com.rssl.phizic.common.types.Money.class, com.rssl.phizic.web.gate.services.deposits.generated.Money.class);
		types.put(com.rssl.phizic.common.types.Currency.class, com.rssl.phizic.web.gate.services.deposits.generated.Currency.class);
		types.put(com.rssl.phizic.gate.deposit.DepositState.class, null);
		types.put(com.rssl.phizic.gate.deposit.Deposit.class, com.rssl.phizic.web.gate.services.deposits.generated.Deposit.class);
		types.put(com.rssl.phizic.gate.deposit.DepositInfo.class, com.rssl.phizic.web.gate.services.deposits.generated.DepositInfo.class);
		types.put(com.rssl.phizic.gate.bankroll.AccountState.class, null);

		types.put(com.rssl.phizic.gate.bankroll.DepositAbstract.class, com.rssl.phizic.web.gate.services.deposits.generated.DepositAbstract.class);
		types.put(com.rssl.phizic.gate.bankroll.DepositTransaction.class, com.rssl.phizic.web.gate.services.deposits.generated.DepositTransaction.class);

		types.put(com.rssl.phizic.web.gate.services.deposits.generated.Money.class, com.rssl.phizic.common.types.Money.class);
		types.put(com.rssl.phizic.web.gate.services.deposits.generated.Currency.class, com.rssl.phizic.web.gate.services.types.CurrencyImpl.class);
		types.put(com.rssl.phizic.gate.bankroll.Account.class, com.rssl.phizic.web.gate.services.deposits.generated.Account.class);

		types.put(com.rssl.phizic.gate.clients.Client.class, com.rssl.phizic.web.gate.services.deposits.generated.Client.class);
		types.put(com.rssl.phizic.gate.clients.Address.class, com.rssl.phizic.web.gate.services.deposits.generated.Address.class);
		types.put(com.rssl.phizic.gate.clients.ClientDocument.class, com.rssl.phizic.web.gate.services.deposits.generated.ClientDocument.class);
		types.put(com.rssl.phizic.common.types.client.ClientDocumentType.class, null);
		types.put(com.rssl.phizic.gate.clients.ClientStateCategory.class, null); //прописан в BeanFormatterMap
		types.put(com.rssl.phizic.common.types.SegmentCodeType.class, null);

		types.put(com.rssl.phizic.web.gate.services.deposits.generated.Client.class, com.rssl.phizic.web.gate.services.types.ClientImpl.class);
		types.put(com.rssl.phizic.web.gate.services.deposits.generated.Address.class, com.rssl.phizic.web.gate.services.types.AddressImpl.class);
		types.put(com.rssl.phizic.web.gate.services.deposits.generated.ClientDocument.class, com.rssl.phizic.web.gate.services.types.ClientDocumentImpl.class);
		types.put(com.rssl.phizic.gate.clients.ClientState.class, com.rssl.phizic.web.gate.services.deposits.generated.ClientState.class);
	}
}