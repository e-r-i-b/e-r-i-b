package com.rssl.phizgate.messaging.internalws.server.protocol;

import org.w3c.dom.Document;

import java.util.Calendar;

/**
 * @author krenev
 * @ created 22.08.2012
 * @ $Author$
 * @ $Revision$
 * Информация о запросе
 */

public interface RequestInfo
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
	 * @return версия протокола
	 */
	String getVersion();

	/**
	 * @return источник сообщения(код отправителя)
	 */
	String getSource();

	/**
	 * @return IP-адрес клиента, в контексте которого сформирован запрос
	 */
	String getIP();

	/**
	 * @return тело собщения
	 */
	Document getBody();
}
