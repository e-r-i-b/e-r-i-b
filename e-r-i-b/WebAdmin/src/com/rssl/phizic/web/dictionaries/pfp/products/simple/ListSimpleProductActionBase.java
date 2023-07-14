package com.rssl.phizic.web.dictionaries.pfp.products.simple;

import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.web.common.ListActionBase;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 * @author akrenev
 * @ created 16.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Базовый экшен списка простых продуктов ПФП
 */

public abstract class ListSimpleProductActionBase extends ListActionBase
{
	protected ActionMessages doRemove(RemoveEntityOperation operation, Long id) throws Exception
	{
		try
		{
			return super.doRemove(operation, id);
		}
		catch (BusinessLogicException ble)
		{
			ActionMessages actionErrors = new ActionMessages();
			actionErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(ble.getMessage(), false));
			return actionErrors;
		}
	}
}
