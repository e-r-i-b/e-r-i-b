package com.rssl.phizic.web.creditcards.incomes;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.creditcards.incomes.ListIncomeLevelOperation;
import com.rssl.phizic.operations.creditcards.incomes.RemoveIncomeLevelOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 * @author Dorzhinov
 * @ created 29.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class ListIncomeLevelAction extends ListActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		ListIncomeLevelOperation operation = createOperation(ListIncomeLevelOperation.class);
		operation.initialize();
		((ListIncomeLevelForm)frm).setCurrencies(operation.getCurrencies());
		return operation;
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveIncomeLevelOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected ActionMessages doRemove(RemoveEntityOperation operation, Long id) throws Exception
	{
		RemoveIncomeLevelOperation op = (RemoveIncomeLevelOperation) operation;
		ActionMessages msgs = new ActionMessages();

		try
		{
			super.doRemove(op, id);
		}
		catch (BusinessLogicException e)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
		}
		return msgs;
	}
}
