package com.rssl.phizic.web.client.login;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * Форма для предложения об удалённом заключении договора УДБО при входе в СБОЛ
 * User: kichinova
 * Date: 15.12.14
 * Time: 10:16
 */
public class ConnectUdboLoginForm extends ActionFormBase
{
	private String cancelMessageTitle;
	private String cancelMessageText;

	/**
	 * @return Текст сообщения при отказе от подключения УДБО
	 */
	public String getCancelMessageText()
	{
		return cancelMessageText;
	}

	/**
	 * Задать Текст сообщения при отказе от подключения УДБО
	 * @param cancelMessageText - Текст
	 */
	public void setCancelMessageText(String cancelMessageText)
	{
		this.cancelMessageText = cancelMessageText;
	}

	/**
	 * @return Заголовок сообщения при отказе от подключения УДБО
	 */
	public String getCancelMessageTitle()
	{
		return cancelMessageTitle;
	}

	/**
	 * Задать Заголовок сообщения при отказе от подключения УДБО
	 * @param cancelMessageTitle - Заголовок
	 */
	public void setCancelMessageTitle(String cancelMessageTitle)
	{
		this.cancelMessageTitle = cancelMessageTitle;
	}
}
