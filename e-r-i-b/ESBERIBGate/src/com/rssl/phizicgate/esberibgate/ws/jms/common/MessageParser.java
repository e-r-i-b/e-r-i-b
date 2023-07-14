package com.rssl.phizicgate.esberibgate.ws.jms.common;

import com.rssl.phizic.gate.exceptions.GateException;

import javax.jms.TextMessage;

/**
 * @author akrenev
 * @ created 28.05.2015
 * @ $Author$
 * @ $Revision$
 *
 * ������ ���������
 */

public interface MessageParser
{
	/**
	 * ��������� jms ���������
	 * @param source jms ���������
	 * @param <RsClass> ����� ����������
	 * @return ���������
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	public <RsClass> RsClass parseMessage(TextMessage source) throws GateException;

	/**
	 * ��������� jms ���������
	 * @param body jms ���������
	 * @param <RsClass> ����� ����������
	 * @return ���������
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	public <RsClass> RsClass parseMessage(String body) throws GateException;
}
