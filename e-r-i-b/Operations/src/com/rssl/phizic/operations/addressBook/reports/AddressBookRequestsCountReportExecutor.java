package com.rssl.phizic.operations.addressBook.reports;

import com.rssl.phizic.business.addressBook.reports.RequestsCountReportEntity;
import com.rssl.phizic.dataaccess.query.ExecutorQuery;

import java.util.Map;

/**
 * @author akrenev
 * @ created 16.06.2014
 * @ $Author$
 * @ $Revision$
 *
 * Запрос данных грида для отчета "По запросам к сервису"
 */

public class AddressBookRequestsCountReportExecutor extends ReportExecutorBase<RequestsCountReportEntity>
{
	static final String FROM_DATE_PARAMETER_NAME = "from_date";
	static final String TO_DATE_PARAMETER_NAME   = "to_date";

	@Override
	protected String getQueryName()
	{
		return AddressBookRequestsCountReportOperation.class.getName() + ".list";
	}

	@Override
	protected void addQueryParameters(ExecutorQuery query, Map<String, Object> parameters)
	{
		query.setParameter(FROM_DATE_PARAMETER_NAME, parameters.get(FROM_DATE_PARAMETER_NAME));
		query.setParameter(TO_DATE_PARAMETER_NAME,   parameters.get(TO_DATE_PARAMETER_NAME));
	}
}
