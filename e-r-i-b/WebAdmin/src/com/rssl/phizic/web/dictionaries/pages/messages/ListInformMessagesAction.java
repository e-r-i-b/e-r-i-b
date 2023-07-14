package com.rssl.phizic.web.dictionaries.pages.messages;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.dictionaries.pages.messages.ListInformMessagesOperation;
import com.rssl.phizic.operations.dictionaries.pages.messages.RemoveInformMessagesOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.Globals;
import org.apache.struts.action.*;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Просмотр списка информационных сообщений.
 * @author komarov
 * @ created 20.09.2011
 * @ $Author$
 * @ $Revision$
 */

public class ListInformMessagesAction extends ListActionBase
{

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListInformMessagesOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListInformMessagesForm.FILTER_FORM;
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveInformMessagesOperation.class);
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		query.setParameter("keyWord", filterParams.get("keyWord"));		
	}

	protected ActionMessages doRemove(RemoveEntityOperation operation, Long id) throws Exception
	{
		ActionMessages msgs = new ActionMessages();
		try
		{
			super.doRemove(operation, id);
		}
		catch(AccessException ex)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(ex.getMessage(), false));
		}
		return msgs;
	}

	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.remove(mapping, form, request, response);
		ActionMessages errors = (ActionMessages) request.getAttribute(Globals.ERROR_KEY);
		if (errors == null || errors.isEmpty())
			saveMessage(currentRequest(), getResourceMessage("informMessagesBundle", "message.all-data-removed"));
		return filter(mapping, form, request, response);
	}
}
