package com.rssl.phizic.limits.servises;

import com.rssl.phizic.common.types.limits.Constants;
import com.rssl.phizic.dataaccess.DatabaseServiceBase;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;

/**
 * @author osminin
 * @ created 21.01.14
 * @ $Author$
 * @ $Revision$
 */
public class ActiveRecord
{
	protected static final DatabaseServiceBase databaseService = new DatabaseServiceBase();

	protected static String getInstanceName()
	{
		return Constants.DB_INSTANCE;
	}

	/**
	 * Добавить запись
	 * @throws Exception
	 */
	public void add() throws Exception
	{
		databaseService.add(this, getInstanceName());
	}

	/**
	 * выполнить действие атомарно
	 * @param hibernateAction действие
	 * @param <T> тип результата
	 * @return результат
	 * @throws Exception
	 */
	public static <T> T executeAtomic(HibernateAction<T> hibernateAction) throws Exception
	{
		return getHibernateExecutor().execute(hibernateAction);
	}

	/**
	 * @return HibernateExecutor
	 */
	public static HibernateExecutor getHibernateExecutor()
	{
		return HibernateExecutor.getInstance(getInstanceName());
	}
}
