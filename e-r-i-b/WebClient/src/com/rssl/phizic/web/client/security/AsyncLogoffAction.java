package com.rssl.phizic.web.client.security;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rtischeva
 * @ created 21.06.2013
 * @ $Author$
 * @ $Revision$
 */
public class AsyncLogoffAction extends LogoffClientAction
{
	protected ActionForward findForward(ActionMapping mapping, HttpServletRequest request)
	{
		return null;
	}
}
