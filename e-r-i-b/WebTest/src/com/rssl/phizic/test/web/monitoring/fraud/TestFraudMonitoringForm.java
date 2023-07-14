package com.rssl.phizic.test.web.monitoring.fraud;

import org.apache.struts.action.ActionForm;

/**
 * @author khudyakov
 * @ created 10.06.15
 * @ $Author$
 * @ $Revision$
 */
public class TestFraudMonitoringForm extends ActionForm
{
	private String request;
	private String response;
	private String error;

	public String getRequest()
	{
		return request;
	}

	public void setRequest(String request)
	{
		this.request = request;
	}

	public String getResponse()
	{
		return response;
	}

	public void setResponse(String response)
	{
		this.response = response;
	}

	public String getError()
	{
		return error;
	}

	public void setError(String error)
	{
		this.error = error;
	}
}
