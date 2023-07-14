package com.rssl.phizic.web.common.dictionaries;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.PersonOperationMode;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.dictionaries.RemovePaymentReceiverOperation;
import com.rssl.phizic.operations.dictionaries.receivers.GetPaymentReceiverListOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.util.Map;

/**
 * @author Kidyaev
 * @ created 29.11.2005
 * @ $Author$
 * @ $Revision$
 */
public abstract class ShowPaymentReceiverListActionBase extends ListActionBase
{
	protected String getQueryName(ListFormBase form) throws BusinessException, BusinessLogicException
	{
		ShowPaymentReceiverListForm frm = (ShowPaymentReceiverListForm) form;

		return "list"+frm.getKind();
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams)
	{
		query.setMaxResults(webPageConfig().getListLimit());
	}

	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation) throws Exception
	{
		ShowPaymentReceiverListForm     frm = (ShowPaymentReceiverListForm) form;
		GetPaymentReceiverListOperation op  = (GetPaymentReceiverListOperation) operation;

		frm.setActivePerson(op.getPerson());
		frm.setExternalPaymentSystemId(op.getExternalPaymentSystemIdLink());
		frm.setModified(PersonOperationMode.shadow.equals(op.getMode()));
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase form) throws Exception
	{
		ShowPaymentReceiverListForm     frm = (ShowPaymentReceiverListForm) form;
		GetPaymentReceiverListOperation op  = (GetPaymentReceiverListOperation) operation;

		Query query = op.createQuery(getQueryName(frm), frm.getFilters());
		frm.setData(query.executeList());
		updateFormAdditionalData(frm, operation);
	}

	protected ActionForward createActionForward(ListFormBase form)
	{
		ShowPaymentReceiverListForm frm = (ShowPaymentReceiverListForm) form;

		return new ActionForward(getCurrentMapping().findForward(FORWARD_START + frm.getKind()).getPath() + "?" + makeParamsFromFormData(form), false);
	}

	protected ActionMessages doRemove(RemoveEntityOperation operation, Long id)
	{
		RemovePaymentReceiverOperation op  = (RemovePaymentReceiverOperation) operation;

		ActionMessages msgs = new ActionMessages();
		try
		{
			op.switchToShadow();
			op.initialize(id);
			op.remove();
		}
		catch (BusinessException e)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage()));
		}
		return msgs;
	}

	private String makeParamsFromFormData ( ActionForm form )
	{
		ShowPaymentReceiverListForm frm = (ShowPaymentReceiverListForm)form;
		return "person=" + frm.getPerson() + "&" + "kind=" + frm.getKind();
	}
}
