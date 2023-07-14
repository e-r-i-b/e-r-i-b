package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.gate.longoffer.LongOffer;

import java.io.Serializable;

/**
 * @author niculichev
 * @ created 19.07.2011
 * @ $Author$
 * @ $Revision$
 */
public class LongOfferCacheKeyComposer extends AbstractCacheKeyComposer
{

	/**
	 * ������������ ����
	 * @param args ��������� ������ ������
	 * @param params ��������� ��� ������ ���������
	 * @return ����
	 */
	public Serializable getKey(Object[] args, String params)
	{
		int paramNum = getOneParam(params);
		return ((LongOffer) args[paramNum]).getExternalId();
	}

	/**
	 * ��������� ���� ���������� getKey, �� �� ���������� ���������� �������.
	 * @param result ��������� ���������� ������
	 * @param params �������������� ���������
	 * @return ����, ��� null ���� �������������� �� ����� ���� ���������.
	 */
	public Serializable getClearCallbackKey(Object result, Object[] params)
	{
		if(!(result instanceof LongOffer))
			return null;

		LongOffer longOffer = (LongOffer) result;
		return longOffer.getExternalId();
	}
}
