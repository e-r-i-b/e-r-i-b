package com.rssl.phizic.gate.loans;

/**
 * @author sergunin
 * @ created 17.06.15
 * @ $Author$
 * @ $Revision$
 */

public class LoanMonthPayment
{
    /**
     * Год
     */
    private final int year;

    /**
     * Номер месяца
     */
    private final int monthNumber;

    private final ScheduleItem scheduleItem;

    public LoanMonthPayment(int year, int monthNumber, ScheduleItem scheduleItem) {
        this.year = year;
        this.monthNumber = monthNumber;
        this.scheduleItem = scheduleItem;
    }

    public int getYear()
    {
        return year;
    }

    public int getMonthNumber()
    {
        return monthNumber;
    }

    public ScheduleItem getScheduleItem()
    {
        return scheduleItem;
    }
}
