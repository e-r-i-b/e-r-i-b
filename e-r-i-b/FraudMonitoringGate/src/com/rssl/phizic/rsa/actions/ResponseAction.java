package com.rssl.phizic.rsa.actions;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.exceptions.RSAIntegrationException;
import com.rssl.phizic.rsa.exceptions.ProhibitionOperationFraudGateException;
import com.rssl.phizic.rsa.exceptions.RequireAdditionConfirmFraudGateException;
import com.rssl.phizic.rsa.integration.ws.control.generated.GenericRequest;
import com.rssl.phizic.rsa.integration.ws.control.generated.GenericResponse;

/**
 * @author tisov
 * @ created 04.02.15
 * @ $Author$
 * @ $Revision$
 * ���������� �����, ����������� ����-����������-������� � ��������������� ������
 */
public interface ResponseAction<RQ extends GenericRequest, RS extends GenericResponse>
{
	/**
	 * ��������� ������ �� �� ��
	 */
	void send() throws GateLogicException, GateException;

	/**
	 * ��������� �������������� � �� ��
	 * @param response ������
	 */
	void process(RS response) throws GateLogicException, GateException;

	/**
	 * @return ��������������� ������� ����� ����-������
	 */
	RQ getRequest();
}
