package com.rssl.phizic.web.client.component;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.widget.LoyaltyWidgetOperation;
import com.rssl.phizic.operations.widget.WidgetOperation;
import com.rssl.phizic.web.component.WidgetActionBase;

import java.security.AccessControlException;

/**
 * @author Dorzhinov
 * @ created 09.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class LoyaltyWidgetAction extends WidgetActionBase
{
	protected WidgetOperation createWidgetOperation() throws BusinessException, AccessControlException
	{
		return createOperation(LoyaltyWidgetOperation.class);
	}
}
