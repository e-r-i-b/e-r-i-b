package com.rssl.phizic.rsa.actions;

import com.rssl.phizic.monitoring.fraud.FraudAuditedObject;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeRequest;

/**
 * Analyze экшен первого шага асинхронного взаимодействия с ВС ФМ
 *
 * @author khudyakov
 * @ created 02.07.15
 * @ $Author$
 * @ $Revision$
 */
public class ASyncAnalyzeSendingPhaseObjectAction extends ASyncAnalyzeSendingPhaseAction
{
	public ASyncAnalyzeSendingPhaseObjectAction(AnalyzeRequest request, FraudAuditedObject object)
	{
		super(request);
	}
}
