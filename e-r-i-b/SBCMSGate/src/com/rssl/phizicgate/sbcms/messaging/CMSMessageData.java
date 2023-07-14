package com.rssl.phizicgate.sbcms.messaging;

import com.rssl.phizgate.common.messaging.StringMessageData;

import java.util.Calendar;

/**
 * @author Egorova
 * @ created 04.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class CMSMessageData  extends StringMessageData
{
	private Calendar date;
	private String abonent;

	public String getAbonent()
	{
		return abonent;
	}

	public void setAbonent(String abonent)
	{
		this.abonent = abonent;
	}

	public Calendar getDate()
	{
		return date;
	}

	public void setDate(Calendar date)
	{
		this.date = date;
	}
}
