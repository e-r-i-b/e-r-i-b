package com.rssl.phizic.operations.widget;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.city.CityService;
import com.rssl.phizic.business.web.WeatherWidget;
import com.rssl.phizic.business.web.WidgetConfig;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author Erkin
 * @ created 03.07.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * �������� ��������� �������
 */
public class WeatherWidgetOperation extends WidgetOperation<WeatherWidget>
{
	@Override
	public void update() throws BusinessException
	{
		super.update();

		WeatherWidget widget = getWidget();
		if (widget.getCity() == null)
		{
			// �� ��������� ������������� ��� ������
			widget.setCity(CityService.DEFAULT_CITY_CODE);
		}
	}

	/**
	 * ���������� ���� ��� ��������� �������
	 * @return ���� ��� ��������� �������
	 */
	public String getWeatherServiceKey()
	{
		return ConfigFactory.getConfig(WidgetConfig.class).getWeatherService();
	}
}
