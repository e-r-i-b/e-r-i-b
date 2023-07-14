package com.rssl.phizic.auth.modes;

import com.rssl.phizic.security.ConfirmableObject;

/**
 * @ author: filimonova
 * @ created: 10.06.2010
 * @ $Author$
 * @ $Revision$
 */
public interface StrategyCondition
{
	/**
	 * �������� ��������� �� ������, ���������� ���������� ������� �������������� ���������� ��� �������� ������� �������������
	 * @return String
	 */
	public String getWarning();

	/**
	 * �������� ������� �������������
	 * @param object - ������ �������������
	 * @return boolean - true ��� ����������� �������� �������� �������������
	 */
	public boolean checkCondition(ConfirmableObject object);
}
