package com.rssl.phizicgate.esberibgate.cache;

/**
 * Ключи для кеша сообщений шины
 * @author Pankin
 * @ created 22.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class CacheKeyConstants
{
	// Ключ для кеша запросов GFL по ОМС
	public static final String IMA_CLIENT_CACHE = "imaccount-client-cache";

	// Ключ для кеша запросов полной выписки по ОМС
	public static final String IMA_ABSTRACT_CACHE = "imaccount-abstract-cache";

	// Ключ для кеша запросов короткой выписки по ОМС
	public static final String IMA_SHORT_ABSTRACT_CACHE = "imaccount-group-cache";
	
	// Ключ для кеша запросов детальной информации по ОМС
	public static final String IMA_DETAILS_CACHE = "imaccount-details-cache";

	// Ключ для кеша запросов детальной информации по карте
	public static final String CARD_DETAILS_CACHE = "card-details-cache";

	// Ключ для кеша запросов детальной информации по вкладу
	public static final String ACCOUNT_DETAILS_CACHE = "account-details-cache";
}
