package com.rssl.common.forms.validators;

/**
 * @author krenev
 * @ created 30.12.2010
 * @ $Author$
 * @ $Revision$
 * Интерфейс - владелец сообщений.
 */
public interface MessageHolder
{
	/**
	 * @return какое-то сообщение
	 */
	String getMessage();

	/**
	* @return ключ какого-то сообщения
	*/
	String getMessageKey();
	/**
	 * Установить сообщение
	 * @param message сообщение
	 */
	void setMessage(String message);
}
