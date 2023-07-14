package com.rssl.phizic.web.skins;

import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.skins.Skin;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.common.forms.Form;

import java.util.List;

import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author egorova
 * @ created 23.03.2009
 * @ $Author$
 * @ $Revision$
 */
public class ListSkinsAction  extends ListActionBase
{

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation("ViewSkinsList", "SkinsManager");
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListSkinsForm.FORM;
	}
	
	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation("DeleteSkins", "SkinsManager");
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
			ActionMessage message = new ActionMessage(e.getMessage(), false);
			msgs.add(ActionMessages.GLOBAL_MESSAGE, message);
		}
		return msgs;
	}


	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListSkinsForm frm = (ListSkinsForm) form;

		String[] selectedIds = frm.getSelectedIds();

		ListEntitiesOperation viewOperation = createListOperation(frm);
		Query query = viewOperation.createQuery(getQueryName(frm));
		List<Skin> skins = query.executeList();

		ActionMessages msgs = new ActionMessages();

		if (selectedIds.length == 0)
		{
			ActionMessage message = new ActionMessage("com.rssl.phizic.web.security.error.noSelected.skin");
			msgs.add(ActionMessages.GLOBAL_MESSAGE, message);
		}
		else if (selectedIds.length == skins.size())
		{
			ActionMessage message = new ActionMessage("com.rssl.phizic.web.security.error.allSelected.skin");
			msgs.add(ActionMessages.GLOBAL_MESSAGE, message);
		}
		else
		{
			return super.remove(mapping, frm, request, response);
		}
		saveErrors(request, msgs);
		return filter(mapping, form, request, response);
	}
}
