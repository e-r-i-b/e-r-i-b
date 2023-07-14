package com.rssl.phizic.gate.mobilebank;

import java.io.Serializable;

/**
 * @author Erkin
 * @ created 22.04.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Шаблон платежа в МБ
 */
public interface MobileBankTemplate extends Serializable
{
	/**
	 * @return карта, к которой принадлежит шаблон
	 */
	MobileBankCardInfo getCardInfo();

	/**
	 * @return наименование получателя в МБ
	 */
	String getRecipient();

	/**
	 * @return список идентификаторов плательщика в МБ. Порядок важен!!
	 */
	String[] getPayerCodes();
}