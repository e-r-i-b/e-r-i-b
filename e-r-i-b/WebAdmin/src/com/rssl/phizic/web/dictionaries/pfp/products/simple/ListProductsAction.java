package com.rssl.phizic.web.dictionaries.pfp.products.simple;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.Map;

/**
 * @author akrenev
 * @ created 01.03.2012
 * @ $Author$
 * @ $Revision$
 */
public abstract class ListProductsAction extends ListSimpleProductActionBase
{
	protected abstract String getListOperationName();
	protected abstract String getRemoveOperationName();

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(getListOperationName());
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(getRemoveOperationName());
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListProductsForm.FILTER_FORM;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams)
	{
		query.setParameter("forComplex", filterParams.get("forComplex"));
	}
}
