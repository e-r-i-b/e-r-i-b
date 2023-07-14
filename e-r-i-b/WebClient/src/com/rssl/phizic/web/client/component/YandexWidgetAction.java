package com.rssl.phizic.web.client.component;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.widget.WidgetOperation;
import com.rssl.phizic.operations.widget.YandexWidgetOperation;
import com.rssl.phizic.web.component.WidgetActionBase;

/**
 * @author Mescheryakova
 * @ created 10.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class YandexWidgetAction extends WidgetActionBase
{
	@Override
	protected WidgetOperation createWidgetOperation() throws BusinessException
	{
		return createOperation(YandexWidgetOperation.class);
	}
}
