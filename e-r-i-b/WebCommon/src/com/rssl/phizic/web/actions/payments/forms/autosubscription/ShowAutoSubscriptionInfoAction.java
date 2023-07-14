package com.rssl.phizic.web.actions.payments.forms.autosubscription;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.autosubscription.GetAutoSubscriptionInfoOperation;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * Детальная информация по автоподписке
 *
 * @author khudyakov
 * @ created 23.10.14
 * @ $Author$
 * @ $Revision$
 */
public class ShowAutoSubscriptionInfoAction extends ShowAutoSubscriptionInfoActionBase
{
	public ViewEntityOperation createViewEntityOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		ShowAutoSubscriptionInfoForm frm = (ShowAutoSubscriptionInfoForm) form;
		return operationInitialize(frm, createOperation(GetAutoSubscriptionInfoOperation.class, "AutoSubscriptionLinkManagment"));
	}
}
