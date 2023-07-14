package com.rssl.phizic.web.loanclaim.creditProduct.type;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.ext.sbrf.dictionaries.loanclaim.creditProduct.type.ListCreditProductTypeOperation;
import com.rssl.phizic.operations.ext.sbrf.dictionaries.loanclaim.creditProduct.type.RemoveCreditProductTypeOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 * @author Moshenko
 * @ created 26.12.2013
 * @ $Author$
 * @ $Revision$
 * Ёкшен получени€ списка типов кредитных продуктов.
 */
public class ListCreditProductTypeAction extends ListActionBase
{

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListCreditProductTypeOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveCreditProductTypeOperation.class);
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
