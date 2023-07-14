package com.rssl.phizic.context;

import com.rssl.phizic.module.Module;

/**
 * @author Erkin
 * @ created 07.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * �������� ������
 */
public class ModuleContext
{
	private static final ThreadLocal<Module> moduleThreadLocal = new ThreadLocal<Module>();

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ���������� ������� ������
	 * @return ������ �������� ������
	 */
	public static Module getModule()
	{
		return moduleThreadLocal.get();
	}

	/**
	 * ������������� ������� ������
	 * @param module - ������ �������� ������
	 */
	public static void setModule(Module module)
	{
		moduleThreadLocal.set(module);
	}

	/**
	 * ������� �������� ������
	 */
	public static void clear()
	{
		moduleThreadLocal.remove();
	}
}
