package com.rssl.phizic.web.actions.payments.forms.autosubscription;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.autosubscription.GetAutoSubscriptionInfoOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;
import org.apache.struts.action.ActionMessages;

import java.util.Calendar;
import java.util.Date;

/**
 * @author bogdanov
 * @ created 07.02.2012
 * @ $Author$
 * @ $Revision$
 */

public class PrintAutoSubscriptionScheduleReportAction extends ViewActionBase
{
	protected ViewEntityOperation createViewEntityOperation(EditFormBase form) throws BusinessException
	{
		return createOperation(GetAutoSubscriptionInfoOperation.class, "AutoSubscriptionLinkManagment");
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase form) throws BusinessException, BusinessLogicException
	{
		PrintAutoSubscriptionScheduleReportForm frm = (PrintAutoSubscriptionScheduleReportForm) form;
		GetAutoSubscriptionInfoOperation op = (GetAutoSubscriptionInfoOperation) operation;

		RequestValuesSource valuesSource = new RequestValuesSource(currentRequest());
		FormProcessor<ActionMessages, ?> formProcessor = createFormProcessor(valuesSource, PrintAutoSubscriptionScheduleReportForm.PRINT_FORM);

		if (formProcessor.process())
		{
			if (frm.isShowPaymentForPeriod())
			{
				Calendar fromDate = DateHelper.toCalendar((Date) formProcessor.getResult().get("fromDateString"));
				Calendar toDate = DateHelper.toCalendar((Date) formProcessor.getResult().get("toDateString"));
				op.initialize(frm.getId(), fromDate, toDate);
			}
			else
				op.initialize(frm.getId());

			frm.setLink(op.getEntity());
			frm.setScheduleItems(op.getScheduleItems());
			frm.setCurrentDepartment(op.getCurrentDepartment());
			frm.setTopLevelDepartment(op.getTopLevelDepartment());
		}
		else
		{
			saveErrors(currentRequest(), formProcessor.getErrors());
		}
	}
}
