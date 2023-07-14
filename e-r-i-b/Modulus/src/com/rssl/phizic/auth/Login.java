package com.rssl.phizic.auth;

import com.rssl.phizic.auth.modes.UserVisitingMode;

/**
 * Created by IntelliJ IDEA.
 * User: Evgrafov
 * Date: 01.09.2005
 * Time: 20:49:23
 */
public interface Login extends CommonLogin
{
	Long getPinEnvelopeId();

	/**
	 * @return тип последнего входа клиента
	 */
	UserVisitingMode getLastUserVisitingMode();

	/**
	 * @param lastUserVisitingMode тип последнего входа клиента
	 */
	void setLastUserVisitingMode(UserVisitingMode lastUserVisitingMode);

	/**
	 * Установить карту последнего входа
	 * @param cardNumber номер карты
	 */
	void setLastLogonCardNumber(String cardNumber);
}
