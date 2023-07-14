package com.rssl.phizic.logging.writers;

import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.LogLevel;
import com.rssl.phizic.logging.system.SystemLogEntry;

/**
 * @author eMakarov
 * @ created 16.06.2009
 * @ $Author$
 * @ $Revision$
 */
public interface SystemLogWriter
{
	/**
	 * �������� ���������
	 * @param source �������� ��������� (����, ����, �������)
	 * @param message ���������
	 * @param level ������� �������� ������� ���������(������, ��������������, ����������...)
	 */
	void write(LogModule source, String message, LogLevel level) throws Exception;

	/**
	 * ������� ��������
	 * @param source �������� ��������� (����, ����, �������)
	 * @param message ���������
	 * @param level ������� �������� ������� ���������(������, ��������������, ����������...)
	 * @return ������ ���������� �������
	 */
	SystemLogEntry createEntry(LogModule source, String message, LogLevel level);
}
