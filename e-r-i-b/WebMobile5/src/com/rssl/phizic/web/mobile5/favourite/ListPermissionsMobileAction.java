package com.rssl.phizic.web.mobile5.favourite;

import com.rssl.phizic.operations.permissions.ListMobilePermissionsOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.common.mobile.permissions.ListPermissionsMobileForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Список доступных операций в mAPI
 * @author Dorzhinov
 * @ created 08.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListPermissionsMobileAction extends OperationalActionBase
{
    protected Map<String, String> getKeyMethodMap()
    {
        return new HashMap<String, String>();
    }

    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        ListPermissionsMobileForm frm = (ListPermissionsMobileForm) form;
        ListMobilePermissionsOperation operation = createOperation(ListMobilePermissionsOperation.class);

        operation.initialize(getOperationFactory());
        frm.setPermissions(operation.getPermissions());
        return mapping.findForward(FORWARD_START);
    }
}
