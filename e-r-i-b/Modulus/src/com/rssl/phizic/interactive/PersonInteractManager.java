package com.rssl.phizic.interactive;

import java.util.Collection;

/**
 * @author Erkin
 * @ created 13.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Менеджер по взаимодействию с клиентом
 */
public interface PersonInteractManager
{
	/**
	 * Сообщить клиенту о пользовательской ошибке
	 * @param error - текст ошибки, готовый для показа пользователю
	 */
	void reportError(String error);

	/**
	 * Сообщить клиенту о пользовательских ошибках
	 * @param errors - перечень ошибок, готовых для показа пользователю
	 */
	void reportErrors(Collection<String> errors);

	/**
	 * Попросить клиента ввести код подтверждения
	 */
	void askForConfirmCode();
}
