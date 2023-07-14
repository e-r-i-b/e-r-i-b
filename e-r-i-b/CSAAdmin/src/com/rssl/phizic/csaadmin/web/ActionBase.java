package com.rssl.phizic.csaadmin.web;

import com.rssl.phizic.utils.StringHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mihaylov
 * @ created 16.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Базовый экшен для CSAAdmin
 */
public abstract class ActionBase extends DispatchAction
{

	@Override
	protected String getMethodName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, String parameter) throws Exception
	{
		String methodName = request.getParameter(parameter);
		if(StringHelper.isEmpty(methodName))
			methodName = "start";
		return methodName;
	}

	/**
	 * Сохранить ошибки в реквест
	 * @param request - реквест
	 * @param errorList - список ошибок
	 */
	protected void saveErrors(HttpServletRequest request, List<String> errorList)
	{
		ActionMessages msgs = new ActionMessages();
		for(String error: errorList)
		{
			ActionMessage msg = new ActionMessage(error, false);
			msgs.add(ActionMessages.GLOBAL_MESSAGE, msg);
		}
		saveErrors(request, msgs);
	}

	/**
	 * Сохранить ошибку в реквест
	 * @param request - реквест
	 * @param error - ошибока
	 */
	protected void saveError(HttpServletRequest request, String error)
	{
		ActionMessages msgs = new ActionMessages();
		ActionMessage msg = new ActionMessage(error, false);
		msgs.add(ActionMessages.GLOBAL_MESSAGE, msg);
		saveErrors(request, msgs);
	}

}
