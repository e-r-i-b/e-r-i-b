package com.rssl.phizic.context;

import com.rssl.phizic.module.Module;

/**
 * @author Erkin
 * @ created 07.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 *  онтекст потока
 */
public class ModuleContext
{
	private static final ThreadLocal<Module> moduleThreadLocal = new ThreadLocal<Module>();

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ¬озвращает текущий модуль
	 * @return модуль текущего потока
	 */
	public static Module getModule()
	{
		return moduleThreadLocal.get();
	}

	/**
	 * ”станавливает текущий модуль
	 * @param module - модуль текущего потока
	 */
	public static void setModule(Module module)
	{
		moduleThreadLocal.set(module);
	}

	/**
	 * ќчищает контекст потока
	 */
	public static void clear()
	{
		moduleThreadLocal.remove();
	}
}
