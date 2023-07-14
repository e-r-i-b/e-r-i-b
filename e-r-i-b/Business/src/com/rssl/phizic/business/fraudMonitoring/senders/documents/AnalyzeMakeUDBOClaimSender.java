package com.rssl.phizic.business.fraudMonitoring.senders.documents;

import com.rssl.phizic.business.documents.RemoteConnectionUDBOClaim;
import com.rssl.phizic.business.fraudMonitoring.senders.documents.builders.AnalyzeMakeUDBOClaimRequestBuilder;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.monitoring.fraud.FraudAuditedObject;
import com.rssl.phizic.rsa.exceptions.RequireAdditionConfirmFraudGateException;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeRequest;

/**
 * Сендер во ФМ информации по заявке подключения УДБО
 *
 * @author khudyakov
 * @ created 10.07.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeMakeUDBOClaimSender extends DocumentFraudMonitoringSenderBase
{
	private RemoteConnectionUDBOClaim document;

	public AnalyzeMakeUDBOClaimSender(RemoteConnectionUDBOClaim document)
	{
		this.document = document;
	}

	@Override
	protected AnalyzeRequest createOnLineRequest() throws GateException, GateLogicException
	{
		return new AnalyzeMakeUDBOClaimRequestBuilder().build();
	}

	@Override
	protected FraudAuditedObject getFraudAuditedObject()
	{
		return document;
	}

	public void send() throws GateLogicException
	{
		try
		{
			super.send();
		}
		catch (RequireAdditionConfirmFraudGateException e)
		{
			//по РО заявка на подключение УДБО в КЦ не переводится
			log.error(e);
		}
	}
}
