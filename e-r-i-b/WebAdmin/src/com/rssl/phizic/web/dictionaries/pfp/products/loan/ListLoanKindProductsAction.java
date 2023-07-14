package com.rssl.phizic.web.dictionaries.pfp.products.loan;

import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.dictionaries.pfp.products.loan.ListLoanKindProductsOperation;
import com.rssl.phizic.operations.dictionaries.pfp.products.loan.RemoveLoanKindProductOperation;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import org.apache.struts.action.ActionMessages;

/**
 * @author akrenev
 * @ created 29.03.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListLoanKindProductsAction extends ListActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListLoanKindProductsOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveLoanKindProductOperation.class);
	}

	protected ActionMessages doRemove(RemoveEntityOperation operation, Long id) throws Exception
	{
		try
		{
			return super.doRemove(operation, id);
		}
		catch (BusinessLogicException ignore)
		{
			saveMessage(currentRequest(), "ƒанный кредитный продукт используетс€ в параметрах созданных целей. ѕожалуйста, измените параметры целей и повторите операцию.");
		}
		return new ActionMessages();
	}
}
