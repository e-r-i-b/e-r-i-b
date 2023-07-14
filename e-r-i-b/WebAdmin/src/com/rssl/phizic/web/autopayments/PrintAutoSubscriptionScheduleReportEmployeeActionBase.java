package com.rssl.phizic.web.autopayments;

import com.rssl.common.forms.parsers.DateParser;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.autosubscription.GetAutoSubscriptionInfoOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;

import java.text.ParseException;
import java.util.Calendar;

/**
 * Базовый класс графика платежей по подписке (админка)
 *
 * @author khudyakov
 * @ created 02.11.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class PrintAutoSubscriptionScheduleReportEmployeeActionBase extends ViewActionBase
{
	protected abstract GetAutoSubscriptionInfoOperation createAutoSubscriptionInfoOperation(EditFormBase frm) throws BusinessException, BusinessLogicException;

	protected ViewEntityOperation createViewEntityOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		PrintAutoSubscriptionScheduleReportForEmployeeForm frm = (PrintAutoSubscriptionScheduleReportForEmployeeForm) form;
		GetAutoSubscriptionInfoOperation operation = createAutoSubscriptionInfoOperation(frm);

		Calendar toDate = null;
		Calendar fromDate = null;

		if (frm.isShowPaymentForPeriod())
		{
			fromDate = getFormattedDate(frm.getFromDateString(), DateHelper.SIMPLE_DATE_FORMAT);
			toDate = getFormattedDate(frm.getToDateString(), DateHelper.SIMPLE_DATE_FORMAT);
		}

		operation.initialize(frm.getId(), fromDate, toDate);
		return operation;
	}

	@Override
	protected void updateFormData(ViewEntityOperation operation, EditFormBase form) throws BusinessException, BusinessLogicException
	{
		PrintAutoSubscriptionScheduleReportForEmployeeForm frm = (PrintAutoSubscriptionScheduleReportForEmployeeForm) form;
		GetAutoSubscriptionInfoOperation op = (GetAutoSubscriptionInfoOperation) operation;

		frm.setLink(op.getEntity());
		frm.setAutoSubscriptionInfo(op.getAutoSubscriptionInfo());
		if (!op.isUpdateSheduleItemsError())
		{
			frm.setScheduleItems(op.getScheduleItems());
		}
	}

	private Calendar getFormattedDate(String date, String format) throws BusinessLogicException
	{
		try
		{
			return DateHelper.toCalendar(new DateParser(format).parse(date));
		}
		catch (ParseException e)
		{
			throw new BusinessLogicException("Неправильный формат даты, введите дату в формате " + format, e);
		}
	}
}
