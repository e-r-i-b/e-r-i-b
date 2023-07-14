package com.rssl.phizic.operations.asynchSearch;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.operations.OperationBase;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.*;

/**
 * @ author: Gololobov
 * @ created: 28.01.2013
 * @ $Author$
 * @ $Revision$
 */
public abstract class AsynchSearchOperationBase extends OperationBase
{
	//Максимальное кол-во возвращаемых элементов для результата поиска
	private static final int MAX_RESULTS_COUNT = 10;
	/**
	 * Название квери для получения результата "живого" поиска
	 * @return String - название квери для получения результата "живого" поиска
	 */
	protected abstract String getQueryName();

	/**
	 * Максимальное кол-во возвращаемых запросом записей
	 * @return
	 */
	protected int getQueryMaxResults()
	{
		return MAX_RESULTS_COUNT;
	}

	/**
	 * Формирование данных "живого" поиска.
	 * getInstanceName() - имя экземпляра БД в котором будет выполнен запрос
	 * getQueryName() - имя квери для получения данных  
	 * @param queryParametersMap мапа с параметрами для запроса. Если параметры не нужны, то передается null
	 * @throws BusinessException
     * @return List&lt;String&gt;
	 */
	public List<String> search(final Map<String, Object> queryParametersMap) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateAction<List<String>>()
			{
				public List<String> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(getQueryName());
					fillQueryParameters(query, queryParametersMap);
					query.setMaxResults(getQueryMaxResults());
					//noinspection unchecked
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Заполнение параметров запроса
	 * @param query
	 */
	private void fillQueryParameters(Query query, Map<String, Object> queryParametersMap)
	{
		if (queryParametersMap != null)
			for (String key : queryParametersMap.keySet())
			{
				Object value = queryParametersMap.get(key);
				if (value instanceof Calendar)
					query.setCalendar(key, (Calendar) value);
				else if (value instanceof String)
				{
					query.setString(key, (String) value);
				}
				else if (value instanceof Long)
					query.setLong(key, (Long) value);
				else if (value instanceof Boolean)
					query.setBoolean(key, (Boolean) value);
				else if (value instanceof Collection)
					query.setParameterList(key, (Collection) value);
				else
					query.setParameter(key, value);
			}
	}
}
