package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.auth.csa.back.servises.nodes.CreateProfileNodeMode;
import com.rssl.auth.csa.back.servises.nodes.ProfileNode;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author osminin
 * @ created 20.10.13
 * @ $Author$
 * @ $Revision$
 *
 * ���������� ������� �� ���������� �������
 *
 * ��������� �������:
 * newUserInfo	                    ����� ���������� ������������                               [1]
 *      firstname                   ��� ������������                                            [1]
 *      patrname                    �������� ������������                                       [0..1]
 *      surname                     ������� ������������                                        [1]
 *      birthdate                   ���� �������� ������������                                  [1]
 *      passport                    ��� ������������                                            [1]
 *      cbCode                      ������������� ������������                                  [1]
 * oldUserInfo	                    ������� ���������� � ������������                           [1]
 *      firstname                   ��� ������������                                            [1]
 *      patrname                    �������� ������������                                       [0..1]
 *      surname                     ������� ������������                                        [1]
 *      birthdate                   ���� �������� ������������                                  [1]
 *      passport                    ��� ������������                                            [1]
 *      cbCode                      ������������� ������������                                  [1]
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
public class UpdateProfileRequestProcessor extends RequestProcessorBase
{
	private static final String RESPONSE_TYPE = "updateProfileRs";
	private static final String REQUEST_TYPE  = "updateProfileRq";

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
	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		Document document = requestInfo.getBody();
		Element element = document.getDocumentElement();
		Element newUserInfoElement = XmlHelper.selectSingleNode(element, Constants.NEW_USER_INFO_TAG);
		Element oldUserInfoElement = XmlHelper.selectSingleNode(element, Constants.OLD_USER_INFO_TAG);

		CSAUserInfo newUserInfo = fillUserInfo(newUserInfoElement);
		CSAUserInfo oldUserInfo = fillUserInfo(oldUserInfoElement);

		return doRequest(newUserInfo, oldUserInfo);
	}

	private ResponseInfo doRequest(CSAUserInfo newUserInfo, CSAUserInfo oldUserInfo) throws Exception
	{
		Profile profile = updateProfile(newUserInfo, oldUserInfo);
		ProfileNode profileNode = Utils.getActiveProfileNode(profile.getId(), CreateProfileNodeMode.CREATION_ALLOWED_FOR_ALL_NODES);

		return getSuccessResponseBuilder().addNodeInfo(profileNode).end();
	}

	private Profile updateProfile(CSAUserInfo newUserInfo, CSAUserInfo oldUserInfo) throws Exception
	{
		//���� ������������ ����� ����� ������ ������ � ����, �� ��� ������� ��� ��������
		Profile newProfile = Profile.getByUserInfo(newUserInfo, true);
		if (newProfile == null)
		{
			//����� ��������� �������
			Profile oldProfile = Profile.getByUserInfo(oldUserInfo, true);
			oldProfile.update(newUserInfo);
			return oldProfile;
		}
		return newProfile;
	}
}
