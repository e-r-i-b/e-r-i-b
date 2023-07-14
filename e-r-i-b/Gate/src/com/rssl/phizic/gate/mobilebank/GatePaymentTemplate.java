package com.rssl.phizic.gate.mobilebank;

import java.io.Serializable;

/**
 * @author Erkin
 * @ created 06.10.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Ўаблон платежа
 */
public interface GatePaymentTemplate extends Serializable
{
	/**
	 * ¬озвращает ID шаблона во внешней системе
	 *
	 * @return ID шаблона во внешней системе
	 */
	String getExternalId();

	/**
	 * @return номер карты, по которой осуществл€етс€ платЄж
	 */
	String getCardNumber();

	/**
	 * ¬озвращает код получател€ платежа
	 * (код в терминах внешней системы)
	 *
	 * @return код получател€ платежа
	 */
	String getRecipientCode();

	/**
	 * ¬озвращает код плательщика
	 * (код в терминах внешней системы)
	 *
	 * @return код плательщика
	 */
	String getPayerCode();

	/**
	 * ¬озвращает статус шаблона
	 *
	 * @return true, если шаблон активный
	 */
	boolean isActive();
}
