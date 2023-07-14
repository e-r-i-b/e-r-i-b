package com.rssl.phizic.operations.addressBook.reports;

import com.rssl.phizic.business.addressBook.reports.ReportEntityBase;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.CustomListExecutor;
import com.rssl.phizic.dataaccess.query.ExecutorQuery;
import com.rssl.phizic.dataaccess.query.OrderParameter;
import com.rssl.phizic.dataaccess.query.PaginalDataSource;

import java.util.List;
import java.util.Map;

/**
 * @author akrenev
 * @ created 16.06.2014
 * @ $Author$
 * @ $Revision$
 *
 * Базовый интерфейс запроса данных грида для отчетов по адресной книге
 */

public abstract class ReportExecutorBase<R extends ReportEntityBase> implements CustomListExecutor<R>
{
	static final String COUNT_PARAMETER_NAME = "count";

	protected abstract void addQueryParameters(ExecutorQuery query, Map<String, Object> parameters);

	protected abstract String getQueryName();

	public final List<R> getList(Map<String, Object> parameters, int size, int offset, List<OrderParameter> orderParameters) throws DataAccessException
	{
		Long maxCount = (Long) parameters.get(COUNT_PARAMETER_NAME);
		ExecutorQuery query = new ExecutorQuery(HibernateExecutor.getInstance(), getQueryName());
		addQueryParameters(query, parameters);
		PaginalDataSource<R> result = (PaginalDataSource<R>) query.<R>executeList();
		result.setOffset(offset);
		result.setSize(getSize(size, offset, maxCount));
		return result;
	}

	private int getSize(int size, int offset, Long maxCount)
	{
		int defaultSize = size + 1;
		if (maxCount == null)
			return defaultSize;

		return Math.min(defaultSize, maxCount.intValue() - offset);
	}
}
