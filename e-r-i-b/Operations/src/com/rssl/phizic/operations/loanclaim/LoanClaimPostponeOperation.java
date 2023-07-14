package com.rssl.phizic.operations.loanclaim;

import com.rssl.ikfl.crediting.CRMMessageService;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.operations.OperationBase;

/**
 * @author Gulov
 * @ created 16.04.15
 * @ $Author$
 * @ $Revision$
 */

/**
 * Отложить заполнение заявки
 */
public class LoanClaimPostponeOperation extends OperationBase
{
	private final CRMMessageService crmMessageService = new CRMMessageService();

	public void execute(ExtendedLoanClaim claim, Employee employee) throws Exception
	{
		crmMessageService.createNewCallTask(claim, employee);
	}
}
