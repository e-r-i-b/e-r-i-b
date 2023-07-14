package com.rssl.phizic.rsa.actions;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.rsa.exceptions.RSAIntegrationException;
import com.rssl.phizic.rsa.integration.ws.control.generated.AdaptiveAuthenticationSoapBindingStub;
import com.rssl.phizic.rsa.integration.ws.control.generated.GenericRequest;
import com.rssl.phizic.rsa.integration.ws.control.generated.GenericResponse;

/**
 * ��������� ������ �������������� � �� ��
 *
 * @author khudyakov
 * @ created 13.06.15
 * @ $Author$
 * @ $Revision$
 */
public interface ResponseProviderAction<RQ extends GenericRequest, RS extends GenericResponse> extends ResponseAction<RQ, RS>
{
	/**
	 * �������� ������� � ������� RSA
	 * @param stub - ������, ���������� ������
	 * @return ���������� ����� ��������� �� ������� �������
	 * @throws GateException
	 */
	RS doSend(AdaptiveAuthenticationSoapBindingStub stub) throws RSAIntegrationException;
}
