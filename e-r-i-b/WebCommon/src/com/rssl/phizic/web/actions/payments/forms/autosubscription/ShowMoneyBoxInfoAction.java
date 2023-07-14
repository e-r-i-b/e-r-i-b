package com.rssl.phizic.web.actions.payments.forms.autosubscription;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.ext.sbrf.payment.ViewMoneyBoxOperation;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.HashMap;
import java.util.Map;

/**
 * @author vagin
 * @ created 21.01.15
 * @ $Author$
 * @ $Revision$
 * Ёкшен детальной информации по копилке.
 */
public class ShowMoneyBoxInfoAction extends ShowAutoSubscriptionInfoActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.remove("button.showScheduleReport");
		map.remove("button.filter");
		return map;
	}

	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		ViewMoneyBoxForm form = (ViewMoneyBoxForm) frm;
		ViewMoneyBoxOperation operation = createOperation(ViewMoneyBoxOperation.class, "MoneyBoxManagement");
		if(form.getLinkId() != null)
		{
			operation.initialize(form.getLinkId(), true);
		}
		else if(form.getClaimId() != null)
		{
			operation.initialize(form.getClaimId(), false);
		}
		return operation;
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase form) throws BusinessException, BusinessLogicException
	{
		ViewMoneyBoxForm frm = (ViewMoneyBoxForm) form;
		ViewMoneyBoxOperation op = (ViewMoneyBoxOperation) operation;
		frm.setLink(op.getEntity());
		frm.setCardLink(op.getCardLink());
		frm.setAccountLink(op.getAccountLink());
	}
}
