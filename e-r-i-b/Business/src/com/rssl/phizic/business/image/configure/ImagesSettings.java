package com.rssl.phizic.business.image.configure;

/**
 * @author akrenev
 * @ created 26.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * Настройки загружаемых изображений в БД
 */

public class ImagesSettings
{
	private Integer advertising;
	private Integer providerLogo;
	private Integer providerPanel;
	private Integer providerHelp;

	/**
	 * @return ограничение на максимальный размер картинки для баннера на главной странице
	 */
	public Integer getAdvertising()
	{
		return advertising;
	}

	/**
	 * задать ограничение на максимальный размер картинки для баннера на главной странице
	 * @param advertising ограничение
	 */
	public void setAdvertising(Integer advertising)
	{
		this.advertising = advertising;
	}

	/**
	 * @return ограничение на максимальный размер картинки для логотипа поставщика услуг
	 */
	public Integer getProviderLogo()
	{
		return providerLogo;
	}

	/**
	 * задать ограничение на максимальный размер картинки для логотипа поставщика услуг
	 * @param providerLogo ограничение
	 */
	public void setProviderLogo(Integer providerLogo)
	{
		this.providerLogo = providerLogo;
	}

	/**
	 * @return ограничение на максимальный размер картинки для иконки на панели быстрой оплаты
	 */
	public Integer getProviderPanel()
	{
		return providerPanel;
	}

	/**
	 * задать ограничение на максимальный размер картинки для иконки на панели быстрой оплаты
	 * @param providerPanel ограничение
	 */
	public void setProviderPanel(Integer providerPanel)
	{
		this.providerPanel = providerPanel;
	}

	/**
	 * @return ограничение на максимальный размер картинки для графической подсказки поставщика услуг
	 */
	public Integer getProviderHelp()
	{
		return providerHelp;
	}

	/**
	 * задать ограничение на максимальный размер картинки для графической подсказки поставщика услуг
	 * @param providerHelp ограничение
	 */
	public void setProviderHelp(Integer providerHelp)
	{
		this.providerHelp = providerHelp;
	}
}
