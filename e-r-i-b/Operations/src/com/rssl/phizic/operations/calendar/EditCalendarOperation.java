package com.rssl.phizic.operations.calendar;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.calendar.CalendarService;
import com.rssl.phizic.business.calendar.DublicateCalendarException;
import com.rssl.phizic.business.calendar.WorkCalendar;
import com.rssl.phizic.business.calendar.WorkDay;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.util.AllowedDepartmentsUtil;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.synchronization.EditDictionaryEntityOperationBase;
import com.rssl.phizic.utils.DateHelper;

import java.text.ParseException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Gainanov
 * @ created 26.03.2009
 * @ $Author$
 * @ $Revision$
 */
public class EditCalendarOperation extends EditDictionaryEntityOperationBase implements EditEntityOperation
{
	private static final DepartmentService departmentService = new DepartmentService();
	private static final CalendarService calendarService = new CalendarService();
	private WorkCalendar calendar;
	private Employee employee;

	public void initializeNew() throws BusinessException
	{
		employee = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee();
		calendar = new WorkCalendar();
		calendar.setWorkDays(new HashSet<WorkDay>());
	}

	public void inintialize(Long id) throws BusinessException
	{
		calendar = calendarService.findCalendarById(id,getInstanceName());
		if(calendar == null)
		{
			throw new ResourceNotFoundBusinessException(" алендарь с id=" + id + " не найден", WorkCalendar.class);
		}
		
		employee = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee();

		if(!(employee.isCAAdmin() || AllowedDepartmentsUtil.isDepartmentsAllowedByCode(calendar.getTb(), null, null)))
 	    {
		   throw new AccessException("¬ы не можете отредактировать данный календарь,"+
				     " так как не имеете доступа к подразделению банка, дл€ которого он был создан.");
	    }
	}

	private Set<WorkDay> getDays( boolean isWork)
	{
		Set<WorkDay> set = new HashSet<WorkDay>();
		for(WorkDay day : calendar.getWorkDays())
		{
			if(day.getIsWorkDay() && isWork)
				set.add(day);
			if(!day.getIsWorkDay() && !isWork)
				set.add(day);
		}
		return set;
	}

	public Set<WorkDay> getHolidays()
	{
		return getDays( false);
	}

	public Set<WorkDay> getWorkdays() throws BusinessException
	{
		return getDays( true);
	}

	private Set<WorkDay> getDaysSet(String days, boolean isWork) throws ParseException
	{
		Set<WorkDay> set = new HashSet<WorkDay>();
		for(String day : days.split(";"))
		{
			if(day.equals(""))
				continue;
			Calendar date = DateHelper.parseCalendar(day);
			WorkDay workDay = new WorkDay();
			workDay.setDate(date);
			workDay.setIsWorkDay(isWork);
			set.add(workDay);
		}
		return set;
	}

	/**
	 * добавить рабочие дни в календарь
	 * @param days набор рабочих дней
	 * @param calendar календарь куда добавл€ем
	 */
	private void addDaysToCalendar(Set<WorkDay> days, WorkCalendar calendar)
	{
		calendar.getWorkDays().addAll(days);
	}

	/**
	 * удалить рабочие дни из календар€
	 * @param days набор рабочих дней
	 * @param calendar календарь, из которого удал€ем
	 */
	private void removeDaysFromCalendar(Set<WorkDay> days, WorkCalendar calendar)
	{
		calendar.getWorkDays().removeAll(days);
	}

	public void setHolidays(String days) throws ParseException
	{
		Set<WorkDay> newSet = getDaysSet(days, false);
		Set<WorkDay> oldSet = getDays( false);
		removeDaysFromCalendar(oldSet, calendar);
		addDaysToCalendar(newSet, calendar);
	}

	public void setWorkdays(String days) throws ParseException
	{
		Set<WorkDay> newSet = getDaysSet(days, true);
		Set<WorkDay> oldSet = getDays( true);
		removeDaysFromCalendar(oldSet, calendar);
		addDaysToCalendar(newSet, calendar);
	}

	public void setName(String name)
	{
		calendar.setName(name);
	}

	public String getName()
	{
		return calendar.getName();
	}

	public void doSave() throws BusinessException, BusinessLogicException
	{
		if(!employee.isCAAdmin() && calendar.getTb() == null)
			throw new BusinessLogicException("ѕожалуйста, введите значение в поле \"“Ѕ\".");

		try
		{
			calendarService.addOrUpdate(calendar, getInstanceName());
		}
		catch (DublicateCalendarException ex)
		{
			throw new BusinessLogicException(ex.getMessage(), ex);
		}
	}

	public WorkCalendar getEntity() throws BusinessException, BusinessLogicException
	{
		return calendar;
	}

	public String getRegion() throws BusinessException
	{
		return calendar.getTb();
	}

	/**
	 * @return €вл€етс€ ли текущий сотрудник сотрудником ÷ј.
	 */
	public boolean isCAAdmin()
	{
		return employee.isCAAdmin();
	}

}
