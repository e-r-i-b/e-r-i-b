package com.rssl.phizic.gate.mobilebank;

import java.util.Calendar;

/**
 * Сообщение унифицированного интерфейса МБК
 * @author Pankin
 * @ created 29.12.14
 * @ $Author$
 * @ $Revision$
 */
public interface UESIMessage
{
	/**
	 * @return идентификатор сообщения
	 */
	Long getMessageId();

	/**
	 * @return дата сообщения
	 */
	Calendar getMessageTime();

	/**
	 * @return тип сообщения
	 */
	String getMessageType();

	/**
	 * @return текст сообщения
	 */
	String getMessageText();
}
