package com.rssl.phizic.web.client.moneyBox;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.ext.sbrf.payment.ViewMoneyBoxOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;

/**
 * @author saharnova
 * @ created 14.10.14
 * @ $Author$
 * @ $Revision$
 */

public class PrintMoneyBoxScheduleReportAction extends ViewActionBase
{
	protected ViewEntityOperation createViewEntityOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		ViewMoneyBoxOperation operation = createOperation(ViewMoneyBoxOperation.class, "MoneyBoxManagement");
		operation.initialize(form.getId(), true);
		return operation;

	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase form) throws BusinessException, BusinessLogicException
	{
		PrintMoneyBoxScheduleReportForm frm = (PrintMoneyBoxScheduleReportForm) form;
		ViewMoneyBoxOperation op = (ViewMoneyBoxOperation) operation;
		frm.setLink(op.getEntity());
		frm.setCardLink(op.getCardLink());
		frm.setAccountLink(op.getAccountLink());
		if (!op.isUpdateSheduleItemsError())
			frm.setScheduleItems(op.getScheduleItems());
		else
			frm.setTextUpdateSheduleItemsError(op.getTextUpdateSheduleItemsError());
	}
}
