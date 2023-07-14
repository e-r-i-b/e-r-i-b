package com.rssl.phizic.operations.loans.claims;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.gate.dictionaries.officies.LoanOffice;
import com.rssl.phizic.business.dictionaries.offices.loans.LoanOfficeService;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.loans.kinds.LoanKind;
import com.rssl.phizic.business.loans.kinds.LoanKindService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.security.SecurityDbException;

import java.util.List;

/**
 * @author gladishev
 * @ created 21.02.2008
 * @ $Author$
 * @ $Revision$
 */

public class ListLoanClaimOperation extends OperationBase implements ListEntitiesOperation
{
	private static final LoanOfficeService loanOfficeService = new LoanOfficeService();
	private static final LoanKindService loanKindService = new LoanKindService();
	private static final SecurityService securityService = new SecurityService();
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();

	public List<LoanKind> getAllLoanKinds() throws BusinessException
	{
		return loanKindService.getAll();
	}

	public List<LoanOffice> getAllLoanOffices() throws BusinessException
	{
		return loanOfficeService.getAll();
	}

	public BusinessDocument findClaimById(Long id) throws BusinessException
	{
		return businessDocumentService.findById(id);
	}

	/**
	 * получить кредитный офис текущего сотрудника
	 */
	public LoanOffice getEmployeeLoanOffice() throws BusinessException
	{
		Employee employee = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee();
		LoanOffice office = loanOfficeService.findSynchKey(employee.getLoanOfficeId());
		if (office == null)
			throw new BusinessException("Ќе найден кредитный офис сотрудника с id=" + employee.getId());
		return office;
	}

	/**
	 * получить loginId анонимного клиента
	 */
	public Long getAnonymousClientLoginId() throws BusinessException
	{
		String anonymousClientName = ConfigFactory.getConfig(SecurityConfig.class).getAnonymousClientName();
		Login login = null;
		try
		{
			login = securityService.getClientLogin(anonymousClientName);
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}
		if (login == null)
			throw new BusinessException("Ќе найден логин пользовател€ " + anonymousClientName);
		return login.getId();
	}
}
