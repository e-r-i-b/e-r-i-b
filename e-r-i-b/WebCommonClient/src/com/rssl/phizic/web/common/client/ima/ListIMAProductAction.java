package com.rssl.phizic.web.common.client.ima;

import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.ima.GetIMAProductListOperation;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;

import java.util.Map;

/**
 * Список ОМС для открытия
 * @author Pankin
 * @ created 27.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class ListIMAProductAction extends ListActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		GetIMAProductListOperation operation = createOperation(GetIMAProductListOperation.class, "IMAOpeningClaim");
		operation.initialize();
		return operation;
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		GetIMAProductListOperation op = (GetIMAProductListOperation) operation;
		//noinspection unchecked
		frm.setData(op.getImaProducts());
		ListIMAProductForm form = (ListIMAProductForm) frm;
		form.setCurrencyRates(op.getCurrencyRates());
	}
}
