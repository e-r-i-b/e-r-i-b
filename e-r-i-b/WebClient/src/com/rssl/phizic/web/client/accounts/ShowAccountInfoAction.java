package com.rssl.phizic.web.client.accounts;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.account.GetAccountInfoOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;

/** Created by IntelliJ IDEA. User: Nonikov Date: 23.12.2006 Time: 13:59:13 */
public class ShowAccountInfoAction extends ViewActionBase
{
	protected ViewEntityOperation createViewEntityOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
	    GetAccountInfoOperation operationAccount = createOperation(GetAccountInfoOperation.class);
	    operationAccount.initialize(new Long(currentRequest().getParameter("accountId")));

	    return operationAccount;
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase form)
			throws BusinessException
	{
       ShowAccountInfoForm frm = (ShowAccountInfoForm) form;
	   if (checkAccess(GetAccountInfoOperation.class))
       {
	      GetAccountInfoOperation operationAccount = (GetAccountInfoOperation) operation;

	      AccountLink accountLink = operationAccount.getEntity();
	      frm.setAccount(accountLink.getAccount());
       }
    }
}

