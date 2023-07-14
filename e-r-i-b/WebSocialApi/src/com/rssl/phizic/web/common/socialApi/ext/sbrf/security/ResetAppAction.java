package com.rssl.phizic.web.common.socialApi.ext.sbrf.security;

import com.rssl.phizic.business.csa.ConnectorsService;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.LookupDispatchAction;
import com.rssl.phizic.web.security.SessionIdFilter;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * —брос регистрации мобильного приложени€
 * @author Pankin
 * @ created 27.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class ResetAppAction extends LookupDispatchAction
{
	private static final String FORWARD_START = "Start";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> keyMethodMap = new HashMap<String, String>(1);
		keyMethodMap.put("reset", "start");
		return keyMethodMap;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ResetAppForm frm = (ResetAppForm) form;
		frm.setMguid(request.getParameter("mGUID"));

		if (StringHelper.isEmpty(frm.getMguid()))
			throw new SecurityException("ѕараметр mGUID не может быть пустым при сбросе регистрации.");

		ConnectorsService.closeSocialConnector(frm.getMguid());

		HttpSession session = request.getSession(false);
		if (session != null)
			session.setAttribute(SessionIdFilter.INVALIDATE_SESSION_KEY, true);

		return mapping.findForward(FORWARD_START);
	}
}
