package com.rssl.phizic.gate.payments;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.documents.AbstractAccountTransfer;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * Перевод между счетами клиента.
 *
 * @author Krenev
 * @ created 22.06.2007
 * @ $Author$
 * @ $Revision$
 */
public interface ClientAccountsTransfer extends AbstractAccountTransfer
{
   /**
    * Счет зачисления.
    * Валюта счета должна совпазать с валютой getAmount и валютой getPayerAccount
    * Счет должен находится в том же банке и принадлежать клиенту.
    * Может возвращать null в случае сумма операции равна 0.
    * 
    * Domain: AccountNumber
    * @return String
    */
   String getReceiverAccount();

	/**
	 * @return валюта счета зачисления
	 */
	Currency getDestinationCurrency() throws GateException;

}
