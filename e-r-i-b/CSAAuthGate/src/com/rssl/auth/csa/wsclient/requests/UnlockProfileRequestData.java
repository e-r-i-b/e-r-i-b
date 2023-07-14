package com.rssl.auth.csa.wsclient.requests;

import com.rssl.auth.csa.wsclient.RequestConstants;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author akrenev
 * @ created 07.11.13
 * @ $Author$
 * @ $Revision$
 *
 * Билдер запроса на снятие блокировки клиента
 */

public class UnlockProfileRequestData extends RequestDataBase
{
	private static final String REQUEST_TYPE = "unlockProfileRq";
	private UserInfo userInfo;

	/**
	 * конструктор
	 * @param userInfo информация о разблокируемом клиенте
	 */
	public UnlockProfileRequestData(UserInfo userInfo)
	{
		this.userInfo = userInfo;
	}

	public String getName()
	{
		return REQUEST_TYPE;
	}

	public Document getBody()
	{
		Document request = createRequest();
		Element root = request.getDocumentElement();
		XmlHelper.appendSimpleElement(root, RequestConstants.FIRSTNAME_PARAM_NAME, userInfo.getFirstname());
		XmlHelper.appendSimpleElement(root, RequestConstants.SURNAME_PARAM_NAME, userInfo.getSurname());
		XmlHelper.appendSimpleElement(root, RequestConstants.PATRNAME_PARAM_NAME, userInfo.getPatrname());
		XmlHelper.appendSimpleElement(root, RequestConstants.TB_TAG, userInfo.getTb());
		XmlHelper.appendSimpleElement(root, RequestConstants.PASSPORT_PARAM_NAME, userInfo.getPassport());
		XmlHelper.appendSimpleElement(root, RequestConstants.BIRTHDATE_PARAM_NAME, XMLDatatypeHelper.formatDateTime(userInfo.getBirthdate()));
		return request;
	}
}
