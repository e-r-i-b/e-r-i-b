package com.rssl.phizic.utils.counter;

import java.util.List;

/**
 * @author osminin
 * @ created 17.04.14
 * @ $Author$
 * @ $Revision$
 *
 * ������ ��� ������ �� ����������
 */
public class CounterUtils
{
	/**
	 * ���������� ��������� ����� ����: (�����) ��� ������ ������, ���� ��� ��� �� �������������
	 * @param objects ������ ��������, �� ������� ������������ �������
	 * @param standardName ������������, ��� �������� �������� �����
	 * @param action ��������� NameCounterAction � ������������ ������� getName
	 * @return �����
	 */
	public static String calcNumber(List<?> objects, String standardName, NameCounterAction action)
	{
		return action.calcNumber(objects, standardName);
	}
}
