package com.rssl.phizic.task;

import com.rssl.phizic.context.ModuleContext;
import com.rssl.phizic.module.Module;

/**
 * @author Erkin
 * @ created 04.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Базовый класс задачи
 */
public abstract class TaskBase implements Task
{
	private transient Module module;

	///////////////////////////////////////////////////////////////////////////

	protected TaskBase()
	{
		module = ModuleContext.getModule();
	}

	public void setModule(Module module)
	{
		if (module == null)
			throw new IllegalArgumentException("Аргумент 'module' не может быть null");
		this.module = module;
	}

	protected Module getModule()
	{
		return module;
	}

	/**
	 * Проверить готовность параметров
	 * @exception TaskNotReadyException - если указаны не все параметры
	 */
	protected void checkParameters() throws TaskNotReadyException
	{
		if (module == null)
			throw new TaskNotReadyException("Не указан параметр module");
	}
}
