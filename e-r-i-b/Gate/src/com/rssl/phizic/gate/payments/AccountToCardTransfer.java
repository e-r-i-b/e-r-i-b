package com.rssl.phizic.gate.payments;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.documents.AbstractAccountTransfer;
import com.rssl.phizic.gate.exceptions.GateException;

import java.util.Calendar;

/**
 * @author krenev
 * @ created 12.07.2010
 * @ $Author$
 * @ $Revision$
 * ѕеревод с карты на счет одного клиента без конверсии
 */
public interface AccountToCardTransfer extends AbstractAccountTransfer
{
	/**
	 *  арта зачислени€.
	 *  арта должна принадлежать одному клиенту.
	 *
	 * @return карта зачислени€
	 */
	String getReceiverCard();

	/**
	 * @return валюта счета зачислени€
	 */
	Currency getDestinationCurrency() throws GateException;

	/**
	 * ƒата окончани€ срока действи€ карты зачислени€
	 *
	 * @return дата окончани€ срока действи€ карты зачислени€
	 */
	Calendar getReceiverCardExpireDate();
}

