package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.connectors.ERMBConnector;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Puzikov
 * @ created 21.03.14
 * @ $Author$
 * @ $Revision$
 *
 * ���������� ������� �� ���������� � ����������� ����
 *
 * ��������� �������:
 * profileInfo		            ���������� � ������������                                   [1]
 *      firstname               ��� ������������                                            [1]
 *      patrname                �������� ������������                                       [1]
 *      surname                 ������� ������������                                        [1]
 *      birthdate               ���� �������� ������������                                  [1]
 *      passport                ��� ������������                                            [1]
 *      cbCode                  ������������� ������������                                  [1]
 *
 * ��������� ������:
 * connected                    ���� �� �����������                                         [1]
 */

public class GetErmbConnectorInfoProcessor extends RequestProcessorBase
{
	public static final String REQUEST_TYPE = "getErmbConnectorInfoRq";
	public static final String RESPONSE_TYPE = "getErmbConnectorInfoRs";

	@Override
	protected String getResponceType()
	{
		return RESPONSE_TYPE;
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
	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		Document document = requestInfo.getBody();
		Element element = document.getDocumentElement();

		CSAUserInfo userInfo = getUserInfo(element);

		boolean existsERMB = ERMBConnector.isExistNotClosedByClientInAnyTB(userInfo);

		return getSuccessResponseBuilder().addParameter(Constants.ERMB_CONNECTED, existsERMB).end();
	}

	private CSAUserInfo getUserInfo(Element root) throws Exception
	{
		Element profileInfo = XmlHelper.selectSingleNode(root, Constants.PROFILE_INFO_TAG);

		return profileInfo == null ? null : fillUserInfo(profileInfo);
	}
}
