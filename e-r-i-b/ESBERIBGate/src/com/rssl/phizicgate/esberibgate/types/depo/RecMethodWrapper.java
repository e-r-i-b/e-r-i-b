package com.rssl.phizicgate.esberibgate.types.depo;

import com.rssl.phizic.utils.StringHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lukina
 * @ created 25.10.2010
 * @ $Author$
 * @ $Revision$
 */

public class RecMethodWrapper
{
	private static final Map<String, String> storageMethodMap = new HashMap<String, String>();

	static
	{
		storageMethodMap.put("1","�����");
		storageMethodMap.put("8","����� �������������� ��������������");
		storageMethodMap.put("9","�������� ������� � ������������");
		storageMethodMap.put("14","����� ���������� ����");
		storageMethodMap.put("10","� ������������ � ����������� � ");
	}

	/**
	 * �������� ������ ������ ��������� �� ��������� ����� ����/������ �������� ���������� ��������� ����� ����
	 * @param key - ����
	 * @return String
	 */
	public static String getRecMethod(String key)
	{
		if(StringHelper.isEmpty(key))
			return null;
		return storageMethodMap.get(key);
	}
}
