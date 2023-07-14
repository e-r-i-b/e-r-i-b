package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.ConnectorType;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.auth.csa.back.servises.connectors.*;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

import java.util.List;

/**
 * @author EgorovaA
 * @ created 21.10.13
 * @ $Author$
 * @ $Revision$
 *
 * ���������� ������� �� ��������� ��������� ����������� getIncognitoSettingRq
 *
 * ��������� �������:
 * SID		                ������������� ������      [1]
 * ����
 * UserInfo                     ���������� � ������������                                   [1]
 *      firstname               ��� ������������                                            [1]
 *      patrname                �������� ������������                                       [0..1]
 *      surname                 ������� ������������                                        [1]
 *      birthdate               ���� �������� ������������                                  [1]
 *      passport                ��� ������������                                            [1]
 *      tb                      �� ������������                                             [1]
 *
 * type                         ��� ������������� �����������. ���� ��������� - ���         [0..1]
 */
public class GetClientConnectorsProcessor extends RequestProcessorBase
{
	public static final String REQUEST_TYPE = "getClientConnectorsRq";
	public static final String RESPONCE_TYPE = "getClientConnectorsRs";

	protected String getResponceType()
	{
		return RESPONCE_TYPE;
	}

	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	@Override
	public boolean isAccessStandIn()
	{
		return true;
	}

	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		final IdentificationContext identificationContext = getIdentificationContextByUserInfoOrSID(requestInfo);
		Element root = requestInfo.getBody().getDocumentElement();
		String connectorTypeSrt = XmlHelper.getSimpleElementValue(root, Constants.TYPE_TAG);
		ConnectorType connectorType = null;
		if (StringHelper.isNotEmpty(connectorTypeSrt))
		{
			connectorType = ConnectorType.valueOf(connectorTypeSrt);
		}
		return getClientConnectors(identificationContext, connectorType);
	}

	private ResponseInfo getClientConnectors(IdentificationContext identificationContext, ConnectorType connectorType) throws Exception
	{
		Profile profile = identificationContext.getProfile();

		List<? extends Connector> connectors = null;
		if (connectorType == null)
		{
			connectors = Connector.findNotClosedByProfileID(profile.getId());
		}
		else
		{
			switch (connectorType)
			{
				case ATM:
					connectors = ATMConnector.findNotClosedByProfileID(profile.getId());
					break;
				case CSA:
					connectors = CSAConnector.findNotClosedByProfileID(profile.getId());
					break;
				case DISPOSABLE:
					connectors = DisposableConnector.findNotClosedByProfileID(profile.getId());
					break;
				case ERMB:
					connectors = ERMBConnector.findNotClosedByProfileID(profile.getId());
					break;
				case MAPI:
					connectors = MAPIConnector.findNotClosedByProfileID(profile.getId());
					break;
				case SOCIAL:
					connectors = SocialAPIConnector.findNotClosedByProfileID(profile.getId());
					break;
				case TERMINAL:
					connectors = TerminalConnector.findNotClosedByProfileID(profile.getId());
					break;
				default:
					throw new IllegalArgumentException("������������ ��� ���������� " + connectorType);
			}
		}

		return getSuccessResponseBuilder().addConnectorsInfo(connectors).end();
	}
}
