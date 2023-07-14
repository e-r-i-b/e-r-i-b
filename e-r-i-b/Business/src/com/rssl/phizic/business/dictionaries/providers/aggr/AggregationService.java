package com.rssl.phizic.business.dictionaries.providers.aggr;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.ExecutorQuery;
import com.rssl.phizic.utils.cache.Cache;
import com.rssl.phizic.utils.cache.OnCacheOutOfDateListener;
import org.hibernate.Session;

import java.sql.PreparedStatement;

/**
 * @author krenev
 * @ created 30.10.14
 * @ $Author$
 * @ $Revision$
 * сервис агрегации каталога услуг
 */
public class AggregationService
{
	private static final String CACHE_KEY = "CURRENT_PARTITION_CACHE_KEY";

	public static final Cache<String, String> currentPartition = new Cache<String, String>(new OnCacheOutOfDateListener<String, String>()
	{
		public String onRefresh(String key)
		{
			try
			{
				return readCurrentPartition();
			}
			catch (Exception e)
			{
				throw new RuntimeException(e);
			}
		}
	}, 5L);

	/**
	 * ѕровести агрегацию
	 */
	public static void aggregate() throws Exception
	{
		HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
		{
			public Void run(Session session) throws Exception
			{
				PreparedStatement statement = null;
				try
				{
					statement = session.connection().prepareStatement("{call SERVICES_AGGREGATION.Refresh()}");
					statement.execute();
				}
				finally
				{
					if (statement != null)
					{
						statement.close();
					}
				}
				return null;
			}
		});
	}

	/**
	 * ¬нести пометки о не обходимости агрегации.
	 */
	public static void markNeedAggregation() throws BusinessException
	{
		try
		{
			new ExecutorQuery(HibernateExecutor.getInstance(), "com.rssl.phizic.business.dictionaries.providers.aggr.AggregationService.markNeedAggregation").executeUpdate();
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private static String readCurrentPartition() throws Exception
	{
		return HibernateExecutor.getInstance().execute(new HibernateAction<String>()
		{
			public String run(Session session) throws Exception
			{
				return (String) session.getNamedQuery("com.rssl.phizic.business.dictionaries.providers.aggr.AggregationService.getCurrentPartition").uniqueResult();
			}
		});
	}

	/**
	 * @return ключ текущей секции, из которой требуетс€ строить каталог услуг по агрегированным данным.
	 */
	public static String getCurrentPartition() throws Exception
	{
		return currentPartition.getValue(CACHE_KEY);
	}
}
