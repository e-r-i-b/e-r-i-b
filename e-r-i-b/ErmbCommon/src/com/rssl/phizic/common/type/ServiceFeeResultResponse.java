package com.rssl.phizic.common.type;

import com.rssl.phizic.common.types.UUID;

import java.util.Calendar;

/**
 * Дополнительные данные по абонентской плате (ЕРИБ - СОС)
 * @author Puzikov
 * @ created 03.06.14
 * @ $Author$
 * @ $Revision$
 */

public class ServiceFeeResultResponse
{
	private String phone;

	private String oldStatus;

	private String currentStatus;

	private Calendar firstDate;

	private Calendar lastDate;

	private UUID correlationId;

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getOldStatus()
	{
		return oldStatus;
	}

	public void setOldStatus(String oldStatus)
	{
		this.oldStatus = oldStatus;
	}

	public String getCurrentStatus()
	{
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus)
	{
		this.currentStatus = currentStatus;
	}

	public Calendar getFirstDate()
	{
		return firstDate;
	}

	public void setFirstDate(Calendar firstDate)
	{
		this.firstDate = firstDate;
	}

	public Calendar getLastDate()
	{
		return lastDate;
	}

	public void setLastDate(Calendar lastDate)
	{
		this.lastDate = lastDate;
	}

	public UUID getCorrelationId()
	{
		return correlationId;
	}

	public void setCorrelationId(UUID correlationId)
	{
		this.correlationId = correlationId;
	}
}
