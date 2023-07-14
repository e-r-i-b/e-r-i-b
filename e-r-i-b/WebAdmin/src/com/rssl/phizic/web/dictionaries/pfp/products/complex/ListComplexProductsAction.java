package com.rssl.phizic.web.dictionaries.pfp.products.complex;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author akrenev
 * @ created 11.04.2012
 * @ $Author$
 * @ $Revision$
 */
public abstract class ListComplexProductsAction extends ListActionBase
{
	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}
}
