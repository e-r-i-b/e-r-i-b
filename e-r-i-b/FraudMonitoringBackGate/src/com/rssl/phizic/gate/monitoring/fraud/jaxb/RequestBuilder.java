package com.rssl.phizic.gate.monitoring.fraud.jaxb;

import com.rssl.phizic.gate.monitoring.fraud.jaxb.generated.*;
import com.rssl.phizic.gate.monitoring.fraud.ws.generated.*;

import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

/**
 * Билдер запроса
 *
 * @author khudyakov
 * @ created 14.06.15
 * @ $Author$
 * @ $Revision$
 */
public class RequestBuilder
{
	private static final RequestBuilder INSTANCE = new RequestBuilder();

	private static JAXBContext context;
	private Request_Type request_type;


	private RequestBuilder()
	{
		try
		{
			context = JAXBContext.newInstance(ObjectFactory.class.getPackage().getName());
		}
		catch (JAXBException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * @return инстанс
	 */
	public static RequestBuilder getInstance()
	{
		return INSTANCE;
	}

	/**
	 * Добавить Request_type
	 * @param request_type request_type
	 * @return this
	 */
	public RequestBuilder append(Request_Type request_type)
	{
		this.request_type = request_type;
		return this;
	}

	/**
	 * Привести Request_Type в строку;
	 * @return запрос
	 */
	public String build() throws JAXBException
	{
		StringWriter writer = new StringWriter();
		context.createMarshaller().marshal(new JAXBRequestBuilder(request_type).build(), writer);
		return writer.toString();
	}

	private class JAXBRequestBuilder
	{
		private Request request;

		private JAXBRequestBuilder(Request_Type request_type)
		{
			if (request_type == null)
			{
				throw new IllegalArgumentException("Не задан парамметр request_type");
			}

			request = new Request();
			request.setIdentificationData(getIdentificationDataType(request_type.getIdentificationData()));
			request.setStatusHeader(getStatusHeaderType(request_type.getStatusHeader()));
			request.setDeviceResult(getDeviceResult(request_type.getDeviceResult()));
			request.setRiskResult(getRiskResultType(request_type.getRiskResult()));
		}

		private IdentificationDataType getIdentificationDataType(IdentificationData_Type identificationData_type)
		{
			if (identificationData_type == null)
			{
				return null;
			}

			IdentificationDataType identificationDataType = new IdentificationDataType();
			identificationDataType.setClientTransactionId(identificationData_type.getClientTransactionId());
			identificationDataType.setUserName(identificationDataType.getUserName());
			return identificationDataType;
		}

		private StatusHeaderType getStatusHeaderType(StatusHeader_Type statusHeader_type)
		{
			if (statusHeader_type == null)
			{
				return null;
			}

			StatusHeaderType statusHeaderType = new StatusHeaderType();
			statusHeaderType.setReasonDescription(statusHeader_type.getReasonDescription());
			statusHeaderType.setStatusCode(statusHeader_type.getStatusCode());
			statusHeaderType.setReasonCode(statusHeader_type.getReasonCode());
			return statusHeaderType;
		}

		private DeviceResultType getDeviceResult(DeviceResult_Type deviceResult_type)
		{
			if (deviceResult_type == null)
			{
				return null;
			}

			DeviceResultType deviceResultType = new DeviceResultType();
			deviceResultType.setDeviceData(getDeviceDataType(deviceResult_type.getDeviceData()));
			return deviceResultType;
		}

		private DeviceDataType getDeviceDataType(DeviceData_Type deviceData_type)
		{
			if (deviceData_type == null)
			{
				return null;
			}

			DeviceDataType deviceDataType = new DeviceDataType();
			deviceDataType.setDeviceTokenCookie(deviceData_type.getDeviceTokenCookie());
			deviceDataType.setDeviceTokenFSO(deviceData_type.getDeviceTokenFSO());
			return deviceDataType;
		}

		private RiskResultType getRiskResultType(RiskResult_Type riskResult_type)
		{
			if (riskResult_type == null)
			{
				return null;
			}

			RiskResultType riskResultType = new RiskResultType();
			riskResultType.setRiskScoreBand(riskResult_type.getRiskScoreBand());
			riskResultType.setRiskScore(riskResult_type.getRiskScore());
			riskResultType.setTriggeredRule(getTriggeredRuleType(riskResult_type.getTriggeredRule()));
			return riskResultType;
		}

		private TriggeredRuleType getTriggeredRuleType(TriggeredRule_Type triggeredRule_type)
		{
			if (triggeredRule_type == null)
			{
				return null;
			}

			TriggeredRuleType triggeredRuleType = new TriggeredRuleType();
			ActionCode_Type actionCode_type = triggeredRule_type.getActionCode();
			if (actionCode_type != null)
			{
				triggeredRuleType.setActionCode(ActionCodeType.fromValue(actionCode_type.getValue()));
			}

			triggeredRuleType.setActionName(triggeredRule_type.getActionName());
			triggeredRuleType.setComments(triggeredRule_type.getComments());
			triggeredRuleType.setBlockClient(triggeredRule_type.getBlockClient());
			triggeredRuleType.setRuleName(triggeredRule_type.getRuleName());
			triggeredRuleType.setRuleId(triggeredRule_type.getRuleId());
			return triggeredRuleType;
		}

		private Request build()
		{
			return request;
		}
	}
}
