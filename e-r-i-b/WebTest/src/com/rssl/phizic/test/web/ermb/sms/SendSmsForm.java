package com.rssl.phizic.test.web.ermb.sms;

import org.apache.struts.action.ActionForm;

/**
 * @author Erkin
 * @ created 27.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class SendSmsForm extends ActionForm
{
	private String phone;
	private String text;
	private String application;
	private int threadCount = 1;

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public int getThreadCount()
	{
		return threadCount;
	}

	public void setThreadCount(int threadCount)
	{
		this.threadCount = threadCount;
	}

	public String getApplication()
	{
		return application;
	}

	public void setApplication(String application)
	{
		this.application = application;
	}
}
