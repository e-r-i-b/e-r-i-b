package com.rssl.phizic.business.fraudMonitoring.senders.documents;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.monitoring.fraud.FraudAuditedObject;
import com.rssl.phizic.rsa.actions.AnalyzeResponseOfflineAction;
import com.rssl.phizic.rsa.actions.ResponseAction;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeRequest;
import com.rssl.phizic.rsa.integration.ws.control.generated.GenericRequest;
import com.rssl.phizic.rsa.senders.enumeration.FraudMonitoringRequestSendingType;

/**
 * @author tisov
 * @ created 10.07.15
 * @ $Author$
 * @ $Revision$
 * �������-������ �������� �� ����������
 */
public class FraudMonitoringDocumentOfflineSender extends DocumentFraudMonitoringSenderBase
{

	protected ResponseAction createResponseAction() throws GateLogicException, GateException
	{
		return new AnalyzeResponseOfflineAction((AnalyzeRequest) createOffLineRequest());
	}

	@Override
	protected FraudAuditedObject getFraudAuditedObject()
	{
		throw new UnsupportedOperationException("����� �� �������������� �������-��������");
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
