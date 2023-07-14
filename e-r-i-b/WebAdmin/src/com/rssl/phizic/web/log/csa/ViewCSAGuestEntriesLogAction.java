package com.rssl.phizic.web.log.csa;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.log.csa.ViewCSAGuestEntriesLogOperation;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.log.LogFilterBaseAction;

/**
 * @author tisov
 * @ created 23.07.15
 * @ $Author$
 * @ $Revision$
 * Ёкше просмотра списка записей о гостевых входах
 */
public class ViewCSAGuestEntriesLogAction extends LogFilterBaseAction
{
	@Override
	protected String[] getIndexedParameters()
	{
		return new String[0];
	}

	@Override
	protected String getLogName()
	{
		return "csa-guest-entry-log.html";
	}

	@Override
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ViewCSAGuestEntriesLogOperation.class);
	}

	@Override
	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ViewCSAGuestEntriesLogForm.createFilterForm();
	}
}
