package com.rssl.phizic.web.cards.products;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.card.products.EditCardProductOperation;
import com.rssl.phizic.operations.card.products.ListCardProductOperation;
import com.rssl.phizic.operations.card.products.RemoveCardProductOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.struts.action.*;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author gulov
 * @ created 03.10.2011
 * @ $Authors$
 * @ $Revision$
 */
public class ListCardProductAction extends ListActionBase
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
		return createOperation(ListCardProductOperation.class, "CardProducts");
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListCardProductForm.FILTER_FORM;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		query.setParameter("productName", filterParams.get("productName"));
		String parameter = (String) filterParams.get("online");
		query.setParameter("online", StringHelper.isEmpty(parameter) ? null : BooleanUtils.toBoolean(parameter));
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveCardProductOperation.class, "CardProducts");
	}

	private void publishOrUnpublish(ActionForm form, HttpServletRequest request, Boolean online, String message) throws Exception
	{
		ListFormBase frm = (ListFormBase) form;
		if (ArrayUtils.isEmpty(frm.getSelectedIds()))
		{
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Выберите продукт", false));
			saveErrors(request, errors);
		}
		Long id = frm.getSelectedId();
		EditCardProductOperation operation = createOperation(EditCardProductOperation.class, "CardProducts");
		operation.initialize(id);
		try
		{
			operation.publishOrUnpublish(online, message);
		}
		catch (BusinessLogicException e)
		{
			ActionMessages errors = new ActionMessages();
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
			saveErrors(request, errors);
		}
	}

	/**
	 * Опубликовать продукт
	 */
	public ActionForward publish(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		publishOrUnpublish(form, request, Boolean.TRUE, "Данный продукт уже опубликован.");
		return filter(mapping, form, request, response);
	}

	/**
	 * Снять продукт с публикации
	 */
	public ActionForward unpublish(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		publishOrUnpublish(form, request, Boolean.FALSE, "Данный продукт уже снят с публикации.");
		return filter(mapping, form, request, response);
	}
}