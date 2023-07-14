package com.rssl.auth.csa.wsclient.requests;

import com.rssl.auth.csa.wsclient.RequestConstants;
import com.rssl.auth.csa.wsclient.requests.profile.lock.LockProfileRequestDataBase;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 07.11.13
 * @ $Author$
 * @ $Revision$
 *
 * Билдер запроса на блокировку клиента
 */

public class LockProfileRequestData extends LockProfileRequestDataBase
{
	private static final String REQUEST_NAME = "lockProfileRq";

	private UserInfo userInfo;

	/**
	 * конструктор
	 * @param userInfo информация о блокируемом клиенте
	 * @param lockFrom начало блокировки
	 * @param lockTo окончание блокировки
	 * @param reason причина блокировки
	 * @param blockerFIO фио блокирующего сотрудника
	 */
	public LockProfileRequestData(UserInfo userInfo, Calendar lockFrom, Calendar lockTo, String reason, String blockerFIO)
	{
		super(lockFrom, lockTo, reason, blockerFIO);

		this.userInfo = userInfo;
	}

	public String getName()
	{
		return REQUEST_NAME;
	}

	@Override
	protected void addUserInfo(Element element)
	{
		XmlHelper.appendSimpleElement(element, RequestConstants.FIRSTNAME_PARAM_NAME, userInfo.getFirstname());
		XmlHelper.appendSimpleElement(element, RequestConstants.SURNAME_PARAM_NAME, userInfo.getSurname());
		XmlHelper.appendSimpleElement(element, RequestConstants.PATRNAME_PARAM_NAME, userInfo.getPatrname());
		XmlHelper.appendSimpleElement(element, RequestConstants.TB_TAG, userInfo.getTb());
		XmlHelper.appendSimpleElement(element, RequestConstants.PASSPORT_PARAM_NAME, userInfo.getPassport());
		XmlHelper.appendSimpleElement(element, RequestConstants.BIRTHDATE_PARAM_NAME, XMLDatatypeHelper.formatDateTime(userInfo.getBirthdate()));
	}
}
