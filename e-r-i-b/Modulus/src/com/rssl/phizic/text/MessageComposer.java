package com.rssl.phizic.text;

import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.security.ConfirmCodeMessage;
import com.rssl.phizic.security.ConfirmableTask;
import com.rssl.phizic.task.Task;

/**
 * @author Erkin
 * @ created 28.05.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���������� ��������� ��� �������
 */
public interface MessageComposer
{
	/**
	 * ������������� ��������� ��������� � ����� �������������
	 * @param confirmCode - ��� ������������� (never null, never empty)
	 * @param confirmableTask - ������, ������� ������������
	 * @return ��������� � ����� ������������� ��� �������� �������
	 */
	ConfirmCodeMessage buildConfirmCodeMessage(String confirmCode, ConfirmableTask confirmableTask);

	/**
	 * ������������� ��������� ��������� � ����������� ������, ��������� ��� ���������� �����
	 * @param task - ������, � ������� �������� ������
	 * @return ��������� ���������
	 */
	TextMessage buildFatalErrorMessage(Task task);

	/**
	 * ������������� ��������� ��������� �� ������ ������� � ��������
	 * @return ��������� ���������
	 */
	TextMessage buildAccessControlErrorMessage();

	/**
	 * ������������� ��������� ��������� �� ������, ������� ��������� �����
	 * � ������ �������� �� ���������� ������ ��������� ����
	 * @return ��������� ���������
	 */
	TextMessage buildProfileNotFoundErrorMessage();

	/**
	 * ������������� ��������� ��������� �� ������ - "�� �������� ���."
	 * @param task - ������, � ������� �������� ������
	 * @return ��������� ���������
	 */
	TextMessage buildInactiveExternalSystemErrorMessage(Task task);
}
