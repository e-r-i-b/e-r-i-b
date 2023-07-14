package com.rssl.phizic.logging.push;

import java.util.Calendar;

/**
 * Запись в журнал статистики по регистрациям устройств для push-сообщений
 * @author basharin
 * @ created 31.10.13
 * @ $Author$
 * @ $Revision$
 */

public class PushQueueDeviceLogEntry implements StatisticLogEntry
{
	private Long id;
	private Calendar creationDate;                       //Время создания сообщения
	private String clientId;                             //Идентификатор клиента
	private String mguidHash;                            //Hash функция SHA-1 от значения MGUID
	private PushDeviceStatus type;                       //Статус подключения push уведомлений у получателя

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Calendar getCreationDate()
	{
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate)
	{
		this.creationDate = creationDate;
	}

	public String getClientId()
	{
		return clientId;
	}

	public void setClientId(String clientId)
	{
		this.clientId = clientId;
	}

	public String getMguidHash()
	{
		return mguidHash;
	}

	public void setMguidHash(String mguidHash)
	{
		this.mguidHash = mguidHash;
	}

	public PushDeviceStatus getType()
	{
		return type;
	}

	public void setType(PushDeviceStatus type)
	{
		this.type = type;
	}
}
