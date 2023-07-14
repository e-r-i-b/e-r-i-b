package com.rssl.phizic.web.autopayments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.autosubscription.GetAutoSubscriptionInfoOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;
import com.rssl.common.forms.parsers.DateParser;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.text.ParseException;

/**
 * График платежей по автоподписке
 *
 * @author basharin
 * @ created 21.02.2012
 * @ $Author$
 * @ $Revision$
 */

public class PrintAutoSubscriptionScheduleReportForEmployeeAction extends PrintAutoSubscriptionScheduleReportEmployeeActionBase
{
	@Override
	protected GetAutoSubscriptionInfoOperation createAutoSubscriptionInfoOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(GetAutoSubscriptionInfoOperation.class, "AutoSubscriptionManagment");
	}
}
