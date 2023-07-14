package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.Config;
import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.exceptions.ConnectorNotFoundException;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.LogableResponseInfo;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.ConnectorType;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.auth.csa.back.servises.Session;
import com.rssl.auth.csa.back.servises.connectors.CSAConnector;
import com.rssl.auth.csa.back.servises.nodes.CreateProfileNodeMode;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.auth.csa.back.servises.operations.LogIdentificationContext;
import com.rssl.auth.csa.back.servises.operations.UserLogonOperation;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.security.RegistrationStatus;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.collections.CollectionUtils;
import org.w3c.dom.Document;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author krenev
 * @ created 15.10.2012
 * @ $Author$
 * @ $Revision$
 * ���������� ������� �� �������������� ������������
 *
 * �������� �������:
 * OUID		                    �������� �����. 	[1]
 *
 * ��������� ������:
 * blockingTimeout              ����� ���������� �� ������                                  [0..1]
 *                              ���������� � ��� � ������ ���� ������
 *                              FailureResponceHelper.ERROR_CODE_CONNECTOR_BLOCKED
 * SessionInfo                  ���������� � ������                                         [0..1]
 *      SID		                ������������� �������� ������.                              [1]
 *      creationDate            ���� �������� ������                                        [1]
 *      expireDate              ���� ���������� ������ (����� ��������� ���� ����
 *                              ������� �� ������ �� ����� �����������)                     [1]
 *      prevSessionDate         ���� �������� ���������� ������                             [0..1]
 *      prevSID                 ������������� ���������� ������                             [0..1]
 * UserInfo		                ���������� � ������������                                   [0..1]
 *      firstname               ��� ������������                                            [1]
 *      patrname                �������� ������������                                       [0..1]
 *      surname                 ������� ������������                                        [1]
 *      birthdate               ���� �������� ������������                                  [1]
 *      passport                ��� ������������                                            [1]
 * ConnectorInfo                ���������� � ����������                                     [0..1]
 *      GUID                    ������������� ����������                                     [1]
 *      deviceState             ���������� ��������� ����������                             [0..1]
 *      deviceInfo              ���������� �� ����������                                    [0..1]
 *      cbCode                  ������������� ������������                                  [1]
 *      userId                  �������� ����� ������������                                 [1]
 *      cardNumber              "����� �����"                                               [1]
 *      login                   �����/�����                                                 [0..1]
 *      type                    ��� ����������(TERMINAL, CSA, MAPI)                             [1]
 *      creationDate            ���� �������� ����������                                    [1]
 *      passwordCreationDate    ���� �������� ������                                        [0..1]
 */
public class FinishCreateSessionRequestProcessor extends LogableProcessorBase
{
	public static final String REQUEST_TYPE = "finishCreateSessionRq";
	public static final String RESPONCE_TYPE = "finishCreateSessionRs";

	public boolean isAccessStandIn()
	{
		return true;
	}

	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	protected String getResponceType()
	{
		return RESPONCE_TYPE;
	}

	@Override
	protected LogableResponseInfo processRequest(RequestInfo requestInfo, IdentificationContext context) throws Exception
	{
		Document body = requestInfo.getBody();
		String ouid = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.OUID_TAG);
		return createSession(ouid, context);
	}

	@Override
	protected LogIdentificationContext getIdentificationContext(RequestInfo requestInfo) throws Exception
	{
		Document body = requestInfo.getBody();
		String ouid = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.OUID_TAG);
		return LogIdentificationContext.createByOperationUID(ouid);
	}

	private LogableResponseInfo createSession(String ouid, final IdentificationContext identificationContext) throws Exception
	{
		final UserLogonOperation operation = identificationContext.findOperation(UserLogonOperation.class, ouid, UserLogonOperation.getLifeTime());
		try
		{
			trace("������ �� ���� " + operation.getOuid() + " ������� ��������. ��������� ��.");
			Session session = operation.execute();
			trace("������ �� ���� " + operation.getOuid() + " ������� ���������.");

			RegistrationStatus registrationStatus = RegistrationStatus.NOT_EXIST;

			Pattern denyPattern = ConfigFactory.getConfig(Config.class).getUserRegistrationCbCodeDenyPattern();
			if (denyPattern != null && denyPattern.matcher(operation.getCbCode()).matches())
			{
				registrationStatus = RegistrationStatus.OFF;
				return new LogableResponseInfo(buildSuccessResponse(session, registrationStatus, operation.getRSAData()));
			}

			Connector connector = operation.getConnector(null);
			if (connector.getType() == ConnectorType.CSA || (connector.getType() == ConnectorType.TERMINAL
			          && CollectionUtils.isNotEmpty(CSAConnector.findByProfileID(operation.getProfileId()))))
			{
				registrationStatus = RegistrationStatus.EXIST;
				return new LogableResponseInfo(buildSuccessResponse(session, registrationStatus, operation.getRSAData()));
			}

			return new LogableResponseInfo(buildSuccessResponse(session, registrationStatus, operation.getRSAData()));
		}
		catch (ConnectorNotFoundException e)
		{
			error("������ �������� ������", e);
			return new LogableResponseInfo(getFailureResponseBuilder().buildBadOUIDResponse(), e);
		}
	}

	private ResponseInfo buildSuccessResponse(Session session, RegistrationStatus registrationStatus, Map<String, String> rsaData) throws Exception
	{
		Connector connector = session.getConnector();
		Profile profile = connector.getProfile();
		return getSuccessResponseBuilder()
				.addSessionInfo(session)
				.addUserInfo(profile)
				.addProfileId(profile)
				.addConnectorInfo(connector, registrationStatus)
				.addSecurityType(connector)
				.addProfileNodeInfo(Utils.getActiveProfileNode(profile.getId(), CreateProfileNodeMode.CREATION_ALLOWED_FOR_ALL_NODES))
				.addRsaData(rsaData)
				.end().getResponceInfo();
	}
}
