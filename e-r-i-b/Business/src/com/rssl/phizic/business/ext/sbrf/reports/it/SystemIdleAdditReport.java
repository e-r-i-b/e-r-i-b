package com.rssl.phizic.business.ext.sbrf.reports.it;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ext.sbrf.reports.ReportAbstract;
import com.rssl.phizic.business.ext.sbrf.reports.ReportAdditionalInfo;
import com.rssl.phizic.business.monitoring.serveravailability.IdleType;

import java.util.Calendar;
import java.util.List;

/**
 * Базовый класс для подотчетов о простое системы
 * @author gladishev
 * @ created 11.10.2011
 * @ $Author$
 * @ $Revision$
 */
public abstract class SystemIdleAdditReport implements ReportAdditionalInfo
{
	private long id;
	private ReportAbstract report_id;
	private Calendar startDate;
	private Calendar endDate;
	private IdleType type;

	public Object buildReportByArrayOfObject(Object[] obj, ReportAbstract report) throws BusinessException
	{
		this.setReport_id(report);
		this.setStartDate((Calendar) obj[0]);
		this.setEndDate((Calendar) obj[1]);
		this.setType(Enum.valueOf(IdleType.class, (String) obj[2]));

		return this;
	}

	public boolean isIds()
	{
		return false;
	}

	public List processData(List list)
	{
		return list;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public ReportAbstract getReport_id()
	{
		return report_id;
	}

	public void setReport_id(ReportAbstract report_id)
	{
		this.report_id = report_id;
	}

	public Calendar getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Calendar startDate)
	{
		this.startDate = startDate;
	}

	public Calendar getEndDate()
	{
		return endDate;
	}

	public void setEndDate(Calendar endDate)
	{
		this.endDate = endDate;
	}

	public IdleType getType()
	{
		return type;
	}

	public void setType(IdleType type)
	{
		this.type = type;
	}
}