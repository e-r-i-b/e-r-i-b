package com.rssl.phizic.web.common.mobile.finances;

import com.rssl.phizic.web.atm.finances.ShowFinanceStructureForm;

/**
 * @author Mescheryakova
 * @ created 30.03.2012
 * @ $Author$
 * @ $Revision$
 */

public class FinanceATMFormBase extends ShowFinanceStructureForm
{
	private String from;
	private String to;
	private String incomeType;
	private boolean showCash;
	private boolean showOtherAccounts;

	public String getFrom()
	{
		return from;
	}

	public void setFrom(String from)
	{
		this.from = from;
	}

	public String getTo()
	{
		return to;
	}

	public void setTo(String to)
	{
		this.to = to;
	}

	public String getIncomeType()
	{
		return incomeType;
	}

	public void setIncomeType(String incomeType)
	{
		this.incomeType = incomeType;
	}

	public boolean isShowCash()
	{
		return showCash;
	}

	public void setShowCash(boolean showCash)
	{
		this.showCash = showCash;
	}

	public boolean isShowOtherAccounts()
	{
		return showOtherAccounts;
	}

	public void setShowOtherAccounts(boolean showOtherAccounts)
	{
		this.showOtherAccounts = showOtherAccounts;
	}	
}
