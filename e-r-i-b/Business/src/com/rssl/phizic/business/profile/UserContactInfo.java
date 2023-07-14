package com.rssl.phizic.business.profile;


/**
 * @author koptyaev
 * @ created 02.06.14
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings("JavaDoc")
public class UserContactInfo
{

	private String homePhone; //домашний телефон
	private String jobPhone;  //рабочий телефон

	public String getHomePhone()
	{
		return homePhone;
	}

	public void setHomePhone(String homePhone)
	{
		this.homePhone = homePhone;
	}

	public String getJobPhone()
	{
		return jobPhone;
	}

	public void setJobPhone(String jobPhone)
	{
		this.jobPhone = jobPhone;
	}
}
