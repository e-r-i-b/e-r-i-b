package com.rssl.phizic.logging.writers;

import com.rssl.phizic.logging.messaging.MessagingLogEntry;

/**
 * @author eMakarov
 * @ created 16.06.2009
 * @ $Author$
 * @ $Revision$
 */
public interface MessageLogWriter
{
	/**
	 * ���������� ������ � ������ ���������
 	 * @param entry - ������, ������������ ����� ������ � ������� ���������
	 * @throws Exception
	 */
	void write(MessagingLogEntry entry) throws Exception;
}
