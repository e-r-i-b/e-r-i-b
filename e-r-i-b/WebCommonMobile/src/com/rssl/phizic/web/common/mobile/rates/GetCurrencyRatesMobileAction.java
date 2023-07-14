package com.rssl.phizic.web.common.mobile.rates;

import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.security.PermissionUtil;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import java.security.AccessControlException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Dorzhinov
 * @ created 29.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class GetCurrencyRatesMobileAction extends OperationalActionBase
{
	private static final String FORWARD_SHOW = "Show";

	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if(!PermissionUtil.impliesService("CurrenciesRateInfo"))
			throw new AccessControlException("Доступ к курсам валют закрыт");

		return mapping.findForward(FORWARD_SHOW);
	}
}
