package com.rssl.phizic.business.ermb.migration.list;

import com.rssl.phizic.module.Module;
import com.rssl.phizic.module.ModuleManager;
import com.rssl.phizic.module.loader.ModuleLoader;
import com.rssl.phizic.module.work.WorkManager;

/**
 * @author Gulov
 * @ created 19.12.13
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������������ ������ � ������� ������
 */
public class ModuleExecutor
{
	/**
	 * �������� �������
	 */
	private final ModuleManager moduleManager;

	/**
	 * ����� ������
	 */
	private final Class<? extends Module> moduleClass;

	public ModuleExecutor(ModuleManager moduleManager, Class<? extends Module> moduleClass)
	{
		this.moduleManager = moduleManager;
		this.moduleClass = moduleClass;
	}

	/**
	 * ������.
	 * �������� ������, ������ ��� �������
	 */
	public void start()
	{
		Module module = moduleManager.getModule(moduleClass);
		ModuleLoader moduleLoader = module.getModuleLoader();
		moduleLoader.start();
		WorkManager workManager = module.getWorkManager();
		workManager.beginWork();
	}

	/**
	 * ������� ������
	 */
	public void stop()
	{
		Module module = moduleManager.getModule(moduleClass);
		WorkManager workManager = module.getWorkManager();
		workManager.endWork();
	}
}
