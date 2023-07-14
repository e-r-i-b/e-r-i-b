package com.rssl.phizic.operations;

import com.rssl.phizic.engine.EngineBase;
import com.rssl.phizic.engine.EngineManager;
import com.rssl.phizic.module.loader.LoadOrder;
import com.rssl.phizic.permission.PermissionEngine;

/**
 * @author Erkin
 * @ created 18.03.2013
 * @ $Author$
 * @ $Revision$
 */
public class PermissionEngineImpl extends EngineBase implements PermissionEngine
{
	/**
	 * ctor
	 * @param manager - менеджер по движкам
	 */
	public PermissionEngineImpl(EngineManager manager)
	{
		super(manager);
	}

	public LoadOrder getLoadOrder()
	{
		return LoadOrder.PERMISSION_ENGINE_ORDER;
	}

	public void start()
	{
	}

	public void stop()
	{
	}
}
