package com.rssl.auth.csa.wsclient.requests;

import com.rssl.auth.csa.utils.UpdateProfileInfo;
import com.rssl.auth.csa.wsclient.RequestConstants;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.collections.CollectionUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;

/**
 * @author osminin
 * @ created 05.11.13
 * @ $Author$
 * @ $Revision$
 *
 * Запрос на получение списка блоков по списку обновленных профилей
 */
public class FindNodesByUpdatedProfilesRequestData extends UserInfoRequestDataBase
{
	private static final String REQUEST_DATA_NAME = "findNodesByUpdatedProfilesRq";

	private List<UpdateProfileInfo> updateProfileInfoList;

	/**
	 * ctor
	 * @param updateProfileInfoList список обновленных профилей
	 */
	public FindNodesByUpdatedProfilesRequestData(List<UpdateProfileInfo> updateProfileInfoList)
	{
		this.updateProfileInfoList = updateProfileInfoList;
	}

	public String getName()
	{
		return REQUEST_DATA_NAME;
	}

	public Document getBody()
	{
		Document document = createRequest();
		Element root = document.getDocumentElement();
		for (UpdateProfileInfo profileInfo : updateProfileInfoList)
		{
			Element profileInfoElement = XmlHelper.appendSimpleElement(root, RequestConstants.UPDATE_PROFILE_INFO_TAG);
			Element newUserInfo = XmlHelper.appendSimpleElement(profileInfoElement, RequestConstants.NEW_USER_INFO_TAG);
			fillUserInfo(newUserInfo, profileInfo.getNewUserInfo());

			if (CollectionUtils.isNotEmpty(profileInfo.getOldUserInfoList()))
			{
				Element oldUserInfoList = XmlHelper.appendSimpleElement(profileInfoElement, RequestConstants.OLD_USER_INFO_LIST_TAG);
				for (UserInfo info: profileInfo.getOldUserInfoList())
				{
					Element oldUserInfo = XmlHelper.appendSimpleElement(oldUserInfoList, RequestConstants.OLD_USER_INFO_TAG);
					fillUserInfo(oldUserInfo, info);
				}
			}
		}

		return document;
	}
}
