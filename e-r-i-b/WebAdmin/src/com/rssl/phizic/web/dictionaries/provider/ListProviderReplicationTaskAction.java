package com.rssl.phizic.web.dictionaries.provider;

import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.dictionaries.provider.ListProviderReplicationTaskOperation;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;

/**
 * @author krenev
 * @ created 24.08.2011
 * @ $Author$
 * @ $Revision$
 */
public class ListProviderReplicationTaskAction extends ListActionBase
{

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListProviderReplicationTaskOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}
}