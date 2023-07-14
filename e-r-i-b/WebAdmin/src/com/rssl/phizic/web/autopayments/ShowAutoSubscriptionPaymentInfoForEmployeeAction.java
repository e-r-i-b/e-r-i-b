package com.rssl.phizic.web.autopayments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.autosubscription.GetAutoSubscriptionPaymentInfoOperation;
import com.rssl.phizic.web.actions.payments.forms.autosubscription.ShowAutoSubscriptionPaymentInfoAction;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author bogdanov
 * @ created 13.02.2012
 * @ $Author$
 * @ $Revision$
 */

public class ShowAutoSubscriptionPaymentInfoForEmployeeAction extends ShowAutoSubscriptionPaymentInfoEmployeeActionBase
{
	@Override
	protected ViewEntityOperation createViewEntityOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		ShowAutoSubscriptionPaymentInfoForEmployeeForm frm = (ShowAutoSubscriptionPaymentInfoForEmployeeForm) form;

		GetAutoSubscriptionPaymentInfoOperation operation = createOperation("GetAutoSubscriptionPaymentInfoOperation", "AutoSubscriptionManagment");
		operation.initialize(frm.getSubscriptionId(), frm.getId());

		return operation;
	}

	@Override
	protected GetAutoSubscriptionPaymentInfoOperation createSimpleViewEntityOperation() throws BusinessException, BusinessLogicException
	{
		GetAutoSubscriptionPaymentInfoOperation operation = createOperation("GetAutoSubscriptionPaymentInfoOperation", "AutoSubscriptionManagment");
		operation.initialize();

		return operation;
	}
}
