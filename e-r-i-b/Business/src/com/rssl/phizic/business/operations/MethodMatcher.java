package com.rssl.phizic.business.operations;

import java.lang.reflect.Method;

/**
 * ������������ ��� �������� ������������ ������ � ��������
 * @author Evgrafov
 * @ created 14.05.2007
 * @ $Author: Evgrafov $
 * @ $Revision: 4221 $
 */

public interface MethodMatcher
{
	/**
	 * @param method ����� ��� �������� ������������
	 * @return true = ����� ������ �������
	 */
	boolean match(Method method);
}
