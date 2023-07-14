package com.rssl.phizic.operations.calendar;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.calendar.CalendarService;
import com.rssl.phizic.business.calendar.WorkDay;
import com.rssl.phizic.operations.OperationBase;

import java.util.Date;
import java.util.List;

/**
 * Получение списка рабочих дней
 * @author basharin
 * @ created 19.06.15
 * @ $Author$
 * @ $Revision$
 */

public class AsyncGetWorkDaysOperation extends OperationBase
{
	private static CalendarService calendarService = new CalendarService();

	private Date fromDate;
	private Date toDate;
	private String tb;

	public void initialize(Date fromDate, Date toDate, String tb)
	{
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.tb = tb;
	}

	public List<WorkDay> getWorkDays() throws BusinessException
	{
		if (fromDate == null || toDate == null || tb == null)
			throw new IllegalArgumentException("Операция не инициализирована");
		return calendarService.findWorkDaysForPeriod(fromDate, toDate, tb);
	}
}
