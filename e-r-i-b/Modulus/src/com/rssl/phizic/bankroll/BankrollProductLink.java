package com.rssl.phizic.bankroll;

import com.rssl.phizic.common.types.Currency;

/**
 * @author Erkin
 * @ created 07.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Линк на банковский финансовый продукт - счёт, карту и т.п
 */
public interface BankrollProductLink
{
	/**
	 * @return уникальный в разрезе всех (разнотипных) ссылок код
	 */
	String getCode();

	/**
	 * @return валюта
	 */
	Currency getCurrency();

	/**
	 * @return автоматический СМС-алиас продукта
	 */
	String getAutoSmsAlias();

	/**
	 * @param autoSmsAlias - автоматический СМС-алиас продукта
	 */
	void setAutoSmsAlias(String autoSmsAlias);

	/**
	 * @return true, если по продукту должны отправляться СМС-уведомления
	 */
	boolean getErmbNotification();

	/**
	 * @param ermbNotification - true, если по продукту должны отправляться СМС-уведомления
	 */
	void setErmbNotification(boolean ermbNotification);

	/**
	 * @return пользовательский СМС-алиас продукта
	 */
	String getErmbSmsAlias();

	/**
	 * @param ermbSmsAlias - пользовательский СМС-алиас продукта
	 */
	void setErmbSmsAlias(String ermbSmsAlias);

	/**
	 *
	 * @return true, если продукт должен быть доступен в смс-запросах
	 */
	boolean getShowInSms();

	/**
	 * установить доступность продукта в смс-запросах
	 * @param showInSms - true, если продукт должен быть доступен в смс-запросах
	 */
	void setShowInSms(boolean showInSms);

	/**
	 * вернуть смс-алиас для ответного сообщения клиенту
	 * @return смс-алиас для ответного сообщения клиенту
	 */
	String getSmsResponseAlias();
}
