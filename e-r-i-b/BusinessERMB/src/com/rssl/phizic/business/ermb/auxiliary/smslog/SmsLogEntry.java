package com.rssl.phizic.business.ermb.auxiliary.smslog;

import com.rssl.phizic.business.ermb.auxiliary.smslog.generated.LogEntry_Type;
import com.rssl.phizic.business.ermb.auxiliary.smslog.generated.MessageIn_Type;
import com.rssl.phizic.business.ermb.auxiliary.smslog.generated.MessageOut_Type;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;

import java.util.Calendar;

/**
 * Представление для записи лога (запрос-ответ)
 * @author Puzikov
 * @ created 28.10.14
 * @ $Author$
 * @ $Revision$
 */

public class SmsLogEntry
{
	//global
	private final String phone;
	private final String system;
	private final String additionalInfo;
	//in
	private final String inRqID;
	private final Calendar inReceiveTime;
	private final String inMessage;
	//out
	private final String outRqID;
	private final Calendar outDeliverTime;
	private final Calendar outSendTime;
	private final String outState;
	private final String outResource;
	private final String outMessage;

	/**
	 * ctor
	 * @param mssLogEntry сообщение из MSS
	 */
	public SmsLogEntry(LogEntry_Type mssLogEntry)
	{
		String messageType = mssLogEntry.getMessageType();
		boolean isIn = "both".equals(messageType) || "in".equals(messageType);
		boolean isOut = "both".equals(messageType) || "out".equals(messageType);
		if (!isIn && !isOut)
			throw new IllegalArgumentException("Неизвестный тип сообщения из MSS");

		//global
		this.phone = isIn ? mssLogEntry.getMessageIn().getPhone() : mssLogEntry.getMessageOut().getPhone();
		this.system = SmsLogEntryHelper.convertSystemCode(mssLogEntry.getSystem());
		this.additionalInfo = StringHelper.getEmptyIfNull(mssLogEntry.getExtinfo());

		//in
		MessageIn_Type messageIn = mssLogEntry.getMessageIn();
		this.inRqID = isIn ? messageIn.getRqID() : StringUtils.EMPTY;
		this.inReceiveTime = isIn ? messageIn.getReceiveTime() : null;
		this.inMessage = isIn ? messageIn.getText() : StringUtils.EMPTY;

		//out
		MessageOut_Type messageOut = mssLogEntry.getMessageOut();
		this.outRqID = isOut ? messageOut.getRqID() : StringUtils.EMPTY;
		this.outDeliverTime = isOut ? messageOut.getDeliverTime() : null;
		this.outSendTime = isOut ? messageOut.getSendTime() : null;
		this.outState = isOut ? SmsLogEntryHelper.getOutStatus(messageOut.getSendStatus(), messageOut.getDeliverStatus()) : StringUtils.EMPTY;
		this.outResource = isOut ? SmsLogEntryHelper.getResourceNumber(messageOut.getResource()) : StringUtils.EMPTY;
		this.outMessage = isOut ? messageOut.getText() : null;
	}

	/**
	 * ctor
	 */
	public SmsLogEntry()
	{
		phone = StringUtils.EMPTY;
		system = StringUtils.EMPTY;
		additionalInfo = StringUtils.EMPTY;
		inRqID = StringUtils.EMPTY;
		inReceiveTime = null;
		inMessage = StringUtils.EMPTY;
		outRqID = StringUtils.EMPTY;
		outDeliverTime = null;
		outSendTime = null;
		outState = StringUtils.EMPTY;
		outResource = StringUtils.EMPTY;
		outMessage = StringUtils.EMPTY;
	}

	public String getPhone()
	{
		return phone;
	}

	public String getInRqID()
	{
		return inRqID;
	}

	public Calendar getInReceiveTime()
	{
		return inReceiveTime;
	}

	public String getInMessage()
	{
		return inMessage;
	}

	public String getOutRqID()
	{
		return outRqID;
	}

	public Calendar getOutDeliverTime()
	{
		return outDeliverTime;
	}

	public Calendar getOutSendTime()
	{
		return outSendTime;
	}

	public String getOutState()
	{
		return outState;
	}

	public String getOutResource()
	{
		return outResource;
	}

	public String getOutMessage()
	{
		return outMessage;
	}

	public String getSystem()
	{
		return system;
	}

	public String getAdditionalInfo()
	{
		return additionalInfo;
	}
}
