package com.rssl.phizic.web.client.userprofile.addressbook;

import com.rssl.phizic.business.profile.addressbook.Contact;
import com.rssl.phizic.business.profile.addressbook.ContactCategory;
import com.rssl.phizic.operations.userprofile.addressbook.EditContactOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Редактирование категории контакта
 *
 * @author shapin
 * @ created 07.10.14
 * @ $Author$
 * @ $Revision$
 */

public class EditContactCategoryAction extends OperationalActionBase
{

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
			EditContactOperation operation = createOperation(EditContactOperation.class);
			EditContactCategoryForm frm = (EditContactCategoryForm) form;
			operation.initialize(frm.getId());
			Contact contact = operation.getEntity();
			contact.setCategory(frm.getCategory().equals(ContactCategory.NONE.toString()) ? ContactCategory.BOOKMARK : ContactCategory.NONE);
			operation.save();
			frm.setCategory(contact.getCategory().toString());
			return mapping.findForward(FORWARD_START);
		}
		catch (Exception e)
		{
			log.warn("Ошибка при изменении категории контакта", e);
			EditContactCategoryForm frm = (EditContactCategoryForm) form;
			frm.setError("true");
			return mapping.findForward(FORWARD_START);
		}
	}
}
