package com.rssl.phizic.common.type;

import com.rssl.phizic.common.types.UUID;

import java.util.Calendar;
import java.util.List;

/**
 * @author osminin
 * @ created 02.08.14
 * @ $Author$
 * @ $Revision$
 *
 * сообщение о подтверждени полученных профилях ЕРМБ
 */
public class ConfirmProfilesRequest
{
	private String rqVersion;
	private UUID rqUID;
	private Calendar rqTime;
	private List<ErmbUpdateProfileInfo> confirmProfilesRqInfo;

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

	public List<ErmbUpdateProfileInfo> getConfirmProfilesRqInfo()
	{
		return confirmProfilesRqInfo;
	}

	public void setConfirmProfilesRqInfo(List<ErmbUpdateProfileInfo> confirmProfilesRqInfo)
	{
		this.confirmProfilesRqInfo = confirmProfilesRqInfo;
	}
}
