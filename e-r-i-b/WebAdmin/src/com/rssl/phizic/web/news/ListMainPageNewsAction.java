package com.rssl.phizic.web.news;

import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.news.ListNewsOperation;
import com.rssl.phizic.operations.news.RemoveNewsOperation;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author basharin
 * @ created 27.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class ListMainPageNewsAction extends ListNewsActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		ListNewsOperation listNewsOperation = createOperation(ListNewsOperation.class, "NewsManagment");
		listNewsOperation.considerMultiBlock();
		return listNewsOperation;
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveNewsOperation.class, "NewsManagment");
	}

	protected void updateFormAdditionalData(ListFormBase frm, ListEntitiesOperation operation) throws Exception
	{
		super.updateFormAdditionalData(frm, operation);
		ListNewsForm form = (ListNewsForm) frm;
		form.setMainNews(true);
	}
}
