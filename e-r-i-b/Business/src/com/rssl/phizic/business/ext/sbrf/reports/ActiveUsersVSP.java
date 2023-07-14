package com.rssl.phizic.business.ext.sbrf.reports;

/**
 * @author Mescheryakova
 * @ created 10.08.2010
 * @ $Author$
 * @ $Revision$
 */

public class ActiveUsersVSP extends ActiveUsersReport
{
	private Long osb_id;
	private Long vsp_id;

	public Long getOsb_id()
	{
		return osb_id;
	}

	public void setOsb_id(Long osb_id)
	{
		this.osb_id = osb_id;
	}

	public Long getVsp_id()
	{
		return vsp_id;
	}

	public void setVsp_id(Long vsp_id)
	{
		this.vsp_id = vsp_id;
	}

	public  Object buildReportByArrayOfObject(Object[] obj, ReportAbstract report)
	{
		this.setReport_id(report);
		this.setTb_id( Long.decode(obj[0].toString()) );
		this.setTb_name(obj[1].toString());
		this.setOsb_id( Long.decode(obj[2].toString()) );
		this.setVsp_id( Long.decode(obj[3].toString()) );
		this.setCount_SBOL_all( Long.decode(obj[4].toString()) );
		this.setCount_getLogins( Long.decode(obj[6].toString()) );
		this.setCount_auth( Long.decode(obj[5].toString()));
		this.setCount_UDBO_all(0L);
		return this;
	}

	public String getQueryName(ReportAbstract report)
	{
		return "com.rssl.phizic.business.ext.sbrf.reports.getActiveUsersVSP";
	}

}
