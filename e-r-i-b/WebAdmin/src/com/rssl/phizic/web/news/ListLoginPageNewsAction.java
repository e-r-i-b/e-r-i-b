package com.rssl.phizic.web.news;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.news.ListLoginPageNewsOperation;
import com.rssl.phizic.operations.news.RemoveLoginPageNewsOperation;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author basharin
 * @ created 27.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class ListLoginPageNewsAction extends ListNewsActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListLoginPageNewsOperation.class, "NewsLoginPageManagment");
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveLoginPageNewsOperation.class, "NewsLoginPageManagment");
	}

	protected void updateFormAdditionalData(ListFormBase frm, ListEntitiesOperation operation) throws Exception
	{
		super.updateFormAdditionalData(frm, operation);
		ListNewsForm form = (ListNewsForm) frm;
		form.setMainNews(false);
	}
}
