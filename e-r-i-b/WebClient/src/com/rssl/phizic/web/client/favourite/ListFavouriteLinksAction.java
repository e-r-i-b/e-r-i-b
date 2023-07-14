package com.rssl.phizic.web.client.favourite;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.favouritelinks.ListFavouriteLinksOperation;
import com.rssl.phizic.operations.favouritelinks.RemoveFavouriteLinksOperation;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Список ссылок избранного
 * @ author gorshkov
 * @ created 20.10.13
 * @ $Author$
 * @ $Revision$
 */
public class ListFavouriteLinksAction extends ListActionBase
{
	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.favourite.edit",   "edit");
		return map;
	}

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListFavouriteLinksOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		ListFavouriteLinksForm form = (ListFavouriteLinksForm) frm;
		ListFavouriteLinksOperation favouriteLinksOperation = (ListFavouriteLinksOperation) operation;
		form.setFavouriteLinks(favouriteLinksOperation.getUsedFavouriteLinks());
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListFavouriteLinksForm frm = (ListFavouriteLinksForm) form;
		Long id = StrutsUtils.parseIds(frm.getSelectedIds()).get(0);
		ListFavouriteLinksOperation favouriteLinksOperation = createOperation(ListFavouriteLinksOperation.class);
		favouriteLinksOperation.initialize();
		favouriteLinksOperation.save(id, frm.getNameFavouriteLink());
		return filter(mapping, form, request, response);
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveFavouriteLinksOperation.class);
	}
}
