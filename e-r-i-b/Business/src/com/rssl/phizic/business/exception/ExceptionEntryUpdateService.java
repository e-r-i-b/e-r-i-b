package com.rssl.phizic.business.exception;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.exceptions.ExceptionEntry;
import com.rssl.phizic.logging.exceptions.ExternalExceptionEntry;
import com.rssl.phizic.logging.exceptions.InternalExceptionEntry;
import com.rssl.phizic.logging.system.*;
import com.rssl.phizic.logging.system.guest.GuestSystemLogEntry;
import org.apache.commons.logging.Log;

/**
 * @author akrenev
 * @ created 09.11.13
 * @ $Author$
 * @ $Revision$
 *
 * ������ ���������� ���������� ����������
 */

public class ExceptionEntryUpdateService
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);
	private static final ExceptionEntryService exceptionEntryService = new ExceptionEntryService();

	/**
	 * �������� ���������, �������� ����������� � ��������� ������� ������ ��� ������� ����������
	 * @param entry - ����������
	 * @param exceptionEntryApplication - ���������� � ������ �������� ���������� ����� ���������
	 * @param tb - �������, � ������ �������� ���������� ����� ���������
	 * @return ���������
	 */
	public String getMessageAndUpdate(final ExceptionEntry entry, ExceptionEntryApplication exceptionEntryApplication, String tb)
	{
		return getMessageAndUpdate(entry, exceptionEntryApplication, tb, null);
	}

	/**
	 * �������� ���������, �������� ����������� � ��������� ������� ������ ��� ������� ����������
	 * @param entry - ����������
	 * @param exceptionEntryApplication - ���������� � ������ �������� ���������� ����� ���������
	 * @param tb - �������, � ������ �������� ���������� ����� ���������
	 * @param systemLogEntry - ������ � ������ �����
	 * @return ���������
	 */
	public String getMessageAndUpdate(final ExceptionEntry entry, ExceptionEntryApplication exceptionEntryApplication, String tb, SystemLogEntry systemLogEntry)
	{
		String message = exceptionEntryService.getMessage(entry.getHash(), exceptionEntryApplication, tb);
		ExceptionSettingsService config = ConfigFactory.getConfig(ExceptionSettingsService.class);

		if (config.isUpdateExceptionInfoAvailable())
		{
			update(entry, systemLogEntry);
		}

		return message;
	}

	private void update(ExceptionEntry entry, SystemLogEntry systemLogEntry)
	{
		try
		{
			ExceptionSystemLogEntry logEntry = createExceptionSystemLogEntry(systemLogEntry);

			logEntry.setHash(entry.getHash());
			logEntry.setOperation(entry.getOperation());
			logEntry.setDetail(entry.getDetail());
			logEntry.setKind(entry.getKind());

			if (entry instanceof ExternalExceptionEntry)
			{
				logEntry.setSystem(((ExternalExceptionEntry) entry).getSystem());
				logEntry.setErrorCode(((ExternalExceptionEntry) entry).getErrorCode());
			}
			else
			{
				logEntry.setApplication(((InternalExceptionEntry) entry).getApplication());
			}

			JMSSystemLogWriter logWriter = new JMSSystemLogWriter();
			logWriter.writeException(logEntry);
		}
		catch (Exception e)
		{
			log.error("������ ���������� ���������� ���������� ��� " + entry.getHash(), e);
		}
	}

	private ExceptionSystemLogEntry createExceptionSystemLogEntry(SystemLogEntry systemLogEntry)
	{
		if (systemLogEntry == null)
		{
			return new ExceptionSystemLogEntry();
		}

		if (LogThreadContext.isGuest())
		{
			return new ExceptionSystemLogEntry((GuestSystemLogEntry) systemLogEntry);
		}

		return new ExceptionSystemLogEntry(systemLogEntry);
	}
}
