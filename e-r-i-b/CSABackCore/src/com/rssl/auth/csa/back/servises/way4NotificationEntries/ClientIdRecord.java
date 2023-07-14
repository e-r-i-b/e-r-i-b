package com.rssl.auth.csa.back.servises.way4NotificationEntries;

import com.rssl.auth.csa.back.servises.ActiveRecord;
import java.util.Calendar;

/**
 * @author vagin
 * @ created 11.11.14
 * @ $Author$
 * @ $Revision$
 */
public class ClientIdRecord extends ActiveRecord
{
	private String clientId;
	private Calendar amndDate;
	private Long profileId;
	private Long id;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getProfileId()
	{
		return profileId;
	}

	public void setProfileId(Long profileId)
	{
		this.profileId = profileId;
	}

	public Calendar getAmndDate()
	{
		return amndDate;
	}

	public void setAmndDate(Calendar amndDate)
	{
		this.amndDate = amndDate;
	}

	public String getClientId()
	{
		return clientId;
	}

	public void setClientId(String clientId)
	{
		this.clientId = clientId;
	}
}
