package com.rssl.phizic.web.client.security;

import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.passwords.ChangePersonPasswordOperation;
import com.rssl.phizic.web.security.LoginStageActionSupport;
import org.apache.struts.action.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Kidyaev
 * @ created 22.12.2005
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings({"JavaDoc"})
public class ChangePasswordAction extends LoginStageActionSupport
{
	private static final String FORWARD_SHOW   = "ShowForm";
    private static final String FORWARD_CLOSE = "Close";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String,String> map = new HashMap<String, String>();
		map.put("button.changePassword"  , "change");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
	    try
	    {
            ChangePersonPasswordOperation operation = createOperation(ChangePersonPasswordOperation.class);
            operation.initialize();
	    }
	    catch ( BusinessLogicException ex )
	    {
			ActionMessages errors = new ActionMessages();
		    errors.add( ActionMessages.GLOBAL_MESSAGE, new ActionMessage(ex.getMessage(), false) );
		    saveMessages(request, errors);
	    }

        return new ActionForward( mapping.findForward(FORWARD_SHOW) );
    }

    public ActionForward change(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        ChangePasswordForm      frm       = (ChangePasswordForm) form;
        ChangePersonPasswordOperation operation = createOperation(ChangePersonPasswordOperation.class);
        operation.initialize();

        operation.setNewPassword( frm.getNewPassword() );

	    ActionMessages msgs = new ActionMessages();

	    String errorMessage = null;

	    try
	    {
	        operation.changePassword();
        }
	    catch (Exception ex)
	    {
		    errorMessage = ex.getMessage();
        }

	    if(errorMessage!=null) {
		    msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorMessage, false));
		    saveMessages(request, msgs);
		    return start(mapping, form, request, response);
	    }

	    msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("password.changedSuccessfuly"));
	    saveMessages(request, msgs);
	    return mapping.findForward(FORWARD_CLOSE);

    }

}
