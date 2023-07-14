package com.rssl.auth.csa.wsclient.requests.profile.incognito;

import com.rssl.auth.csa.wsclient.RequestConstants;
import com.rssl.auth.csa.wsclient.requests.RequestDataBase;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Класс запроса на получение настройки приватности клиента в CSABack
 *
 * @author EgorovaA
 * @ created 18.10.13
 * @ $Author$
 * @ $Revision$
 */
public class GetIncognitoSettingData extends RequestDataBase
{
	public static final String REQUEST_NAME = "getIncognitoSettingRq";
	private UserInfo userInfo;

	private String sid;

	public GetIncognitoSettingData(String sid)
	{
		this.sid = sid;
	}

	public GetIncognitoSettingData(UserInfo userInfo)
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

		if (sid != null)
		{
			root.appendChild(createTag(request, RequestConstants.SID_PARAM_NAME, sid));
		}
		else if (userInfo != null)
		{
			fillUserInfo(XmlHelper.appendSimpleElement(root, RequestConstants.USER_INFO_TAG), userInfo);
		}
		else
		{
			throw new IllegalStateException("Должен быть задан или SID или USER INFO");
		}

		return request;
	}

	protected void fillUserInfo(Element element, UserInfo userInfo)
	{
		XmlHelper.appendSimpleElement(element, RequestConstants.FIRSTNAME_PARAM_NAME, userInfo.getFirstname());
		XmlHelper.appendSimpleElement(element, RequestConstants.SURNAME_PARAM_NAME, userInfo.getSurname());
		XmlHelper.appendSimpleElement(element, RequestConstants.PATRNAME_PARAM_NAME, userInfo.getPatrname());
		XmlHelper.appendSimpleElement(element, RequestConstants.TB_TAG, userInfo.getCbCode());
		XmlHelper.appendSimpleElement(element, RequestConstants.PASSPORT_PARAM_NAME, userInfo.getPassport());
		XmlHelper.appendSimpleElement(element, RequestConstants.BIRTHDATE_PARAM_NAME, XMLDatatypeHelper.formatDateTimeWithoutTimeZone(userInfo.getBirthdate()));
	}
}
