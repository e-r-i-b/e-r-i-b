package com.rssl.phizic.business.ermb.sms.messaging;

import com.rssl.phizic.business.ermb.sms.command.Command;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.session.PersonSession;
import com.rssl.phizic.text.MessageComposer;

/**
 * @author Gulov
 * @ created 25.06.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Необходимые реквизиты и действия при выполнении команды
 */
public interface ExecutorRequisites
{
	/**
	 * Выполнить аутентификацию, создать сессию
	 * @return сессия
	 */
	PersonSession authenticate();

	/**
	 * Создать команду
	 * @return команда
	 */
	Command createCommand();

	/**
	 * Получить модуль
	 * @return модуль
	 */
	Module getModule();

	/**
	 * Получить телефон
	 * @return телефон
	 */
	String getPhone();

	/**
	 * Обработка ошибки Не доступна АБС.
	 * @param command - команда
	 * @param messageComposer - Композитор сообщений для клиента
	 */
	void handleInactiveExternalSystemException(Command command, MessageComposer messageComposer);

	/**
	 * Действия, которые необходимо выполнить сразу после успешного выполнения команды
	 * @param command - команда
	 */
	void doAfterExecute(Command command);
}
