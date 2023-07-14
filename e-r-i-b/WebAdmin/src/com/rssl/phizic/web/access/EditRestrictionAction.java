package com.rssl.phizic.web.access;

import com.rssl.phizic.business.operations.OperationDescriptor;
import com.rssl.phizic.business.operations.OperationDescriptorService;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.util.Map;
import java.util.HashMap;

/**
 * @author Roshka
 * @ created 19.04.2006
 * @ $Author$
 * @ $Revision$
 */

public class EditRestrictionAction extends OperationalActionBase
{
	private static final String FORWARD_UNSUPPORTED = "Unsupported";

	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	protected String getLookupMapName(HttpServletRequest request, String keyName, ActionMapping mapping) throws ServletException
	{
		return getDefaultMethodName();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Long operationId = Long.valueOf(request.getParameter("operationId"));

		OperationDescriptorService operationDescriptorService = new OperationDescriptorService();
		OperationDescriptor operationDescriptor = operationDescriptorService.findById(operationId);

		ActionForward forward = mapping.findForward(operationDescriptor.getRestrictionInterfaceName());

		ActionForward actionForward;
		if( forward != null )
			actionForward = new ActionForward(forward.getPath() + "?" + request.getQueryString(), false);
		else
			actionForward = mapping.findForward(FORWARD_UNSUPPORTED);

		return actionForward;
	}
}