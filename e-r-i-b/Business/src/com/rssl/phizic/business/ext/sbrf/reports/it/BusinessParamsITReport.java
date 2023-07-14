package com.rssl.phizic.business.ext.sbrf.reports.it;

import com.rssl.phizic.business.ext.sbrf.reports.ReportAdditionalInfo;
import com.rssl.phizic.business.ext.sbrf.reports.SubReports;
import com.rssl.phizic.business.ext.sbrf.reports.ReportAbstract;

import java.util.*;

/**
 * @author Mescheryakova
 * @ created 24.08.2010
 * @ $Author$
 * @ $Revision$
 */

public class BusinessParamsITReport extends SubReports
{
	private double countClientsDayAvg;
	private long countClientsDayMax;
	private long countOperationsSecondMax;

	/**
	 * Служит для возврата списка классов подотчетов данного отчета
	 * @return возвращает 2 подотчета: количество платежных документов в день и подотчет по  нагрузке
	 */
 	public List<ReportAdditionalInfo> getSubReportsList(ReportAbstract report)
	{
		List<ReportAdditionalInfo> subReports = new ArrayList<ReportAdditionalInfo>();
		subReports.add(new CountPaymentDocsITReport());
		subReports.add(new CountUsersDayITReport());

		return subReports;
	}

	public long getCountOperationsSecondMax()
	{
		return countOperationsSecondMax;
	}

	public void setCountOperationsSecondMax(long countOperationsSecondMax)
	{
		this.countOperationsSecondMax = countOperationsSecondMax;
	}


	public double getCountClientsDayAvg()
	{
		return countClientsDayAvg;
	}

	public void setCountClientsDayAvg(double countClientsDay)
	{
		this.countClientsDayAvg = countClientsDay;
	}

	public long getCountClientsDayMax()
	{
		return countClientsDayMax;
	}

	public void setCountClientsDayMax(long countClientsDayMax)
	{
		this.countClientsDayMax = countClientsDayMax;
	}
}
