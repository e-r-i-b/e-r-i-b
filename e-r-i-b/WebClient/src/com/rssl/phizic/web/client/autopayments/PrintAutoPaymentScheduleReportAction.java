package com.rssl.phizic.web.client.autopayments;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.autopayments.GetAutoPaymentInfoOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;
import org.apache.struts.action.ActionMessages;

import java.util.Calendar;
import java.util.Date;

/**
 * @author osminin
 * @ created 14.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class PrintAutoPaymentScheduleReportAction extends ViewActionBase
{
	protected ViewEntityOperation createViewEntityOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		PrintAutoPaymentScheduleReportForm frm = (PrintAutoPaymentScheduleReportForm) form;
		GetAutoPaymentInfoOperation operation = createOperation(GetAutoPaymentInfoOperation.class);
		operation.initialize(frm.getId());
		return operation;
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase form) throws BusinessException, BusinessLogicException
	{
		PrintAutoPaymentScheduleReportForm frm = (PrintAutoPaymentScheduleReportForm) form;
		GetAutoPaymentInfoOperation op = (GetAutoPaymentInfoOperation) operation;

		RequestValuesSource valuesSource = new RequestValuesSource(currentRequest());
		FormProcessor<ActionMessages, ?> formProcessor = createFormProcessor(valuesSource, PrintAutoPaymentScheduleReportForm.PRINT_FORM);

		if (formProcessor.process())
		{
			Calendar fromDate = DateHelper.toCalendar((Date) formProcessor.getResult().get("fromDateString"));
			Calendar toDate = DateHelper.toCalendar((Date) formProcessor.getResult().get("toDateString"));
			frm.setScheduleItems(op.getScheduleItems(fromDate, toDate));
			frm.setLink(op.getEntity());
			frm.setCurrentDepartment(op.getCurrentDepartment());
			frm.setTopLevelDepartment(op.getTopLevelDepartment());
		}
		else
		{
			saveErrors(currentRequest(), formProcessor.getErrors());
		}
	}
}
