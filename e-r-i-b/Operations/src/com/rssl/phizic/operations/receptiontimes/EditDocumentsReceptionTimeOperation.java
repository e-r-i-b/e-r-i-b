package com.rssl.phizic.operations.receptiontimes;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.calendar.CalendarService;
import com.rssl.phizic.business.calendar.WorkCalendar;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.receptiontimes.ReceptionTime;
import com.rssl.phizic.business.receptiontimes.ReceptionTimeService;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.context.EmployeeData;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.synchronization.EditDictionaryEntityOperationBase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Pakhomova
 * @created: 18.08.2009
 * @ $Author$
 * @ $Revision$
 */
public class EditDocumentsReceptionTimeOperation extends EditDictionaryEntityOperationBase implements EditEntityOperation
{
	private DepartmentService departmentService = new DepartmentService();
	private ReceptionTimeService timeService = new ReceptionTimeService();
	private CalendarService calendarService = new CalendarService();

	private Department department;
	private List<ReceptionTime> receptionTimes = new ArrayList<ReceptionTime>();
	private List<WorkCalendar> calendars = new ArrayList<WorkCalendar>();

	public void initialize(Long departmentId) throws BusinessException
	{
		department = departmentService.findById(departmentId, getInstanceName());
		receptionTimes = timeService.findByDepartmentId(department.getId(),getInstanceName());
		if (receptionTimes.size() == 0)
		{
			receptionTimes = timeService.buildNewReceptionTimes(departmentId);
		}
		EmployeeData employeeData = EmployeeContext.getEmployeeDataProvider().getEmployeeData();
		if(employeeData.isAllTbAccess() || employeeData.getEmployee().isCAAdmin())
			calendars = calendarService.getAllCalendars(getInstanceName());
		else
			calendars = calendarService.getAllowedCalendars(MultiBlockModeDictionaryHelper.getEmployeeData().getLoginId(), getInstanceName());
	}

	public List<ReceptionTime> getEntity()
	{
		return receptionTimes;
	}

	public void doSave() throws BusinessException
	{
		timeService.save(receptionTimes, getInstanceName());
	}

	// получаем время приема документов вышестоящего департамента независимо
	public ReceptionTime getParentRecepionTime(String paymentType) throws BusinessException
	{
		Department parent = departmentService.getParent(department);
		if (parent == null)
		{
			return null;
		}
		return timeService.getRecepionTime(parent, paymentType);
	}

	public WorkCalendar getCalendarById(Long calendarId) throws BusinessException
	{
		if (calendarId == null)
			return null;
		return calendarService.findCalendarById(calendarId, getInstanceName());
	}

	public List<WorkCalendar> getCalendars()
	{
		return calendars;
	}

	public void setReceptionTimes(List<ReceptionTime> receptionTimes)
	{
		this.receptionTimes = receptionTimes;
	}

	public Department getDepartment()
	{
		return department;
	}
}
