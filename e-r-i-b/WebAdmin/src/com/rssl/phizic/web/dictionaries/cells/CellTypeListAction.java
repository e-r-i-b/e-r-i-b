package com.rssl.phizic.web.dictionaries.cells;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.bankcells.CellType;
import com.rssl.phizic.operations.dictionaries.AddCellTypeOperation;
import com.rssl.phizic.operations.dictionaries.CellTypeListOperation;
import com.rssl.phizic.operations.dictionaries.RemoveCellTypeOperation;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import org.apache.struts.action.*;
import org.hibernate.exception.ConstraintViolationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kidyaev
 * @ created 10.11.2006
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings({"ProhibitedExceptionDeclared"})
public class CellTypeListAction extends ListActionBase
{
	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.add","add");
		map.put("button.checkpresence", "checkPresence");
		return map;
	}

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(CellTypeListOperation.class);
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveCellTypeOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return CellTypeListForm.EDIT_FORM;
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CellTypeListForm     frm       = (CellTypeListForm) form;
		AddCellTypeOperation operation = createOperation(AddCellTypeOperation.class);
		FieldValuesSource                fieldsSource = new RequestValuesSource(request);
		FormProcessor<ActionMessages, ?> processor    = createFormProcessor(fieldsSource, CellTypeListForm.EDIT_FORM);
		ActionForward                    actionForward;

		if ( processor.process() )
		{
		    String   newCellTypeDescription = (String) processor.getResult().get("newCellTypeDescription");
			CellType newCellType            = operation.getNewCellType();

			newCellType.setDescription(newCellTypeDescription);
			operation.save();

			addLogParameters(new BeanLogParemetersReader("ƒобавл€ема€ сущность", operation.getNewCellType()));

		    frm.setNewCellTypeDescription("");
			actionForward = sendRedirectToSelf(request);
		}
		else
		{
		    saveErrors(request, processor.getErrors());
			actionForward = start(mapping, form, request, response);
		}

		return actionForward;
	}

	protected ActionMessages doRemove(RemoveEntityOperation operation, Long id) throws Exception
	{
		ActionMessages msgs = new ActionMessages();
		try
		{
			return super.doRemove(operation, id);
		}
		catch (ConstraintViolationException ex)
		{
			CellType cellType = (CellType) operation.getEntity();
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("“ип €чейки \\u0022" + cellType.getDescription() + "\\u0022 не может быть удален, т.к. прив€зан к одному или нескольким офисам банка."));
		}
		return msgs;
	}

	//TODO что этот метод делает?
	public ActionForward checkPresence(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CellTypeListForm frm         = (CellTypeListForm) form;
		String[]         selectedIds = frm.getSelectedIds();
		if ( selectedIds.length > 0 )
		{
			CellTypeListOperation            operation                  = createOperation(CellTypeListOperation.class);
			Long                             cellTypeId                 = Long.decode(selectedIds[0]);

			frm.setData(operation.createQuery("list").executeList() );
			frm.setOffices(operation.getOfficeList(cellTypeId));

			addLogParameters(new SimpleLogParametersReader("ID провер€емой €чейки", cellTypeId));
		}
		else
		{
			saveError(ActionMessages.GLOBAL_MESSAGE, "com.rssl.phizic.web.dictionaries.bankcells.noSelected", request);
			return start(mapping, form, request, response);
		}

		return mapping.findForward(FORWARD_START);
	}
}
