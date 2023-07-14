package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.gate.longoffer.LongOffer;

import java.io.Serializable;

/**
 * @author niculichev
 * @ created 20.07.2011
 * @ $Author$
 * @ $Revision$
 */
public class LongOfferExternalIdCacheKeyComposer extends StringCacheKeyComposer
{
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

	/**
	 * �������������� �� ������������ ����� �� ����������
	 * @return true - ��������������.
	 */
	public boolean isKeyFromResultSupported()
	{
		return true;
	}
}
