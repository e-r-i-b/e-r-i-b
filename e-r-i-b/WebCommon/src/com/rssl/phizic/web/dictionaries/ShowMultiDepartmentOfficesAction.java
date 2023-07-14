package com.rssl.phizic.web.dictionaries;

import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.dictionaries.GetMultiDepartmentOfficesOperation;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.common.forms.Form;

/**
 * @author hudyakov
 * @ created 11.09.2009
 * @ $Author$
 * @ $Revision$
 */

public class ShowMultiDepartmentOfficesAction extends ShowOfficesAction
{
	protected ListEntitiesOperation createListOperation(ListFormBase form) throws BusinessException, BusinessLogicException
	{
		ShowMultiDepartmentOfficesForm frm = (ShowMultiDepartmentOfficesForm) form;
		GetMultiDepartmentOfficesOperation operation = createOperation(GetMultiDepartmentOfficesOperation.class);
	    operation.initialize(frm.getExternalSystemId());
		return operation;
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ShowMultiDepartmentOfficesForm.FILTER_FORM;
	}
}
