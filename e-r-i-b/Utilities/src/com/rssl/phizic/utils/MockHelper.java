package com.rssl.phizic.utils;

import com.rssl.phizic.common.types.MockObject;

/**
 * @author Erkin
 * @ created 29.06.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ��� ������ � ����������
 */
public class MockHelper
{
	/**
	 * �������� �� ������: "�������� �� ��������� ������ ���������?"
	 * @param object - ������ � ����������� �� ��������
	 * @return true, ���� ������� null ���� ������ � ����������� MockObject
	 */
	public static boolean isMockObject(Object object)
	{
		if (object == null)
			return true;
		
		return (object instanceof MockObject);
	}
}
