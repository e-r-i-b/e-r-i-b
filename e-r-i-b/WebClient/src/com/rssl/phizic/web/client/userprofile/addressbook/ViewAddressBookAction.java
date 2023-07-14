package com.rssl.phizic.web.client.userprofile.addressbook;

import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.userprofile.addressbook.ViewAddressBookOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Просмотр адресной книги.
 *
 * @author bogdanov
 * @ created 19.09.14
 * @ $Author$
 * @ $Revision$
 */

public class ViewAddressBookAction extends OperationalActionBase
{
	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ViewAddressBookOperation operation = createOperation(ViewAddressBookOperation.class);
		ViewAddressBookForm frm = (ViewAddressBookForm) form;

		operation.initialize(PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin());
		frm.setContactList(operation.getContacts());

		return mapping.findForward(FORWARD_START);
	}
}
