package com.rssl.phizic.business.ext.sbrf.reports;

/**
 * @author Mescheryakova
 * @ created 10.08.2010
 * @ $Author$
 * @ $Revision$
 */

 /**
 * Бизнес-отчет "Количество договоров по ВСП"
 */

public class ContractVSP extends ContractReports
{
	private long vsp_id;
	private long osb_id;


	public long getOsb_id()
	{
		return osb_id;
	}

	public void setOsb_id(long osb_id)
	{
		this.osb_id = osb_id;
	}


	public long getVsp_id()
	{
		return vsp_id;
	}

	public void setVsp_id(long vsp_id)
	{
		this.vsp_id = vsp_id;
	}

	public  Object buildReportByArrayOfObject(Object[] obj, ReportAbstract report)
	{
		this.setReport_id(report);
		this.setTb_id((Long) obj[0]);
		this.setTb_name((String) obj[1]);
		this.setOsb_id((Long) obj[2]);
		this.setVsp_id((Long) obj[3]);
		this.setCount_UDBO_all(((Number) obj[4]).intValue());
		this.setCount_UDBO_year(((Number) obj[5]).intValue());
		this.setCount_UDBO_month(((Number) obj[6]).intValue());
		this.setCount_SBOL_all(((Number) obj[7]).intValue());
		this.setCount_SBOL_year(((Number) obj[8]).intValue());
		this.setCount_SBOL_month(((Number) obj[9]).intValue());
		return this;
	}

	public String getQueryName(ReportAbstract report)
	{
		return "com.rssl.phizic.business.ext.sbrf.reports.getContractVSP";
	}
}
