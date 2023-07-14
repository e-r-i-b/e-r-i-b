package com.rssl.phizic.business.finances;

import java.util.Calendar;

/**
 * Операция для построения графиков за период на странице "Операции"
 *
 * @author lepihina
 * @ created 10.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class BalanceByOperations
{
	Calendar date; // дата операции

	public Calendar getDate()
	{
		return date;
	}

	public void setDate(Calendar date)
	{
		this.date = date;
	}
}
