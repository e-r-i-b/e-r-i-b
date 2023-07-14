package com.rssl.phizic.rsa.senders;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.actions.NotifyResponseOfflineAction;
import com.rssl.phizic.rsa.actions.ResponseAction;
import com.rssl.phizic.rsa.integration.ws.control.generated.GenericRequest;
import com.rssl.phizic.rsa.integration.ws.control.generated.NotifyRequest;
import com.rssl.phizic.rsa.senders.enumeration.FraudMonitoringRequestSendingType;

/**
 * @author tisov
 * @ created 10.07.15
 * @ $Author$
 * @ $Revision$
 * �������-������ �������� �� ����-����������
 */
public class FraudMonitoringOfflineNotifySender extends FraudMonitoringNotifySenderBase
{

	protected ResponseAction createResponseAction() throws GateLogicException, GateException
	{
		return new NotifyResponseOfflineAction((NotifyRequest) createOffLineRequest());
	}

	@Override
	protected GenericRequest createOnLineRequest() throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("����� �� �������������� �������-��������");
	}

	@Override
	public FraudMonitoringRequestSendingType getSendingType()
	{
		return FraudMonitoringRequestSendingType.OFFLINE;
	}
}
