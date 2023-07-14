package com.rssl.phizic.interactive;

import java.util.Collection;

/**
 * @author Erkin
 * @ created 13.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * �������� �� �������������� � ��������
 */
public interface PersonInteractManager
{
	/**
	 * �������� ������� � ���������������� ������
	 * @param error - ����� ������, ������� ��� ������ ������������
	 */
	void reportError(String error);

	/**
	 * �������� ������� � ���������������� �������
	 * @param errors - �������� ������, ������� ��� ������ ������������
	 */
	void reportErrors(Collection<String> errors);

	/**
	 * ��������� ������� ������ ��� �������������
	 */
	void askForConfirmCode();
}
