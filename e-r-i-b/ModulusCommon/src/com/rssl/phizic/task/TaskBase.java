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
 * ������� ����� ������
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
			throw new IllegalArgumentException("�������� 'module' �� ����� ���� null");
		this.module = module;
	}

	protected Module getModule()
	{
		return module;
	}

	/**
	 * ��������� ���������� ����������
	 * @exception TaskNotReadyException - ���� ������� �� ��� ���������
	 */
	protected void checkParameters() throws TaskNotReadyException
	{
		if (module == null)
			throw new TaskNotReadyException("�� ������ �������� module");
	}
}
