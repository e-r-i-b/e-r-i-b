package com.rssl.phizic.business.web;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author lepihina
 * @ created 10.12.2012
 * @ $Author$
 * @ $Revision$
 */
public class WidgetConfig extends Config
{
	public static final String TWITTER_ID = "widget.twitter.id";
	private static final String WEATHER_SERVICE_KEY = "widget.weather.service.key";
	private String weatherService;

	///////////////////////////////////////////////////////////////////////////

	public WidgetConfig(PropertyReader reader) 
	{
		super(reader);
	}

	/**
	 * Взвращает id твиттера для отображения виджета
	 * @return id твиттера
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public String getTwitterId() throws BusinessException
	{
		try
		{
			return getProperty(TWITTER_ID);
		}
		catch (Exception e)
		{
			throw new BusinessException("Не удалось получить значение параметра TwitterId", e);
		}
	}

	protected void doRefresh() throws ConfigurationException
	{
		weatherService = getProperty(WEATHER_SERVICE_KEY);
	}

	public String getWeatherService()
	{
		return weatherService;
	}
}
