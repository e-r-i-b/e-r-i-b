package com.rssl.phizic.web.common.mobile.contacts;

import com.rssl.phizic.operations.contacts.GetAddressBookOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.commons.lang.BooleanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ѕолучение адресной книги пользовател€
 *
 * @author bogdanov
 * @ created 28.10.14
 * @ $Author$
 * @ $Revision$
 */

public class GetAddressBookAction extends OperationalActionBase
{
	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		GetAddressBookOperation operation = createOperation(GetAddressBookOperation.class);
		GetAddressBookForm frm = (GetAddressBookForm) form;

		try
		{
 			operation.initialize(frm.getPhones(), BooleanUtils.isTrue(frm.getShowBookmark()));
			frm.setContacts(operation.getContacts());
			if (operation.getErrors() != null && !operation.getErrors().isEmpty())
				frm.setErrorPhones(operation.getErrors());
		}
		catch(Exception e)
		{
			saveError(request, e.getMessage());
		}

		return mapping.findForward(FORWARD_START);
	}
}
