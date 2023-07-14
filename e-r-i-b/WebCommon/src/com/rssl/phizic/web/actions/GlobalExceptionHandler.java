package com.rssl.phizic.web.actions;

import com.rssl.phizic.web.util.ExceptionLogHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ExceptionConfig;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Evgrafov
 * @ created 14.09.2006
 * @ $Author: osminin $
 * @ $Revision: 77494 $
 */

public class GlobalExceptionHandler extends DefaultExceptionHandler
{
	public ActionForward execute(Exception ex, ExceptionConfig ae, ActionMapping mapping, ActionForm formInstance,
	                             HttpServletRequest request, HttpServletResponse response) throws ServletException
	{
		ExceptionLogHelper.processExceptionEntry(ex,request.getSession(false));
		return super.execute(ex, ae, mapping, formInstance, request, response);
	}
}