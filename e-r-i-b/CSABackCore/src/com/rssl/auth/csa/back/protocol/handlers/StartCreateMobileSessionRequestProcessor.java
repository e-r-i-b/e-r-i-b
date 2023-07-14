package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.exceptions.ConnectorNotFoundException;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.LogableResponseInfo;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.handlers.data.StartCreateMobileSessionData;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.auth.csa.back.servises.operations.LogIdentificationContext;
import com.rssl.auth.csa.back.servises.operations.MobileAuthenticationOperation;
import com.rssl.phizic.common.types.csa.AuthorizedZoneType;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.rsa.senders.types.EventsType;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.TreeMap;

/**
 * @author osminin
 * @ created 31.07.13
 * @ $Author$
 * @ $Revision$
 *
 * ���������� ������� �� ������ ����� ������������ ����� ����
 *
 * �������� �������:
 * GUID		            ������������� ���������� ����������(mGUID). 	[1]
 * deviceState		    ��������� ����������.                           [0..1]
 * devID                ������������� ����������                        [0..1]
 * authorizedZone       ��� ���� ����� ������������                     [1]
 * password             PIN-���                                         [0..1]
 *
 * ��������� ������:
 * blockingTimeout              ����� ���������� �� ������                                  [0..1]
 *                              ���������� � ��� � ������ ���� ������
 *                              FailureResponceHelper.ERROR_CODE_CONNECTOR_BLOCKED
 * OUID		                    ������������� �������� �����.                               [0..1]
 * nodeInfo                     ���������� � �����                                          [1]
 *      host                    ���� �����                                                  [1]
 * UserInfo		                ���������� � ������������                                   [0..1]
 *      firstname               ��� ������������                                            [1]
 *      patrname                �������� ������������                                       [0..1]
 *      surname                 ������� ������������                                        [1]
 *      birthdate               ���� �������� ������������                                  [1]
 *      passport                ��� ������������                                            [1]
 * ConnectorInfo                ���������� � ����������                                     [0..1]
 *      GUID                    ������������� ����������                                    [1]
 *      deviceState             ���������� ��������� ����������                             [0..1]
 *      deviceInfo              ���������� �� ����������                                    [0..1]
 *      cbCode                  ������������� ������������                                  [1]
 *      userId                  �������� ����� ������������                                 [1]
 *      cardNumber              "����� �����"                                               [1]
 *      login                   �����/�����                                                 [0..1]
 *      type                    ��� ����������(TERMINAL, CSA, MAPI)                         [1]
 *      creationDate            ���� �������� ����������                                    [1]
 *      passwordCreationDate    ���� �������� ������                                        [0..1]
 * ConfirmParameters            ��������� �������������	                                    [0] � ������� ������ �� ����������.
 *      Timeout		            ������� �������� �������������	                            [1]
 *      Attempts		        ���������� ���������� ������� ����� ���� �������������. 	[1]
 */
public class StartCreateMobileSessionRequestProcessor extends StartCreateSessionRequestProcessorBase
{
	private static final String REQUEST_TYPE    = "startCreateMobileSessionRq";
	private static final String RESPONCE_TYPE   = "startCreateMobileSessionRs";

	@Override
	protected String getResponceType()
	{
		return RESPONCE_TYPE;
	}

	@Override
	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	@Override
	public boolean isAccessStandIn()
	{
		return true;
	}

	@Override
	protected LogableResponseInfo processRequest(RequestInfo requestInfo, IdentificationContext context) throws Exception
	{
		Document body = requestInfo.getBody();
		Element element = body.getDocumentElement();

		String guid = XmlHelper.getSimpleElementValue(element, Constants.GUID_TAG);
		String deviceState = XmlHelper.getSimpleElementValue(element, Constants.DEVICE_STATE_TAG);
		String deviceId = XmlHelper.getSimpleElementValue(element, Constants.DEVICE_ID_TAG);
		String version = XmlHelper.getSimpleElementValue(element, Constants.MOBILE_API_VERSION_TAG);
		AuthorizedZoneType authorizedZoneType = AuthorizedZoneType.valueOf(XmlHelper.getSimpleElementValue(element, Constants.AUTHORIZED_ZONE_PARAM_NAME));
		String pin = XmlHelper.getSimpleElementValue(element, Constants.PASSWORD_TAG);
		String mobileSDKData = XmlHelper.getSimpleElementValue(element, Constants.MOBILE_SDK_DATA_TAG);

		LogThreadContext.setMGUID(guid);
		return doRequest(new StartCreateMobileSessionData(context, guid, deviceState, deviceId, version, authorizedZoneType, pin, mobileSDKData));
	}

	@Override
	protected LogIdentificationContext getIdentificationContext(RequestInfo requestInfo) throws Exception
	{
		Document body = requestInfo.getBody();
		Element element = body.getDocumentElement();

		String guid = XmlHelper.getSimpleElementValue(element, Constants.GUID_TAG);
		return LogIdentificationContext.createByConnectorUID(guid);
	}

	protected LogableResponseInfo doRequest(StartCreateMobileSessionData data) throws Exception
	{
		IdentificationContext identificationContext = data.getContext();
		try
		{
			trace("������� ������ �� �������������� ��� ������� " + identificationContext.getProfile().getId());
			MobileAuthenticationOperation authenticationOperation = creat�AuthenticationOperation(identificationContext, data.getGuid());
			trace("�������� ��������������");
			Connector connector = authenticationOperation.execute(data.getDeviceState(), data.getDeviceId(), data.getVersion(), data.getPin());
			if (StringHelper.isNotEmpty(data.getPin()))
				data.setAuthorizedZoneType(AuthorizedZoneType.AUTHORIZATION);
			return new LogableResponseInfo(prepareMAPILogon(identificationContext, connector, data.getAuthorizedZoneType(), data.getMobileSDKData()));
		}
		catch (ConnectorNotFoundException e)
		{
			error("������ ��������������", e);
			return new LogableResponseInfo(getFailureResponseBuilder().buildBadMGUIDResponse(), e);
		}
	}

	protected MobileAuthenticationOperation instantiateAuthenticationOperation(IdentificationContext context)
	{
		return new MobileAuthenticationOperation(context);
	}

	protected MobileAuthenticationOperation creat�AuthenticationOperation(IdentificationContext context, String guid) throws Exception
	{
		MobileAuthenticationOperation operation = instantiateAuthenticationOperation(context);
		operation.initialize(guid);
		return operation;
	}

	@Override
	protected EventsType getEventType()
	{
		return EventsType.FAILED_MOBILE_LOGIN_ATTEMPT;
	}

	@Override
	protected TreeMap<String, String> createRSAData(RequestInfo info)
	{
		TreeMap<String, String> map = new TreeMap<String, String>();

		Document body = info.getBody();
		Element element = body.getDocumentElement();
		String mobileSDKData = XmlHelper.getSimpleElementValue(element, Constants.MOBILE_SDK_DATA_TAG);
		map.put(Constants.MOBILE_SDK_DATA_TAG, mobileSDKData);

		return map;
	}
}
