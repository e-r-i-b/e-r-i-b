package com.rssl.phizicgate.mdm.business;

import com.rssl.phizic.dataaccess.DatabaseServiceBase;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;

/**
 * @author akrenev
 * @ created 17.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * Базовый сервис работы с БД МДМ
 */

public abstract class MDMDatabaseServiceBase
{
	protected static final DatabaseServiceBase databaseService = new DatabaseServiceBase();

	/**
	 * выполнить действие в транзакции
	 * @param action действие
	 * @param <T> тип возвращаемых данных
	 * @return результат
	 * @throws Exception
	 */
	public static <T> T executeAtomic(HibernateAction<T> action) throws Exception
	{
		return HibernateExecutor.getInstance(getInstance()).execute(action);
	}

	protected static String getInstance()
	{
		return null;
	}
}
