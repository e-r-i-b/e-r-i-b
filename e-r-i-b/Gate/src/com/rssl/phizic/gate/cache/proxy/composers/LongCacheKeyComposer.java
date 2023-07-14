package com.rssl.phizic.gate.cache.proxy.composers;

import java.io.Serializable;

/**
 * @author krenev
 * @ created 21.07.2010
 * @ $Author$
 * @ $Revision$
 *
 * ������������ ��� ������� c �������� ����� ���� Long.
 * ���������� ����� � ������� ���������� ������ ��������� ��������� ����� �������� � ���������� ���������.
 */
public class LongCacheKeyComposer extends AbstractCacheKeyComposer
{
	public Serializable getKey(Object[] args, String params)
	{
		int paramNum = getOneParam(params);
		return (Long)args[paramNum];
	}
}
