package com.rssl.phizic.business.skins;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author lepihina
 * @ created 06.03.14
 * $Author$
 * $Revision$
 * Конфиг для получения настроек из скинов.
 * Получает настройку глобального скина из БД.
 */
public class SkinConfig extends Config
{
	private Skin globalSkin;
	private static final SkinsService skinsService = new SkinsService();

	/**
	 * @param reader ридер.
	 */
	public SkinConfig(PropertyReader reader)
	{
		super(reader);
	}

	protected void doRefresh() throws ConfigurationException
	{
		try
		{
			globalSkin = skinsService.getGlobalUrl();
		}
		catch (BusinessException e)
		{
			throw new ConfigurationException("Ошибка получения глобального скина.", e);
		}
	}

	/**
	 * @return глобальный скин
	 */
	public Skin getGlobalSkin()
	{
		return globalSkin;
	}
}
