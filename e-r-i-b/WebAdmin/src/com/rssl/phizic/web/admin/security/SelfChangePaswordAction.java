package com.rssl.phizic.web.admin.security;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.passwords.ChangeEmployeePasswordOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.*;

import java.util.Collections;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Gainanov
 * @ created 21.03.2007
 * @ $Author$
 * @ $Revision$
 */
public class SelfChangePaswordAction extends OperationalActionBase
{

	protected Map<String, String> getKeyMethodMap()
	{
		return Collections.singletonMap("button.changePassword", "change");
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		createOperation(ChangeEmployeePasswordOperation.class);
		return mapping.findForward(FORWARD_SHOW_FORM);
	}

	public ActionForward change(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ChangePasswordForm               frm             = (ChangePasswordForm) form;
	    ChangeEmployeePasswordOperation operation = createOperation(ChangeEmployeePasswordOperation.class);

	    MapValuesSource mapValuesSource = new MapValuesSource(frm.getFields());
	    FormProcessor<ActionMessages, ?> formProcessor   = createFormProcessor(mapValuesSource, ChangePasswordForm.CHANGE_PASSWORD_FORM);

	    try
	    {
		    if (formProcessor.process())
		    {
			    Map<String,Object> validationResult = formProcessor.getResult();
				operation.changePassword((String)validationResult.get("newPassword"));

			    ActionMessages msgs = new ActionMessages();
			    msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Пароль на вход в систему изменен", false));
			    saveMessages(request, msgs);
		    }
		    else
		    {
		        saveErrors(request, formProcessor.getErrors());
		    }
	    }
	    catch (BusinessLogicException e)
	    {
		    saveError(request,e.getMessage());
	    }

	    return mapping.findForward(FORWARD_SHOW_FORM);
	}
}
