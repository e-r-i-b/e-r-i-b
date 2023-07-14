package com.rssl.phizic.web.common.socialApi.payments.forms;

import com.rssl.phizic.security.util.MobileApiUtil;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Удаление шаблона
 * @author Dorzhinov
 * @ created 18.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class RemoveTemplateMobileAction extends ViewTemplateMobileAction
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		MobileApiUtil.checkAuthorizedZone();
		return super.remove(mapping, form, request, response);
	}

	protected ActionForward createRemoveTemplateForward()
	{
		return getCurrentMapping().findForward(FORWARD_START);
	}
}