package com.rssl.phizic.security;

import com.rssl.phizic.person.PersonTask;

/**
 * @author Erkin
 * @ created 12.05.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������, ����������� � ��������� �������������
 */
public interface ConfirmableTask extends PersonTask
{
	/**
	 * ����������, ����� ������������� ��������
	 */
	void confirmGranted();
}
