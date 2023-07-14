package com.rssl.auth.csa.wsclient.requests.profile.incognito;

import com.rssl.auth.csa.wsclient.RequestConstants;
import com.rssl.auth.csa.wsclient.requests.RequestDataBase;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.gate.clients.IncognitoState;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Calendar;

/**
 * Класс запроса на изменение настройки приватности клиента в CSABack
 *
 * @author EgorovaA
 * @ created 18.10.13
 * @ $Author$
 * @ $Revision$
 */
public class ChangeIncognitoSettingData extends RequestDataBase
{
	public static final String REQUEST_NAME = "changeIncognitoSettingRq";

	private IdentificationMode identificationMode;
	private String sid;
	private UserInfo userInfo;
	private IncognitoState incognitoSetting;

	public ChangeIncognitoSettingData(String sid, boolean incognitoSetting)
	{
		identificationMode = IdentificationMode.SID;
		this.sid = sid;
		this.incognitoSetting = incognitoSetting? IncognitoState.incognito: IncognitoState.publicly;
	}

	public ChangeIncognitoSettingData(UserInfo userInfo, IncognitoState incognito)
	{
		identificationMode = IdentificationMode.USER_INFO;
		this.userInfo = userInfo;
		this.incognitoSetting = incognito;
	}

	public String getName()
	{
		return REQUEST_NAME;
	}

	public Document getBody()
	{
		Document request = createRequest();
		Element root = request.getDocumentElement();

		appendUserInfo(request, root);
		root.appendChild(createTag(request, RequestConstants.INCOGNITO_SETTING_PARAM_NAME, incognitoSetting.name()));

		return request;
	}

	private void appendUserInfo(Document request, Element root)
	{
		switch (identificationMode)
		{
			case SID:       fillUserInfo(request, root, sid);       break;
			case USER_INFO: fillUserInfo(request, root, userInfo);  break;
		}
	}

	private void fillUserInfo(Document request, Element root, UserInfo userInfo)
	{
		Element element = XmlHelper.appendSimpleElement(root, RequestConstants.USER_INFO_TAG);

		addTag(request, element, RequestConstants.FIRSTNAME_PARAM_NAME, userInfo.getFirstname());
		addTag(request, element, RequestConstants.SURNAME_PARAM_NAME, userInfo.getSurname());
		addTag(request, element, RequestConstants.PATRNAME_PARAM_NAME, userInfo.getPatrname());
		addTag(request, element, RequestConstants.TB_TAG, userInfo.getCbCode());
		addTag(request, element, RequestConstants.PASSPORT_PARAM_NAME, userInfo.getPassport());
		addTag(request, element, RequestConstants.BIRTHDATE_PARAM_NAME, userInfo.getBirthdate());
	}

	private void fillUserInfo(Document request, Element element, String sid)
	{
		addTag(request, element, RequestConstants.SID_PARAM_NAME, sid);
	}

	private void addTag(Document request, Element element, String tagName, String value)
	{
		element.appendChild(createTag(request, tagName, value));
	}

	private void addTag(Document request, Element element, String tagName, Calendar value)
	{
		element.appendChild(createTag(request, tagName, XMLDatatypeHelper.formatDateTimeWithoutTimeZone(value)));
	}

	private enum IdentificationMode
	{
		SID,
		USER_INFO
	}
}
