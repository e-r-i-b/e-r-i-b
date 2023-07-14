package com.rssl.phizic.web.loans.products;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.loans.kinds.LoanKind;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.loans.products.ListLoanProductOperation;
import com.rssl.phizic.operations.loans.products.PublishLoanProductOperation;
import com.rssl.phizic.operations.loans.products.RemoveLoanProductOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.*;
import org.hibernate.exception.ConstraintViolationException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author gladishev
 * @ created 04.12.2007
 * @ $Author$
 * @ $Revision$
 */

public class ListLoanProductAction extends ListActionBase
{
	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String, String> map = super.getAditionalKeyMethodMap();
		map.put("button.publish", "publish");
		map.put("button.unpublish", "unpublish");
		return map;
	}

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListLoanProductOperation.class);
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws DataAccessException, BusinessException, BusinessLogicException
	{
		ListLoanProductOperation op = (ListLoanProductOperation) operation;

		if(frm instanceof ListModifiableLoanProductForm)
		{
			ListModifiableLoanProductForm form = (ListModifiableLoanProductForm) frm;
			form.setProductsByKind(op.getProductsByKind(filterParams));
		}
		else if(frm instanceof ListLoanProductForm)
			frm.setData(op.getAllLoanProducts());

		updateFormAdditionalData(frm, operation);
	}

	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation) throws BusinessException
	{
		ListLoanProductOperation op = (ListLoanProductOperation) operation;
		if(form instanceof ListLoanProductForm)
		{
			ListLoanProductForm frm = (ListLoanProductForm) form;
			frm.setLoanKinds(getLoanKindsMap(op.getAllLoanKinds()));
		}
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListModifiableLoanProductForm.FILTER_FORM;
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveLoanProductOperation.class);
	}

	protected ActionMessages doRemove(RemoveEntityOperation operation, Long id) throws Exception
	{
		RemoveLoanProductOperation op = (RemoveLoanProductOperation) operation;
		ActionMessages msgs = new ActionMessages();

		if (!op.checkBeforeRemove(op, id.toString()))
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.cannot-remove-loanproduct-claims"));
			return msgs;
		}

		try
		{
			super.doRemove(op, id);
		}
		catch (ConstraintViolationException e)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.cannot-remove-loanproduct-offers"));
		}
		catch (BusinessLogicException e)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
		}

		return msgs;
	}

	public ActionForward publish(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PublishLoanProductOperation op = createOperation(PublishLoanProductOperation.class);
		ListModifiableLoanProductForm frm = (ListModifiableLoanProductForm) form;
		ActionMessages msgs = new ActionMessages();

		op.initialize(frm.getSelectedId());
		try
		{
			op.publish();
		}
		catch (BusinessLogicException e)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
		}
		saveMessages(request, msgs);
		return filter(mapping, form, request, response);
	}

	public ActionForward unpublish(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PublishLoanProductOperation op = createOperation(PublishLoanProductOperation.class);
		ListModifiableLoanProductForm frm = (ListModifiableLoanProductForm) form;
		ActionMessages msgs = new ActionMessages();

		op.initialize(frm.getSelectedId());
		try
		{
			op.unpublish();
		}
		catch (BusinessLogicException e)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
		}
		saveMessages(request, msgs);
		return filter(mapping, form, request, response);
	}

	private Map<Long, String> getLoanKindsMap(List<LoanKind> loanKinds) throws BusinessException
	{
		Map<Long, String> result = new HashMap<Long, String>();

		for (LoanKind kind : loanKinds)
		{
			result.put(kind.getId(), kind.getName());
		}
		return result;
	}
}
