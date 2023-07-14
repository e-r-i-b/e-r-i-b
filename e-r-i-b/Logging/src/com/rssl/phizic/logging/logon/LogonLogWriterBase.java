package com.rssl.phizic.logging.logon;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.operations.context.OperationContext;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import java.util.Calendar;

/**
 * @author krenev
 * @ created 29.06.15
 * @ $Author$
 * @ $Revision$
 */
public abstract class LogonLogWriterBase implements LogonLogWriter
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	public void writeFindProfile(Long loginId, String firstName, String patrName, String surName, Calendar birthday, String cardNumber, String docSeries, String docNumber, String browserInfo)
	{
		write(LogonLogEntryType.FIND, loginId, firstName, patrName, surName, birthday, cardNumber, docSeries, docNumber, browserInfo);
	}

	public void writeLogon(Long loginId, String firstName, String patrName, String surName, Calendar birthday, String cardNumber, String docSeries, String docNumber, String browserInfo)
	{
		write(LogonLogEntryType.LOGON, loginId, firstName, patrName, surName, birthday, cardNumber, docSeries, docNumber, browserInfo);
	}

	private void write(LogonLogEntryType type, Long loginId, String firstName, String patrName, String surName, Calendar birthday, String cardNumber, String docSeries, String docNumber, String browserInfo)
	{
		try
		{
			Application application = LogThreadContext.getApplication();
			if(application == null)
				return;

			LogonLogEntry entry = new LogonLogEntry();
			entry.setLoginId(loginId);
			entry.setFirstName(firstName);
			entry.setPatrName(patrName);
			entry.setSurName(surName);
			entry.setDocSeries(docSeries);
			entry.setDocNumber(docNumber);
			entry.setBirthday(birthday);
			entry.setCardNumber(cardNumber);
			entry.setDeviceInfo(browserInfo);
		    entry.setType(type);

			entry.setDate(Calendar.getInstance());
			entry.setApplication(application);
			entry.setSessionId(LogThreadContext.getSessionId());
			entry.setIpAddress(LogThreadContext.getIPAddress());
			entry.setOperationUID(OperationContext.getCurrentOperUID());

			doSave(entry);
		}
		catch (Exception e)
		{
			log.error("Ошибка логирования в журнал регистрации входов", e);
		}
	}


	protected abstract void doSave(LogonLogEntry entry) throws Exception;
}
