package com.rssl.phizic.logging.writers;

import com.rssl.phizic.logging.LogEntry;
import com.rssl.phizic.logging.LoggingException;

/**
 * @author mihaylov
 * @ created 30.06.14
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ��������� �����
 */
public interface LogWriter
{

	/**
	 * �������� ������ � ���
	 * @param entry �������� ��� ������
	 * @throws LoggingException - ������ ������ � ���
	 */
	public void write(LogEntry entry) throws LoggingException;

}
