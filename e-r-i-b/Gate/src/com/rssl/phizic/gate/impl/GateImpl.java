package com.rssl.phizic.gate.impl;

import com.rssl.phizic.gate.Gate;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.cache.CacheServiceCreator;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author Evgrafov
 * @ created 19.05.2006
 * @ $Author: omeliyanchuk $
 * @ $Revision: 15578 $
 */

public class GateImpl implements Gate
{
	private GateFactoryImpl gateFactory;

	/** @return ������ ��� �������� �������� ����� */
	public GateFactory getFactory()
	{
		return gateFactory;
	}

	/** ���� ����� ���������� ������ �� ��������� � ����� ���� ���� ������� */
	public void initialize() throws GateException
	{
		GateFactoryImpl temp = new GateFactoryImpl(new CacheServiceCreator());
		temp.initialize();
		gateFactory = temp;
	}
}