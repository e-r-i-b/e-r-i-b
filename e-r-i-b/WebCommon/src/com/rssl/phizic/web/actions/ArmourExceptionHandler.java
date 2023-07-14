package com.rssl.phizic.web.actions;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ExceptionConfig;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.Constants;

/**
 * @author Evgrafov
 * @ created 14.09.2006
 * @ $Author: omeliyanchuk $
 * @ $Revision: 4391 $
 */

public class ArmourExceptionHandler extends org.apache.struts.action.ExceptionHandler
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	public ActionForward execute(Exception ex, ExceptionConfig ae, ActionMapping mapping, ActionForm formInstance,
	                             HttpServletRequest request, HttpServletResponse response) throws ServletException
	{
		log.warn(ex.getMessage());
		return new ActionForward(ae.getPath());
	}
}
