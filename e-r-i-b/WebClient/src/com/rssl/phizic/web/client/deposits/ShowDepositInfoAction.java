package com.rssl.phizic.web.client.deposits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.DepositLink;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.deposits.GetDepositInfoOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;

/**
 * Created by IntelliJ IDEA. User: Novikov_A Date: 28.12.2006 Time: 18:50:35 To change this template use File
 * | Settings | File Templates.
 */
public class ShowDepositInfoAction extends ViewActionBase
{
	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException
	{
		GetDepositInfoOperation operationDeposit = createOperation(GetDepositInfoOperation.class);
	    operationDeposit.initialize(Long.valueOf(currentRequest().getParameter("depositId")));

		return operationDeposit;
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase form)
			throws BusinessException, BusinessLogicException
	{
       ShowDepositInfoForm frm = (ShowDepositInfoForm) form;

	   if (checkAccess(GetDepositInfoOperation.class))
       {
	      GetDepositInfoOperation operationDeposit = (GetDepositInfoOperation) operation;

	      DepositLink link = operationDeposit.getEntity();
	      frm.setDeposit(link.getDeposit());
	      frm.setDepositInfo(link.getDepositInfo());
       }
    }
}
