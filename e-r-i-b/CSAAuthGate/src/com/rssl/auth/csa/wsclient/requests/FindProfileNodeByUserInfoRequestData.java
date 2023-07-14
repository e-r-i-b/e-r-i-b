package com.rssl.auth.csa.wsclient.requests;

import com.rssl.auth.csa.back.servises.nodes.CreateProfileNodeMode;
import com.rssl.auth.csa.wsclient.RequestConstants;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

/**
 * @author osminin
 * @ created 19.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Запрос на получение информации о блоке пользователя по ФИО ДУЛ ДР ТБ
 */
public class FindProfileNodeByUserInfoRequestData extends UserInfoRequestDataBase
{
	private static final String REQUEST_DATA_NAME = "findProfileNodeByUserInfoRq";

	private UserInfo userInfo;
	private boolean needCreateProfile;
	private CreateProfileNodeMode createProfileNodeMode;

	/**
	 * ctor
	 * @param userInfo информация о пользователе
	 */
	public FindProfileNodeByUserInfoRequestData(UserInfo userInfo, boolean needCreateProfile, CreateProfileNodeMode createProfileNodeMode)
	{
		if (userInfo == null)
		{
			throw new IllegalArgumentException("информация о пользователе не может быть null");
		}

		if (createProfileNodeMode == null)
		{
			throw new IllegalArgumentException("информация о типе создания профиля не может быть null");
		}

		this.userInfo = userInfo;
		this.needCreateProfile = needCreateProfile;
		this.createProfileNodeMode = createProfileNodeMode;
	}

	public String getName()
	{
		return REQUEST_DATA_NAME;
	}

	public Document getBody()
	{
		Document document = createRequest();
		fillUserInfo(document.getDocumentElement(), userInfo);
		XmlHelper.appendSimpleElement(document.getDocumentElement(), RequestConstants.NEED_CREATE_PROFILE, Boolean.toString(needCreateProfile));
		XmlHelper.appendSimpleElement(document.getDocumentElement(), RequestConstants.CREATE_PROFILE_NODE_MODE, createProfileNodeMode.name());

		return document;
	}
}
