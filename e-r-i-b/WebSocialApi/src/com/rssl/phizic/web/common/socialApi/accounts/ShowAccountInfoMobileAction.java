package com.rssl.phizic.web.common.socialApi.accounts;

import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.operations.ext.sbrf.account.GetAccountAbstractExtendedOperation;
import com.rssl.phizic.operations.finances.targets.GetTargetOperation;
import com.rssl.phizic.web.common.client.accounts.ShowAccountInfoAction;
import com.rssl.phizic.web.common.client.accounts.ShowAccountInfoForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Детальная информация по вкладу
 * @author mihaylov
 * @ created 23.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class ShowAccountInfoMobileAction extends ShowAccountInfoAction
{
    private static final String FORWARD_SAVE_NAME = "SaveName";
    
    protected Map<String, String> getKeyMethodMap()
    {
        Map<String, String> map = new HashMap<String, String>();
        map.put("saveName", "saveAccountName");
        return map;
    }

    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
	    ShowAccountInfoMobileForm frm = (ShowAccountInfoMobileForm) form;
	    Long linkId = frm.getId();

	    GetAccountAbstractExtendedOperation operation = createOperation(GetAccountAbstractExtendedOperation.class);
		operation.initialize(linkId);
        frm.setAccountLink(operation.getAccount());

	    if (checkAccess(GetTargetOperation.class))
	    {
		    GetTargetOperation targetOperation = createOperation(GetTargetOperation.class);
			frm.setTarget(targetOperation.getTargetByAccountId(operation.getAccount().getId()));
	    }

		return mapping.findForward(FORWARD_SHOW);
    }

    protected MapValuesSource getSaveAccountNameFieldValuesSource(ShowAccountInfoForm form)
    {
        ShowAccountInfoMobileForm frm = (ShowAccountInfoMobileForm) form;
        Map<String,Object> filter = new HashMap<String,Object>();
        filter.put("accountName", frm.getAccountName());
        return new MapValuesSource(filter);
    }

    protected ActionForward forwardSaveAccountName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        return mapping.findForward(FORWARD_SAVE_NAME);
    }
}