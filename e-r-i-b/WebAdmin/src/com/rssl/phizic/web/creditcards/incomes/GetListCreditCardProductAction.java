package com.rssl.phizic.web.creditcards.incomes;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.creditcards.products.ListCreditCardProductOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.Map;

/**
 * @author Dorzhinov
 * @ created 01.07.2011
 * @ $Author$
 * @ $Revision$
 */
public class GetListCreditCardProductAction extends ListActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListCreditCardProductOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase form) throws Exception
	{
		GetListCreditCardProductForm frm = (GetListCreditCardProductForm) form;
		ListCreditCardProductOperation op = (ListCreditCardProductOperation) operation;

		frm.setData(op.getAvailableProducts(frm.getCurrency(), frm.getCreditLimit(), frm.getInclude()));
	}
}
