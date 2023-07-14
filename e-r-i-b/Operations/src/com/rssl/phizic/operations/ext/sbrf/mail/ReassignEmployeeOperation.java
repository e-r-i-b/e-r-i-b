package com.rssl.phizic.operations.ext.sbrf.mail;

import com.rssl.phizic.auth.BankLogin;
import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.employees.EmployeeService;
import com.rssl.phizic.business.mail.Mail;
import com.rssl.phizic.business.mail.MailService;
import com.rssl.phizic.business.mail.reassign.history.ReassignMailHistoryService;
import com.rssl.phizic.business.mail.reassign.history.ReassignMailReason;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.csaadmin.EmployeeImportService;
import com.rssl.phizic.security.SecurityDbException;
import org.apache.commons.collections.CollectionUtils;

import java.util.Calendar;
import java.util.List;

/**
 * Операция переназначения ответственного сотрудника
 * @author komarov
 * @ created 14.10.13 
 * @ $Author$
 * @ $Revision$
 */

public class ReassignEmployeeOperation  extends OperationBase implements EditEntityOperation
{
	private static final MailService service =  new MailService();
	private static final EmployeeService employeeService = new EmployeeService();
	private static final ReassignMailHistoryService reassignMailHistoryService = new ReassignMailHistoryService();
	private static final SecurityService securityService = new SecurityService();
	private static final EmployeeImportService employeeImportService = new EmployeeImportService();
	private Mail mail;
	private ReassignMailReason reassignMailReason;

	/**
	 * Инициализирует операцию
	 * @param id идентификатор письма
	 * @param loginId идентификатор сотрудника
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize(Long id, String loginId)  throws BusinessException, BusinessLogicException
	{
		try
		{
			BankLogin login = securityService.getActiveBankLoginByCsaUserId(loginId);
			if (login == null)
				initialize(id, employeeImportService.importEmployee(loginId));
			else
				initialize(id, getEmployee(login));
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException("Ошибка поиска логина сотрудника по CsaUserId=" + loginId, e);
		}
	}

	/**
	 * Инициализирует операцию
	 * @param id идентификатор письма
	 * @param loginId идентификатор сотрудника в блоке
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize(Long id, Long loginId)  throws BusinessException, BusinessLogicException
	{
		BankLogin employeeLogin = (BankLogin) employeeService.findLoginById(loginId, null);
		if(employeeLogin == null)
			throw new BusinessException("Логин сотрудника не найден.");

		initialize(id, getEmployee(employeeLogin));
	}

	private Employee getEmployee(BankLogin employeeLogin) throws BusinessException
	{
		Employee employee = employeeService.findByLogin(employeeLogin);
		if(employee == null)
			throw new BusinessException("Сотрудник не найден.");

		return employee;
	}

	/**
	 * Инициализирует операцию
	 * @param id идентификатор письма
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	private void initialize(Long id, Employee employee)  throws BusinessException, BusinessLogicException
	{
		mail = service.findMailById(id);
		if(mail == null)
			throw new ResourceNotFoundBusinessException("Письмо с id=" + id + " не найдено", Mail.class);

		BankLogin employeeLogin = employee.getLogin();
		List<ReassignMailReason> reassignHistory = reassignMailHistoryService.getReassignMailHistoryByMailId(id);
		if(employeeLogin.equals(mail.getEmployee()) && CollectionUtils.isNotEmpty(reassignHistory))
			reassignMailReason = reassignHistory.get(0);
		else
		{
			reassignMailReason = new ReassignMailReason();
			reassignMailReason.setMailId(id);
			reassignMailReason.setEmployeeFIO(employee.getFullName());
			mail.setEmployee(employeeLogin);
		}
		reassignMailReason.setDate(Calendar.getInstance());
	}


	@Transactional
	public void save() throws BusinessException, BusinessLogicException
	{
		reassignMailHistoryService.saveReassignMailReason(reassignMailReason);
		service.addOrUpdate(mail);
	}

	public Mail getEntity()
	{
		return mail;
	}

	/**
	 * @return причина переназначения
	 */
	public ReassignMailReason getReason()
	{
		return reassignMailReason;
	}
}
