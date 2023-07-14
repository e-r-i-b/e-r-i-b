package com.rssl.phizic.web.ext.sbrf.reports;

import com.rssl.phizic.business.ext.sbrf.reports.CountIOSReport;
import com.rssl.phizic.business.ext.sbrf.reports.ReportAbstract;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.List;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 29.11.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Форма для просмотра отчета о количестве клиентов iOS 
 */
public class ReportViewForm extends EditFormBase
{
	private ReportAbstract reportAbstract;
	private Map<String, Map<String, List<CountIOSReport>>> reportMap;

	public ReportAbstract getReportAbstract()
	{
		return reportAbstract;
	}

	public void setReportAbstract(ReportAbstract reportAbstract)
	{
		this.reportAbstract = reportAbstract;
	}

	public Map<String, Map<String, List<CountIOSReport>>> getReportMap()
	{
		return reportMap;
	}

	public void setReportMap(Map<String, Map<String, List<CountIOSReport>>> reportMap)
	{
		this.reportMap = reportMap;
	}
}
