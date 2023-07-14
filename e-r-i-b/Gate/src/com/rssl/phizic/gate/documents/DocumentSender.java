package com.rssl.phizic.gate.documents;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.Map;

/**
 * @author Krenev
 * @ created 16.08.2007
 * @ $Author$
 * @ $Revision$
 */
public interface DocumentSender
{
	/**
	 * ���������� ���������
	 * @param params ���������
	 */
	void setParameters(Map<String,?> params);
	/**
	 * ������� ��������
	 * @param document ��������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	void send(GateDocument document) throws GateException, GateLogicException;

	/**
	 * ������� ��������
	 * @param document ��������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	void repeatSend(GateDocument document) throws GateException, GateLogicException;

	/**
	 * ����������� ��������
	 * @param document ��������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	void prepare(GateDocument document) throws GateException, GateLogicException;

	/**
	 * �������� ��������
	 * @param document ��������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	void rollback(WithdrawDocument document) throws GateException, GateLogicException;

	/**
	 * ����������� ������
	 * @param document ������ ��� �������������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	void confirm(GateDocument document) throws GateException, GateLogicException;

	/**
	 * ��������� �������
	 * @param document  ������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	void validate(GateDocument document) throws GateException, GateLogicException;
}
