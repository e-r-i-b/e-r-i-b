package com.rssl.phizic.gate.mobilebank;

import java.io.Serializable;

/**
 * @author Erkin
 * @ created 22.04.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Информация по карте в МБ (регистрация)
 */
public interface MobileBankCardInfo extends Serializable
{
	/**
	 * @return номер карты
	 */
	String getCardNumber();

	/**
	 * @return статус карты в МБ.
	 */
	MobileBankCardStatus getStatus();

	/**
	 * @return номер телефона или null в случае связанной карты
	 */
	String getMobilePhoneNumber();
}