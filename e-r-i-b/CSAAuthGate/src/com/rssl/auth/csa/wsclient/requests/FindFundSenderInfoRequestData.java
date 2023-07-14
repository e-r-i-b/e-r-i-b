package com.rssl.auth.csa.wsclient.requests;

import com.rssl.phizic.gate.csa.UserInfo;
import org.w3c.dom.Document;

/**
 * @author osminin
 * @ created 10.12.14
 * @ $Author$
 * @ $Revision$
 *
 * Запрос на получение информации о получателе запроса на сбор средств по ФИО ДУЛ ДР ТБ
 */
public class FindFundSenderInfoRequestData extends UserInfoRequestDataBase
{
	private static final String REQUEST_DATA_NAME = "findFundSenderInfoRq";

	private UserInfo userInfo;

	/**
	 * ctor
	 * @param userInfo информация о пользователе
	 */
	public FindFundSenderInfoRequestData(UserInfo userInfo)
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
