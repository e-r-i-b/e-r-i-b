package com.rssl.auth.csa.wsclient.requests;

import com.rssl.auth.csa.wsclient.RequestConstants;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Запрос на информацию о текущих коннекторах ЕРМБ
 * @author Puzikov
 * @ created 21.03.14
 * @ $Author$
 * @ $Revision$
 */

public class GetErmbConnectorInfoRequestData extends UserInfoRequestDataBase
{
	public static final String REQUEST_NAME = "getErmbConnectorInfoRq";

	private UserInfo userInfo;

	/**
	 * ctor
	 * @param userInfo информация о клиенте
	 */
	public GetErmbConnectorInfoRequestData(UserInfo userInfo)
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
		fillUserInfo(XmlHelper.appendSimpleElement(root, RequestConstants.PROFILE_INFO_TAG), userInfo);

		return request;
	}
}
