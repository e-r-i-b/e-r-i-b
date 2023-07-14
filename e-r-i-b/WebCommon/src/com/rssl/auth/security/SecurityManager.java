package com.rssl.auth.security;

/**
 * »нтерфейс проверки доверенного действи€ пользовател€
 * @author niculichev
 * @ created 14.11.13
 * @ $Author$
 * @ $Revision$
 */
public interface SecurityManager
{
	/**
	 * ќбработать пользовательское действие
	 * @param key ключ дл€ обработки
	 */
	void processUserAction(String key);

	/**
	 * явл€етс€ ли действие св€занное с ключом обработки доверенным
	 * @param key ключ обработки
	 * @return true - дейсвтие по ключу доверенное
	 */
	boolean userTrusted(String key);

	/**
	 * —брос состо€ни€
	 * @param key ключ обработки
	 */
	void reset(String key);
}

