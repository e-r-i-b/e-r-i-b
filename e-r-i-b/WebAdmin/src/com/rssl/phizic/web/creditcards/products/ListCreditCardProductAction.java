package com.rssl.phizic.web.creditcards.products;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.creditcards.products.ListCreditCardProductOperation;
import com.rssl.phizic.operations.creditcards.products.PublishCreditCardProductOperation;
import com.rssl.phizic.operations.creditcards.products.RemoveCreditCardProductOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.*;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Dorzhinov
 * @ created 15.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class ListCreditCardProductAction extends ListActionBase
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
		return createOperation(ListCreditCardProductOperation.class);
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveCreditCardProductOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListCreditCardProductForm.FILTER_FORM;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		query.setParameter("product", filterParams.get("product"));
		query.setParameter("publicity", StringHelper.getNullIfEmpty((String)filterParams.get("publicity")));
	}

	protected ActionMessages doRemove(RemoveEntityOperation operation, Long id) throws Exception
	{
		RemoveCreditCardProductOperation op = (RemoveCreditCardProductOperation) operation;
		ActionMessages msgs = new ActionMessages();

		try
		{
			super.doRemove(op, id);
		}
		catch (BusinessLogicException e)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
		}

		return msgs;
	}

	public ActionForward publish(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PublishCreditCardProductOperation op = createOperation(PublishCreditCardProductOperation.class);
		ListCreditCardProductForm frm = (ListCreditCardProductForm) form;
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
		PublishCreditCardProductOperation op = createOperation(PublishCreditCardProductOperation.class);
		ListCreditCardProductForm frm = (ListCreditCardProductForm) form;
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
}
