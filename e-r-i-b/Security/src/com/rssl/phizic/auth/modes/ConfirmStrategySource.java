package com.rssl.phizic.auth.modes;

import com.rssl.phizic.security.ConfirmableObject;

/**
 * @ author: filimonova
 * @ created: 10.06.2010
 * @ $Author$
 * @ $Revision$
 */
public interface ConfirmStrategySource
{
	/**
	 * �������� ��������������� ��������� �������������
	 * @param object - ������ �������������
	 * @param strategy - �������� ��������� �������������
	 * @param userChoice - �������� ���� ��������� ������������� ���������� ������������� (���, ���)
	 * @return ConfirmStrategy
	*/
	public ConfirmStrategy getStrategy(ConfirmableObject object, ConfirmStrategy strategy, String userChoice);
}
