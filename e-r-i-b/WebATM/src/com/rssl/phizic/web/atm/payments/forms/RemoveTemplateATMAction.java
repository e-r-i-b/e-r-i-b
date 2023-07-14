package com.rssl.phizic.web.atm.payments.forms;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Balovtsev
 * Date: 30.01.13
 * Time: 12:10
 */
public class RemoveTemplateATMAction extends ViewTemplateATMAction
{
	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return super.remove(mapping, form, request, response);
	}

	@Override
	protected ActionForward createRemoveTemplateForward()
	{
		return getCurrentMapping().findForward(FORWARD_START);
	}
}
