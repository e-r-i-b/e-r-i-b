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
		//�������������� ��������
		initializeContext();
		Mail tempMail = findMailById(id);
		if (tempMail == null)
			throw new ResourceNotFoundBusinessException("������ � id=" + id + " �� �������", Mail.class);

		//��������� ������ � ������
		checkMailAccess(tempMail);
		mail = tempMail;
		if(PermissionUtil.impliesOperation("EditMailOperation", "MailManagment") && !fromQuestionary)
		{
			//���������� ������ �� ����������� ��������� ������.
			setResponsibleEmployee(mail, getLogin());

			//���� ������������� ������, �� ������ ���
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
	 * ������������� ��������� ��� ��������������
	 * @param id �������������
	 * @return �������������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public Long initializeDraft(Long id) throws BusinessException, BusinessLogicException
	{
		initialize(id);
		if(mail.getState() != getMailDraftState())
			throw new ResourceNotFoundBusinessException("������ � id=" + id + " �� �������", Mail.class);
		checkReply(mail);
		return mail.getId();
	}

	/**
	 * @return ��� �������������� ����������
	 */
	public String getResponsibleEmployeeFio()
	{
		return responsibleEmployee== null ? null : responsibleEmployee.getFullName();
	}

	/**
	 * @return ������� ��������������
	 */
	public List<ReassignMailReason> getReassignHistory()
	{
		return Collections.unmodifiableList(reassignHistory);
	}
}
