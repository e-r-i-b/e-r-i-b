package com.rssl.phizic.gate.einvoicing;

import com.rssl.phizic.common.types.Entity;

/**
 * Поставщик услуг для обслуживания интернет-заказов.
 *
 * @author bogdanov
 * @ created 14.02.14
 * @ $Author$
 * @ $Revision$
 */

public interface ShopProvider extends Entity
{
	/**
	 * @return url перехода после оплаты.
	 */
	public String getBackUrl();

	/**
	 * @return имя системны.
	 */
	public String getCodeRecipientSBOL();

	/**
	 * @return имя формы.
	 */
	public String getFormName();

	/**
	 * @return url для обратной связи.
	 */
	public String getUrl();

	/**
	 * @return действия после оплаты.
	 */
	public boolean isAfterAction();

	/**
	 * @return доступен ли MobileCheckout.
	 */
	public boolean isAvailableMobileCheckout();

	/**
	 * @return проверка заказа перед оплатой.
	 */
	public boolean isCheckOrder();

	/**
	 * @return является ли поставщик фасилитатором.
	 */
	public boolean isFacilitator();

	/**
	 * @return признак федерального поставщика.
	 */
	public boolean isFederal();

	/**
	 * @return передавать в интернет-магазин информацию о карте списания.
	 */
	public boolean isSendChargeOffInfo();
}
