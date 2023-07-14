package com.rssl.phizic.auth.modes;

import com.rssl.phizic.security.SecurityLogicException;

import java.io.Serializable;

/**
 * @author Evgrafov
 * @ created 18.12.2006
 * @ $Author: erkin $
 * @ $Revision: 45904 $
 */

/**
 * В РЕАЛИЗАЦИЯХ НЕ ДОЛЖНО БЫТЬ СОСТОЯНИЯ!!!!!!
 */
public interface AthenticationCompleteAction extends Serializable
{
	/**
	 * Выполнить действие после аутентификации пользователя
	 * @param context контекст аутентификации
	 */
	void execute(AuthenticationContext context) throws SecurityException, SecurityLogicException;
}
