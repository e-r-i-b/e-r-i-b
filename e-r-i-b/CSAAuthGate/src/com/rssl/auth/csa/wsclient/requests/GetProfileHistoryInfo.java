package com.rssl.auth.csa.wsclient.requests;

import com.rssl.phizic.gate.csa.UserInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Запрос на получение истории изменения данных пользователя.
 *
 * @author bogdanov
 * @ created 25.10.13
 * @ $Author$
 * @ $Revision$
 */

public class GetProfileHistoryInfo extends UserInfoRequestDataBase
{
	public static final String REQUEST_NAME = "getProfileHistoryInfoRq";

		private final UserInfo userInfo;

		public GetProfileHistoryInfo(UserInfo userInfo)
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
