package com.rssl.phizic.web.common.dictionaries;

import com.rssl.phizic.operations.forms.GetPaymentFormListOperation;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;

/**
 * @author Kosyakov
 * @ created 14.03.2007
 * @ $Author: krenev $
 * @ $Revision: 12186 $
 */
public class ShowFormListAction extends ListActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(GetPaymentFormListOperation.class); 
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}
}
