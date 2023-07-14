package com.rssl.auth.csa.wsclient.requests.profile.lock;

import com.rssl.auth.csa.wsclient.RequestConstants;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

import java.util.Calendar;

/**
 * @author khudyakov
 * @ created 30.06.15
 * @ $Author$
 * @ $Revision$
 */
public class LockProfileCHG071536ByProfileIdRequestData extends LockProfileRequestDataBase
{
	private static final String REQUEST_NAME = "lockProfileCHG071536ByProfileIdRq";

	private Long profileId;

	/**
	 * конструктор
	 * @param profileId идентификатор профиля в ЦСА
	 * @param lockFrom начало блокировки
	 * @param lockTo окончание блокировки
	 * @param reason причина блокировки
	 * @param blockerFIO фио блокирующего сотрудника
	 */
	public LockProfileCHG071536ByProfileIdRequestData(Long profileId, Calendar lockFrom, Calendar lockTo, String reason, String blockerFIO)
	{
		super(lockFrom, lockTo, reason, blockerFIO);

		this.profileId = profileId;
	}

	@Override
	protected void addUserInfo(Element element)
	{
		XmlHelper.appendSimpleElement(element, RequestConstants.PROFILE_ID, profileId.toString());
	}

	public String getName()
	{
		return REQUEST_NAME;
	}
}
