package com.rssl.phizicgate.iqwave.messaging;

import org.w3c.dom.Document;

import java.util.Calendar;

/**
 * @author Krenev
 * @ created 26.05.2010
 * @ $Author$
 * @ $Revision$
 * Контейнер информации о сообщениии.
 */
public interface MessageInfoContainer
{
	/**
	 * @return тип сообщения
	 */
	String getMessageTag();

	/**
	 * @return идентифкатор сообщения
	 */
	String getMessageId();

	/**
	 * @return да та сообщения
	 */
	Calendar getMessageDate();

	/**
	 * @return отправитель сообщения
	 */
	String getFromAbonent();

	/**
	 * @return идентифкатор сообщения
	 */
	String getParentMessageId();

	/**
	 * @return да та сообщения
	 */
	Calendar getParentMessageDate();

	/**
	 * @return отправитель сообщения
	 */
	String getParentFromAbonent();

	/**
	 * @return Содежимое body
	 */
	Document getBodyContent();

	/**
	 * @return код ошибки. нал если ошибки нет
	 */
	String getErrorCode();

	/**
	 * @return описание ошибки. нал если ошибки нет
	 */
	String getErrorDescription();
}
