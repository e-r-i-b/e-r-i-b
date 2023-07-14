package com.rssl.phizic.web.client.loyalty;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akrenev
 * @ created 15.03.2013
 * @ $Author$
 * @ $Revision$
 *
 * печать операций по программе лояльности
 */

public class PrintLoyaltyProgramInfoAction extends LoyaltyProgramInfoAction
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return filter(mapping, form, request,response);
	}
}
