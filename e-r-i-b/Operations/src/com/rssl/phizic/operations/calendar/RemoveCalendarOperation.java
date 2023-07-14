package com.rssl.phizic.operations.calendar;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.calendar.CalendarService;
import com.rssl.phizic.business.calendar.WorkCalendar;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.util.AllowedDepartmentsUtil;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;

/**
 * @author hudyakov
 * @ created 18.05.2009
 * @ $Author$
 * @ $Revision$
 */

public class RemoveCalendarOperation extends OperationBase implements RemoveEntityOperation
{
	private static final CalendarService calendarService = new CalendarService();
	private WorkCalendar calendar;

	public void initialize(Long id) throws BusinessException
	{
		WorkCalendar temp = calendarService.findCalendarById(id,getInstanceName());
		if (temp == null)
			throw new BusinessException("календарь с id " + id + " не найден");

		Employee empl = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee();

		if(!(empl.isCAAdmin() || AllowedDepartmentsUtil.isDepartmentsAllowedByCode(temp.getTb(), null, null)))
 	    {
		   throw new AccessException("Вы не можете удалить данный календарь,"+
				     " так как не имеете доступа к подразделению банка, для которого он был создан.");
	    }

		calendar = temp;
	}

	public void remove() throws BusinessLogicException, BusinessException
	{
		calendarService.remove(calendar,getInstanceName());
	}

	public Object getEntity()
	{
		return calendar;
	}

	@Override
	protected String getInstanceName()
	{
		return MultiBlockModeDictionaryHelper.getDBInstanceName();
	}
}
