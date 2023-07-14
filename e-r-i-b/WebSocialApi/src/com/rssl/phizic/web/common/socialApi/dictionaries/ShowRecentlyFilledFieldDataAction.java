package com.rssl.phizic.web.common.socialApi.dictionaries;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.BusinessFieldSubType;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.dictionaries.ShowRecentlyFilledFieldDataOperation;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ author: Vagin
 * @ created: 22.07.2013
 * @ $Author
 * @ $Revision
 * Ёкшен получени€ справочника доверенных получателей
 */
public class ShowRecentlyFilledFieldDataAction extends OperationalActionBase
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowRecentlyFilledFieldDataForm frm = (ShowRecentlyFilledFieldDataForm) form;
		BusinessFieldSubType subtype = BusinessFieldSubType.valueOf(frm.getType());

        ShowRecentlyFilledFieldDataOperation operation;

        if(PermissionUtil.impliesOperation("ShowRecentlyFilledFieldDataOperation", "AllRecentlyFilledFieldDataManagement"))
        {
            operation = createOperation("ShowRecentlyFilledFieldDataOperation", "AllRecentlyFilledFieldDataManagement");
		    operation.initialize(PersonContext.getPersonDataProvider().getPersonData().getLogin(), subtype);
	        frm.setContacts(operation.getContacts());
        }
        else if (PermissionUtil.impliesOperation("ShowRecentlyFilledFieldDataOperation", "SelfRecentlyFilledFieldDataManagement")
                       && subtype.equals(BusinessFieldSubType.phone))
        {
            operation = createOperation("ShowRecentlyFilledFieldDataOperation", "SelfRecentlyFilledFieldDataManagement");
            operation.initializeSelf(PersonContext.getPersonDataProvider().getPersonData().getLogin(), subtype);
	        frm.setPhones(operation.getSelfPhones());
        }
        else
            throw new BusinessException("operation ShowRecentlyFilledFieldDataOperation is not allowed");


		return mapping.findForward(FORWARD_START);
	}
}
