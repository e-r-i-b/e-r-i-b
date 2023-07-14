package com.rssl.phizic.gate;

/**
 * Используется для проверки прав отображения
 * продукта в системах (СБОЛ, УС)
 * @author gladishev
 * @ created 13.01.2011
 * @ $Author$
 * @ $Revision$
 */
public interface ProductPermission  extends AdditionalProductData
{
	/**
	 * Отображать продукт в СБОЛ
	 * @return true - отображать
	 */
	Boolean showInSbol();

	/**
	 * Отображать продукт в устройствах самообслуживания
	 * @return true - отображать
	 */
	Boolean showInES();

	/**
	 * Отображать продукт в АТМ
	 * @return true - отображать
	 */
	Boolean showInATM();

	/**
	 * Отображать продукт в мобильной версии
	 * @return true - отображать
	 */
	Boolean showInMobile();

    /**
	 * Отображать продукт в сщц. приложениях
	 * @return true - отображать
	 */
	Boolean showInSocial();
}
