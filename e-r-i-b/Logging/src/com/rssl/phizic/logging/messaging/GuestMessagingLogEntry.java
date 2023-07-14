package com.rssl.phizic.logging.messaging;

import com.rssl.phizic.logging.LogThreadContext;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author vagin
 * @ created 05.02.15
 * @ $Author$
 * @ $Revision$
 * Объект журнала сообщений для гостевого входа
 */
public class GuestMessagingLogEntry extends MessagingLogEntryBase
{
	private Long guestCode;
	private String phoneNumber;
	private String guestLogin;

	public GuestMessagingLogEntry(MessagingLogEntry logEntry)
	{
		this.id = logEntry.id;
		this.date = logEntry.date;
		this.executionTime = logEntry.executionTime;
		this.application = logEntry.application;
		this.messageType = logEntry.messageType;
		this.messageRequestId = logEntry.messageRequestId;
		this.messageRequest = logEntry.messageRequest;
		this.messageResponseId = logEntry.messageResponseId;
		this.messageResponse = logEntry.messageResponse;
		this.system = logEntry.system;
		this.sessionId = logEntry.sessionId;
		this.surName = logEntry.surName;
		this.patrName = logEntry.patrName;
		this.firstName = logEntry.firstName;
		this.departmentName = logEntry.departmentName;
		this.personSeries = logEntry.personSeries;
		this.personNumbers = logEntry.personNumbers;
		this.birthDay = logEntry.birthDay;
		this.nodeId = logEntry.nodeId;
		this.threadInfo = logEntry.threadInfo;
		this.tb = logEntry.tb;
		this.operationUID = logEntry.getOperationUID();
		this.login = logEntry.getLogin();
		this.ipAddress = logEntry.getIpAddress();
		this.errorCode = logEntry.getErrorCode();
		this.logUID = logEntry.getLogUID();
		this.addInfo = logEntry.getAddInfo();
		this.departmentCode = logEntry.getDepartmentCode();

		this.guestLogin = LogThreadContext.getGuestLogin();
		this.phoneNumber = LogThreadContext.getGuestPhoneNumber();
		this.guestCode = LogThreadContext.getGuestCode();
	}

	public GuestMessagingLogEntry() {}

	public String getGuestLogin()
	{
		return guestLogin;
	}

	public void setGuestLogin(String guestLogin)
	{
		this.guestLogin = guestLogin;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public Long getGuestCode()
	{
		return guestCode;
	}

	public void setGuestCode(Long guestCode)
	{
		this.guestCode = guestCode;
	}

	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append(super.toString())
				.append("guestCode", guestCode)
				.append("phoneNumber", phoneNumber)
				.append("guestLogin", guestLogin)
				.toString();
	}
}