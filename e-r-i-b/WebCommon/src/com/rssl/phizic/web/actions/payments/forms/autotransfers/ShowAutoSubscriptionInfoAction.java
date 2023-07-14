package com.rssl.phizic.web.actions.payments.forms.autotransfers;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.autosubscription.GetAutoSubscriptionInfoOperation;
import com.rssl.phizic.web.actions.payments.forms.autosubscription.ShowAutoSubscriptionInfoActionBase;
import com.rssl.phizic.web.actions.payments.forms.autosubscription.ShowAutoSubscriptionInfoForm;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * Детальная информация по автопереводу
 * 
 * @author khudyakov
 * @ created 01.03.15
 * @ $Author$
 * @ $Revision$
 */
public class ShowAutoSubscriptionInfoAction extends ShowAutoSubscriptionInfoActionBase
{
	public ViewEntityOperation createViewEntityOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		ShowAutoSubscriptionInfoForm frm = (ShowAutoSubscriptionInfoForm) form;
		return operationInitialize(frm, createOperation(GetAutoSubscriptionInfoOperation.class, "ClientAutotransfersManagement"));
	}
}
