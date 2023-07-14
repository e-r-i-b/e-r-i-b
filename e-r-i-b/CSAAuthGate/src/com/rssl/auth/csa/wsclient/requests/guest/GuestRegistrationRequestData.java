package com.rssl.auth.csa.wsclient.requests.guest;

import com.rssl.auth.csa.wsclient.RequestConstants;
import com.rssl.auth.csa.wsclient.requests.UserInfoRequestDataBase;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import static com.rssl.auth.csa.wsclient.RequestConstants.*;

/**
 * Данные для запроса на регистрацию гостя
 * @author niculichev
 * @ created 31.01.15
 * @ $Author$
 * @ $Revision$
 */
public class GuestRegistrationRequestData extends UserInfoRequestDataBase
{
	public static final String REQUEST_NAME = "guestRegistrationRq";
	private String phone;
	private String login;
	private String password;
	private Long code;
	private Long nodeNumber;
	private UserInfo userInfo;

	/**
	 * ctor
	 * @param phone номер телефона
	 * @param code код гостя
	 * @param login логин
	 * @param password пароль
	 * @param nodeNumber номер блока
	 * @param userInfo информация о госте
	 */
	public GuestRegistrationRequestData(String phone, Long code, String login, String password, Long nodeNumber, UserInfo userInfo)
	{
		this.phone = phone;
		this.login = login;
		this.password = password;
		this.code = code;
		this.nodeNumber = nodeNumber;
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

		root.appendChild(createTag(request, PHONE_NUMBER_PARAM_NAME, phone));
		root.appendChild(createTag(request, LOGIN_PARAM_NAME, login));
		root.appendChild(createTag(request, PASSWORD_PARAM_NAME, password));
		root.appendChild(createTag(request, GUEST_CODE, StringHelper.getEmptyIfNull(code)));
		root.appendChild(createTag(request, NODE_ID_TAG, StringHelper.getEmptyIfNull(nodeNumber)));

		fillUserInfo(root, userInfo);

		return request;
	}
	@Override
	protected void fillUserInfo(Element element, UserInfo userInfo)
	{
		XmlHelper.appendSimpleElement(element, RequestConstants.FIRSTNAME_PARAM_NAME, userInfo.getFirstname());
		XmlHelper.appendSimpleElement(element, RequestConstants.SURNAME_PARAM_NAME, userInfo.getSurname());
		XmlHelper.appendSimpleElement(element, RequestConstants.PATRNAME_PARAM_NAME, userInfo.getPatrname());
		XmlHelper.appendSimpleElement(element, RequestConstants.CB_CODE_PARAM_NAME, StringHelper.isEmpty(userInfo.getCbCode()) ? userInfo.getTb() : userInfo.getCbCode());
		XmlHelper.appendSimpleElement(element, RequestConstants.PASSPORT_PARAM_NAME, userInfo.getPassport());
		if (userInfo.getBirthdate() != null)
			XmlHelper.appendSimpleElement(element, RequestConstants.BIRTHDATE_PARAM_NAME, XMLDatatypeHelper.formatDateTimeWithoutTimeZone(userInfo.getBirthdate()));
	}
}
