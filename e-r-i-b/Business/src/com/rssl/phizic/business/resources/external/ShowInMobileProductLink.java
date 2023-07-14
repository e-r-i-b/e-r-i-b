package com.rssl.phizic.business.resources.external;

/**
 * Базовый класс для ссылок на продукты, которые должны отображаться в мобильном приложении
 * @author Pankin
 * @ created 26.08.2012
 * @ $Author$
 * @ $Revision$
 */

public abstract class ShowInMobileProductLink extends EditableExternalResourceLink
{
	private Boolean isShowInMobile;
    private Boolean isShowInSocial;

	/**
		 * Возвращает признак отображения ссылки в мобильной версии
		 * @return true - отображается
	 */
	public Boolean getShowInMobile()
	{
		return isShowInMobile;
	}

	/**
	 * Установить признак отображения ссылки в мобильной версии
	 * @param showInMobile - true -  отображать
	 */
	public void setShowInMobile(Boolean showInMobile)
	{
		isShowInMobile = showInMobile;
	}

    public Boolean getShowInSocial()
    {
        return isShowInSocial;
    }

    public void setShowInSocial(Boolean showInSocial)
    {
        isShowInSocial = showInSocial;
    }
}
