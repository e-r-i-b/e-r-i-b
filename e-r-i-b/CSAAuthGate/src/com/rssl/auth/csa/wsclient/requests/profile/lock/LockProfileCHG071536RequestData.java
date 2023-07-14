package com.rssl.auth.csa.wsclient.requests.profile.lock;

import com.rssl.auth.csa.wsclient.requests.LockProfileRequestData;
import com.rssl.phizic.gate.csa.UserInfo;

import java.util.Calendar;

/**
 * @author mihaylov
 * @ created 24.04.14
 * @ $Author$
 * @ $Revision$
 *
 * Билдер запроса на "блокировку" клиента реализованную в рамках запроса CHG071536
 */
public class LockProfileCHG071536RequestData extends LockProfileRequestData
{
	private static final String REQUEST_NAME = "lockProfileCHG071536Rq";

	/**
	 * конструктор
	 * @param userInfo информация о блокируемом клиенте
	 * @param lockFrom начало блокировки
	 * @param lockTo окончание блокировки
	 * @param reason причина блокировки
	 * @param blockerFIO фио блокирующего сотрудника
	 */
	public LockProfileCHG071536RequestData(UserInfo userInfo, Calendar lockFrom, Calendar lockTo, String reason, String blockerFIO)
	{
		super(userInfo, lockFrom, lockTo, reason, blockerFIO);
	}

	public String getName()
	{
		return REQUEST_NAME;
	}

}
