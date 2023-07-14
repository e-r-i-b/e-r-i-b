package com.rssl.phizic.common.type;

import com.rssl.phizic.common.types.UUID;
import com.rssl.phizic.utils.PhoneNumber;

import java.util.Calendar;

/**
 * @author Erkin
 * @ created 14.01.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * СМС-запрос
 */
public class SmsXmlRequest
{
	private String rqVersion;

	private Calendar rqTime;

	private UUID rqUID;

	private PhoneNumber phone;

	private String text;

	private UUID eribSID;

	///////////////////////////////////////////////////////////////////////////

	public String getRqVersion()
	{
		return rqVersion;
	}

	public void setRqVersion(String rqVersion)
	{
		this.rqVersion = rqVersion;
	}

	public Calendar getRqTime()
	{
		return rqTime;
	}

	public void setRqTime(Calendar rqTime)
	{
		this.rqTime = rqTime;
	}

	public UUID getRqUID()
	{
		return rqUID;
	}

	public void setRqUID(UUID rqUID)
	{
		this.rqUID = rqUID;
	}

	public PhoneNumber getPhone()
	{
		return phone;
	}

	public void setPhone(PhoneNumber phone)
	{
		this.phone = phone;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public UUID getEribSID()
	{
		return eribSID;
	}

	public void setEribSID(UUID eribSID)
	{
		this.eribSID = eribSID;
	}
}
