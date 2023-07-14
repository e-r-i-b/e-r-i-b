package com.rssl.phizic.web.loans.offices;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.gate.dictionaries.officies.LoanOffice;
import com.rssl.phizic.operations.loans.offices.ListLoanOfficeOperation;
import com.rssl.phizic.operations.loans.offices.RemoveLoanOfficeOperation;
import com.rssl.phizic.operations.loans.offices.SetMainLoanOfficeOperation;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import org.apache.struts.action.*;
import org.hibernate.exception.ConstraintViolationException;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Krenev
 * @ created 12.12.2007
 * @ $Author$
 * @ $Revision$
 */
public class ListLoanOfficeAction extends ListActionBase
{
	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.set.main", "setMain");
		return map;
	}

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListLoanOfficeOperation.class);
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveLoanOfficeOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected ActionMessages doRemove(RemoveEntityOperation operation, Long id) throws Exception
	{
		ActionMessages msgs = new ActionMessages();
		try
		{
			super.doRemove(operation, id);
		}
		catch(ConstraintViolationException ex)
		{
			ActionMessage error = new ActionMessage("error.cannot-remove-object",((LoanOffice) operation.getEntity()).getSynchKey().toString());
			msgs.add(ActionMessages.GLOBAL_MESSAGE, error);
			error = new ActionMessage("error.caused-remove-object");
			msgs.add(ActionMessages.GLOBAL_MESSAGE, error);
		}
		catch (BusinessException e)
		{
			ActionMessage error = new ActionMessage("error.cannot-remove-object",((LoanOffice) operation.getEntity()).getName());
			msgs.add(ActionMessages.GLOBAL_MESSAGE, error);
		}
		return msgs;
	}

	public ActionForward setMain(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		ListLoanOfficeForm frm = (ListLoanOfficeForm) form;
		SetMainLoanOfficeOperation operation = createOperation(SetMainLoanOfficeOperation.class);

		ActionMessages errors = new ActionMessages();
		String[] ids = frm.getSelectedIds();
		if (ids.length != 1)
		{
			ActionMessage error = new ActionMessage("error.select-one-object");
			errors.add(ActionMessages.GLOBAL_MESSAGE, error);
		}
		else
		{
			operation.initialize(ids[0]);
			try
			{
				operation.save();
			}
			catch (BusinessException e)
			{
				ActionMessage error = new ActionMessage("error.cannot-set-main-office");
				errors.add(ActionMessages.GLOBAL_MESSAGE, error);
			}
		}

		addLogParameters(new BeanLogParemetersReader("Сохраняемая сущность", operation.getOffice()) );

		saveErrors(request, errors);
		return start(mapping, form, request, response);
	}
}
