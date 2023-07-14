package com.rssl.phizic.einvoicing;

/**
 * @author Erkin
 * @ created 18.06.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Константы по функциональности E-Invoicing
 * (оплата платёжных поручений Интернет-магазинов, ФНС, УЭК)
 */
public class EInvoicingConstants
{
	/**
	 * Идентификатор заказа (платёжного поручения) из Интернет-магазина
	 */
	public static final String WEBSHOP_REQ_ID    = "ReqId";

	/**
	 * Данные платёжного поручения из ФНС
	 */
	public static final String FNS_PAY_INFO      = "PayInfo";

	/**
	 * Данные платёжного поручения из УЭК
	 */
	public static final String UEC_PAY_INFO      = "UECPayInfo";
}
