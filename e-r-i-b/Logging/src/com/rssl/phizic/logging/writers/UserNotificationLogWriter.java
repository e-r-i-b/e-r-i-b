package com.rssl.phizic.logging.writers;

import com.rssl.phizic.logging.settings.UserNotificationLogRecord;

/**
 * Writer ��� ����������� ��������� �������� ����������
 * @author lukina
 * @ created 06.08.2014
 * @ $Author$
 * @ $Revision$
 */
public interface UserNotificationLogWriter
{
	/**
	 * �������� ���������
	 * @param entry ���������
	 */
	void write(UserNotificationLogRecord entry) throws Exception;
}

