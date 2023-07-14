package com.rssl.phizic.web.persons;

import com.rssl.phizic.operations.userprofile.EditPersonAvatarOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Редактирование аватара клиента
 * @author miklyaev
 * @ created 29.05.14
 * @ $Author$
 * @ $Revision$
 */

public class AsyncEditAvatarAction extends OperationalActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.deletePersonAvatar", "deletePersonAvatar");
		return map;
	}

	private ActionForward getEmptyPage(HttpServletResponse response) throws Exception
	{
	    response.getOutputStream().println(" ");
		return null;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return getEmptyPage(response);
	}

	//Удалить текущий аватар клиента
	public ActionForward deletePersonAvatar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AsyncEditAvatarForm frm = (AsyncEditAvatarForm) form;
		EditPersonAvatarOperation operation = createOperation(EditPersonAvatarOperation.class, "EditPersonAvatar");
		operation.deletePersonAvatar(frm.getLoginId());
		return getEmptyPage(response);
	}
}
