package com.rssl.phizic.utils;

/**
 * ������������ � BeanHelper-�, ���� � ������� ���������� �� ������� �������� ��� ������
 * @author Rtischeva
 * @ created 27.02.2013
 * @ $Author$
 * @ $Revision$
 */
public class EmptyFormatter implements BeanFormatter
{
	public Object format(Object object)
	{
		return object;
	}
}

