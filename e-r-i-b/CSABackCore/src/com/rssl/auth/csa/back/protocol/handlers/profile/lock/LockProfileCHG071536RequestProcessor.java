package com.rssl.auth.csa.back.protocol.handlers.profile.lock;

import com.rssl.auth.csa.back.protocol.LogableResponseInfo;
import com.rssl.auth.csa.back.protocol.handlers.LockProfileRequestProcessor;
import com.rssl.auth.csa.back.servises.ActiveRecord;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import org.hibernate.Session;

import java.util.Calendar;

/**
 * @author mihaylov
 * @ created 24.04.14
 * @ $Author$
 * @ $Revision$
 * Обработчик запроса на блокировку профиля для запроса CHG071536.
 * Всего лишь добавляем в профиль причину блокировки.
 *
 */
public class LockProfileCHG071536RequestProcessor extends LockProfileRequestProcessor
{
	private static final String REQUEST_TYPE = "lockProfileCHG071536Rq";
	private static final String RESPONCE_TYPE = "lockProfileCHG071536Rs";

	@Override
	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	@Override
	protected String getResponceType()
	{
		return RESPONCE_TYPE;
	}

	protected LogableResponseInfo blockProfile(final Profile profile, final Calendar blockFrom, final Calendar blockTo, final String reason, final String blockerFIO) throws Exception
	{
		return ActiveRecord.executeAtomic(new HibernateAction<LogableResponseInfo>()
		{
			public LogableResponseInfo run(Session session) throws Exception
			{
				profile.lockForCHG071536(blockFrom, blockTo, reason, blockerFIO);
				return new LogableResponseInfo(buildSuccessResponse());
			}
		});
	}
}
