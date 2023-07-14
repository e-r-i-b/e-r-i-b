package com.rssl.phizic.gate;

import com.rssl.phizic.engine.EngineBase;
import com.rssl.phizic.engine.EngineManager;
import com.rssl.phizic.module.loader.LoadOrder;

/**
 * Реализация движка шлюзов
 * @author Rtischeva
 * @ created 19.03.2013
 * @ $Author$
 * @ $Revision$
 */
public class GateEngineImpl extends EngineBase implements GateEngine
{

	/**
	 * ctor
	 * @param manager - менеджер по движкам
	 */
	public GateEngineImpl(EngineManager manager)
	{
		super(manager);
	}

	public LoadOrder getLoadOrder()
	{
		return LoadOrder.GATE_ENGINE_ORDER;
	}

	public void start()
	{
	}

	public void stop()
	{
	}
}
