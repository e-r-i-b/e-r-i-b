package com.rssl.phizic.web.ext.sbrf.deposits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ext.sbrf.dictionaries.SynchronizeSettingsUtil;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.deposits.GetDepositProductsListForDeleteOperation;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Barinov
 * @ created 03.02.2012
 * @ $Author$
 * @ $Revision$
 */

public class ListDepositProductsForDeleteAction extends ListDepositProductsAction
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(GetDepositProductsListForDeleteOperation.class);
	}

	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		RemoveEntityOperation operation = createRemoveOperation((ListFormBase)form);
		operation.remove();
		return start(mapping,form,request,response);
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		ListDepositProductsForm form = (ListDepositProductsForm)frm;
		GetDepositProductsListForDeleteOperation operation = createOperation(GetDepositProductsListForDeleteOperation.class);
		operation.initialize(SynchronizeSettingsUtil.convertDepositProductList(form.getSelectedIds()));
		return operation;
	}
}
