package com.rssl.phizic.common.type;

import com.rssl.phizic.common.types.UUID;

import java.util.Calendar;

/**
 * Cообщение об изменении данных клиента в ЕРМБ
 * @author Rtischeva
 * @ created 14.03.2013
 * @ $Author$
 * @ $Revision$
 */
public class UpdateClientRequest
{
	private String rqVersion;

	private UUID rqUID;

	private Calendar rqTime;

	private ErmbProfileIdentity clientIdentity;

	private ErmbProfileIdentity clientOldIdentity;

	public String getRqVersion()
	{
		return rqVersion;
	}

	public void setRqVersion(String rqVersion)
	{
		this.rqVersion = rqVersion;
	}

	public UUID getRqUID()
	{
		return rqUID;
	}

	public void setRqUID(UUID rqUID)
	{
		this.rqUID = rqUID;
	}

	public Calendar getRqTime()
	{
		return rqTime;
	}

	public void setRqTime(Calendar rqTime)
	{
		this.rqTime = rqTime;
	}

	public ErmbProfileIdentity getClientIdentity()
	{
		return clientIdentity;
	}

	public void setClientIdentity(ErmbProfileIdentity clientIdentity)
	{
		this.clientIdentity = clientIdentity;
	}

	public ErmbProfileIdentity getClientOldIdentity()
	{
		return clientOldIdentity;
	}

	public void setClientOldIdentity(ErmbProfileIdentity clientOldIdentity)
	{
		this.clientOldIdentity = clientOldIdentity;
	}
}
