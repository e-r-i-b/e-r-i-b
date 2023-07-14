package com.rssl.phizic.business.addressBook.reports;

/**
 * @author akrenev
 * @ created 11.06.2014
 * @ $Author$
 * @ $Revision$
 *
 * —ущность отчета "ѕо запросам к сервису"
 */

public class RequestsCountReportEntity extends ReportEntityBase
{
	private Long count;

	/**
	 * @return количество запросов к сервису
	 */
	public Long getCount()
	{
		return count;
	}

	/**
	 * задать количество запросов к сервису
	 * @param count количество запросов к сервису
	 */
	public void setCount(Long count)
	{
		this.count = count;
	}
}
