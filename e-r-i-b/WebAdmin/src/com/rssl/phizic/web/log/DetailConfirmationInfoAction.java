package com.rssl.phizic.web.log;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.log.ListOperationConfirmOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lukina
 * @ created 15.02.2012
 * @ $Author$
 * @ $Revision$
 */

public class DetailConfirmationInfoAction  extends ListActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		if (checkAccess(ListOperationConfirmOperation.class, "LoggingJournalServiceEmployee"))
		{
			return createOperation(ListOperationConfirmOperation.class, "LoggingJournalServiceEmployee");
		}
		else if (checkAccess(ListOperationConfirmOperation.class, "LoggingJournalService"))
		{
			return createOperation(ListOperationConfirmOperation.class, "LoggingJournalService");
		}
		else if (checkAccess(ListOperationConfirmOperation.class, "LoggingJournalServiceEmployeeUseClientForm"))
		{
			return createOperation(ListOperationConfirmOperation.class, "LoggingJournalServiceEmployeeUseClientForm");
		}
		else if (checkAccess(ListOperationConfirmOperation.class, "ViewPaymentList"))
		{
			return createOperation(ListOperationConfirmOperation.class, "ViewPaymentList");
		}
		else if (checkAccess(ListOperationConfirmOperation.class, "ViewPaymentListUseClientForm"))
		{
			return createOperation(ListOperationConfirmOperation.class, "ViewPaymentListUseClientForm");
		}
		return createOperation(ListOperationConfirmOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		query.setParameter("operationUID", filterParams.get("operationUID"));
	}

	protected Map<String, Object> getFilterParams(ListFormBase frm, ListEntitiesOperation operation)
	{
		DetailConfirmationInfoForm form  = (DetailConfirmationInfoForm) frm;
		Map<String, Object> filterParameters = new HashMap<String, Object>();
		filterParameters.put("operationUID", form.getFilter("operationUID"));
		return filterParameters;
	}
}
