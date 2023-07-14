package com.rssl.phizic.messaging.push;

import com.rssl.phizic.logging.push.PushEventType;

import java.util.Calendar;

/**
 * Push ����������� � ����� ��������� ������������ ����������� ������������ �������
 * @author basharin
 * @ created 07.08.13
 * @ $Author$
 * @ $Revision$
 */

public class PushEvent
{
	private Long id;
	private Calendar timestamp;                         //����� �������� ���������
	private String eventId;                             //������������� ����������� �� ������� ����
	private String clientId;                            //������������� �������
	private String shortMessage;                        //����������� ����� ���������. ������������� ��� ����������� � ������ ��������� � ���� ���������
	private String smsMessage;                          //������ ���-����������� ������������� � ������ ������� �������� push-�����������
	private String systemCode;                          //��� �������, ������������ �����������
	private PushEventType typeCode;                     //��� ���� �����������
	private String content;                             //����������� ���������� �����������, ��������� ������� ����� ������ ����������� �� ������.	�������� ������� ���� ����� �������� push �������� ���������� ����������
	private String logContent;                          //����������� ���������� �����������, ��������� ������� ����� ������ ����������� �� ������.	������ �������� ���� � ������������� �������
	private Integer priority;                           //��������� �������� ���������. ��� �������� 0 ��������� �������� ��������� � ��������� �� ���� ���������� ��������
	private Calendar startTime;                         //���� � ����� ������ ���������� �������� ���������� ���������
	private Calendar stopTime ;                         //���� � ����� ��������� ���������� �������� ���������� ���������
	private Long dlvFrom;                               //������ ������������ ������� �������� ��������� � ������� � ������ �����. ����������� �����, ������������ ����*60+������
	private Long dlvTo ;                                //��������� ������������ ������� �������� ��������� � ������� � ������ �����. ����������� �����, ������������ ����*60+������
	private String privateFlag;                         //������� ������������������ �����������. Y � ��� ��������� ����������� ����������� �� push ������� ���������� �������������� �������.
														//                                        N � ��� ��������� ����������� ����������� �� push ������� �������������� ������� �� ���������
	private PushProcStatus procStatus;                  //��������� ���������
	private Calendar procStatusAt;                      //����� ���������� ��������� procStatus ���������
	private String procError;                           //�������� ������

	String getPrivateFlag()
	{
		return privateFlag;
	}

	void setPrivateFlag(String privateFlag)
	{
		this.privateFlag = privateFlag;
	}

	String getContent()
	{
		return content;
	}

	void setContent(String content)
	{
		this.content = content;
	}

	PushEventType getTypeCode()
	{
		return typeCode;
	}

	void setTypeCode(PushEventType typeCode)
	{
		this.typeCode = typeCode;
	}

	String getSystemCode()
	{
		return systemCode;
	}

	void setSystemCode(String systemCode)
	{
		this.systemCode = systemCode;
	}

	String getSmsMessage()
	{
		return smsMessage;
	}

	void setSmsMessage(String smsMessage)
	{
		this.smsMessage = smsMessage;
	}

	String getClientId()
	{
		return clientId;
	}

	void setClientId(String clientId)
	{
		this.clientId = clientId;
	}

	String getEventId()
	{
		return eventId;
	}

	void setEventId(String eventId)
	{
		this.eventId = eventId;
	}

	@Override public String toString()
	{
		StringBuilder stringBuilder = new StringBuilder();
		addIfNotEmpty(stringBuilder, "id", id);
		addIfNotEmpty(stringBuilder, "timestamp", timestamp);
		addIfNotEmpty(stringBuilder, "event_id", eventId);
		addIfNotEmpty(stringBuilder, "client_id", clientId);
		addIfNotEmpty(stringBuilder, "short_message", shortMessage);
		addIfNotEmpty(stringBuilder, "system_code", systemCode);
		addIfNotEmpty(stringBuilder, "type_code", typeCode);
		addIfNotEmpty(stringBuilder, "content", logContent);
		addIfNotEmpty(stringBuilder, "priority", priority);
		addIfNotEmpty(stringBuilder, "start_time", startTime);
		addIfNotEmpty(stringBuilder, "stop_time", stopTime);
		addIfNotEmpty(stringBuilder, "dlv_from", dlvFrom);
		addIfNotEmpty(stringBuilder, "dlv_to", dlvTo);
		addIfNotEmpty(stringBuilder, "private_flag", privateFlag);
		addIfNotEmpty(stringBuilder, "proc_status", procStatus);
		addIfNotEmpty(stringBuilder, "proc_status_at", procStatusAt);
		addIfNotEmpty(stringBuilder, "proc_error", procError);
		return stringBuilder.toString();
	}

	private void addIfNotEmpty(StringBuilder stringBuilder, String key, Object value)
	{
		if (value != null)
			stringBuilder.append(key + "-" + value.toString() + ";");
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Calendar getTimestamp()
	{
		return timestamp;
	}

	public void setTimestamp(Calendar timestamp)
	{
		this.timestamp = timestamp;
	}

	public String getShortMessage()
	{
		return shortMessage;
	}

	public void setShortMessage(String shortMessage)
	{
		this.shortMessage = shortMessage;
	}

	public Integer getPriority()
	{
		return priority;
	}

	public void setPriority(Integer priority)
	{
		this.priority = priority;
	}

	public Calendar getStartTime()
	{
		return startTime;
	}

	public void setStartTime(Calendar startTime)
	{
		this.startTime = startTime;
	}

	public Calendar getStopTime()
	{
		return stopTime;
	}

	public void setStopTime(Calendar stopTime)
	{
		this.stopTime = stopTime;
	}

	public Long getDlvFrom()
	{
		return dlvFrom;
	}

	public void setDlvFrom(Long dlvFrom)
	{
		this.dlvFrom = dlvFrom;
	}

	public Long getDlvTo()
	{
		return dlvTo;
	}

	public void setDlvTo(Long dlvTo)
	{
		this.dlvTo = dlvTo;
	}

	public PushProcStatus getProcStatus()
	{
		return procStatus;
	}

	public void setProcStatus(PushProcStatus procStatus)
	{
		this.procStatus = procStatus;
	}

	public Calendar getProcStatusAt()
	{
		return procStatusAt;
	}

	public void setProcStatusAt(Calendar procStatusAt)
	{
		this.procStatusAt = procStatusAt;
	}

	public String getProcError()
	{
		return procError;
	}

	public void setProcError(String procError)
	{
		this.procError = procError;
	}

	public String getLogContent()
	{
		return logContent;
	}

	public void setLogContent(String logContent)
	{
		this.logContent = logContent;
	}
}
