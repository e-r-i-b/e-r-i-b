package com.rssl.phizic.web.autopayments;

import com.rssl.phizic.business.resources.external.AutoSubscriptionLink;
import com.rssl.phizic.gate.autopayments.ScheduleItem;
import com.rssl.phizic.gate.payments.longoffer.AutoSubscriptionDetailInfo;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.List;

/**
 * @author basharin
 * @ created 21.02.2012
 * @ $Author$
 * @ $Revision$
 */

public class PrintAutoSubscriptionScheduleReportForEmployeeForm extends EditFormBase
{
	private String fromDateString;
	private String toDateString;
	private AutoSubscriptionLink link;
	private AutoSubscriptionDetailInfo autoSubscriptionInfo;
	private List<ScheduleItem> scheduleItems;
	private boolean showPaymentForPeriod = false;

	public boolean isShowPaymentForPeriod()
	{
		return showPaymentForPeriod;
	}

	public void setShowPaymentForPeriod(boolean showPaymentForPeriod)
	{
		this.showPaymentForPeriod = showPaymentForPeriod;
	}

	public String getToDateString()
	{
		return toDateString;
	}

	public void setToDateString(String toDateString)
	{
		this.toDateString = toDateString;
	}

	public String getFromDateString()
	{
		return fromDateString;
	}

	public void setFromDateString(String fromDateString)
	{
		this.fromDateString = fromDateString;
	}

	public List<ScheduleItem> getScheduleItems()
	{
		return scheduleItems;
	}

	public void setScheduleItems(List<ScheduleItem> scheduleItems)
	{
		this.scheduleItems = scheduleItems;
	}

	public AutoSubscriptionLink getLink()
	{
		return link;
	}

	public void setLink(AutoSubscriptionLink link)
	{
		this.link = link;
	}

	public AutoSubscriptionDetailInfo getAutoSubscriptionInfo()
	{
		return autoSubscriptionInfo;
	}

	public void setAutoSubscriptionInfo(AutoSubscriptionDetailInfo autoSubscriptionInfo)
	{
		this.autoSubscriptionInfo = autoSubscriptionInfo;
	}
}
