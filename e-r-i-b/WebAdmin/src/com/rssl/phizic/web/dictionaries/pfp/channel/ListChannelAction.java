package com.rssl.phizic.web.dictionaries.pfp.channel;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.dictionaries.pfp.channel.ListChannelOperation;
import com.rssl.phizic.operations.dictionaries.pfp.channel.RemoveChannelOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 * @author komarov
 * @ created 12.08.13 
 * @ $Author$
 * @ $Revision$
 */

public class ListChannelAction extends ListActionBase
{
	@Override
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListChannelOperation.class);
	}

	@Override
	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	@Override
	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveChannelOperation.class);
	}

	@Override
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
