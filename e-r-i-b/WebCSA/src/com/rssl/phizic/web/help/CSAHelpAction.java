package com.rssl.phizic.web.help;

import com.rssl.auth.csa.front.operations.help.ReadHelpMappingOpertaion;
import com.rssl.phizic.web.common.LookupDispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akrenev
 * @ created 11.02.2013
 * @ $Author$
 * @ $Revision$
 *
 *  Ёкшен хелпа
 */

public class CSAHelpAction extends LookupDispatchAction
{
	private static final String FORWARD_SHOW = "Show";

	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ReadHelpMappingOpertaion opertaion = new ReadHelpMappingOpertaion();
		CSAHelpForm frm = (CSAHelpForm) form;
		opertaion.initialize(frm.getId());
		frm.setPath(opertaion.getPath());
		frm.setSharp(opertaion.getSharp());
		return mapping.findForward(FORWARD_SHOW);
	}
}
