package com.rssl.phizic.web.common.mobile.contacts;

import com.rssl.phizic.operations.contacts.SearchContactInAddressBookOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Поиск контактов в адресной книге
 *
 * @author bogdanov
 * @ created 28.10.14
 * @ $Author$
 * @ $Revision$
 */

public class SearchContactAction extends OperationalActionBase
{
	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SearchContactInAddressBookOperation operation = createOperation(SearchContactInAddressBookOperation.class);
		SearchContactForm frm = (SearchContactForm) form;

		try
		{
			operation.initialize(frm.getByName(), frm.getByAlias(), frm.getBySmallAlias(), frm.getByPhone(), frm.isUseLike());
			frm.setContacts(operation.getContacts());
		}
		catch(Exception e)
		{
			saveError(request, e.getMessage());
		}

		return mapping.findForward(FORWARD_START);
	}
}
