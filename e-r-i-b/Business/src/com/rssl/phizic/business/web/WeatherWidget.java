package com.rssl.phizic.business.web;

/**
 * @author Barinov
 * @ created 28.06.2012
 * @ $Author$
 * @ $Revision$
 */
public class WeatherWidget extends Widget
{
	private String city;

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	@Override
	protected Widget clone()
	{
		return super.clone();
	}
}
