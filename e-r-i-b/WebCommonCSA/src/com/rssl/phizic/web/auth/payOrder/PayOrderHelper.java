package com.rssl.phizic.web.auth.payOrder;

import com.rssl.phizic.config.CSAFrontConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.store.StoreManager;

import static com.rssl.phizic.einvoicing.EInvoicingConstants.*;

/**
 * @ author: Vagin
 * @ created: 08.02.2013
 * @ $Author
 * @ $Revision
 * ’елпер дл€ работы с данными платежа при входе дл€ оплаты с внешней ссылки.
 */
public class PayOrderHelper
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Web);

	public static final String PAY_ORDER_BACK_URL = "payOrderBackURL";

	/**
	 * ¬озвращает режим оплаты с внешней ссылки
	 * @return ‘Ќ—, »нтернет-магазин или ”Ё 
	 */
	public static String getPayOrderMode()
	{
		try
		{
			if (StringHelper.isNotEmpty((String) StoreManager.getCurrentStore().restore(WEBSHOP_REQ_ID)))
				return WEBSHOP_REQ_ID;

			if (StringHelper.isNotEmpty((String) StoreManager.getCurrentStore().restore(FNS_PAY_INFO)))
				return FNS_PAY_INFO;

			if (StringHelper.isNotEmpty((String) StoreManager.getCurrentStore().restore(UEC_PAY_INFO)))
				return UEC_PAY_INFO;

			return null;
		}
		catch (Exception e)
		{
			log.error("—бой при определении режима оплаты с внешней ссылки", e);
			return null;
		}
	}

	/**
	 * @return true, если текущий сеанс пользовател€ - оплата платЄжного поручени€ с сайта ”Ё 
	 */
	public static boolean isUECPaymentSession()
	{
		return UEC_PAY_INFO.equals(getPayOrderMode());
	}

	/**
	 * @return true, если текущий сеанс пользовател€ - оплата платЄжного поручени€ с сайта ”Ё 
	 */
	public static boolean isWebShopPaymentSession()
	{
		return WEBSHOP_REQ_ID.equals(getPayOrderMode());
	}

	/**
	 * явл€етс€ ли вход дл€ оплаты с внешней ссылки.
	 * @return true, если клиент зашел дл€ оплаты с внешней ссылки
	 */
	public static boolean isPayOrder()
	{
		return getPayOrderBackURL() != null;
	}

	/**
	 *  явл€етс€ ли вход корректным.
	 * @return false, если клиент зашел дл€ оплаты с внешней ссылки не с соответствующего сайта
	 */
	public static boolean isCorrectEnter()
	{
		if (getPayOrderBackURL() == null)
			return true;
		if (getPayOrderMode() != null)
			return true;
		return false;
	}	

	/**
	 * ”рл возврата, в случае если зашли оплачивать с внешней ссылки.
	 * @return URL
	 */
	public static String getPayOrderBackURL()
	{
		return StringHelper.getNullIfEmpty((String) StoreManager.getCurrentStore().restore(PAY_ORDER_BACK_URL));
	}

	/**
	 * ќчищаем значени€ данных об оплате с внешней ссылки
	 * в случае успешной аутентификации и перехода в клиентскую часть
	 */
	public static void clearPayOrderSessionData()
	{
		StoreManager.getCurrentStore().remove(UEC_PAY_INFO);
		StoreManager.getCurrentStore().remove(FNS_PAY_INFO);
		StoreManager.getCurrentStore().remove(WEBSHOP_REQ_ID);
		StoreManager.getCurrentStore().remove(PAY_ORDER_BACK_URL);
	}

	/**
	 * ¬озвращает адрес ”Ё 
	 * @return адрес сайта ”Ё 
	 */
	public static String getUECWebSiteUrl()
	{
		CSAFrontConfig frontConfig = ConfigFactory.getConfig(CSAFrontConfig.class);
		return frontConfig.getUECWebSiteUrl();
	}
}
