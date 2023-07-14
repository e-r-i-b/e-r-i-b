package com.rssl.phizic.dataaccess.hibernate;

import com.rssl.phizic.dataaccess.query.Query;

/**
 * @author Krenev
 * @ created 08.12.2008
 * @ $Author$
 * @ $Revision$
 */
public interface Executor
{
	/**
	 * Выполнить действие, перед выполнением действие открывается сессия
	 * и если надо начинается транзакция. После выполнения действие все что было открыто закрывается.
	 *
	 * При вложенных вызовах вниз по стеку наследуются открытые сессия и транзакция
	 * @param action действие
	 * @return
	 * @throws Exception
	 */
	<T> T execute(HibernateAction<T> action) throws Exception;

	/**
	 * Врнуть именованыый запрос.
	 * @param name имя запроса
	 * @return запрос. Внимание!! получение данных по запросу происходит лениво!!!
	 */
	Query getNamedQuery(String name);

	/**
	 * Вернуть имя инстанса БД, с которым работает executor
	 * @return имя инстанса БД, с которым работает executor
	 */
	String getInstanceName();
}
