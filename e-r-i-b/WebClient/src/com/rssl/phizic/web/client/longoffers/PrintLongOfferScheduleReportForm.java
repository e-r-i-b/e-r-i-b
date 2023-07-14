package com.rssl.phizic.web.client.longoffers;

import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.resources.external.LongOfferLink;
import com.rssl.phizic.gate.longoffer.ScheduleItem;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.List;

/**
 * @author osminin
 * @ created 12.10.2010
 * @ $Author$
 * @ $Revision$
 */
public class PrintLongOfferScheduleReportForm extends EditFormBase
{
	private String fromDateString;
	private String toDateString;
	private LongOfferLink longOfferLink;
	private List<ScheduleItem> scheduleItems;
	private Department currentDepartment;
	private Department topLevelDepartment;

	public String getFromDateString()
	{
		return fromDateString;
	}

	public void setFromDateString(String fromDateString)
	{
		this.fromDateString = fromDateString;
	}

	public String getToDateString()
	{
		return toDateString;
	}

	public void setToDateString(String toDateString)
	{
		this.toDateString = toDateString;
	}

	public LongOfferLink getLongOfferLink()
	{
		return longOfferLink;
	}

	public void setLongOfferLink(LongOfferLink longOfferLink)
	{
		this.longOfferLink = longOfferLink;
	}

	public List<ScheduleItem> getScheduleItems()
	{
		return scheduleItems;
	}

	public void setScheduleItems(List<ScheduleItem> scheduleItems)
	{
		this.scheduleItems = scheduleItems;
	}

	public Department getCurrentDepartment()
	{
		return currentDepartment;
	}

	public void setCurrentDepartment(Department currentDepartment)
	{
		this.currentDepartment = currentDepartment;
	}

	public Department getTopLevelDepartment()
	{
		return topLevelDepartment;
	}

	public void setTopLevelDepartment(Department topLevelDepartment)
	{
		this.topLevelDepartment = topLevelDepartment;
	}
}
