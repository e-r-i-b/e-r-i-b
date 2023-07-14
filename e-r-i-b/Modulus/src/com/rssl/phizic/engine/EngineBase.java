package com.rssl.phizic.engine;

import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.module.Module;

/**
 * @author Erkin
 * @ created 16.03.2013
 * @ $Author$
 * @ $Revision$
 */

public abstract class EngineBase implements Engine
{
	protected static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private final Module module;

	///////////////////////////////////////////////////////////////////////////

	protected EngineBase(EngineManager manager)
	{
		this.module = manager.getModule();
		//noinspection ThisEscapedInObjectConstruction
		manager.addEngine(this);
	}

	public String getName()
	{
		return getClass().getSimpleName();
	}

	protected Module getModule()
	{
		return module;
	}
}
