package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.Config;
import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.auth.csa.back.servises.nodes.CreateProfileNodeMode;
import com.rssl.auth.csa.back.servises.nodes.ProfileNode;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author osminin
 * @ created 19.10.13
 * @ $Author$
 * @ $Revision$
 *
 * ���������� ������� �� ��������� ���������� � ����� ������������ �� ��� ��� �� ��
 *
 * ��������� �������:
 * firstname               ��� ������������                                            [1]
 * patrname                �������� ������������                                       [1]
 * surname                 ������� ������������                                        [1]
 * birthdate               ���� �������� ������������                                  [1]
 * passport                ��� ������������                                            [1]
 * cbCode                  ������������� ������������                                  [1]
 * needCreateProfile       ���������� �� ��������� �������                             [1]
 * createProfileNodeMode   ��� � ������� ��������� �������                             [1]
 *
 * ��������� ������:
 * nodeInfo                         ���������� �� ����                                          [1]
 *      host                        ����                                                        [1]
 *      smsQueueName                ������������ ������� ��� ������                             [1]
 *      smsFactoryName              ������������ ������� ��� ������                             [1]
 *      serviceProfileQueueName     ������������ ������� ���������� ������ (ConfirmProfilesRq)  [1]
 *      serviceProfileFactoryName   ������������ ������� ���������� ������ (ConfirmProfilesRq)  [1]
 *      serviceClientQueueName      ������������ ������� ���������� ������ (UpdateClientRq)     [1]
 *      serviceClientFactoryName    ������������ ������� ���������� ������ (UpdateClientRq)     [1]
 *      serviceResourceQueueName    ������������ ������� ���������� ������ (UpdateResourceRq)   [1]
 *      serviceResourceFactoryName  ������������ ������� ���������� ������ (UpdateResourceRq)   [1]
 *
 */
public class FindProfileNodeByUserInfoRequestProcessor extends RequestProcessorBase
{
	private static final String RESPONSE_TYPE   = "findProfileNodeByUserInfoRs";
	private static final String REQUEST_TYPE    = "findProfileNodeByUserInfoRq";

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
		boolean needCreateProfile = Boolean.parseBoolean(XmlHelper.getSimpleElementValue(element, Constants.NEED_CREATE_PROFILE));
		CreateProfileNodeMode createProfileNodeMode = CreateProfileNodeMode.valueOf(XmlHelper.getSimpleElementValue(element, Constants.CREATE_PROFILE_NODE_MODE));

		CSAUserInfo userInfo = fillUserInfo(element);
		userInfo.setCbCode(formatCbCode(userInfo.getCbCode()));
		Profile profile = Profile.getByUserInfo(userInfo, true);
		Config config = ConfigFactory.getConfig(Config.class);

		if (profile == null && (!needCreateProfile || config.isStandInMode()))
		{
			return getFailureResponseBuilder().buildUserNotFoundResponse(userInfo);
		}
		else if (profile == null)
		{
			profile = Profile.create(userInfo);
		}

		ProfileNode profileNode = Utils.getActiveProfileNode(profile.getId(), createProfileNodeMode);
		if (profileNode == null)
			return getFailureResponseBuilder().buildUserNotFoundResponse(userInfo);
		return getSuccessResponseBuilder().addNodeInfo(profileNode).end();
	}

	/**
	 * �������������� ���� ��������.
	 * ���������� 2 �������.
	 * ���� ��� ������� �� 1 �������, �� ����� ��������� �� 2 �����,
	 * ���� �� 3 � ����� ��������, �� ����� ���������� 2 ������� ������,
	 * ���� �� ����, �� 2 �������
	 * @param cbCode ��� ��������
	 * @return ����������������� ��� ��������
	 */
	private String formatCbCode(String cbCode)
	{
		if (StringHelper.isNotEmpty(cbCode))
		{
			cbCode = StringHelper.addLeadingZeros(cbCode, 2);
			cbCode = cbCode.substring(cbCode.length() - 2);
		}
		return cbCode;
	}
}
