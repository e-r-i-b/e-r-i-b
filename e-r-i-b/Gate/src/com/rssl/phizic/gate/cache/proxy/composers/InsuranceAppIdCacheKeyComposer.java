package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.gate.insurance.InsuranceApp;

import java.io.Serializable;

/**
 * @author lukina
 * @ created 25.03.2013
 * @ $Author$
 * @ $Revision$
 *  �������� ��� �������, ������� � �������� ��������� �������� ������������� ���������� ��������
 */

public class InsuranceAppIdCacheKeyComposer   extends StringCacheKeyComposer
{
	public Serializable getClearCallbackKey(Object result, Object[] params)
	{
		if(!(result instanceof InsuranceApp))
			return null;

		InsuranceApp insuranceApp = (InsuranceApp)result;
		return insuranceApp.getId();
	}
}
