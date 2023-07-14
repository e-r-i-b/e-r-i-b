package com.rssl.phizic.business.monitoring;

import com.rssl.phizic.business.departments.Department;

/**
 * @author mihaylov
 * @ created 24.02.2011
 * @ $Author$
 * @ $Revision$
 */

// Пороговые значения параметров мониторинга
public class MonitoringThresholdValues
{
	private Long id;
	private Department department;  // департамент
	private MonitoringReport report;// параметр мониторинга
	private Long warningThreshold;  // порог предупреждения
	private Long errorThreshold;    // порог ошибки

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Department getDepartment()
	{
		return department;
	}

	public void setDepartment(Department department)
	{
		this.department = department;
	}

	public MonitoringReport getReport()
	{
		return report;
	}

	public void setReport(MonitoringReport report)
	{
		this.report = report;
	}

	public Long getWarningThreshold()
	{
		return warningThreshold;
	}

	public void setWarningThreshold(Long warningThreshold)
	{
		this.warningThreshold = warningThreshold;
	}

	public Long getErrorThreshold()
	{
		return errorThreshold;
	}

	public void setErrorThreshold(Long errorThreshold)
	{
		this.errorThreshold = errorThreshold;
	}
}
