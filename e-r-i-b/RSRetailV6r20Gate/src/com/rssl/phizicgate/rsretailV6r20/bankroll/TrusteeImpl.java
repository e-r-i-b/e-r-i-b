package com.rssl.phizicgate.rsretailV6r20.bankroll;

import com.rssl.phizic.gate.bankroll.Trustee;

import java.util.Calendar;

/**
 * @author: Pakhomova
 * @created: 24.11.2008
 * @ $Author$
 * @ $Revision$
 */
public class TrusteeImpl implements Trustee
{
	/*дата окончания доверенности*/
	private Calendar endingDate;

	/*ФИО доверенного лица*/
	private String fullName;

	public Calendar getEndingDate()
	{
		return endingDate;
	}

	public void setEndingDate(Calendar endingDate)
	{
		this.endingDate = endingDate;
	}

	public String getName()
	{
		return fullName;
	}

	public void setName(String name)
	{
		fullName = name;
	}

}
