package com.rssl.phizic.web.atm.permissions;

import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.login.exceptions.DegradationFromUDBOToCardException;
import com.rssl.phizic.operations.permissions.ListPermissionsATMOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Права доступа клиента
 * @author Pankin
 * @ created 27.03.2013
 * @ $Author$
 * @ $Revision$
 */

public class ListPermissionsATMAction extends OperationalActionBase
{

    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        ListPermissionsATMForm frm = (ListPermissionsATMForm) form;
        ListPermissionsATMOperation operation = createOperation(ListPermissionsATMOperation.class);
	    try
	    {
		    operation.checkUDBO(frm.isNeedCheckUDBO());
	    }
	    catch (DegradationFromUDBOToCardException e)
	    {
		    saveError(request, e);
	    }
        operation.initialize(getOperationFactory());
        frm.setPermissions(operation.getPermissions());
	    frm.setCheckedUDBO(AuthenticationContext.getContext().isCheckedCEDBO());
        return mapping.findForward(FORWARD_START);
    }
}
