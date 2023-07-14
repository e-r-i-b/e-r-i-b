package com.rssl.phizic.logging.system;

import com.rssl.phizic.engine.EngineBase;
import com.rssl.phizic.engine.EngineManager;
import com.rssl.phizic.module.loader.LoadOrder;

/**
 * @author Erkin
 * @ created 10.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���������� ������ ����� ��� ��������
 */
public class MockLogEngine extends EngineBase implements LogEngine
{
	/**
	 * ctor
	 * @param manager - �������� �� ������� ������
	 */
	public MockLogEngine(EngineManager manager)
	{
		super(manager);
	}

	public LoadOrder getLoadOrder()
	{
		return LoadOrder.LOG_ENGINE_ORDER;
	}

	public void start()
	{
	}

	public void stop()
	{
	}
}
