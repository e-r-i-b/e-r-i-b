package com.rssl.phizic.business.ermb.sms.module.work;

import com.rssl.phizic.module.Module;
import com.rssl.phizic.module.work.WorkManagerImpl;
import com.rssl.phizic.session.SessionEngine;
import com.rssl.phizic.session.PersonSession;
import com.rssl.phizic.utils.store.Store;

/**
 * @author Erkin
 * @ created 14.05.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Менеджер потока для СМС-канала ЕРМБ
 */
public class SmsChannelWorkManager extends WorkManagerImpl
{
	/**
	 * ctor
	 * @param module - модуль (СМС-канала)
	 */
	public SmsChannelWorkManager(Module module)
	{
		super(module);
	}

	@Override
	public void endWork()
	{
		// Закрываем сессию сразу после обработки запроса
		destroySession();

		super.endWork();
	}

	private void destroySession()
	{
		try
		{
			Store session = getSession();
			if (session != null)
			{
				setSession(null);
				SessionEngine sessionEngine = getModule().getSessionEngine();
				sessionEngine.destroySession((PersonSession) session);
			}
		}
		catch (RuntimeException e)
		{
			log.error(e.getMessage(), e);
		}
	}
}
