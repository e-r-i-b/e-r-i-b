package com.rssl.phizic.web.client.component;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.widget.TwitterWidgetOperation;
import com.rssl.phizic.operations.widget.WidgetOperation;
import com.rssl.phizic.web.component.WidgetActionBase;
import com.rssl.phizic.web.component.WidgetForm;

/**
 @author: Egorovaa
 @ created: 01.08.2012
 @ $Author$
 @ $Revision$
 */
public class TwitterWidgetAction extends WidgetActionBase
{
	@Override
	protected WidgetOperation createWidgetOperation() throws BusinessException
	{
		return createOperation("TwitterWidgetOperation");
	}

	@Override
	protected void updateForm(WidgetForm form, WidgetOperation operation) throws BusinessException, BusinessLogicException
	{
		super.updateForm(form, operation);

		TwitterWidgetOperation twitterWidgetOperation = (TwitterWidgetOperation) operation;
		TwitterWidgetForm frm = (TwitterWidgetForm) form;
		frm.setTwitterId(twitterWidgetOperation.getTwitterId());
	}
}
