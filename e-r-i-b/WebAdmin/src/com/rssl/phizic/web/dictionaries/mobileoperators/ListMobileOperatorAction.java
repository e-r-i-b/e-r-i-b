package com.rssl.phizic.web.dictionaries.mobileoperators;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.dictionaries.mobileoperators.ListMobileOperatorOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author Dorzhinov
 * @ created 05.10.2011
 * @ $Author$
 * @ $Revision$
 */
public class ListMobileOperatorAction extends ListActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListMobileOperatorOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM; 
	}
}
