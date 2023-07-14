package com.rssl.phizic.gate.cache.proxy.composers;

import java.io.Serializable;

/**
 * �������� ��� ������������ ����� ���� ��� ������ getAllowedAutoPaymentTypes
 * @author niculichev
 * @ created 05.10.2011
 * @ $Author$
 * @ $Revision$
 */
public class AllowedAutoPaymentTypesComposer extends AbstractCacheKeyComposer
{
	/**
	 * ������������ ����
	 * @param args ��������� ������ ������
	 * @param params ��������� ��� ������ ���������
	 * @return ����
	 */
	public Serializable getKey(Object[] args, String params)
	{
		StringBuilder builder = new StringBuilder();
		return builder.append(args[0]).append(args[1]).append(args[2]).toString();
	}
}
