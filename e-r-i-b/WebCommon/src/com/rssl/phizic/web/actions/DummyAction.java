package com.rssl.phizic.web.actions;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import java.util.Map;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Пустой экшн, необходим для страниц без указания Action'ов, для помощи
 * @author Gainanov
 * @ created 26.09.2007
 * @ $Author$
 * @ $Revision$
 */

public class DummyAction extends OperationalActionBase
{
	private static final String FORWARD_SHOW = "Show";
	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return mapping.findForward(FORWARD_SHOW);
	}
}
