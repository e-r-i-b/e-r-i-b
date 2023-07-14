package com.rssl.phizic.logging.monitoring;

import com.rssl.phizic.logging.LoggingException;

/**
 * ���������� ������ ��������
 *
 * @author bogdanov
 * @ created 24.02.15
 * @ $Author$
 * @ $Revision$
 */

public interface BusinessOperationMonitoringWriter
{
	/**
	 * ���������� ���������� ��� ����������� � ���.
	 *
	 * @param logEntry ������ ����.
	 * @throws LoggingException
	 */
	public void write(MonitoringEntry logEntry) throws LoggingException;
}
