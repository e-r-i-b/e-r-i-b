package com.rssl.phizic.gate.payments;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.documents.AbstractAccountTransfer;

/**
 * Покупка\продажа\конвертация валюты
 *
 * При отправке должна быть определена:
 *
 *  сумма продажи getChargeOffAmount. При этом ее валюта должна совпадать с валютой getChargeOffAccount.
 *  сумма покупки getDestinationAmount. При этом ее валюта должна совпадать с валютой getDestinationAccount.
 *
 *
 * @author Krenev
 * @ created 26.06.2007
 * @ $Author$
 * @ $Revision$
 */
@Deprecated
public interface CurrencyExchangeTransfer extends AbstractAccountTransfer
{
    /**
    * Счет зачисления
    * Domain: AccountNumber
    *
    * @return счет списания
    */
    String getDestinationAccount();

    /**
    * Сумма покупки. (без комиссии)
    *
    * @return сумма платежа.
    */
    Money getDestinationAmount();

	/**
	 * Установить сумму зачисления(в том случае, если она изменилась в процессе исполнения платежа)
	 * @param amount сумма зачисленная на счет
	 */
	void setDestinationAmount(Money amount);

	/**
	 * Установить сумму списания(в том случае, если она изменилась в процессе исполнения платежа)
	 * @param amount сумма списанная со счета
	 */
    void setChargeOffAmount(Money amount);
}