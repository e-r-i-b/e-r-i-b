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
 * Билдер запроса на установку лока на профиль для исполнения документа сотрудником
 */

public class LockProfileForExecuteDocumentRequestData extends UserInfoRequestDataBase
{
	private static final String REQUEST_NAME = "lockProfileForExecuteDocumentRq";

	private final UserInfo userInfo;

	/**
	 * конструктор
	 * @param userInfo информация о клиенте
	 */
	public LockProfileForExecuteDocumentRequestData(UserInfo userInfo)
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
