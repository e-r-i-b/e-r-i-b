package com.rssl.phizic.web.dictionaries.pfp.products.simple.trustManaging;

import com.rssl.phizic.web.dictionaries.pfp.products.simple.ListSimpleProductActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;

/**
 * @author akrenev
 * @ created 16.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * экшен списка продуктов "доверительное управление"
 */

public class ListTrustManagingProductAction extends ListSimpleProductActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation("ListTrustManagingProductOperation");
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation("RemoveTrustManagingProductOperation");
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected String getQueryName(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return "trustManaging.list";
	}
}
