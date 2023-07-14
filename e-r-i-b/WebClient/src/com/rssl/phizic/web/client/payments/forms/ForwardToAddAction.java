package com.rssl.phizic.web.client.payments.forms;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Evgrafov
 * @ created 05.12.2006
 * @ $Author: kosyakova $
 * @ $Revision: 3307 $
 */

public class ForwardToAddAction extends Action
{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String formName = request.getParameter("form");
		ActionForward forward = mapping.findForward("Create" + formName);

		if (forward == null)
		{
			String queryString = request.getQueryString();
			forward = new ActionForward(mapping.findForward("Create").getPath() + "?" + queryString, true);
		}
		return forward;

	}
}