package com.rssl.phizic.test.web.common;

import org.apache.struts.action.ActionForm;

import java.net.URL;

/**
 * @author Mescheryakova
 * @ created 28.06.2012
 * @ $Author$
 * @ $Revision$
 */

public abstract class TestAbstractForm extends ActionForm
{
	private String address;

	private String cookie;

	private String url; // = "http://localhost:8888/mobile";

	private String proxyUrl; // = "myth";

	private Integer proxyPort; // = 8080;

	private String transactionToken;

	private String params;

	private String result;

	private String status;

	private String contentType;

	private boolean submit = false;

	private Object response;

	private URL lastUrl;


	///////////////////////////////////////////////////////////////////////////

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getCookie()
	{
		return cookie;
	}

	public void setCookie(String cookie)
	{
		this.cookie = cookie;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getProxyUrl()
	{
		return proxyUrl;
	}

	public void setProxyUrl(String proxyUrl)
	{
		this.proxyUrl = proxyUrl;
	}

	public Integer getProxyPort()
	{
		return proxyPort;
	}

	public void setProxyPort(Integer proxyPort)
	{
		this.proxyPort = proxyPort;
	}

	public String getTransactionToken()
	{
		return transactionToken;
	}

	public void setTransactionToken(String transactionToken)
	{
		this.transactionToken = transactionToken;
	}

	public String getParams()
	{
		return params;
	}

	public void setParams(String params)
	{
		this.params = params;
	}

	public String getResult()
	{
		return result;
	}

	public void setResult(String result)
	{
		this.result = result;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getContentType()
	{
		return contentType;
	}

	public void setContentType(String contentType)
	{
		this.contentType = contentType;
	}

	public boolean getSubmit()
	{
		return submit;
	}

	public void setSubmit(boolean submit)
	{
		this.submit = submit;
	}

	public Object getResponse()
	{
		return response;
	}

	public void setResponse(Object response)
	{
		this.response = response;
	}

	public URL getLastUrl()
	{
		return lastUrl;
	}

	public void setLastUrl(URL lastUrl)
	{
		this.lastUrl = lastUrl;
	}
}
