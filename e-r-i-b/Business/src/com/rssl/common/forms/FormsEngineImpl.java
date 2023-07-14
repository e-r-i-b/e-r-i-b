package com.rssl.common.forms;

import com.rssl.phizic.engine.EngineBase;
import com.rssl.phizic.engine.EngineManager;
import com.rssl.phizic.module.loader.LoadOrder;

/**
 * @author Erkin
 * @ created 14.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���������� ������ �� ������
 */
public class FormsEngineImpl extends EngineBase implements FormsEngine
{
	/**
	 * ctor
	 * @param manager - �������� �� �������
	 */
	public FormsEngineImpl(EngineManager manager)
	{
		super(manager);
	}

	public LoadOrder getLoadOrder()
	{
		return LoadOrder.FORMS_ENGINE_ORDER;
	}

	public void start()
	{
	}

	public void stop()
	{
	}
}
