package com.rssl.phizic.web.autopayments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.autosubscription.GetAutoSubscriptionInfoOperation;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * * График платежей по автопереводу
 *
 * @author khudyakov
 * @ created 02.11.14
 * @ $Author$
 * @ $Revision$
 */
public class PrintAutoTransferScheduleReportEmployeeAction extends PrintAutoSubscriptionScheduleReportEmployeeActionBase
{
	@Override
	protected GetAutoSubscriptionInfoOperation createAutoSubscriptionInfoOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(GetAutoSubscriptionInfoOperation.class, "AutoTransfersManagement");
	}
}
