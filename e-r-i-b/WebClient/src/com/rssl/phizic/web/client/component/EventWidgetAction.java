package com.rssl.phizic.web.client.component;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.widget.EventWidgetOperation;
import com.rssl.phizic.operations.widget.WidgetOperation;
import com.rssl.phizic.web.component.WidgetActionBase;

/**
 * Экшн виджета "События"
 * @ author Rtischeva
 * @ created 16.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class EventWidgetAction extends WidgetActionBase
{
	protected WidgetOperation createWidgetOperation() throws BusinessException
	{
		return createOperation(EventWidgetOperation.class);
	}
}
