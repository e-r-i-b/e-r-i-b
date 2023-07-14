package com.rssl.phizic.web.client.component;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.widget.WidgetOperation;
import com.rssl.phizic.web.component.WidgetActionBase;

/**
 * @author Mescheryakova
 * @ created 11.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class FacebookWidgetAction extends WidgetActionBase
{
	@Override
	protected WidgetOperation createWidgetOperation() throws BusinessException
	{
		return createOperation("FacebookWidgetOperation");
	}
}
