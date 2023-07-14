package com.rssl.phizic.operations.log;

import com.rssl.phizic.auth.BankLogin;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.employees.EmployeeService;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.logging.operations.GuestLogEntry;
import com.rssl.phizic.logging.operations.LogEntry;
import com.rssl.phizic.logging.operations.LogEntryBase;
import com.rssl.phizic.security.SecurityDbException;

/**
 * @author Roshka
 * @ created 14.03.2006
 * @ $Author$
 * @ $Revision$
 */

/**
 * загрузка лога операций пользовател€.
 */
public class DownloadUserLogOperation extends LogFilterOperationBase
{
	private static final SimpleService service = new SimpleService();
	private static final PersonService personService = new PersonService();
	private static final EmployeeService employeeService = new EmployeeService();
	private static final SecurityService securityService = new SecurityService();

	protected LogEntry logEntry;
	private GuestLogEntry guestLogEntry;
	private Long departmentId;
	private boolean isGuest;

	/**
	 *
	 * @param id идентификатор сущности лога
	 * @param type тип лога: guest - гостевой журнал, simple - обычный
	 * @throws BusinessException
	 */
	public void initialize(Long id, String type) throws BusinessException
	{
		if(id == null)
			throw new BusinessException("id не может быть null");
		if (type == null)
			throw new BusinessException("ѕараметр типа сущности не может быть null");
		if (type.equals("simple"))
		{
			LogEntry temp = service.findById(LogEntry.class, id, getInstanceName());
			if (temp == null)
				throw new BusinessException("Ќе установлены параметры операции");
			logEntry = temp;
			isGuest = false;
		}
		else if (type.equals("guest"))
		{
			GuestLogEntry temp = service.findById(GuestLogEntry.class, id, getInstanceName());
			if (temp == null)
				throw new BusinessException("Ќе установлены параметры операции");
			guestLogEntry = temp;
			isGuest = true;
		}
		else
			throw new BusinessException("Ќедопустимое значение типа сущности type=" + type);
	}

	public LogEntryBase getEntity() throws BusinessException
	{
		return isGuest ? guestLogEntry : logEntry;
	}

	public void setDepartmentId(Long departmentId)
	{
		this.departmentId = departmentId;
	}

	/**
	 * @return €вл€етс€ ли журнал гостевым
	 */
	public boolean isGuest()
	{
		return isGuest;
	}

	public Long getDepartmentId()
	{
		return departmentId;
	}

	public String getFullName() throws BusinessException
	{
		if (isGuest)
		{
			if(guestLogEntry!=null)
				return guestLogEntry.getFullName();

			return null;
		}

		try
		{
			if (logEntry!=null && logEntry.getLoginId()!=null && !logEntry.getLoginId().toString().equals("0"))
			{
				CommonLogin login = securityService.findById(logEntry.getLoginId());
				if (login instanceof BankLogin) return employeeService.findByLogin((BankLogin) login).getFullName();
				else return personService.findPersonByLogin((Login) login).getFullName();
			}
		}
		catch (SecurityDbException ex)
		{
			throw new BusinessException(ex);
		}

		return null;
	}	
}