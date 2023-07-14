package com.rssl.phizic.web.auth.registration;

import com.rssl.phizic.web.common.LookupDispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * экшен для странице по помощи к входу
 * @author basharin
 * @ created 13.02.14
 * @ $Author$
 * @ $Revision$
 */

public class LoginErrorAction extends LookupDispatchAction
{
	protected static final String START_FORWARD = "start";

	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		return mapping.findForward(START_FORWARD);
	}

}
