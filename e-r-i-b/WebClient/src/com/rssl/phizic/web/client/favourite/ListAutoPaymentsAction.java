package com.rssl.phizic.web.client.favourite;

import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.security.PermissionUtil;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author bogdanov
 * @ created 24.01.2012
 * @ $Author$
 * @ $Revision$
 */

public class ListAutoPaymentsAction extends OperationalActionBase
{
	private static final String ACCESS_ERROR_MESSAGE = "У вас нет прав на работу с вкладкой «Автоплатежи».";
	protected static final String FORWARD_CANCEL = "Cancel";

	protected final Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	public final ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if(!PermissionUtil.impliesService("AutoPaymentsManagment"))
		{
			saveError(request, ACCESS_ERROR_MESSAGE);
			return mapping.findForward(FORWARD_CANCEL);
		}
		return mapping.findForward(FORWARD_START );
	}
}
