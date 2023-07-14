package com.rssl.phizic.web.client.userprofile.addressbook;

import com.rssl.phizic.business.profile.addressbook.Contact;
import com.rssl.phizic.business.profile.addressbook.ContactStatus;
import com.rssl.phizic.operations.userprofile.addressbook.EditContactOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Редактирование статуса контакта
 *
 * @author shapin
 * @ created 07.10.14
 * @ $Author$
 * @ $Revision$
 */

public class EditContactStatusAction extends OperationalActionBase
{
	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
			EditContactStatusForm frm = (EditContactStatusForm) form;
			EditContactOperation operation = createOperation(EditContactOperation.class);
			operation.initialize(frm.getId());
			Contact contact = operation.getEntity();
			contact.setStatus(frm.getRemove() ? ContactStatus.DELETE : ContactStatus.ACTIVE);
			operation.save();
			return mapping.findForward(FORWARD_START);
		}
		catch (Exception e)
		{
			log.warn("Ошибка при изменении статуса контакта", e);
			EditContactStatusForm frm = (EditContactStatusForm) form;
			frm.setError("true");
			return mapping.findForward(FORWARD_START);
		}
	}
}
