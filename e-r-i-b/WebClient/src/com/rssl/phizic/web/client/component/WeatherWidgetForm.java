package com.rssl.phizic.web.client.component;

import com.rssl.phizic.web.component.WidgetForm;

/**
 * @author Erkin
 * @ created 18.06.2012
 * @ $Author$
 * @ $Revision$
 */

public class WeatherWidgetForm extends WidgetForm
{
	private String weatherServiceKey; // ключ для погодного сервиса

	public String getWeatherServiceKey()
	{
		return weatherServiceKey;
	}

	public void setWeatherServiceKey(String weatherServiceKey)
	{
		this.weatherServiceKey = weatherServiceKey;
	}
}
