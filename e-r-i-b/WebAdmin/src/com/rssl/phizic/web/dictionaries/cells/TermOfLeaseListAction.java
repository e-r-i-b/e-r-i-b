package com.rssl.phizic.web.dictionaries.cells;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.bankcells.TermOfLease;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.dictionaries.cells.AddTermOfLeaseOperation;
import com.rssl.phizic.operations.dictionaries.cells.RemoveTermOfLeaseOperation;
import com.rssl.phizic.operations.dictionaries.cells.TermOfLeaseListOperation;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Kidyaev
 * @ created 10.11.2006
 * @ $Author$
 * @ $Revision$
 */
public class TermOfLeaseListAction extends ListActionBase
{

	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();

		map.put("button.add", "add"); //Нестандартный функционал.:(
		return map;
	}

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(TermOfLeaseListOperation.class);
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveTermOfLeaseOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		TermOfLeaseListForm frm = (TermOfLeaseListForm) form;
		AddTermOfLeaseOperation operation = createOperation(AddTermOfLeaseOperation.class);
		FieldValuesSource fieldsSource = new RequestValuesSource(request);
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(fieldsSource, TermOfLeaseListForm.EDIT_FORM);
		ActionForward actionForward;

		if (processor.process())
		{
			String newTermOfLeaseDescription = (String) processor.getResult().get("newTermOfLeaseDescription");
			String newTermOfLeaseSortOrder = (String) processor.getResult().get("newTermOfLeaseSortOrder");
			TermOfLease newTermOfLease = operation.getTermOfLease();

			newTermOfLease.setDescription(newTermOfLeaseDescription);
			newTermOfLease.setSortOrder(Long.parseLong(newTermOfLeaseSortOrder));
			operation.save();

			frm.setNewTermOfLeaseDescription("");
			actionForward = sendRedirectToSelf(request);
		}
		else
		{
			saveErrors(request, processor.getErrors());
			actionForward = start(mapping, form, request, response);
		}

		addLogParameters(new BeanLogParemetersReader("Редактируемая сущность", operation.getTermOfLease()));

		return actionForward;
	}
}
