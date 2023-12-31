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
 * ����� ��������� �� ������ ��������
 *
 * @author bogdanov
 * @ created 20.10.14
 * @ $Author$
 * @ $Revision$
 */

public class SearchContactAction extends OperationalActionBase
{
	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
			SearchContactForm frm = (SearchContactForm) form;
			ViewAddressBookOperation operation = createOperation(ViewAddressBookOperation.class);
			frm.setFoundIds(operation.findByPhone(PersonContext.getPersonDataProvider().getPersonData().getLogin(), frm.getIds(), frm.getPhone()));
			frm.setError(false);
			return mapping.findForward(FORWARD_START);
		}
		catch (Exception e)
		{
			log.warn("������ ��� ������ ��������", e);
			SearchContactForm frm = (SearchContactForm) form;
			frm.setError(true);
			return mapping.findForward(FORWARD_START);
		}
	}
}
