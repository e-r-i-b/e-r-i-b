package com.rssl.phizic.business.web;

/**
 * @author Gololobov
 * @ created 09.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class LoanNotificationWidget extends Widget
{
	//Номер счета кредита клиента
	private String loanAccountNumber;
	////За сколько до дня расплаты оповещать клиента об очередном платеже по кредиту
	private int loanNotifyDayCount = 5;

	public String getLoanAccountNumber()
	{
		return loanAccountNumber;
	}

	public void setLoanAccountNumber(String loanAccountNumber)
	{
		this.loanAccountNumber = loanAccountNumber;
	}

	public int getLoanNotifyDayCount()
	{
		return loanNotifyDayCount;
	}

	public void setLoanNotifyDayCount(int loanNotifyDayCount)
	{
		this.loanNotifyDayCount = loanNotifyDayCount;
	}

	@Override
	protected Widget clone()
	{
		return super.clone();
	}
}
