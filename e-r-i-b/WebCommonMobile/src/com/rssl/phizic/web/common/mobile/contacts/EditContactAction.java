package com.rssl.phizic.web.common.mobile.contacts;

import com.rssl.phizic.operations.contacts.EditAddressBookContactOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Редактирование контактов в адресной книге.
 *
 * @author bogdanov
 * @ created 28.10.14
 * @ $Author$
 * @ $Revision$
 */

public class EditContactAction extends OperationalActionBase
{
	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditAddressBookContactOperation operation = createOperation(EditAddressBookContactOperation.class);
		EditContactForm frm = (EditContactForm) form;

		try
		{
			operation.initialize(frm.getId(), frm.getName(), frm.getAlias(), frm.getSmallalias(), frm.getCardnumber(), frm.getCategoryEnum(), frm.getTrusted());
			frm.setContact(operation.getContact());
		}
		catch(Exception e)
		{
			saveError(request, e.getMessage());
		}

		return mapping.findForward(FORWARD_START);
	}
}
