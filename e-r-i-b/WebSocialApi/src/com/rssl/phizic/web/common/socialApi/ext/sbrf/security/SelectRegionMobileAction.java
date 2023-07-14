package com.rssl.phizic.web.common.socialApi.ext.sbrf.security;

import com.rssl.phizic.web.common.client.ext.sbrf.security.SelectRegionAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Выбор региона при первом входе
 * @author Barinov
 * @ created 03.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class SelectRegionMobileAction extends SelectRegionAction
{

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> methodMap = new HashMap<String, String>();
		methodMap.put("save", "save");
		return methodMap;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		return mapping.findForward(FORWARD_START);
	}
}
