package com.rssl.auth.csa.wsclient.requests.clients;

import com.rssl.auth.csa.wsclient.requests.UserInfoRequestDataBase;
import com.rssl.phizic.gate.csa.UserInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author akrenev
 * @ created 30.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * Билдер запроса на получение состояния клиента
 */

public class GetClientNodeStateRequestData extends UserInfoRequestDataBase
{
	private static final String REQUEST_NAME = "getClientNodeStateRq";

	private final UserInfo userInfo;

	/**
	 * конструктор
	 * @param userInfo информация о клиенте
	 */
	public GetClientNodeStateRequestData(UserInfo userInfo)
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
		fillUserInfo(root, userInfo);
		return request;
	}
}
