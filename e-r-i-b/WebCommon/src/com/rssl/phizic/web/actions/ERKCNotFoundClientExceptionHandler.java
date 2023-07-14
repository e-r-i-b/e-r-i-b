package com.rssl.phizic.web.actions;

import com.rssl.phizic.business.ERKCNotFoundClientBusinessException;
import com.rssl.phizic.web.util.ExceptionLogHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ExceptionConfig;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lepihina
 * @ created 22.08.13
 * @ $Author$
 * @ $Revision$
 */
public class ERKCNotFoundClientExceptionHandler extends org.apache.struts.action.ExceptionHandler
{
	public ActionForward execute(Exception ex, ExceptionConfig ae, ActionMapping mapping, ActionForm formInstance, HttpServletRequest request, HttpServletResponse response) throws ServletException
	{
		ExceptionLogHelper.writeLogMessage(ex);
		ERKCNotFoundClientBusinessException exception = (ERKCNotFoundClientBusinessException) ex;
		request.setAttribute("exceptionMessage", exception.getMessage());

		String path = ae.getPath();
		if (mapping instanceof UserAgentActionMapping)
			path = ((UserAgentActionMapping)mapping).getAgentRelativePath(path);
		return new ActionForward(path);
	}
}
