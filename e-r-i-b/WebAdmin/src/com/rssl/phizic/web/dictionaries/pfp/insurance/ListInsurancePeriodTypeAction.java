package com.rssl.phizic.web.dictionaries.pfp.insurance;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.dictionaries.pfp.insurance.ListInsurancePeriodTypeOperation;
import com.rssl.phizic.operations.dictionaries.pfp.insurance.RemoveInsurancePeriodTypeOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.ActionMessages;

/**
 * @author akrenev
 * @ created 15.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListInsurancePeriodTypeAction extends ListActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListInsurancePeriodTypeOperation.class);
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveInsurancePeriodTypeOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected ActionMessages doRemove(RemoveEntityOperation operation, Long id) throws Exception
	{
		try
		{
			return super.doRemove(operation, id);
		}
		catch (BusinessLogicException ble)
		{
			saveMessage(currentRequest(), ble.getMessage());
		}
		return new ActionMessages();
	}
}
