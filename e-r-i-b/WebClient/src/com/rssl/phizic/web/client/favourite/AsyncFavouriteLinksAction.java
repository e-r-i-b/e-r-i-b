package com.rssl.phizic.web.client.favourite;

import com.rssl.phizic.operations.favouritelinks.ListFavouriteLinksOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Сохранение нового порядка отображения списка избранного
 * @ author gorshkov
 * @ created 20.10.13
 * @ $Author$
 * @ $Revision$
 */
public class AsyncFavouriteLinksAction extends OperationalActionBase
{
	public final ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AsyncFavouriteLinksForm frm = (AsyncFavouriteLinksForm) form;

		ListFavouriteLinksOperation favouriteLinksOperation = createOperation(ListFavouriteLinksOperation.class);
		favouriteLinksOperation.initialize();
		favouriteLinksOperation.save(frm.getSortFavouriteLinks());

		return null;
	}

	protected boolean isAjax()
	{
		return true;
	}
}
