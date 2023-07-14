package com.rssl.phizic.logging.writers;

import com.rssl.phizic.logging.settings.UserMessageLogRecord;

/**
 * Writer ��� ����������� �������� ���������� �������
 * @author lukina
 * @ created 06.08.2014
 * @ $Author$
 * @ $Revision$
 */
public interface UserMessageLogWriter
{
	/**
	 * �������� ���������
	 * @param entry ���������
	 */
	void write(UserMessageLogRecord entry) throws Exception;
}
