package com.rssl.phizic.web.ext.sbrf.reports;

import com.rssl.phizic.web.common.ListFormBase;

import java.util.Calendar;

/**
 * @author Mescheryakova
 * @ created 04.06.2010
 * @ $Author$
 * @ $Revision$
 */

public class ReportsListForm extends ListFormBase
{
	Long id;
	Calendar reportStartDate;
	Calendar reportEndDate;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Calendar getReportStartDate()
	{
		return reportStartDate;
	}

	public void setReportStartDate(Calendar reportStartDate)
	{
		this.reportStartDate = reportStartDate;
	}

	public Calendar getReportEndDate()
	{
		return reportEndDate;
	}

	public void setReportEndDate(Calendar reportEndDate)
	{
		this.reportEndDate = reportEndDate;
	}
}
