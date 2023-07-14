package com.rssl.phizic.operations.person.list;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.auth.BlockingReasonType;
import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;
import java.util.Date;

/**
 * @author mihaylov
 * @ created 25.04.14
 * @ $Author$
 * @ $Revision$
 * Блокировка пользователя в ЕРИБЕ из списка клиентов ЦСА. CHG071536
 */
public class ChangeActivePersonLockOperation extends OperationBase
{
	private static final PersonService personService = new PersonService();
	private static final SecurityService securityService = new SecurityService();

	private Person person;
	private UserInfo userInfo;

	public void initialize(Long personId, UserInfo userInfo) throws BusinessException
	{
		person = personService.findById(personId);
		if(person == null)
			throw new ResourceNotFoundBusinessException("Не найден клиент с идентификатором " + personId,Person.class);
		this.userInfo = userInfo;
	}

	@Transactional
	public void lock(String reason, Date blockedFrom, Date blockedUntil) throws BusinessException, BusinessLogicException
	{
		Employee employee = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee();
		try
		{
			securityService.lock(person.getLogin(),blockedFrom,blockedUntil, BlockingReasonType.employee,reason,employee.getLogin().getId(),null);
			CSABackRequestHelper.sendLockProfileCHG071536Rq(userInfo, DateHelper.toCalendar(blockedFrom), DateHelper.toCalendar(blockedUntil), reason, employee.getFullName());
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (BackException e)
		{
			throw new BusinessException(e);
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}
	}

	@Transactional
	public void unlock() throws BusinessException, BusinessLogicException
	{
		try
		{
			securityService.unlock(person.getLogin(),false,null, Calendar.getInstance().getTime());
			CSABackRequestHelper.sendUnlockProfileRq(userInfo);
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (BackException e)
		{
			throw new BusinessException(e);
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}
	}

}
