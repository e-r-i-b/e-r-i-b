package com.rssl.phizic.business;

/**
 * @author Dorzhinov
 * @ created 20.09.2011
 * @ $Author$
 * @ $Revision$
 */
public class Constants
{
	/**
	 * название базы для CSA
	 */
	public static final String DB_CSA = "CSA";
	/**
	 * режим работы текущего пользователя
	 */
	public static final String USER_VISITING_MODE = "UserVisitingMode";

	/**
	 * название параметра формы для шаблона замены в случае изменения названия продукта, содержащегося в личном меню пользователя
	 */
	public static final String FORM_PATTERN_NAME = "formPatternName";

	// Файл справочника ЦАС НСИ, загружаемого по умолчанию из таска
	public static final String DEFAULT_FILE_NAME = "deposit_products.xml";

	/**
	 * Ключи для выгрузки заявок на кредиты, кредитные карты, виртуальные карты.
	 */
	public static final String LOAN_PRODUCT_KEY      = "UnloadLoanProduct";
	public static final String LOAN_OFFER_KEY        = "UnloadLoanOffer";
	public static final String LOAN_CARD_PRODUCT_KEY = "UnloadLoanCardProduct";
	public static final String LOAN_CARD_OFFER_KEY   = "UnloadLoanCardOffer";
	public static final String VIRTUAL_CARD_KEY      = "UnloadVirtualCard";

	public static final String COMMON_EXCEPTION_MESSAGE = "Операция временно недоступна. Повторите попытку позже.";
}
