package com.rssl.phizic.business.dictionaries.url;

/**
 * Разрешенные URL-адреса
 * @author lukina
 * @ created 13.06.2012
 * @ $Author$
 * @ $Revision$
 */

public class WhiteListUrl
{
	private Long id; //id сущности
	private String url;//маска URL

	public  WhiteListUrl(){};

	public  WhiteListUrl(Long id, String mask)
	{
		this.id = id;
		this.url = mask;
	};

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
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

	public boolean equals(Object obj)
	{
		if (!(obj instanceof WhiteListUrl))
			return false;
		WhiteListUrl other = (WhiteListUrl) obj;
		return  isObjectsEquals(other.getId(),id) && isObjectsEquals(other.getUrl(),url);
	}

	private boolean isObjectsEquals(Object o1, Object o2)
	{
		if (o1 == null && o2 == null)
		{
			return true;
		}
		if (o1 == null || o2 == null)
		{
			return false;
		}
		return o1.equals(o2);
	}
}
