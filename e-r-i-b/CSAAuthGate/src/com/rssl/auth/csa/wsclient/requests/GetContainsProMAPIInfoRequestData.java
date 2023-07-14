package com.rssl.auth.csa.wsclient.requests;

import com.rssl.phizic.gate.csa.UserInfo;
import org.w3c.dom.Document;

/**
 * @author osminin
 * @ created 16.04.15
 * @ $Author$
 * @ $Revision$
 *
 * запрос на получение информации о наличии мАпи ПРо-версии у клиента
 */
public class GetContainsProMAPIInfoRequestData extends UserInfoRequestDataBase
{
	private static final String REQUEST_DATA_NAME = "getContainsProMAPIInfoRq";

	private UserInfo userInfo;

	/**
	 * ctor
	 * @param userInfo информация о пользователе
	 */
	public GetContainsProMAPIInfoRequestData(UserInfo userInfo)
	{
		if (userInfo == null)
		{
			throw new IllegalArgumentException("Информация о пользователе не может быть null.");
		}
		this.userInfo = userInfo;
	}

	public String getName()
	{
		return REQUEST_DATA_NAME;
	}

	public Document getBody()
	{
		Document document = createRequest();
		fillUserInfo(document.getDocumentElement(), userInfo);

		return document;
	}
}
