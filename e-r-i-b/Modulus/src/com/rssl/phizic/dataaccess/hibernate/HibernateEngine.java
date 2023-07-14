package com.rssl.phizic.dataaccess.hibernate;

import com.rssl.phizic.common.types.annotation.ThreadSafe;
import com.rssl.phizic.engine.Engine;
import org.hibernate.SessionFactory;

/**
 * @author Erkin
 * @ created 08.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Движок хибернейта:
 * - содержит фабрики 
 */
@ThreadSafe
public interface HibernateEngine extends Engine
{
	/**
	 * Добавить в контекст фабрику сессий
	 * @param instanceName - имя инстанса, например, Log
	 * @param factory - фабрика
	 */
	public void addSessionFactory(String instanceName, SessionFactory factory);

	/**
	 * Получить фабику сессий по имени инстанса
	 * @param instanceName - имя инстанса, например, Log
	 * @return фабрика или null, если не найдена
	 */
	public SessionFactory getSessionFactory(String instanceName);

	/**
	 * Убрать из контекста фабрику сессий
	 * @param instanceName - имя инстанса, например, Log
	 */
	public void removeSessionFactory(String instanceName);
}
