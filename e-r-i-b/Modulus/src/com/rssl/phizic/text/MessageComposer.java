package com.rssl.phizic.text;

import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.security.ConfirmCodeMessage;
import com.rssl.phizic.security.ConfirmableTask;
import com.rssl.phizic.task.Task;

/**
 * @author Erkin
 * @ created 28.05.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Композитор сообщений для клиента
 */
public interface MessageComposer
{
	/**
	 * Сгенерировать текстовку сообщения с кодом подтверждения
	 * @param confirmCode - код подтверждения (never null, never empty)
	 * @param confirmableTask - задача, которую подтверждаем
	 * @return текстовка с кодом подтверждения для передачи клиенту
	 */
	ConfirmCodeMessage buildConfirmCodeMessage(String confirmCode, ConfirmableTask confirmableTask);

	/**
	 * Сгенерировать текстовку сообщения о неизвестной ошибке, возникшей при выполнении таска
	 * @param task - задача, в которой возникла ошибка
	 * @return текстовка сообщения
	 */
	TextMessage buildFatalErrorMessage(Task task);

	/**
	 * Сгенерировать текстовку сообщения об ошибке доступа к операции
	 * @return текстовка сообщения
	 */
	TextMessage buildAccessControlErrorMessage();

	/**
	 * Сгенерировать текстовку сообщения об ошибке, которая возникает когда
	 * к номеру телефона не подключена услуга мобильный банк
	 * @return текстовка сообщения
	 */
	TextMessage buildProfileNotFoundErrorMessage();

	/**
	 * Сгенерировать текстовку сообщения об ошибке - "Не доступна АБС."
	 * @param task - задача, в которой возникла ошибка
	 * @return текстовка сообщения
	 */
	TextMessage buildInactiveExternalSystemErrorMessage(Task task);
}
