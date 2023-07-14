package com.rssl.phizic.web.client.component;

import com.rssl.phizic.web.component.WidgetActionBase;
import com.rssl.phizic.operations.widget.WidgetOperation;
import com.rssl.phizic.business.BusinessException;

/**
 @author: Egorovaa
 @ created: 31.07.2012
 @ $Author$
 @ $Revision$
 */
public class VkGroupsWidgetAction extends WidgetActionBase
{
	@Override
	protected WidgetOperation createWidgetOperation() throws BusinessException
	{
		return createOperation("VkGroupsWidgetOperation");
	}
}
