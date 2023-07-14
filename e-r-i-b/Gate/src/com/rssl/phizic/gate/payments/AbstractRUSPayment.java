package com.rssl.phizic.gate.payments;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * Абстрактный перевод по свободным реквизитам
 *
 * @author Krenev
 * @ created 26.06.2007
 * @ $Author$
 * @ $Revision$
 */
public interface AbstractRUSPayment extends AbstractTransfer
{
	/**
	 * @return ИНН получателя платежа- может отсутствовать, в этом случае == null
	 */
	String getReceiverINN();

    /**
     * Счет зачисления.
     * @return номер счета
     */
	String getReceiverAccount();

	/**
	 * @return валюта счета зачисления
	 */
	Currency getDestinationCurrency() throws GateException;

	/**
	 * Часть данных в платеже может отсутствовать,
	 * обязательным для заполнния является БИК
	 *
	 * @return банк получателя
	 */
	ResidentBank getReceiverBank();

}