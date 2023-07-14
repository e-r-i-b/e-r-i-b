package com.rssl.phizic.business.timeoutsessionrequest;

/**
 * @author belyi
 * @ created 18.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class TimeoutSession
{
	private long id;
	private String randomRecordId;
	private String url;
	private String parametres;

	public String getRandomRecordId()
	{
		return randomRecordId;
	}

	public void setRandomRecordId(String randomRecordId)
	{
		this.randomRecordId = randomRecordId;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getParametres()
	{
		return parametres;
	}

	public void setParametres(String parametres)
	{
		this.parametres = parametres;
	}

	public String getFullUrl()
	{
		if (parametres == null || parametres.length() == 0)
		{
			return url;
		}
		StringBuffer result = new StringBuffer(url);

		if (!url.contains("?"))
		{
			result.append("?");
		}
		else if (!url.endsWith("?"))
		{
			result.append("&");
		}
		if (parametres.startsWith("?"))
		{
			result.append("&").append(parametres.substring(1));
		}
		else
		{
			result.append(parametres);
		}
		return result.toString();
	}
}
