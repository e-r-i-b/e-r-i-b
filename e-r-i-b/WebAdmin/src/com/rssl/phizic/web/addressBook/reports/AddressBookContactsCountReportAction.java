package com.rssl.phizic.web.addressBook.reports;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.addressBook.reports.ContactsCountReportEntity;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.addressBook.reports.AddressBookContactsCountReportOperation;
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
 * Экшен постороения отчета "По количеству контактов"
 */

public class AddressBookContactsCountReportAction extends ListActionBase
{
	@Override
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException
	{
		return createOperation(AddressBookContactsCountReportOperation.class);
	}

	@Override
	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return AddressBookContactsCountReportForm.getFilterForm();
	}

	@Override
	protected void fillQuery(Query query, Map<String, Object> filterParams)
	{
		query.setParameter(AddressBookContactsCountReportOperation.COUNT_PARAMETER_NAME,    filterParams.get(AddressBookContactsCountReportForm.COUNT_FIELD_NAME));
		query.setParameter(AddressBookContactsCountReportOperation.LOGIN_ID_PARAMETER_NAME, filterParams.get(AddressBookContactsCountReportForm.LOGIN_ID_FIELD_NAME));
	}

	@Override
	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		AddressBookContactsCountReportForm form = (AddressBookContactsCountReportForm) frm;
		if (form.isFromStart())
		{
			form.setData(Collections.<ContactsCountReportEntity>emptyList());
			form.setFilters(filterParams);
			updateFormAdditionalData(form, operation);
		}
		else
		{
			super.doFilter(filterParams, operation, form);
		}
	}
}
