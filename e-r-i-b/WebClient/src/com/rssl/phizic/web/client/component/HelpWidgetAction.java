package com.rssl.phizic.web.client.component;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.widget.HelpWidgetOperation;
import com.rssl.phizic.operations.widget.WidgetOperation;
import com.rssl.phizic.web.component.WidgetActionBase;

/**
 * @author Erkin
 * @ created 15.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class HelpWidgetAction extends WidgetActionBase
{
	protected WidgetOperation createWidgetOperation() throws BusinessException
	{
		return createOperation(HelpWidgetOperation.class);
	}
}
