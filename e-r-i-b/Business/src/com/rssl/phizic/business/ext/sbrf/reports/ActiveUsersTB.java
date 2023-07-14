package com.rssl.phizic.business.ext.sbrf.reports;

/**
 * @author Mescheryakova
 * @ created 10.08.2010
 * @ $Author$
 * @ $Revision$
 */

public class ActiveUsersTB  extends ActiveUsersReport
{

	public  Object buildReportByArrayOfObject(Object[] obj, ReportAbstract report)
	{
		this.setReport_id(report);
		this.setTb_id( obj[0] == null ? 0 : Long.decode(obj[0].toString()) );
		this.setTb_name(obj[1].toString());
		this.setCount_SBOL_all( Long.decode(obj[2].toString()) );
		this.setCount_getLogins( Long.decode(obj[4].toString()) );
		this.setCount_auth( Long.decode(obj[3].toString()));
		this.setCount_UDBO_all(0L);
	
		return this;
	}

	public String getQueryName(ReportAbstract report)
	{
		return "com.rssl.phizic.business.ext.sbrf.reports.getActiveUsersTB";
	}
}
