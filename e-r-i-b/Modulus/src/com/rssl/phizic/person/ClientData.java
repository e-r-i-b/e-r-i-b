package com.rssl.phizic.person;

import java.util.Calendar;

/**
 * обертка для передачи данных о клиенте в PushTransportService
 * @author basharin
 * @ created 25.10.13
 * @ $Author$
 * @ $Revision$
 */

public abstract class ClientData
{
	protected Long loginId;
	protected String surName;
	protected String firstName;
	protected String patrName;
	protected String documentSeriesNumber;
	protected Calendar birthDay;
	protected String tb;

	public Long getLoginId()
	{
		return loginId;
	}

	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	public String getSurName()
	{
		return surName;
	}

	public void setSurName(String surName)
	{
		this.surName = surName;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getPatrName()
	{
		return patrName;
	}

	public void setPatrName(String patrName)
	{
		this.patrName = patrName;
	}

	public String getDocumentSeriesNumber()
	{
		return documentSeriesNumber;
	}

	public void setDocumentSeriesNumber(String documentSeriesNumber)
	{
		this.documentSeriesNumber = documentSeriesNumber;
	}

	public Calendar getBirthDay()
	{
		return birthDay;
	}

	public void setBirthDay(Calendar birthDay)
	{
		this.birthDay = birthDay;
	}

	public String getTb()
	{
		return tb;
	}

	public void setTb(String tb)
	{
		this.tb = tb;
	}
}
