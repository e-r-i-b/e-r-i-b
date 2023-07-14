package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.exceptions.ClientNotFoundException;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.auth.csa.back.servises.ProfileHistory;
import com.rssl.auth.csa.back.servises.SecurityTypeHelper;
import com.rssl.phizic.common.types.security.SecurityType;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.hibernate.LockMode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * @author osminin
 * @ created 05.02.14
 * @ $Author$
 * @ $Revision$
 *
 * ���������� ������� �� ���������� ������� ������ ������������ �������
 *
 * ��������� �������:
 *      firstname               ��� ������������                                            [1]
 *      patrname                �������� ������������                                       [1]
 *      surname                 ������� ������������                                        [1]
 *      birthdate               ���� �������� ������������                                  [1]
 *      passport                ��� ������������                                            [1]
 *      cbCode                  ������������� ������������                                  [1]
 */
public class LowerProfileSecurityTypeRequestProcessor extends RequestProcessorBase
{
	private static final String RESPONSE_TYPE = "lowerProfileSecurityTypeRs";
	private static final String REQUEST_TYPE  = "lowerProfileSecurityTypeRq";

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
		List<CSAUserInfo> userInfoHistory = getUserInfoHistory(XmlHelper.selectSingleNode(document.getDocumentElement(), Constants.USER_INFO_LIST_TAG));

		for (CSAUserInfo userInfo : userInfoHistory)
		{
			List<ProfileHistory> profileHistoryList = ProfileHistory.getFullHistoryFor(userInfo);
			if (profileHistoryList.size() > 0)
			{
				ProfileHistory profileHistory = profileHistoryList.get(0);
				Profile profile = Profile.findById(profileHistory.getProfileId(), LockMode.UPGRADE_NOWAIT);
				profile.setSecurityType(SecurityType.LOW);
				profile.save();

				SecurityTypeHelper.logSecurityType(SecurityTypeHelper.LOW_SECURITY_TYPE_DESCRIPTION_CONFIRM_CC);
				return buildSuccessResponse();
			}
		}

		throw new ClientNotFoundException("������ �� ������� ��������� ������ �� ������.");
	}

	private List<CSAUserInfo> getUserInfoHistory(Element history) throws Exception
	{
		final List<CSAUserInfo> userInfoHistory = new ArrayList<CSAUserInfo>();
		XmlHelper.foreach(history, Constants.USER_INFO_TAG, new ForeachElementAction()
		{
			public void execute(Element element) throws Exception
			{
				userInfoHistory.add(fillUserInfo(element));
			}
		});
		return userInfoHistory;
	}
}
