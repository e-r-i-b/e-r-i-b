package com.rssl.phizicgate.sbrf.bankroll;

import com.rssl.phizic.gate.bankroll.Trustee;

import java.util.Calendar;

/**
 * @author Omeliyanchuk
 * @ created 08.04.2008
 * @ $Author$
 * @ $Revision$
 */

public class TrusteeImpl implements Trustee
{
	private Calendar endingDate;
	private String name;

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
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
