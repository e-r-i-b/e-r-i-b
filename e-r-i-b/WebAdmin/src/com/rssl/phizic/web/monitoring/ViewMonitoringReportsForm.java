package com.rssl.phizic.web.monitoring;

import com.rssl.phizic.business.monitoring.MonitoringReport;
import com.rssl.phizic.business.monitoring.MonitoringThresholdValues;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.List;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 01.03.2011
 * @ $Author$
 * @ $Revision$
 */

public class ViewMonitoringReportsForm extends EditFormBase
{
	private Map<MonitoringReport,Long> monitoringReportValues;
	private List<MonitoringThresholdValues> thresholdValueses;
	private Long departmentId; // id департамента, для которого необходимо отобразить данные, получаем через GET запрос

	public Map<MonitoringReport, Long> getMonitoringReportValues()
	{
		return monitoringReportValues;
	}

	public void setMonitoringReportValues(Map<MonitoringReport, Long> monitoringReportValues)
	{
		this.monitoringReportValues = monitoringReportValues;
	}

	public List<MonitoringThresholdValues> getThresholdValueses()
	{
		return thresholdValueses;
	}

	public void setThresholdValueses(List<MonitoringThresholdValues> thresholdValueses)
	{
		this.thresholdValueses = thresholdValueses;
	}	

	public Long getDepartmentId()
	{
		return departmentId;
	}

	public void setDepartmentId(Long departmentId)
	{
		this.departmentId = departmentId;
	}
}
