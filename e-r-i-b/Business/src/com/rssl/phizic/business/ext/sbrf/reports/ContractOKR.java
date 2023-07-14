package com.rssl.phizic.business.ext.sbrf.reports;

import com.rssl.phizic.utils.StringHelper;

/**
 * @author Mescheryakova
 * @ created 10.08.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Бизнес-отчет "Количество договоров по ОКР"
 */

public class ContractOKR extends ContractReports
{
	private String employe_name;
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
	public String getEmploye_name()
	{
		return employe_name;
	}

	public void setEmploye_name(String employe_name)
	{
		this.employe_name = employe_name;
	}

	public  Object buildReportByArrayOfObject(Object[] obj, ReportAbstract report)
	{
		this.setReport_id(report);
		this.setTb_id( Long.decode(obj[0].toString()) );
		this.setTb_name( obj[1].toString() );
		this.setOsb_id( Long.decode(obj[2].toString()) );
		this.setVsp_id( Long.decode(obj[3].toString()) );
		this.setEmploye_name(StringHelper.getEmptyIfNull(obj[4]) + " " + StringHelper.getEmptyIfNull(obj[5]) + " " +  StringHelper.getEmptyIfNull(obj[6]));      // ФИО через пробел
		this.setCount_UDBO_all(((Number) obj[7]).intValue());
		this.setCount_UDBO_year(((Number) obj[8]).intValue());
		this.setCount_UDBO_month(((Number) obj[9]).intValue());
		this.setCount_SBOL_all(((Number) obj[10]).intValue());
		this.setCount_SBOL_year(((Number) obj[11]).intValue());
		this.setCount_SBOL_month(((Number) obj[12]).intValue());
		return this;
	}

	public String getQueryName(ReportAbstract report)
	{
		return "com.rssl.phizic.business.ext.sbrf.reports.getContractOKR";
	}
}
