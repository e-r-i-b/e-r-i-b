package com.rssl.phizgate.ext.sbrf.technobreaks;

import com.rssl.phizic.common.types.UUID;

import java.io.Serializable;
import java.util.Calendar;

/**
 * —ущность технологического перерыва
 * @author niculichev
 * @ created 10.10.2011
 * @ $Author$
 * @ $Revision$
 */
public class TechnoBreak implements Serializable
{
	private Long id;
	private UUID uuid;          //уникальный кроссблочный идентификатор
	private String adapterUUID; // идентификатор внешней системы
	private Calendar fromDate; // дата начала действи€
	private Calendar toDate; // дата конца действи€
	private PeriodicType periodic;  // периодичность
	private String message; // сообщение клиенту
	private boolean defaultMessage = true; // тип сообщени€ (true сообщение по умолчанию)
	private TechnoBreakStatus status; // статус
	private boolean autoEnabled; //выставлен автоматически
	private boolean allowOfflinePayments; //разрешены ли платежи при недоступной ¬—

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Calendar getFromDate()
	{
		return fromDate;
	}

	public void setFromDate(Calendar fromDate)
	{
		this.fromDate = fromDate;
	}

	public Calendar getToDate()
	{
		return toDate;
	}

	public void setToDate(Calendar toDate)
	{
		this.toDate = toDate;
	}

	public PeriodicType getPeriodic()
	{
		return periodic;
	}

	public void setPeriodic(PeriodicType periodic)
	{
		this.periodic = periodic;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public boolean isDefaultMessage()
	{
		return defaultMessage;
	}

	public void setDefaultMessage(boolean defaultMessage)
	{
		this.defaultMessage = defaultMessage;
	}

	public TechnoBreakStatus getStatus()
	{
		return status;
	}

	public void setStatus(TechnoBreakStatus status)
	{
		this.status = status;
	}

	public String getAdapterUUID()
	{
		return adapterUUID;
	}

	public void setAdapterUUID(String adapterUUID)
	{
		this.adapterUUID = adapterUUID;
	}

	public boolean isAutoEnabled()
	{
		return autoEnabled;
	}

	public void setAutoEnabled(boolean autoEnabled)
	{
		this.autoEnabled = autoEnabled;
	}

	public boolean isAllowOfflinePayments()
	{
		return allowOfflinePayments;
	}

	public void setAllowOfflinePayments(boolean allowOfflinePayments)
	{
		this.allowOfflinePayments = allowOfflinePayments;
	}

	public UUID getUuid()
	{
		return uuid;
	}

	public void setUuid(UUID uuid)
	{
		this.uuid = uuid;
	}
}
