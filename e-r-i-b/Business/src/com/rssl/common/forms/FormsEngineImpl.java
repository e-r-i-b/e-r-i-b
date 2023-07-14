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
 * Реализация движка по формам
 */
public class FormsEngineImpl extends EngineBase implements FormsEngine
{
	/**
	 * ctor
	 * @param manager - менеджер по движкам
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
