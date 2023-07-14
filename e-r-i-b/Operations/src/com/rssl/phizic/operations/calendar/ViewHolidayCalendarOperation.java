package com.rssl.phizic.operations.calendar;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.calendar.CalendarService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.operations.OperationBase;

import java.util.Calendar;

/**
 * User: Balovtsev
 * Date: 14.09.2011
 * Time: 10:53:48
 */
public class ViewHolidayCalendarOperation extends OperationBase
{
	private final static CalendarService   calendarService   = new CalendarService();
	private final static DepartmentService departmentService = new DepartmentService();

	private Department department;

	public void initialize(Long departmentId) throws BusinessException
	{
		department = departmentService.findById(departmentId);

		if (department == null)
		{
			throw new BusinessException("ƒепартамент с id=" + departmentId + " не найден.");
		}
	}

	/**
	 *
	 * ѕровер€етс€ €вл€етс€ ли дата выходным днем или нет
	 *
	 * @param date провер€ема€ дата
	 * @param paymentType тип платежа
	 * @return boolean
	 * @throws BusinessException
	 */
	public boolean isHoliday(Calendar date, String paymentType) throws BusinessException
	{
		return calendarService.isHoliday(date, department, paymentType);
	}

}
