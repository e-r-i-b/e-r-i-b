package com.rssl.phizic.web.struts.forms;

import org.apache.struts.Globals;

/** Ключи для сохранения сообщений в реквест
 * @author akrenev
 * @ created 25.11.2011
 * @ $Author$
 * @ $Revision$
 */
public enum ActionMessagesKeys
{
	error(Globals.ERROR_KEY), // Ошибки
	message(Globals.MESSAGE_KEY), // Сообщения
	inactiveExternalSystem("com.rssl.phizgate.ext.sbrf.technobreaks.TechnoBreak.class"),                // Недоступность внешней системы
	sessionError("com.rssl.phizic.web.actions.SESSION_ERRORS_KEY"),     // Сообщение(я) об ошибке, помещенное в сессию
    sessionNotificationsTarget("com.rssl.phizic.web.actions.SESSION_NOTIFICATIONS_TARGET_KEY"), //URL адресата сообщений и ошибок
	additionalMessage("com.rssl.phizic.web.actions.SESSION_ADDITIONAL_MESSAGE_KEY");  // Дополнительное сообщение в отдельном блоке

	private String key;

	private ActionMessagesKeys(String key)
	{
		this.key = key;
	}

	public String getKey()
	{
		return key;
	}
}
