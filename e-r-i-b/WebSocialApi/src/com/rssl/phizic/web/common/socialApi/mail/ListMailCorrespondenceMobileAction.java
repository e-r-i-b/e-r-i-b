package com.rssl.phizic.web.common.socialApi.mail;

import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.operations.ext.sbrf.mail.EditClientMailOperation;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Получение переписки
 * @author Dorzhinov
 * @ created 17.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListMailCorrespondenceMobileAction extends OperationalActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditClientMailOperation op = createOperation(EditClientMailOperation.class);
		ListMailCorrespondenceMobileForm frm = (ListMailCorrespondenceMobileForm) form;
		Long parentId = frm.getParentId();
		if(parentId == null || parentId == 0)
			saveError(request, "Не указан parentId");
		else
		{
			op.initialize(parentId);
			frm.setCorrespondence(op.getCorrespondence(parentId));
		}
		return mapping.findForward(FORWARD_START);
	}
}
