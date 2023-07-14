package com.rssl.phizic.operations.ext.sbrf.mail;

import com.rssl.phizic.auth.BankLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.employees.EmployeeService;
import com.rssl.phizic.business.mail.Mail;
import com.rssl.phizic.business.mail.MailHelper;
import com.rssl.phizic.business.mail.reassign.history.ReassignMailHistoryService;
import com.rssl.phizic.business.mail.reassign.history.ReassignMailReason;
import com.rssl.phizic.security.PermissionUtil;

import java.util.Collections;
import java.util.List;

/**
 * @author lepihina
 * @ created 20.08.13
 * @ $Author$
 * @ $Revision$
 */
public class ViewMailOperation extends EditMailOperation
{
	private static final EmployeeService employeeService = new EmployeeService();
	private static final ReassignMailHistoryService reassignMailHistoryService = new ReassignMailHistoryService();
	private Employee responsibleEmployee;
	private List<ReassignMailReason> reassignHistory = Collections.emptyList();

	public void initialize(Long id, boolean fromQuestionary) throws BusinessException
	{
		//инициализируем клиентом
		initializeContext();
		Mail tempMail = findMailById(id);
		if (tempMail == null)
			throw new ResourceNotFoundBusinessException("Письмо с id=" + id + " не найдено", Mail.class);

		//проверяем доступ к письму
		checkMailAccess(tempMail);
		mail = tempMail;
		if(PermissionUtil.impliesOperation("EditMailOperation", "MailManagment") && !fromQuestionary)
		{
			//Закрепляем письмо за сотрудником открывшим письмо.
			setResponsibleEmployee(mail, getLogin());

			//если просматриваем письмо, то читаем его
			if (!mail.getSender().equals(getLogin()))
				markMailReceived(mail);
		}

		if(mail.getEmployee() != null)
		{
			responsibleEmployee = employeeService.findByLogin((BankLogin)mail.getEmployee());
			reassignHistory = reassignMailHistoryService.getReassignMailHistoryByMailId(id);
		}
	}

	/**
	 * Инициализация черновика для редактирования
	 * @param id идентификатор
	 * @return идентификатор
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public Long initializeDraft(Long id) throws BusinessException, BusinessLogicException
	{
		initialize(id);
		if(mail.getState() != getMailDraftState())
			throw new ResourceNotFoundBusinessException("Письмо с id=" + id + " не найдено", Mail.class);
		checkReply(mail);
		return mail.getId();
	}

	/**
	 * @return ФИО ответственного сотрудника
	 */
	public String getResponsibleEmployeeFio()
	{
		return responsibleEmployee== null ? null : responsibleEmployee.getFullName();
	}

	/**
	 * @return история переназначений
	 */
	public List<ReassignMailReason> getReassignHistory()
	{
		return Collections.unmodifiableList(reassignHistory);
	}
}
