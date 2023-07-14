package com.rssl.phizic.logging.registration;

import java.util.Calendar;

/**
 * ѕричина отказа от самосто€тельной регистрации
 * @author basharin
 * @ created 12.12.13
 * @ $Author$
 * @ $Revision$
 */

public class RegistrationFailureReason
{
	private long id;
	private Long loginId;
	private Calendar additionDate;
	private String reason;

	long getId()
	{
		return id;
	}

	void setId(long id)
	{
		this.id = id;
	}

	public Long getLoginId()
	{
		return loginId;
	}

	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	public Calendar getAdditionDate()
	{
		return additionDate;
	}

	public void setAdditionDate(Calendar additionDate)
	{
		this.additionDate = additionDate;
	}

	public String getReason()
	{
		return reason;
	}

	public void setReason(String reason)
	{
		this.reason = reason;
	}
}
