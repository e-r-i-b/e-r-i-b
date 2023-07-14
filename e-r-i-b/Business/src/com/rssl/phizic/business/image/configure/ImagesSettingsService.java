package com.rssl.phizic.business.image.configure;

import com.rssl.phizic.config.*;

import java.math.BigDecimal;

/**
 * @author akrenev
 * @ created 26.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * сервис работы с настройкфми загружаемых изображений в БД
 */

public class ImagesSettingsService extends Config
{
	public static final String ADVERTISING_PARAMETER_NAME = "iccs.properties.image.max.size.advertising";
	public static final String PROVIDER_LOGO_PARAMETER_NAME = "iccs.properties.image.max.size.providerLogo";
	public static final String PROVIDER_PANEL_PARAMETER_NAME = "iccs.properties.image.max.size.providerPanel";
	public static final String PROVIDER_HELP_PARAMETER_NAME = "iccs.properties.image.max.size.providerHelp";
	public static final String MAX_SIZE_PARAMETER_NAME = "iccs.properties.image.max.size";

	private static final String BANNER_WIDTH = "com.rssl.iccs.banner.img.width";
	private static final String BANNER_HEIGHT = "com.rssl.iccs.banner.img.height";

	private ImagesSettings settings;
	private BigDecimal maxSize;
	private int bannerWidth;
	private int bannerHeight;

	public ImagesSettingsService(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * @return Настройки загружаемых изображений в БД
	 * @throws Exception
	 */
	public ImagesSettings getSettings() throws Exception
	{
		return settings;
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		settings = new ImagesSettings();
		settings.setAdvertising(getIntProperty(ADVERTISING_PARAMETER_NAME));
		settings.setProviderLogo(getIntProperty(PROVIDER_LOGO_PARAMETER_NAME));
		settings.setProviderPanel(getIntProperty(PROVIDER_PANEL_PARAMETER_NAME));
		settings.setProviderHelp(getIntProperty(PROVIDER_HELP_PARAMETER_NAME));

		maxSize = new BigDecimal(getProperty(MAX_SIZE_PARAMETER_NAME));
		bannerWidth = getIntProperty(BANNER_WIDTH);
		bannerHeight = getIntProperty(BANNER_HEIGHT);
	}

	public BigDecimal getMaxSize()
	{
		return maxSize;
	}

	public int getBannerWidth()
	{
		return bannerWidth;
	}

	public int getBannerHeight()
	{
		return bannerHeight;
	}
}
