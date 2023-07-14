package com.rssl.phizic.dataaccess.query;

import com.rssl.phizic.cache.CacheAction;
import com.rssl.phizic.cache.CacheHelper;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.utils.StringHelper;
import net.sf.ehcache.Cache;
import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Balovtsev
 * @since  05.06.2015.
 */
public class CachedQueryPagingDataSource<T> extends QueryPaginalDataSource<T> implements PaginalDataSource<T>
{
	public static final long CACHE_KEY = 1L;

	private final Cache cache;

	private Closure    resultHandler;
	private Comparator sortComparator;
	private Predicate  resultFilter;

	/**
	 * @param query запрос
	 * @param cacheName название кэша
	 */
	public CachedQueryPagingDataSource(InternalQuery query, String cacheName)
	{
		super(query);

		if (StringHelper.isEmpty(cacheName))
		{
			throw new IllegalArgumentException("Аргумент cacheName должен быть задан");
		}

		this.cache = CacheProvider.getCache(cacheName);
	}

	public void setResultHandler(Closure resultHandler)
	{
		this.resultHandler = resultHandler;
	}

	public void setSortComparator(Comparator sortComparator)
	{
		this.sortComparator = sortComparator;
	}

	public void setResultFilter(Predicate resultFilter)
	{
		this.resultFilter = resultFilter;
	}

	@Override
	protected List<T> prepareData()
	{
		try
		{
			List<OrderParameter> orderParameters = getOrderParameters();

			if (orderParameters != null)
			{
				getQuery().setOrderParameterList(orderParameters);
			}

			List<T> values = CacheHelper.getCachedEntity(cache, CACHE_KEY, new CacheAction<List<T>>()
			{
				public List<T> getEntity() throws Exception
				{
					List<T> values = getQuery().executeListInternal();

					if (resultHandler != null)
					{
						CollectionUtils.forAllDo(values, resultHandler);
					}

					if (sortComparator != null)
					{
						//noinspection unchecked
						Collections.sort(values, sortComparator);
					}

					return values;
				}
			});

			List<T> filtered = new ArrayList<T>(values);
			if (resultFilter != null)
			{
				CollectionUtils.filter(filtered, resultFilter);
			}

			return filtered.subList(getOffset(filtered), getSize(filtered));
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	protected Integer getOffset(List<T> values)
	{
		Integer offset = super.getOffset();

		if (offset == null)
		{
			offset = 0;
		}

		if (offset > values.size())
		{
			offset = values.size();
		}

		return offset;
	}

	protected Integer getSize(List<T> values)
	{
		Integer offset = super.getOffset();
		Integer size   = super.getSize();

		if (size == null || size > values.size() || offset + size > values.size())
		{
			size = values.size();
		}
		else
		{
			size = size + offset;
		}

		return size;
	}
}
