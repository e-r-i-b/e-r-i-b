package com.rssl.phizic.messaging.push;

import java.util.Calendar;

/**
 * информация об устройстве клиента на которое будет отправлено push-уведомление
 * @author basharin
 * @ created 27.09.13
 * @ $Author$
 * @ $Revision$
 */

public class PushDevice
{
	private Long id;
	private Calendar timestamp;                         //Время создания сообщения
	private PushEvent pushEvent;                        //связанное сообщение
	private String deviceId;                            //Идентификатор устройства, используемый для адресации push-уведомлений
	private PushProcStatus procStatus;                  //Состояние сообщения
	private Calendar procStatusAt;                      //Время последнего изменения procStatus сообщения
	private String procError;                           //Описание ошибки
	private String mguidHash;                           //Hash функция SHA-1 от значения MGUID

	PushEvent getPushEvent()
	{
		return pushEvent;
	}

	void setPushEvent(PushEvent pushEvent)
	{
		this.pushEvent = pushEvent;
	}

	String getDeviceId()
	{
		return deviceId;
	}

	void setDeviceId(String deviceId)
	{
		this.deviceId = deviceId;
	}

	@Override public String toString()
	{
		StringBuilder stringBuilder = new StringBuilder();
		addIfNotEmpty(stringBuilder, "id", id);
		addIfNotEmpty(stringBuilder, "timestamp", timestamp);
		addIfNotEmpty(stringBuilder, "device_id", deviceId);
		addIfNotEmpty(stringBuilder, "proc_status", procStatus);
		addIfNotEmpty(stringBuilder, "proc_status_at", procStatusAt);
		addIfNotEmpty(stringBuilder, "proc_error", procError);
		addIfNotEmpty(stringBuilder, "mguid", mguidHash);
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

	public void setMguidHash(String mguid)
	{
		this.mguidHash = mguid;
	}

	public String getMguidHash()
	{
		return mguidHash;
	}
}
