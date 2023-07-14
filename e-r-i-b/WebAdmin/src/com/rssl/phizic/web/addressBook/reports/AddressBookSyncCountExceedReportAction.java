package com.rssl.phizic.web.addressBook.reports;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.addressBook.reports.AddressBookSyncCountExceedReportOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.Map;

/**
 * @author mihaylov
 * @ created 01.07.14
 * @ $Author$
 * @ $Revision$
 *
 * Ёкшен построени€ отчета "ќповещени€ о превышении порога обращени€ к сервису"
 */
public class AddressBookSyncCountExceedReportAction extends ListActionBase
{
	@Override
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(AddressBookSyncCountExceedReportOperation.class);
	}

	@Override
	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return AddressBookSyncCountExceedReportForm.FILTER_FORM;
	}

	@Override
	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		query.setParameter("fromDate", filterParams.get("fromDate"));
		query.setParameter("toDate", filterParams.get("toDate"));
	}
}
