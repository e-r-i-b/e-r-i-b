package com.rssl.phizic.rsa.actions;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.exceptions.BlockClientOperationFraudException;
import com.rssl.phizic.rsa.exceptions.ProhibitionOperationFraudGateException;
import com.rssl.phizic.rsa.exceptions.RequireAdditionConfirmFraudGateException;
import com.rssl.phizic.rsa.integration.jaxb.generated.ActionCodeType;
import com.rssl.phizic.rsa.integration.jaxb.generated.Request;
import com.rssl.phizic.rsa.integration.jaxb.generated.RiskResultType;
import com.rssl.phizic.rsa.integration.jaxb.generated.TriggeredRuleType;
import com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeResponse;
import org.apache.commons.lang.BooleanUtils;

import static com.rssl.phizic.rsa.Constants.*;

/**
 * Analyze базовый экшен взаимодействия с ВС ФМ
 *
 * @author khudyakov
 * @ created 15.06.15
 * @ $Author$
 * @ $Revision$
 */
public abstract class AnalyzeResponseProcessActionBase extends AnalyzeResponseProviderActionBase
{
	protected abstract String getClientTransactionId();

	public void process(AnalyzeResponse response) throws GateLogicException, GateException
	{
		Request jmsRequest = getRequestFromQueue(getClientTransactionId());
		handleTokens(jmsRequest.getDeviceResult().getDeviceData());
		handleRequest(jmsRequest);
	}

	private void handleRequest(Request jmsRequest) throws GateException, GateLogicException
	{
		RiskResultType riskResult = jmsRequest.getRiskResult();
		if (riskResult == null)
		{
			return;
		}

		TriggeredRuleType triggeredRuleType = riskResult.getTriggeredRule();
		if (triggeredRuleType == null)
		{
			return;
		}

		ActionCodeType actionCodeType = triggeredRuleType.getActionCode();
		if (ActionCodeType.REVIEW == actionCodeType)
		{
			setComments(triggeredRuleType.getComments());
			throw new RequireAdditionConfirmFraudGateException(REQUIRED_ADDITIONAL_CONFIRM_DEFAULT_ERROR_MESSAGE);
		}

		if (ActionCodeType.DENY == actionCodeType)
		{
			boolean profileBlocked = BooleanUtils.isTrue(triggeredRuleType.getBlockClient());
			if (profileBlocked)
			{
				throw new BlockClientOperationFraudException(PROFILE_BLOCKED_ERROR_MESSAGE);
			}
			throw new ProhibitionOperationFraudGateException(PROHIBITION_OPERATION_DEFAULT_ERROR_MESSAGE);
		}
	}

	protected void setComments(String comments) {}
}
