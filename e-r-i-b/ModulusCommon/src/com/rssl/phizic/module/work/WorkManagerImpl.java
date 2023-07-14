package com.rssl.phizic.module.work;

import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.context.ModuleContext;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.utils.store.Store;
import com.rssl.phizic.utils.store.StoreManager;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Erkin
 * @ created 15.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Реализация менеджера потока
 */
public class WorkManagerImpl implements WorkManager
{
	protected static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private final Module module;


	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param module - модуль, поток котрого нужно настроить
	 */
	public WorkManagerImpl(Module module)
	{
		this.module = module;
	}

	protected Module getModule()
	{
		return module;
	}

	public void beginWork()
	{
		try
		{
			ModuleContext.setModule(module);
			LogThreadContext.setIPAddress(ApplicationConfig.getIt().getApplicationHost().getHostAddress());
			ApplicationInfo.setCurrentApplication(module.getApplication());
		}
		catch (UnknownHostException e)
		{
			throw new InternalErrorException(e);
		}
	}

	public void setSession(Store session)
	{
		StoreManager.setCurrentStore(session);
		if (session != null)
			LogThreadContext.setSessionId(session.getId());
		else LogThreadContext.setSessionId("null");
	}

	public Store getSession()
	{
		return StoreManager.getCurrentStore();
	}

	public void endWork()
	{
		StoreManager.setCurrentStore(null);

		LogThreadContext.clear();

		ApplicationInfo.setDefaultApplication();

		ModuleContext.setModule(null);
	}
}
