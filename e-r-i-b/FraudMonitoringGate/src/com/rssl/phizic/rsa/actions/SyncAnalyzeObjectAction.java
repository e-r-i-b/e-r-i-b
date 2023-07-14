package com.rssl.phizic.rsa.actions;

import com.rssl.phizic.monitoring.fraud.FraudAuditedObject;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeRequest;
import com.rssl.phizic.utils.StringHelper;

/**
 * Синхронный Analyze экшен взаимодействия с ВС ФМ
 *
 * @author khudyakov
 * @ created 02.07.15
 * @ $Author$
 * @ $Revision$
 */
public class SyncAnalyzeObjectAction extends SyncAnalyzeAction
{
	private static final int MAX_COMMENT_LENGTH = 255;

	private FraudAuditedObject object;

	public SyncAnalyzeObjectAction(AnalyzeRequest request, FraudAuditedObject _object)
	{
		super(request);

		object = _object;
		object.setClientTransactionId(request.getIdentificationData().getClientTransactionId());
	}

	@Override
	protected String getClientTransactionId()
	{
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
