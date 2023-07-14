package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;
import com.rssl.phizic.utils.StringHelper;

import java.io.Serializable;

/**
 * @author osminin
 * @ created 25.02.2011
 * @ $Author$
 * @ $Revision$
 *  �������� ��� �������, ������� � �������� ���������� �������� ����������
 */
public class AutoPaymentCacheKeyComposer extends AbstractCacheKeyComposer
{
	/**
	 * ������������ ����
	 * @param args ��������� ������ ������
	 * @param params ��������� ��� ������ ���������
	 * @return ����
	 */
	public String getKey(Object[] args, String params)
	{
		int paramNum = 0;
		if (!StringHelper.isEmpty(params))
			paramNum = Integer.parseInt(params);

		return ((AutoPayment) args[paramNum]).getExternalId();
	}

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
