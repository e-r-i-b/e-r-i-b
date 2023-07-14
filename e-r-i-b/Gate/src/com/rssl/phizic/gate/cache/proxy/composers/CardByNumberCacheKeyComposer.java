package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.gate.clients.Client;

/**
 * @author krenev
 * @ created 08.11.2010
 * @ $Author$
 * @ $Revision$
 * ��������� ���� �� ������� � ��������� �������
 */

public class CardByNumberCacheKeyComposer extends PairCacheKeyComposer
{
	protected static final String SEPARATOR = "^";

	public String getKey(Object[] args, String params)
	{
		StringBuilder key = new StringBuilder();
		//�������� ���� �� ��������
		key.append(super.getKey(args, params));
		//��������� � ������������� �������
		key.append( SEPARATOR );
		Client client = (Client) args[0];
		key.append(client.getInternalOwnerId());
		return key.toString();
	}

}
