package com.rssl.phizic.operations.addressBook.reports;

import com.rssl.phizic.business.addressBook.reports.ContactsCountReportEntity;
import com.rssl.phizic.dataaccess.query.ExecutorQuery;

import java.util.Map;

/**
 * @author akrenev
 * @ created 16.06.2014
 * @ $Author$
 * @ $Revision$
 *
 * Запрос данных грида для отчета "По количеству контактов"
 */

public class AddressBookContactsCountReportExecutor extends ReportExecutorBase<ContactsCountReportEntity>
{
	static final String LOGIN_ID_PARAMETER_NAME = "login_id";

	@Override
	protected String getQueryName()
	{
		return AddressBookContactsCountReportOperation.class.getName() + ".list";
	}

	@Override
	protected void addQueryParameters(ExecutorQuery query, Map<String, Object> parameters)
	{
		query.setParameter(LOGIN_ID_PARAMETER_NAME, parameters.get(LOGIN_ID_PARAMETER_NAME));
	}
}
