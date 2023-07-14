package com.rssl.phizic.web.addressBook.reports;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.addressBook.reports.RequestsCountReportEntity;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.addressBook.reports.AddressBookRequestsCountReportOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.Collections;
import java.util.Map;

/**
 * @author akrenev
 * @ created 11.06.2014
 * @ $Author$
 * @ $Revision$
 *
 * Экшен постороения отчета "По запросам к сервису"
 */

public class AddressBookRequestsCountReportAction extends ListActionBase
{
	@Override
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(AddressBookRequestsCountReportOperation.class);
	}

	@Override
	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return AddressBookRequestsCountReportForm.getFilterForm();
	}

	@Override
	protected void fillQuery(Query query, Map<String, Object> filterParams)
	{
		query.setParameter(AddressBookRequestsCountReportOperation.COUNT_PARAMETER_NAME,     filterParams.get(AddressBookRequestsCountReportForm.COUNT_FIELD_NAME));
		query.setParameter(AddressBookRequestsCountReportOperation.FROM_DATE_PARAMETER_NAME, filterParams.get(AddressBookRequestsCountReportForm.FROM_DATE_FIELD_NAME));
		query.setParameter(AddressBookRequestsCountReportOperation.TO_DATE_PARAMETER_NAME,   filterParams.get(AddressBookRequestsCountReportForm.TO_DATE_FIELD_NAME));
	}

	@Override
	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		AddressBookRequestsCountReportForm form = (AddressBookRequestsCountReportForm) frm;
		if (form.isFromStart())
		{
			form.setData(Collections.<RequestsCountReportEntity>emptyList());
			form.setFilters(filterParams);
			updateFormAdditionalData(form, operation);
		}
		else
		{
			super.doFilter(filterParams, operation, form);
		}
	}
}
