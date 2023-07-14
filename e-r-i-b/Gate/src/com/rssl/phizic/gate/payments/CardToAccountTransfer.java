package com.rssl.phizic.gate.payments;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.documents.AbstractCardTransfer;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author krenev
 * @ created 12.07.2010
 * @ $Author$
 * @ $Revision$
 * ѕеревод с карты на счет одного клиента без конверсии
 */
public interface CardToAccountTransfer extends AbstractCardTransfer
{
	/**
	 * —чет зачислени€.
	 * ¬алюта счета должна совпадать с валютой getAmount и валютой getPayerAccount
	 * —чет должен находитс€ в том же банке и принадлежать клиенту.
	 */
	String getReceiverAccount();

	/**
	 * @return валюта счета зачислени€
	 */
	Currency getDestinationCurrency() throws GateException;
}
