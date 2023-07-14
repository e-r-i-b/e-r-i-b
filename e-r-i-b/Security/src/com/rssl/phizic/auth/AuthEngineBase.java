package com.rssl.phizic.auth;

import com.rssl.phizic.engine.EngineBase;
import com.rssl.phizic.engine.EngineManager;
import com.rssl.phizic.module.loader.LoadOrder;

/**
 * @author Erkin
 * @ created 18.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���� ������ ��������������
 * ������������ ������� �� ������� ������������, � �.�.:
 * - ������ ���� �������
 */
public abstract class AuthEngineBase extends EngineBase implements AuthEngine
{
	/**
	 * ctor
	 * @param manager - �������� �������
	 */
	protected AuthEngineBase(EngineManager manager)
	{
		super(manager);
	}

	public LoadOrder getLoadOrder()
	{
		return LoadOrder.AUTH_ENGINE_ORDER;
	}

	public void start()
	{
	}

	public void stop()
	{
	}
}
