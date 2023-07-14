package com.rssl.phizicgate.esberibgate.ws.jms.common;

import com.rssl.phizic.gate.exceptions.GateException;

import javax.jms.TextMessage;

/**
 * @author akrenev
 * @ created 28.05.2015
 * @ $Author$
 * @ $Revision$
 *
 * Парсер сообщений
 */

public interface MessageParser
{
	/**
	 * разобрать jms сообщение
	 * @param source jms сообщение
	 * @param <RsClass> класс результата
	 * @return результат
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	public <RsClass> RsClass parseMessage(TextMessage source) throws GateException;

	/**
	 * разобрать jms сообщение
	 * @param body jms сообщение
	 * @param <RsClass> класс результата
	 * @return результат
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	public <RsClass> RsClass parseMessage(String body) throws GateException;
}
