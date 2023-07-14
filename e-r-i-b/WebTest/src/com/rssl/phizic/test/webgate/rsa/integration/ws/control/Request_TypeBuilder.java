package com.rssl.phizic.test.webgate.rsa.integration.ws.control;

import com.rssl.phizic.test.webgate.rsa.integration.ws.back.generated.*;
import com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.GenericRequest;
import com.rssl.phizic.test.webgate.rsa.integration.ws.control.generated.IdentificationData;
import com.rssl.phizic.test.webgate.rsa.jaxb.generated.*;
import com.rssl.phizic.utils.RandomHelper;
import com.rssl.phizic.utils.xml.jaxb.JAXBUtils;

import javax.xml.bind.JAXBException;

/**
 * @author khudyakov
 * @ created 10.06.15
 * @ $Author$
 * @ $Revision$
 */
public class Request_TypeBuilder
{
	private Request_Type request_type = new Request_Type();

	public Request_TypeBuilder(String string) throws JAXBException
	{
		Request request = JAXBUtils.unmarshalBean(Request.class, string);

		setIdentificationData(request.getIdentificationData());
		setStatusHeader(request.getStatusHeader());
		setRiskResult(request.getRiskResult());
	}

	public Request_TypeBuilder(GenericRequest request)
	{
		setIdentificationData(request.getIdentificationData());
		setStatusHeader();
		setDeviceResult();
		setRiskResult();
	}

	private void setIdentificationData(IdentificationDataType identificationDataType)
	{
		if (identificationDataType != null)
		{
			IdentificationData_Type identificationData_type = new IdentificationData_Type();
			identificationData_type.setClientTransactionId(identificationDataType.getClientTransactionId());
			identificationData_type.setUserName(identificationDataType.getUserName());

			request_type.setIdentificationData(identificationData_type);
		}
	}

	private void setIdentificationData(IdentificationData identificationData)
	{
		if (identificationData != null)
		{
			IdentificationData_Type identificationData_type = new IdentificationData_Type();
			identificationData_type.setClientTransactionId(identificationData.getClientTransactionId());
			identificationData_type.setUserName(identificationData.getUserName());

			request_type.setIdentificationData(identificationData_type);
		}
	}

	private void setStatusHeader(StatusHeaderType statusHeaderType)
	{
		if (statusHeaderType != null)
		{
			StatusHeader_Type statusHeader_type = new StatusHeader_Type();
			statusHeader_type.setReasonDescription(statusHeaderType.getReasonDescription());
			statusHeader_type.setStatusCode(statusHeaderType.getStatusCode());
			statusHeader_type.setReasonCode(statusHeaderType.getReasonCode());

			request_type.setStatusHeader(statusHeader_type);
		}
	}

	private void setStatusHeader()
	{
		StatusHeader_Type statusHeader_type = new StatusHeader_Type();
		statusHeader_type.setReasonDescription("Ok");
		statusHeader_type.setStatusCode(200);
		statusHeader_type.setReasonCode(1001);

		request_type.setStatusHeader(statusHeader_type);
	}

	private void setDeviceResult()
	{
		DeviceResult_Type deviceResult_type = new DeviceResult_Type();
		deviceResult_type.setDeviceData(new DeviceData_Type(RandomHelper.rand(20, RandomHelper.ENGLISH_LETTERS), "PMV60D%2BVQ%2FxoKeysMBwC01AGcHxVeJmKqyO5PSQQ4goub%2BHaKJ8euBgamJ%" + RandomHelper.rand(12, RandomHelper.ENGLISH_LETTERS)));
		request_type.setDeviceResult(deviceResult_type);
	}

	private void setRiskResult()
	{
		RiskResult_Type riskResult_type = new RiskResult_Type();
		riskResult_type.setRiskScore(0);
		riskResult_type.setRiskScoreBand("Test RiskScoreBand");

		TriggeredRule_Type triggeredRule_type = new TriggeredRule_Type();
		triggeredRule_type.setActionName("Test ActionName");
		triggeredRule_type.setActionCode(ActionCode_Type.ALLOW);
		triggeredRule_type.setBlockClient(false);
		triggeredRule_type.setComments("Test Comments");
		triggeredRule_type.setRuleId("Test RuleId");
		triggeredRule_type.setRuleName("Test RuleName");
		riskResult_type.setTriggeredRule(triggeredRule_type);

		request_type.setRiskResult(riskResult_type);
	}

	private void setRiskResult(RiskResultType riskResultType)
	{
		if (riskResultType != null)
		{
			RiskResult_Type riskResult_type = new RiskResult_Type();
			riskResult_type.setRiskScore(riskResultType.getRiskScore());
			riskResult_type.setRiskScoreBand(riskResultType.getRiskScoreBand());

			TriggeredRuleType triggeredRuleType = riskResultType.getTriggeredRule();
			if (triggeredRuleType != null)
			{
				TriggeredRule_Type triggeredRule_type = new TriggeredRule_Type();
				triggeredRule_type.setActionName(triggeredRuleType.getActionName());
				triggeredRule_type.setBlockClient(triggeredRuleType.getBlockClient());
				triggeredRule_type.setComments(triggeredRuleType.getComments());
				triggeredRule_type.setRuleId(triggeredRuleType.getRuleId());
				triggeredRule_type.setRuleName(triggeredRuleType.getRuleName());

				ActionCodeType actionCodeType = triggeredRuleType.getActionCode();
				if (actionCodeType != null)
				{
					triggeredRule_type.setActionCode(ActionCode_Type.fromString(actionCodeType.value()));
				}

				riskResult_type.setTriggeredRule(triggeredRule_type);
			}

			request_type.setRiskResult(riskResult_type);
		}
	}

	public Request_Type build()
	{
		return request_type;
	}
}
