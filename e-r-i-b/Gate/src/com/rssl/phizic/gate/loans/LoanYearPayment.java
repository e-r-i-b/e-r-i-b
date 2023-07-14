package com.rssl.phizic.gate.loans;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sergunin
 * @ created 17.06.15
 * @ $Author$
 * @ $Revision$
 */

public class LoanYearPayment
{
    /**
   	 * Год
   	 */
   	private int year;

   	/**
   	 * За месяц
   	 */
   	private List<LoanMonthPayment> months;

   	public LoanYearPayment(int year, List<LoanMonthPayment> months)
   	{
   		this.year = year;
   		this.months = months;
   	}

   	public LoanYearPayment(int year)
   	{
   		this.year = year;
   	}

    public int getYear()
    {
        return year;
    }

    public List<LoanMonthPayment> getMonths()
    {
        if (months == null)
            months = new ArrayList<LoanMonthPayment>();
        return months;
    }
}
