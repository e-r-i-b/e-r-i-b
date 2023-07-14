package com.rssl.phizic.rsa.senders;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.senders.enumeration.FraudMonitoringRequestSendingType;
import com.rssl.phizic.rsa.senders.initialization.InitializationData;

/**
 * ��������� �������� �� ����-����������
 *
 * @author khudyakov
 * @ created 03.02.15
 * @ $Author$
 * @ $Revision$
 */
public interface FraudMonitoringSender<RQ, ID extends InitializationData>
{
	/**
	 * ������������� �������
	 * @param data - ������ ��� �������������
	 */
	void initialize(ID data);

	/**
	 * @return ������ �������������
	 */
	ID getInitializationData();

	/**
	 * ��������� ������ �� ����-����������
	 */
	void send() throws GateLogicException;

	/**
	 * �������� �� ������ �����������
	 * @return ��������
	 */
	boolean isNull();

	/**
	 * ��������������� � xml-�������� ������
	 * @return ��������������� ���������
	 */
	String getRequestBody() throws GateException, GateLogicException;

	FraudMonitoringRequestSendingType getSendingType();

}
