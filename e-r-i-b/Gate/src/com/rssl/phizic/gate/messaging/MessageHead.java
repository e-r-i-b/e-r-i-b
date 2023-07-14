package com.rssl.phizic.gate.messaging;

import java.util.Calendar;

/**
 * Заголовок запроса
 * @author niculichev
 * @ created 19.06.2012
 * @ $Author$
 * @ $Revision$
 */
public interface MessageHead
{
	/**
	 * @return идентифкатор сообщения
	 */
	public String getMessageId();

	/**
	 * @return дата сообщения
	 */
	public Calendar getMessageDate();

	/**
	 * @return отправитель сообщения
	 */
	public String getFromAbonent();

	/**
	 * @return идентифкатор сообщения c входными данными
	 */
	public String getParentMessageId();

	/**
	 * @return дата сообщения c входными данными
	 */
	public String getParentMessageDate();

	/**
	 * @return отправитель сообщения c входными данными
	 */
	public String getParentFromAbonent();
}
