package com.rssl.phizgate.messaging.internalws.server.protocol;

import java.util.Calendar;

/**
 * @author krenev
 * @ created 22.08.2012
 * @ $Author$
 * @ $Revision$
 * Информация об ответе.
 */

public interface ResponseInfo
{
	/**
	 * @return тип сообщения
	 */
	String getType();

	/**
	 * @return идентфикатор сообщения
	 */
	String getUID();

	/**
	 * @return дата формирования сообщения
	 */
	Calendar getDate();

	/**
	 * @return код ошибки
	 */
	int getErrorCode();

	/**
	 * @return описание ошибки
	 */
	String getErrorDescription();

	/**
	 * @return строковое представление ответа.
	 */
	String asString();
}
