package com.rssl.phizic.web.access.restrictions;

import com.rssl.phizic.operations.access.restrictions.RestrictionOperationBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Evgrafov
 * @ created 24.04.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 1208 $
 */

public abstract class ResourceListResstrictionActionBase<O extends RestrictionOperationBase> extends RestrictionActionBase<O>
{
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		return super.save(mapping, form, request, response);
	}

	protected List<Long> getSelectedIds(ActionForm form)
	{
		RestrictionFormBase frm = (RestrictionFormBase) form;
		String[] selectedIds = frm.getSelectedIds();
		List<Long> ids = new ArrayList<Long>();
		for (String stringId : selectedIds)
		{
			ids.add(Long.valueOf(stringId));
		}
		return ids;
	}
}