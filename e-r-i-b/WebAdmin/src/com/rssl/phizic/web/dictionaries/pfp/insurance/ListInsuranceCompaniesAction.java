package com.rssl.phizic.web.dictionaries.pfp.insurance;

import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.dictionaries.pfp.insurance.ListInsuranceCompaniesOperation;
import com.rssl.phizic.operations.dictionaries.pfp.insurance.RemoveInsuranceCompanyOperation;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import org.apache.struts.action.ActionMessages;

/**
 * @author akrenev
 * @ created 03.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListInsuranceCompaniesAction extends ListActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListInsuranceCompaniesOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveInsuranceCompanyOperation.class);
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
