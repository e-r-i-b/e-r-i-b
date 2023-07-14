package com.rssl.phizic.web.client.header;

import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Dorzhinov
 * @ created 28.06.2012
 * @ $Author$
 * @ $Revision$
 */
public class AsyncHeaderFormatAction extends OperationalActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	protected boolean isAjax()
	{
		return true;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AsyncHeaderFormatForm frm = (AsyncHeaderFormatForm) form;
		return mapping.findForward(FORWARD_START);
	}
}
