package com.rssl.phizic.messaging.push;

import com.rssl.phizic.logging.push.PushEventType;

import java.util.Calendar;

/**
 * Push уведомление о новом сообщении отправляемое Фронтальной транспортной системе
 * @author basharin
 * @ created 07.08.13
 * @ $Author$
 * @ $Revision$
 */

public class PushEvent
{
	private Long id;
	private Calendar timestamp;                         //Время создания сообщения
	private String eventId;                             //Идентификатор уведомления на стороне ЕРИБ
	private String clientId;                            //Идентификатор клиента
	private String shortMessage;                        //Сокращенная форма сообщения. Предназначена для отображения в списке сообщений в виде заголовка
	private String smsMessage;                          //текста смс-уведомления отправляемого в случае неудачи отправки push-уведомления
	private String systemCode;                          //Код системы, отправляющая уведомление
	private PushEventType typeCode;                     //Код типа уведомления
	private String content;                             //Расширенное содержание уведомления, доступное клиенту после выбора уведомления из списка.	Значение данного поля будет передано push сервером мобильному приложению
	private String logContent;                          //Расширенное содержание уведомления, доступное клиенту после выбора уведомления из списка.	Аналог верхнего поля с маскированным паролем
	private Integer priority;                           //Приоритет отправки сообщения. При значении 0 приоритет является наивысшим и снижается по мере увеличения значения
	private Calendar startTime;                         //Дата и время начала отложенной отправки исходящего сообщения
	private Calendar stopTime ;                         //Дата и время окончания отложенной отправки исходящего сообщения
	private Long dlvFrom;                               //Начало разрешенного периода отправки сообщения в минутах с начала суток. Указывается число, обозначающее часы*60+минуты
	private Long dlvTo ;                                //Окончание разрешенного периода отправки сообщения в минутах с начала суток. Указывается число, обозначающее часы*60+минуты
	private String privateFlag;                         //Признак конфендициальности уведомления. Y – для получения содержимого уведомления на push сервере необходима аутентификация клиента.
														//                                        N – для получения содержимого уведомления на push сервере аутентификация клиента не требуется
	private PushProcStatus procStatus;                  //Состояние сообщения
	private Calendar procStatusAt;                      //Время последнего изменения procStatus сообщения
	private String procError;                           //Описание ошибки

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
