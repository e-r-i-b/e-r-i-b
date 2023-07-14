package com.rssl.phizic.web.client.payments.forms;

import com.rssl.phizic.web.actions.OperationalActionBase;

import java.util.Map;
import java.util.HashMap;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Gainanov
 * @ created 07.10.2009
 * @ $Author$
 * @ $Revision$
 */
public class RedirectFromCSAAction extends OperationalActionBase
{
	private static final String FORWARD_SHOW = "ShowForm";
	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		RedirectFromCSAForm frm = (RedirectFromCSAForm)form;
		if(request.getParameter("rec")!=null && request.getParameter("rec").equals("1"))
			frm.setPayment(false);
		if(request.getParameter("payment")!=null && request.getParameter("payment").equals("1"))
			frm.setPayment(true);
		String path = request.getQueryString();
		frm.setPath(path);
		return mapping.findForward(FORWARD_SHOW);
	}
}
