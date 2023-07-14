package com.rssl.auth.csa.wsclient.requests.profile.lock;

import com.rssl.auth.csa.wsclient.RequestConstants;
import com.rssl.auth.csa.wsclient.requests.UserInfoRequestDataBase;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author akrenev
 * @ created 14.10.2014
 * @ $Author$
 * @ $Revision$
 *
 * Билдер запроса на снятие лока с профиля после исполнения документа сотрудником
 */

public class UnlockProfileForExecuteDocumentRequestData extends UserInfoRequestDataBase
{
	private static final String REQUEST_NAME = "unlockProfileForExecuteDocumentRq";

	private final UserInfo userInfo;

	/**
	 * конструктор
	 * @param userInfo информация о клиенте
	 */
	public UnlockProfileForExecuteDocumentRequestData(UserInfo userInfo)
	{
		this.userInfo = userInfo;
	}

	public String getName()
	{
		return REQUEST_NAME;
	}

	public Document getBody()
	{
		Document request = createRequest();
		Element root = request.getDocumentElement();
		fillUserInfo(XmlHelper.appendSimpleElement(root, RequestConstants.USER_INFO_TAG), userInfo);
		return request;
	}
}
