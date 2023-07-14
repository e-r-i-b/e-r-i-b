package com.rssl.phizic.gate.einvoicing;

import com.rssl.phizic.gate.cache.proxy.composers.AbstractCacheKeyComposer;

import java.io.Serializable;

/**
 * �������� ��� ��������� ����� ���� �� ������ ����������� �������������� ������
 * @author gladishev
 * @ created 10.07.2014
 * @ $Author$
 * @ $Revision$
 */

public class OrdersInternalKeyComposer extends AbstractCacheKeyComposer
{
	public Serializable getKey(Object[] args, String params)
	{
		return (Serializable)args[0];
	}

	public Serializable getClearCallbackKey(Object result, Object[] params)
	{
		if (result instanceof ShopOrder)
			return ((ShopOrder) result).getId();
		return (Serializable) params[0];
	}
}
