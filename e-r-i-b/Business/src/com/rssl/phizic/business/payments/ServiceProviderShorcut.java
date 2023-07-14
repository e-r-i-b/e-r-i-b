package com.rssl.phizic.business.payments;

/**
 * @author Erkin
 * @ created 17.08.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * ярлычок со ссылкой на поставщика услуг
 */
public class ServiceProviderShorcut
{
	private Long id;

	private String name;

	///////////////////////////////////////////////////////////////////////////

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String toString()
	{
		return "id=" + id + ", name='" + name + '\'';
	}
}
