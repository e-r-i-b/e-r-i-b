package com.rssl.phizic.web.news;

import com.rssl.auth.csa.front.operations.news.LoginViewNewsOperation;
import com.rssl.phizic.web.common.LookupDispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author basharin
 * @ created 06.09.2012
 * @ $Author$
 * @ $Revision$
 */

public class LoginViewNewsAction extends LookupDispatchAction
{
	protected static final String START_FORWARD = "start";

	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			LoginViewNewsForm frm = (LoginViewNewsForm) form;
			LoginViewNewsOperation loginViewNewsOperation = new LoginViewNewsOperation();
			frm.setNews(loginViewNewsOperation.initialize(frm.getId()));
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
		return mapping.findForward(START_FORWARD);
	}

}
