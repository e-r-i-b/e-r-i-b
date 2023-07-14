package com.rssl.phizic.gate.messaging;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Novikov_A
 * @ created 22.09.2006
 * @ $Author$
 * @ $Revision$
 */
public interface GateMessage
{
	/**
	 * Название сообщения (первый тег в xml сообщении)
	 * @return название тега
	 */
	String getMessageName();

	/**
	 * Добавить(изменить существующий) тег к документу
	 * @param name - название тега
	 * @param value - значение тега
	 * @return - тег
	 */
	Element addParameter(String name, Object value);

	/**
	 * Добавить тег к документу
	 * @param name - название тега
	 * @param value - значение тега
	 * @return элемент в котором содержится значение
	 */
	Element appendParameter(String name, Object value);

	/**
	 * xml документ сообщения
	 * @return - xml документ
	 */
	Document getDocument ();
}
