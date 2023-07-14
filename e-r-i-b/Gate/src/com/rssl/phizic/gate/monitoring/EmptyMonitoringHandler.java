package com.rssl.phizic.gate.monitoring;

import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * @author mihaylov
 * @ created 06.12.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ������� ����������� ��� ������ � ������� ��(����������) ��� �� ����������
 */
public class EmptyMonitoringHandler extends MonitoringHandler
{
	public void processRequest(String requestTag, long time, String errorCode)
	{
		//������ �� ������
	}

	public void checkRequest(String requestTag) throws GateLogicException
	{
		//������ �� ������
	}

	public void processError(String requestTag)
	{
		//������ �� ������
	}

	public void processResponce(String responceTag, String errorCode)
	{
		//������ �� ������
	}

	public void processOk(String responceTag)
	{
		//������ �� ������
	}
}
