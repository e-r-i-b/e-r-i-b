package com.rssl.phizic.web.monitoring.documents;

import com.rssl.phizic.web.common.ListFormBase;

import java.util.Calendar;

/**
 * @author mihaylov
 * @ created 18.03.2011
 * @ $Author$
 * @ $Revision$
 */

public class ViewMonitoredDocumentsListForm extends ListFormBase
{
	private Long departmentId;
	private String report;
	private Calendar reportTime;// время формирования отчета

	public Long getDepartmentId()
	{
		return departmentId;
	}

	public void setDepartmentId(Long departmentId)
	{
		this.departmentId = departmentId;
	}

	public String getReport()
	{
		return report;
	}

	public void setReport(String report)
	{
		this.report = report;
	}

	public Calendar getReportTime()
	{
		return reportTime;
	}

	public void setReportTime(Calendar reportTime)
	{
		this.reportTime = reportTime;
	}
}
