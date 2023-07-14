package com.rssl.phizic.web.tabs;

import com.rssl.auth.csa.front.operations.tabs.GetListTabsOperation;
import com.rssl.phizic.web.common.LookupDispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author osminin
 * @ created 15.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class AsyncListTabsAction extends LookupDispatchAction
{
	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
			AsyncListTabsForm frm = (AsyncListTabsForm) form;
			GetListTabsOperation operation = new GetListTabsOperation();
			operation.initialize();
			frm.setTabs(operation.getAllTabs());
		}
		catch(Exception e)
		{
			log.error(e.getMessage(), e);
		}

		return mapping.findForward(getDefaultMethodName());
	}
}
