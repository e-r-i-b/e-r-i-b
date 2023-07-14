package com.rssl.phizic.business.ext.sbrf.mobilebank;

/**
 * @author Erkin
 * @ created 28.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class MobileBankConstants
{
	/**
	 * Максимально допустимое кол-во шаблонов СМС-платежей на одного поставщика (для одного пользователя)
	 * При изменении значения нужно также поменять и значение
	 * com.rssl.phizicgate.mobilebank.MobileBankServiceImpl.MAX_PAYERS_PER_RECIPIENT
	 */
	public static final int MAX_PAYERS_PER_RECIPIENT = 5;

	/**
	 * Ссылка на сайт Сбербанка, по которой можно
	 * ознакомиться с услугой "Мобильный банк"
	 */
	public static final String SBERBANK_MOBILE_BANK_DESCRIPTION_URL =
			"http://www.sberbank.ru/ru/person/dist_services/inner_mbank/";

	/**
	 * Ссылка на сайт Сбербанка, по которой можно
	 * просмотреть список поставщиков услуг "Мобильного банка"
	 */
	public static final String SBERBANK_SERVICE_PROVIDERS_LIST_URL =
			"http://www.sberbank.ru/ru/person/dist_services/list_organisations/";

	public static final String DEFAULT_CARD_NAME = "Банковская карта";
}
