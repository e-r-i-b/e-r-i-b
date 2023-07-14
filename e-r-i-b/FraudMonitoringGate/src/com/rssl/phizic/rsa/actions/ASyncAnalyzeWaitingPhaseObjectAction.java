package com.rssl.phizic.rsa.actions;

import com.rssl.phizic.monitoring.fraud.FraudAuditedObject;
import com.rssl.phizic.rsa.senders.FraudMonitoringRequestHelper;
import com.rssl.phizic.utils.StringHelper;

/**
 * Analyze экшен второго шага асинхронного взаимодействия с ВС ФМ
 *
 * @author khudyakov
 * @ created 03.07.15
 * @ $Author$
 * @ $Revision$
 */
public class ASyncAnalyzeWaitingPhaseObjectAction extends ASyncAnalyzeWaitingPhaseAction
{
	private static final int MAX_COMMENT_LENGTH = 255;

	private FraudAuditedObject object;

	public ASyncAnalyzeWaitingPhaseObjectAction(FraudAuditedObject object)
	{
		this.object = object;
	}

	@Override
	protected String getClientTransactionId()
	{
		object.setClientTransactionId(FraudMonitoringRequestHelper.restoreClientTransactionId());
		return object.getClientTransactionId();
	}

	@Override
	protected void setComments(String comments)
	{
		if (StringHelper.isNotEmpty(comments))
		{
			int length = comments.length() < MAX_COMMENT_LENGTH ? comments.length() : MAX_COMMENT_LENGTH;
			object.setRefusingReason(comments.substring(0 , length));
		}
	}
}
