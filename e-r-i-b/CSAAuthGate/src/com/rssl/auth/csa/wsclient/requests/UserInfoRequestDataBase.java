package com.rssl.auth.csa.wsclient.requests;

import com.rssl.auth.csa.wsclient.RequestConstants;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

/**
 * @author osminin
 * @ created 19.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Базовый класс для запросов по ФИО ДУЛ ДР ТБ
 */
public abstract class UserInfoRequestDataBase extends RequestDataBase
{
	protected void fillUserInfo(Element element, UserInfo userInfo)
	{
		XmlHelper.appendSimpleElement(element, RequestConstants.FIRSTNAME_PARAM_NAME, userInfo.getFirstname());
		XmlHelper.appendSimpleElement(element, RequestConstants.SURNAME_PARAM_NAME, userInfo.getSurname());
		XmlHelper.appendSimpleElement(element, RequestConstants.PATRNAME_PARAM_NAME, userInfo.getPatrname());
		XmlHelper.appendSimpleElement(element, RequestConstants.CB_CODE_PARAM_NAME, StringHelper.isEmpty(userInfo.getCbCode()) ? userInfo.getTb() : userInfo.getCbCode());
		XmlHelper.appendSimpleElement(element, RequestConstants.PASSPORT_PARAM_NAME, userInfo.getPassport());
		XmlHelper.appendSimpleElement(element, RequestConstants.BIRTHDATE_PARAM_NAME, XMLDatatypeHelper.formatDateTimeWithoutTimeZone(userInfo.getBirthdate()));
	}
}
