package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;

import java.io.Serializable;

/**
 * @author osminin
 * @ created 25.02.2011
 * @ $Author$
 * @ $Revision$
 *  �������� ��� �������, ������� � �������� ��������� �������� ������������� �����������
 */
public class AutoPaymentExternalIdCacheKeyComposer extends StringCacheKeyComposer
{
	/**
	 * ��������� ���� ���������� getKey, �� �� ���������� ���������� �������.
	 * @param result ��������� ���������� ������
	 * @param params �������������� ���������
	 * @return ����, ��� null ���� �������������� �� ����� ���� ���������.
	 */
	public Serializable getClearCallbackKey(Object result, Object[] params)
	{
		if (result == null || !(result instanceof AutoPayment))
			return null;
		AutoPayment autoPayment = (AutoPayment) result;
		return autoPayment.getExternalId();
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
