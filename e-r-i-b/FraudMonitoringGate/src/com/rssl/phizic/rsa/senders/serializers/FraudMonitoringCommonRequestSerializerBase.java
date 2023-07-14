package com.rssl.phizic.rsa.senders.serializers;

import com.rssl.phizic.rsa.integration.ws.control.generated.*;
import org.w3c.dom.Node;

import static com.rssl.phizic.rsa.senders.serializers.support.RSARequestXMLDocumentConstants.*;

/**
 * @author tisov
 * @ created 07.07.15
 * @ $Author$
 * @ $Revision$
 *
 * Базовый класс сериализации основных запросов во фрод-мониторинг
 */
public abstract class FraudMonitoringCommonRequestSerializerBase<RQ extends GenericRequest> extends FraudMonitoringRequestSerializerBase<RQ>
{

	private Node createActionTypeListTag()
	{
		Node result = createElement(ACTION_TYPE_LIST);
		result.appendChild(createSimpleTag(GENERIC_ACTION_TYPES, getRequest().getActionTypeList().getGenericActionTypes(0).getValue()));
		return result;
	}

	private Node createMessageHeaderTag()
	{
		Node result = createElement(MESSAGE_HEADER);

		MessageHeader messageHeader = getRequest().getMessageHeader();

		result.appendChild(createSimpleTag(API_TYPE, messageHeader.getApiType().getValue()));
		result.appendChild(createSimpleTag(REQUEST_TYPE, messageHeader.getRequestType().getValue()));
		result.appendChild(createSimpleTag(TIME_STAMP, messageHeader.getTimeStamp()));
		result.appendChild(createSimpleTag(VERSION, messageHeader.getVersion().getValue()));

		return result;
	}

	private Node createSecurityHeaderTag()
	{
		Node result = createElement(SECURITY_HEADER);

		SecurityHeader securityHeader = getRequest().getSecurityHeader();

		result.appendChild(createSimpleTag(CALLER_CREDENTIAL, securityHeader.getCallerCredential()));
		result.appendChild(createSimpleTag(CALLER_ID, securityHeader.getCallerId()));
		result.appendChild(createSimpleTag(METHOD, securityHeader.getMethod().getValue()));

		return result;
	}

	private Node createIdentificationDataTag()
	{
		Node result = createElement(IDENTIFICATION_DATA);

		IdentificationData identificationData = getRequest().getIdentificationData();

		result.appendChild(createSimpleTag(USER_NAME, identificationData.getUserName()));
		result.appendChild(createSimpleTag(CLIENT_TRANSACTION_ID, identificationData.getClientTransactionId()));
		result.appendChild(createSimpleTag(USER_LOGIN_NAME, identificationData.getUserLoginName()));
		result.appendChild(createSimpleTag(ORG_NAME, identificationData.getOrgName()));
		result.appendChild(createSimpleTag(USER_TYPE, identificationData.getUserType().getValue()));
		result.appendChild(createSimpleTag(USER_STATUS, identificationData.getUserStatus().getValue()));

		return result;
	}

	private Node createDeviceRequestTag()
	{
		Node result = createElement(DEVICE_REQUEST);

		DeviceRequest deviceRequest = getRequest().getDeviceRequest();

		appendSimpleTagIfNotNull(result, HTTP_ACCEPT, deviceRequest.getHttpAccept());
		appendSimpleTagIfNotNull(result, HTTP_ACCEPT_CHARS, deviceRequest.getHttpAcceptChars());
		appendSimpleTagIfNotNull(result, HTTP_ACCEPT_LANGUAGE, deviceRequest.getHttpAcceptLanguage());
		appendSimpleTagIfNotNull(result, HTTP_REFERRER, deviceRequest.getHttpReferrer());
		appendSimpleTagIfNotNull(result, HTTP_ACCEPT_ENCODING, deviceRequest.getHttpAcceptEncoding());
		appendSimpleTagIfNotNull(result, IP_ADDRESS, deviceRequest.getIpAddress());
		appendSimpleTagIfNotNull(result, USER_AGENT, deviceRequest.getUserAgent());
		appendSimpleTagIfNotNull(result, PAGE_ID, deviceRequest.getPageId());
		appendSimpleTagIfNotNull(result, DOM_ELEMENTS, deviceRequest.getDomElements());
		appendSimpleTagIfNotNull(result, JS_EVENTS, deviceRequest.getJsEvents());
		appendSimpleTagIfNotNull(result, DEVICE_PRINT, deviceRequest.getDevicePrint());
		appendSimpleTagIfNotNull(result, DEVICE_TOKEN_COOKIE, deviceRequest.getDeviceTokenCookie());
		appendSimpleTagIfNotNull(result, DEVICE_TOKEN_FSO, deviceRequest.getDeviceTokenFSO());

		if (deviceRequest.getDeviceIdentifier() != null && deviceRequest.getDeviceIdentifier().length != 0)
		{
			MobileDevice device = (MobileDevice) deviceRequest.getDeviceIdentifier(0);
			appendSimpleTagIfNotNull(result, MOBILE_SDK_DATA, device.getMobileSdkData());
		}

		return result;
	}

	private Node createDeviceManagementRequestTag()
	{
		Node result = createElement(DEVICE_MANAGEMENT_REQUEST);

		result.appendChild(createSimpleTag(DEVICE_ACTION_TYPES, getDeviceActionTypes()));
		result.appendChild(createSimpleTag(BINDING_TYPE, getBindingTypeTag()));

		return result;
	}

	protected abstract String getAutoCreateUserFlag();

	protected abstract String getChannelIndicator();

	protected abstract String getRunRiskType();

	protected abstract String getDeviceActionTypes();

	protected abstract String getBindingTypeTag();

	protected abstract Node createEventDataTag();

	@Override
	protected void fillRootNode(Node rootNode)
	{

		GenericRequest request = getRequest();

		rootNode.appendChild(createActionTypeListTag());
		rootNode.appendChild(createMessageHeaderTag());
		rootNode.appendChild(createSecurityHeaderTag());
		rootNode.appendChild(createIdentificationDataTag());

		if (request.getDeviceRequest() != null)
		{
			rootNode.appendChild(createDeviceRequestTag());
		}

		rootNode.appendChild(createSimpleTag(AUTO_CREATE_USER_FLAG, getAutoCreateUserFlag()));
		rootNode.appendChild(createSimpleTag(CHANNEL_INDICATOR, getChannelIndicator()));
		appendSimpleTagIfNotNull(rootNode, RUN_RISK_TYPE, getRunRiskType());
		rootNode.appendChild(createDeviceManagementRequestTag());
		rootNode.appendChild(createEventDataTag());

	}

}
