package com.rssl.phizic.web.atm.autotransfers;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.autosubscription.GetAutoSubscriptionInfoOperation;
import com.rssl.phizic.web.actions.payments.forms.autosubscription.ShowAutoSubscriptionInfoForm;
import com.rssl.phizic.web.atm.autosubscriptions.ShowAutoSubscriptionAbstractATMAction;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * Получение графика исполнения платежей по автопереводу
 *
 * @author khudyakov
 * @ created 01.03.15
 * @ $Author$
 * @ $Revision$
 */
public class ShowAutoTransferAbstractATMAction extends ShowAutoSubscriptionAbstractATMAction
{
	public ViewEntityOperation createViewEntityOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		ShowAutoSubscriptionInfoForm frm = (ShowAutoSubscriptionInfoForm) form;
		return operationInitialize(frm, createOperation(GetAutoSubscriptionInfoOperation.class, "ClientAutotransfersManagement"));
	}
}
