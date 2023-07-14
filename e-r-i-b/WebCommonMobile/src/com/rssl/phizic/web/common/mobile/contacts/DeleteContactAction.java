package com.rssl.phizic.web.common.mobile.contacts;

import com.rssl.phizic.operations.contacts.RemoveContactFromAddressBookOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Удаление контактов из адресной книги.
 *
 * @author bogdanov
 * @ created 28.10.14
 * @ $Author$
 * @ $Revision$
 */

public class DeleteContactAction extends OperationalActionBase
{
	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		RemoveContactFromAddressBookOperation operation = createOperation(RemoveContactFromAddressBookOperation.class);
		DeleteContactForm frm = (DeleteContactForm) form;

		try
		{
			operation.initialize(frm.getId());
			frm.setDeleted(operation.isDeleted());
		}
		catch(Exception e)
		{
			saveError(request, e.getMessage());
		}

		return mapping.findForward(FORWARD_START);
	}
}
