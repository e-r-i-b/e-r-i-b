package com.rssl.phizic.rsa.senders.builders.offline;

import com.rssl.phizic.rsa.integration.ws.control.generated.*;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import static com.rssl.phizic.rsa.senders.serializers.support.RSARequestXMLDocumentConstants.*;

/**
 * @author tisov
 * @ created 09.07.15
 * @ $Author$
 * @ $Revision$
 * Базовый класс билдера оффлайн запросов в ApaptiveAuthentication(основной веб-сервис фрод-мониторинга)
 */
public abstract class FraudMonitoringAdaptiveAuthenticationOfflineRequestBuilderBase<RQ extends GenericRequest> extends FraudMonitoringOfflineRequestBuilderBase<RQ>
{

	protected abstract RQ createRequest();


	private GenericActionTypeList createActionTypeList(Node node)
	{
		GenericActionType genericActionType = GenericActionType.fromValue(XmlHelper.getSimpleElementValue((Element) node, GENERIC_ACTION_TYPES));
		return  new GenericActionTypeList(new GenericActionType[]{genericActionType});
	}

	private MessageHeader createMessageHeader(Node node)
	{
		MessageHeader result = new MessageHeader();

		Element elem = (Element) node;

		result.setApiType(APIType.fromValue(XmlHelper.getSimpleElementValue(elem, API_TYPE)));
		result.setRequestType(RequestType.fromValue(XmlHelper.getSimpleElementValue(elem, REQUEST_TYPE)));
		result.setTimeStamp(XmlHelper.getSimpleElementValue(elem, TIME_STAMP));
		result.setVersion(MessageVersion.fromValue(XmlHelper.getSimpleElementValue(elem, VERSION)));

		return result;
	}

	private SecurityHeader createSecurityHeader(Node node)
	{
		SecurityHeader result = new SecurityHeader();

		Element elem = (Element) node;

		result.setCallerCredential(XmlHelper.getSimpleElementValue(elem, CALLER_CREDENTIAL));
		result.setCallerId(XmlHelper.getSimpleElementValue(elem, CALLER_ID));
		result.setMethod(AuthorizationMethod.fromValue(XmlHelper.getSimpleElementValue(elem, METHOD)));

		return result;
	}

	private IdentificationData createIdentificationData(Node node)
	{
		IdentificationData result = new IdentificationData();

		Element elem = (Element) node;

		result.setUserName(XmlHelper.getSimpleElementValue(elem, USER_NAME));
		result.setClientTransactionId(XmlHelper.getSimpleElementValue(elem, CLIENT_TRANSACTION_ID));
		result.setUserLoginName(XmlHelper.getSimpleElementValue(elem, USER_LOGIN_NAME));
		result.setOrgName(XmlHelper.getSimpleElementValue(elem, ORG_NAME));
		result.setUserType(WSUserType.fromValue(XmlHelper.getSimpleElementValue(elem, USER_TYPE)));
		result.setUserStatus(UserStatus.fromValue(XmlHelper.getSimpleElementValue(elem, USER_STATUS)));

		return result;
	}

	private DeviceRequest createDeviceRequest(Node node)
	{
		if (node == null)
		{
			return null;
		}

		DeviceRequest result = new DeviceRequest();

		Element elem = (Element)node;

		result.setHttpAccept(XmlHelper.getSimpleElementValue(elem, HTTP_ACCEPT));
		result.setHttpAcceptChars(XmlHelper.getSimpleElementValue(elem, HTTP_ACCEPT_CHARS));
		result.setHttpAcceptEncoding(XmlHelper.getSimpleElementValue(elem, HTTP_ACCEPT_ENCODING));
		result.setHttpAcceptLanguage(XmlHelper.getSimpleElementValue(elem, HTTP_ACCEPT_LANGUAGE));
		result.setHttpReferrer(XmlHelper.getSimpleElementValue(elem, HTTP_REFERRER));
		result.setIpAddress(XmlHelper.getSimpleElementValue(elem, IP_ADDRESS));
		result.setUserAgent(XmlHelper.getSimpleElementValue(elem, USER_AGENT));
		result.setPageId(XmlHelper.getSimpleElementValue(elem, PAGE_ID));
		result.setDomElements(XmlHelper.getSimpleElementValue(elem, DOM_ELEMENTS));
		result.setJsEvents(XmlHelper.getSimpleElementValue(elem, JS_EVENTS));
		result.setDevicePrint(XmlHelper.getSimpleElementValue(elem, DEVICE_PRINT));
		result.setDeviceTokenCookie(XmlHelper.getSimpleElementValue(elem, DEVICE_TOKEN_COOKIE));
		result.setDeviceTokenFSO(XmlHelper.getSimpleElementValue(elem, DEVICE_TOKEN_FSO));

		if (elem.getElementsByTagName(MOBILE_SDK_DATA) != null)
		{
			MobileDevice mobileDevice = new MobileDevice();
			mobileDevice.setMobileSdkData(XmlHelper.getSimpleElementValue(elem, MOBILE_SDK_DATA));
			result.setDeviceIdentifier(new DeviceIdentifier[]{mobileDevice});
		}

		return result;
	}


	public RQ build()
	{
		RQ result = createRequest();


		result.setActionTypeList(createActionTypeList(this.rootNode.getElementsByTagName(ACTION_TYPE_LIST).item(0)));
		result.setMessageHeader(createMessageHeader(this.rootNode.getElementsByTagName(MESSAGE_HEADER).item(0)));
		result.setSecurityHeader(createSecurityHeader(this.rootNode.getElementsByTagName(SECURITY_HEADER).item(0)));
		result.setIdentificationData(createIdentificationData(this.rootNode.getElementsByTagName(IDENTIFICATION_DATA).item(0)));
		result.setDeviceRequest(createDeviceRequest(this.rootNode.getElementsByTagName(DEVICE_REQUEST).item(0)));

		return result;
	}
}
