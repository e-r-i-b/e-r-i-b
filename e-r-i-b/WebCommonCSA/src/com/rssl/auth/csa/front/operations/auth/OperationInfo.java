package com.rssl.auth.csa.front.operations.auth;

import com.rssl.phizic.web.auth.Stage;

import java.util.Map;

/**
 * @author niculichev
 * @ created 13.09.2012
 * @ $Author$
 * @ $Revision$
 */
public interface OperationInfo
{
	/**
	 * @return false - некорректный контекст
	 */
	boolean isValid();

	/**
	 * @return идентификатор операции
	 */
	String getOUID();

	/**
	 * @return “екущее состо€ние
	 */
	Stage getCurrentStage();

	/**
	 * @return им€ текущего состо€ни€
	 */
	String getCurrentName();

	/**
	 * ѕереключить на следующее состо€ние
	 */
	void nextStage();

	/**
	 * @return параметры подтверждени€(тип подтверждени€, количество попыток, оставшеес€ врем€, номер парол€ на чеке, номер чека, количество паролей на чеке)
	 */
	Map<String, Object> getConfirmParams();

	/**
	 * @return токен аутентификации
	 */
	public String getAuthToken();
}
