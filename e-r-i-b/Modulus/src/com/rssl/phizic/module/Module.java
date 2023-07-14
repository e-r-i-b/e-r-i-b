package com.rssl.phizic.module;

import com.rssl.common.forms.FormsEngine;
import com.rssl.phizic.auth.AuthEngine;
import com.rssl.phizic.bankroll.BankrollEngine;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.annotation.ThreadSafe;
import com.rssl.phizic.dataaccess.hibernate.HibernateEngine;
import com.rssl.phizic.engine.EngineManager;
import com.rssl.phizic.gate.GateEngine;
import com.rssl.phizic.interactive.UserInteractEngine;
import com.rssl.phizic.logging.system.LogEngine;
import com.rssl.phizic.messaging.MessagingEngine;
import com.rssl.phizic.module.loader.ModuleLoader;
import com.rssl.phizic.module.work.WorkManager;
import com.rssl.phizic.payment.PaymentEngine;
import com.rssl.phizic.permission.PermissionEngine;
import com.rssl.phizic.security.ConfirmEngine;
import com.rssl.phizic.session.SessionEngine;
import com.rssl.phizic.text.TextEngine;

/**
 * @author Erkin
 * @ created 06.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Модуль системы
 * Предоставляет информацию:
 * - о том, как модуль загружается/выгружается
 * - о том, как настраивается каждый поток модуля
 * - о механизмах, задействованных в модуле
 */
@ThreadSafe
public interface Module
{
	String KEY = Module.class.getName();

	/**
	 * @return имя модуля
	 */
	String getName();

	/**
	 * @return приложение, в составе которого функцианирует модуль
	 */
	Application getApplication();

	/**
	 * Возвращает загрузчик модуля
	 * @return загрузчик модуля (never null)
	 */
	ModuleLoader getModuleLoader();

	/**
	 * Возвращает менеджер по управлению потоком модуля
	 * @return менеджер потока модуля (never null)
	 */
	WorkManager getWorkManager();

	/**
	 * Возвращает менеджер по движкам
	 * @return менеджер по движкам (never null)
	 */
	EngineManager getEngineManager();

	/**
	 * Возвращает движок логирования
	 * @return движок логирования (never null)
	 */
	LogEngine getLogEngine();

	/**
	 * Возвращает движок хибернейта
	 * @return движок хибернейта или null, если модуль не задействует хибернейт
	 */
	HibernateEngine getHibernateEngine();

	/**
	 * Возвращает движок текстовок
	 * @return движок текстовок или null, если модуль не использует движок текстовок
	 */
	TextEngine getTextEngine();

	/**
	 * Возвращает движок аутентификации
	 * @return движок аутентификации или null, если модуль не предоставляет аутентификацию
	 */
	AuthEngine getAuthEngine();

	/**
	 * Возвращает движок сессий пользователя
	 * @return движок сессий или null, если модуль не предоставляет сессии пользователя
	 */
	SessionEngine getSessionEngine();

	/**
	 * Возвращает движок прав
	 * @return движок прав или null, если модуль не предполагает работу с правами клиента
	 */
	PermissionEngine getPermissionEngine();

	/**
	 * Возвращает движок шлюзов
	 * @return движок шлюзов или null, если модуль не использует движок шлюзов
	 */
	GateEngine getGateEngine();

	/**
	 * Возвращает движок взаимодействия с пользователем
	 * @return движок взаимодействия с пользователем или null, если модуль не взаимодействует с пользователем
	 */
	UserInteractEngine getUserInteractEngine();

	/**
	 * Возвращает движок по формам
	 * @return движок по формам или null, если модуль не работает с формами
	 */
	FormsEngine getFormsEngine();

	/**
	 * Возвращает движок по банковским продуктам
	 * @return движок банкролл или null, если модуль не работает с банковскими продуктами
	 */
	BankrollEngine getBankrollEngine();

	/**
	 * Возвращает движок отправки смс-сообщений
	 * @return движок отправки смс-сообщений или null, если модуль не использует отправку смс-сообщений
	 */
	MessagingEngine getMessagingEngine();

	/**
	 * Возвращает движок платежей
	 * @return движок платежей или null, если модуль не осуществляет платежи
	 */
	PaymentEngine getPaymentEngine();

	/**
	 * Возвращает движок подтверждения
	 * @return движок подтверждения или null, если модуль не работает с подтверждениями
	 */
	ConfirmEngine getConfirmEngine();
}
