package com.rssl.phizic.web.servlet;

import com.rssl.phizic.module.Module;
import com.rssl.phizic.module.ModuleManager;
import com.rssl.phizic.utils.ClassHelper;

import javax.servlet.ServletContext;

/**
 * @author Erkin
 * @ created 27.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Реализация менеджера модулей на базе сервлет-контекста
 */
public class ModuleServletManager implements ModuleManager
{
	private final ServletContext servletContext;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param servletContext - сервлет-контекст
	 */
	public ModuleServletManager(ServletContext servletContext)
	{
		this.servletContext = servletContext;
	}

	public <T extends Module> T getModule(Class<T> moduleClass)
	{
		String moduleKey = "Module " + moduleClass.getName();

		//noinspection unchecked
		T module = (T) servletContext.getAttribute(moduleKey);
		if (module == null)
		{
			synchronized (servletContext)
			{
				//noinspection unchecked
				module = (T) servletContext.getAttribute(moduleKey);
				if (module == null)
					module = ClassHelper.newInstance(moduleClass);
				servletContext.setAttribute(moduleKey, module);
			}
		}
		return module;
	}
}
