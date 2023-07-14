package com.rssl.phizic.web.news;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.news.ListNewsOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.actions.SaveFilterParameterAction;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.util.Date;
import java.util.Map;

/**
 * User: Zhuravleva
 * Date: 19.07.2006
 * Time: 13:28:41
 */
public abstract class ListNewsActionBase extends SaveFilterParameterAction
{
	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListNewsForm.FILTER_FORM;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams)
	{
		Date toDate = (Date)filterParams.get("toDate");
		if (toDate != null)
		{
			toDate = DateHelper.add(toDate, 0, 0, 1);
		}
		query.setParameter("title", filterParams.get("title"));
		query.setParameter("fromDate", filterParams.get("fromDate"));
		query.setParameter("toDate", toDate);
		query.setParameter("important", filterParams.get("important"));   //важность
		query.setParameter("state", filterParams.get("state"));
		query.setMaxResults(webPageConfig().getListLimit() + 1);
	}

	protected ActionMessages doRemove(RemoveEntityOperation operation, Long id) throws Exception
	{
		ActionMessages msgs = new ActionMessages();
		try
		{
			return super.doRemove(operation, id);
		}
		catch (BusinessLogicException e)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
		}
		return msgs;
	}

	protected void updateFormAdditionalData(ListFormBase frm, ListEntitiesOperation operation) throws Exception
	{
		ListNewsForm form = (ListNewsForm) frm;
		ListNewsOperation op = (ListNewsOperation) operation;
		form.setDistributions(op.findNewsDistributions(form.getData()));
	}
}
