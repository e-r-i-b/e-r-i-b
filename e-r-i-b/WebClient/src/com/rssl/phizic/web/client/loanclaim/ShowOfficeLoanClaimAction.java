package com.rssl.phizic.web.client.loanclaim;

import com.rssl.phizgate.ext.sbrf.etsm.OfficeLoanClaim;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.loanclaim.officeclaim.ShowOfficeLoanClaimOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;

/**
 * @author Nady
 * @ created 15.07.15
 * @ $Author$
 * @ $Revision$
 */

/**
 * Отображение зявки на кредит, созданной в каналах отличных от УКО
 */
public class ShowOfficeLoanClaimAction extends ViewActionBase
{
	@Override
	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ShowOfficeLoanClaimOperation.class, "LoanProduct");
	}

	@Override
	protected void updateFormData(ViewEntityOperation operation, EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		ShowOfficeLoanClaimOperation showOfficeLoanClaimOperation = (ShowOfficeLoanClaimOperation) operation;
		ShowOfficeLoanClaimForm form = (ShowOfficeLoanClaimForm) frm;
		showOfficeLoanClaimOperation.initialize(form.getApplicationNumber());
		form.setDocument((OfficeLoanClaim) showOfficeLoanClaimOperation.getEntity());
		form.setDepartment(((ShowOfficeLoanClaimOperation) operation).getDepartment());
	}
}
