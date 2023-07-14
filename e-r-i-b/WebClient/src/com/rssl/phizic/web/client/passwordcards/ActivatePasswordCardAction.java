package com.rssl.phizic.web.client.passwordcards;

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
 * @ created 21.03.2007
 * @ $Author$
 * @ $Revision$
 */
public class ActivatePasswordCardAction extends OperationalActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String,String> map = new HashMap<String, String>();
	    map.put("button.activate" , "activate");
        return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return new LastPasswordOnCardAction().start(mapping, form, request, response);

	}

	public ActionForward activate (ActionMapping mapping, ActionForm form, HttpServletRequest request,
	                               HttpServletResponse response) throws Exception
	{
		return new LastPasswordOnCardAction().activate(mapping, form, request, response);
	}
}
