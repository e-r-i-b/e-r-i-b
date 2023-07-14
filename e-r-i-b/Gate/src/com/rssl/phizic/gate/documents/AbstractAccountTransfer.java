/***********************************************************************
 * Module:  AbstractTransfer.java
 * Author:  Evgrafov
 * Purpose: Defines the Interface AbstractTransfer
 ***********************************************************************/

package com.rssl.phizic.gate.documents;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * Абстрактный перевод со счета клиента куда либо
 *
 * По умолчанию если в описании конкретного интерфейса не указано возможен
 * только способ взымания комиссии со счета списания (getChargeOffAccount)
 */
public interface AbstractAccountTransfer extends AbstractTransfer
{
   /**
    * Счет списания. Счет с которого списываются средства при совершении платежа(или другой операции)
    * Domain: AccountNumber
    *
    * @return счет списания
    */
   String getChargeOffAccount();

	/**
	 * @return валоюта счета списания
	 */
    Currency getChargeOffCurrency() throws GateException;
}
