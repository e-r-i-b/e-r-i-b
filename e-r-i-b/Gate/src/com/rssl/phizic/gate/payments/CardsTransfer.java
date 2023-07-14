package com.rssl.phizic.gate.payments;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.documents.AbstractCardTransfer;
import com.rssl.phizic.gate.exceptions.GateException;

import java.util.Calendar;

/**
 * @author krenev
 * @ created 19.04.2010
 * @ $Author$
 * @ $Revision$
 * ѕеревод с карты на карту.
 * Ќа текущий момент используетс€ этот же интерфейс и дл€ конверсионных операций.
 */
public interface CardsTransfer extends AbstractCardTransfer
{
	/**
	 *  арта зачислени€.
	 *  арта может принадлежать другому клиенту.
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

	/**
	 * “ип карточного перевода
	 * @return тип карты получател€
	 */
	ReceiverCardType getReceiverCardType();
}
