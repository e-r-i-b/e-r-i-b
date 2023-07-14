package com.rssl.phizic.operations;

import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.dataaccess.query.Query;
import org.apache.commons.collections.Closure;
import org.apache.commons.collections.Predicate;

import java.util.Comparator;

/**
 * @author Balovtsev
 * @since  06.06.2015.
 */
public abstract class CachedOperationBase<T, R extends Restriction> extends OperationBase<R>
{
	private int resultSize;

	@Override
	public Query createQuery(String name)
	{
		CachedOperationQuery operationQuery = new CachedOperationQuery(this, name, getCacheName(), getInstanceName());
		operationQuery.setResultSize(resultSize);
		operationQuery.setResultFilter(getResultFilter());
		operationQuery.setResultHandler(getResultHandler());
		operationQuery.setSortComparator(getSortComparator());
		return operationQuery;
	}

	/**
	 * Выполняется после обработчика результатов запроса
	 * {@link com.rssl.phizic.operations.CachedOperationBase#getResultHandler()}
	 *
	 * @return компаратор для сортировки результатов запроса.
	 * @see java.util.Collections#sort(java.util.List, java.util.Comparator)
	 */
	protected Comparator<T> getSortComparator()
	{
		return null;
	}

	/**
	 * Выполняется до сортировки результатов запроса
	 * {@link CachedOperationBase#getSortComparator()}
	 *
	 * @return замыкание реализующее какую-либо обработку списка результатов
	 * @see org.apache.commons.collections.CollectionUtils#forAllDo(java.util.Collection, org.apache.commons.collections.Closure)
	 */
	protected Closure getResultHandler()
	{
		return null;
	}

	/**
	 *
	 * @return объект реализующий фильтрацию списка
	 * @see org.apache.commons.collections.CollectionUtils#filter(java.util.Collection, org.apache.commons.collections.Predicate)
	 */
	protected Predicate getResultFilter()
	{
		return null;
	}

	/**
	 * @return название кэша хранящего список кредитных оферт
	 */
	protected abstract String getCacheName();

	/**
	 *
	 * @param resultSize
	 */
	public void setResultSize(int resultSize)
	{
		this.resultSize = resultSize;
	}
}
