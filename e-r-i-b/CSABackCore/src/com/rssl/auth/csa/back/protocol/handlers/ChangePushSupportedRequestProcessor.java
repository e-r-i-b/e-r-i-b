package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.SyncUtil;
import com.rssl.auth.csa.back.servises.operations.ChangePushSupportedParamOperation;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

import java.util.List;

/**
 * @author EgorovaA
 * @ created 07.08.13
 * @ $Author$
 * @ $Revision$
 *
 * Обработчик запроса на изменение признака подключения push-уведомлений changePushSupportedRq
 *
 * Парметры запроса:
 * GUID		                идентификатор мобильного приложения(mGUID). 	[1]
 * deviceId                 идентификатор устройства.
 * pushSupported		    признак подключения push-уведомлений.           [1]
 * securityToken            токен безопасности, сформированный мобильным приложением.
 */
public class ChangePushSupportedRequestProcessor extends RequestProcessorBase
{
	public static final String REQUEST_TYPE = "changePushSupportedRq";
	public static final String RESPONCE_TYPE = "changePushSupportedRs";
	private static final String INFO_FORMAT_STRING = "Для коннектора %s изменены: признак подключения push-уведомлений - %s";
	private static final String TOKEN_SUFFIX = ", токен безопасности - %s";

	protected String getResponceType()
	{
		return RESPONCE_TYPE;
	}

	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		Document body = requestInfo.getBody();
		String guid = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.GUID_TAG);
		String deviceId = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.DEVICE_ID_TAG);
		boolean pushSupported = Boolean.parseBoolean(XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.PUSH_SUPPORTED_TAG));
		String securityToken = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.SECURITY_TOKEN_TAG);
		return setPushsupportedParam(guid, deviceId, pushSupported, securityToken);
	}

	private ResponseInfo setPushsupportedParam(String guid, String deviceId, boolean pushSupported, String securityToken) throws Exception
	{
		String initGuid;
		if (StringHelper.isNotEmpty(guid))
		{
			initGuid = guid;
		}
		else
		{
			Connector initConnector = getConnectorByDeviceId(deviceId);
			initGuid = initConnector.getGuid();
		}
		final IdentificationContext identificationContext = IdentificationContext.createByConnectorUID(initGuid);
		ChangePushSupportedParamOperation operation = new ChangePushSupportedParamOperation(identificationContext);
		operation.initialize(initGuid);

		Connector connector = operation.execute(pushSupported, securityToken);
		if (StringHelper.isEmpty(securityToken))
		{
			info(String.format(INFO_FORMAT_STRING, connector.getGuid(), pushSupported));
		}
		else
		{
			info(String.format(INFO_FORMAT_STRING + TOKEN_SUFFIX, connector.getGuid(), pushSupported, securityToken));
		}
		return buildSuccessResponse();
	}

	private Connector getConnectorByDeviceId(String deviceId) throws Exception
	{
		List<Connector> connectorList = Connector.findByDeviceId(deviceId);
		Connector connector = SyncUtil.getMoreActualConnector(connectorList);
		if (connector == null)
			throw new IllegalArgumentException("Коннектор, соотвествующий устройству с идентефикатором " + deviceId + ", не найден");
		return connector;
	}
}

