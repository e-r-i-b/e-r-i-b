package com.rssl.phizic.config;

/**
 * Содержит ключи к настройкам хранящим временные интервалы, показывающие, возможно ли ещё использование
 * сохранённой информации по продукту
 */
public enum ResourceTypeKey
{
	CARD_TYPE_KEY               ("card.time2refresh.key"),
	LOAN_TYPE_KEY               ("loan.time2refresh.key"),
	LONG_OFFER_TYPE_KEY         ("long.offer.time2refresh.key"),
	ACCOUNT_TYPE_KEY            ("account.time2refresh.key"),
	IMACCOUNT_TYPE_KEY          ("imaccount.time2refresh.key"),
	DEPOACCOUNT_TYPE_KEY        ("depoaccount.time2refresh.key"),
	SECURITY_ACC_TYPE_KEY        ("security.account.time2refresh.key"),

	CARD_INFO_TYPE_KEY          ("cardInfo.time2refresh.key"),
	LOAN_INFO_TYPE_KEY          ("loanInfo.time2refresh.key"),
	ACCOUNT_INFO_TYPE_KEY       ("accountInfo.time2refresh.key");

	private String key;

	ResourceTypeKey(String key)
	{
		this.key = key;
	}

	/**
	 *
	 * Возвращает ключ к значению настройки продукта
	 *
	  * @return String
	 */
	public String toValue()
	{
		return key;
	}
}
