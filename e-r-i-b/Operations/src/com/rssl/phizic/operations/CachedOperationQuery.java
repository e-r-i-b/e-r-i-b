package com.rssl.phizic.operations;

import com.rssl.phizic.business.operations.Operation;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.CachedQueryPagingDataSource;
import org.apache.commons.collections.Closure;
import org.apache.commons.collections.Predicate;

import java.util.Comparator;
import java.util.List;

/**
* @author Balovtsev
* @since 05.06.2015.
*/
class CachedOperationQuery extends OperationQuery
{
	private final String cacheName;

	private Closure    resultHandler;
	private Comparator sortComparator;
	private Predicate  resultFilter;

	private int resultSize;

	CachedOperationQuery(Operation operation, String name, String cacheName, String instanceName)
	{
		super(operation, name, instanceName);
		this.cacheName = cacheName;
	}

	@Override
	public <T> List<T> executeList() throws DataAccessException
	{
		CachedQueryPagingDataSource<T> dataSource = new CachedQueryPagingDataSource<T>(this, cacheName);
		dataSource.setSize(resultSize);
		dataSource.setResultFilter(resultFilter);
		dataSource.setResultHandler(resultHandler);
		dataSource.setSortComparator(sortComparator);
		return dataSource;
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

	public void setResultSize(int resultSize)
	{
		this.resultSize = resultSize;
	}
}
