package com.rssl.phizic.web.common.dictionaries;

import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.dictionaries.GetOperationCodesOperation;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.gate.dictionaries.CurrencyOperationType;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.common.forms.Form;

import java.util.Map;
import java.util.List;

/**
 * @author Egorova
 * @ created 08.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class ShowOperationCodesAction extends ListActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(GetOperationCodesOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ShowOperationCodesForm.FILTER_FORM;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		query.setParameter("name", filterParams.get("name"));
        query.setParameter("code", filterParams.get("code"));
	}
}
