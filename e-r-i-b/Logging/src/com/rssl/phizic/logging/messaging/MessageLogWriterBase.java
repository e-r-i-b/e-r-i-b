package com.rssl.phizic.logging.messaging;

import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.LoggingHelper;
import com.rssl.phizic.logging.LoggingMode;
import com.rssl.phizic.logging.operations.context.OperationContext;
import com.rssl.phizic.logging.writers.MessageLogWriter;
import com.rssl.phizic.utils.StringHelper;

import java.util.Calendar;

/**
 * @author krenev
 * @ created 08.02.2012
 * @ $Author$
 * @ $Revision$
 */

public abstract class MessageLogWriterBase  implements MessageLogWriter
{
	private MessagingLoggingAccessor accessor = new MessagingLoggingAccessor();

	private MessagingLogEntryBase fillBasePart(MessagingLogEntry logEntry)
	{
		if (StringHelper.isEmpty(logEntry.getMessageRequestId()))
			logEntry.setMessageRequestId("unidentified");
		if (StringHelper.isEmpty(logEntry.getMessageType()))
			logEntry.setMessageType("unidentified");
		if (logEntry.getApplication() == null)
			logEntry.setApplication(LogThreadContext.getApplication());

		logEntry.setDate(Calendar.getInstance());
		logEntry.setLoginId(LogThreadContext.getLoginId());
		logEntry.setSurName(LogThreadContext.getSurName());
		logEntry.setPatrName(LogThreadContext.getPatrName());
		logEntry.setFirstName(LogThreadContext.getFirstName());
		logEntry.setDepartmentName(LogThreadContext.getDepartmentName());
		logEntry.setPersonSeries(LogThreadContext.getSeries());
		logEntry.setPersonNumbers(LogThreadContext.getNumber());
		if (logEntry.getTb() == null)
		{
			logEntry.setTb(LogThreadContext.getDepartmentRegion());
			logEntry.setVsp(LogThreadContext.getDepartmentVSP());
			logEntry.setOsb(LogThreadContext.getDepartmentOSB());
		}
		logEntry.setBirthDay(LogThreadContext.getBirthday());
		logEntry.setSessionId(LogThreadContext.getSessionId());
		logEntry.setOperationUID(OperationContext.getCurrentOperUID());
		logEntry.setDepartmentCode(LogThreadContext.getDepartmentCode());
		logEntry.setLogin(LogThreadContext.getLogin());
		logEntry.setIpAddress(LogThreadContext.getIPAddress());
		logEntry.setPromoterId(LogThreadContext.getPromoterID());
		logEntry.setmGUID(LogThreadContext.getMGUID());
		logEntry.setLogUID(LogThreadContext.getLogUID());
		if(logEntry.getMessageType() == null)
			logEntry.setMessageType(LogThreadContext.getProcName());
		logEntry.setNodeId(LogThreadContext.getNodeId() != null ? LogThreadContext.getNodeId() : ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber());
		logEntry.setThreadInfo(Thread.currentThread().getId());
		logEntry.setAddInfo(LogThreadContext.getAppServerInfo());
		//в зависимости от признака в контексте создаем гостевую запись
		if (LogThreadContext.isGuest())
			return new GuestMessagingLogEntry(logEntry);

		return logEntry;
}

	public final void write(MessagingLogEntry entry) throws Exception
	{
		if (LoggingMode.OFF == ConfigFactory.getConfig(LoggingHelper.class).getLoggingMode(accessor, entry.getSystem(), entry.getMessageType()))
			return;

		doWrite(fillBasePart(entry));
	}

	protected abstract void doWrite(MessagingLogEntryBase entry) throws Exception;
}
