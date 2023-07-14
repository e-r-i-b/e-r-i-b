package com.rssl.phizic.business.ext.sbrf.reports;

/**
 * @author Mescheryakova
 * @ created 18.06.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Бизнес-отчет "Количество договоров по ТБ"
 */

public class ContractTB extends ContractReports
{

	public  Object buildReportByArrayOfObject(Object[] obj, ReportAbstract report)
	{
		this.setReport_id(report);
		this.setTb_id( (Long) obj[0] );
		this.setTb_name(obj[1].toString());
		this.setCount_UDBO_all(((Number) obj[2]).intValue());
		this.setCount_UDBO_year(((Number) obj[3]).intValue());
		this.setCount_UDBO_month(((Number) obj[4]).intValue());
		this.setCount_SBOL_all(((Number) obj[5]).intValue());
		this.setCount_SBOL_year(((Number) obj[6]).intValue());
		this.setCount_SBOL_month(((Number) obj[7]).intValue());
		return this;
	}

	public String getQueryName(ReportAbstract report)
	{
		return "com.rssl.phizic.business.ext.sbrf.reports.getContractTB";
	}
}
