package com.rssl.phizic.web.common.mobile.profile;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.profile.addressbook.Contact;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.userprofile.addressbook.EditContactOperation;
import com.rssl.phizic.web.actions.ActionFormBase;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.actions.StrutsUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author sergunin
 * @ created 28.10.14
 * $Author$
 * $Revision$
 * Добавление/изменение/удаление номера карты для контакта в адресной книге ЕРИБ
 */
public class EditAddressBookMobileAction extends OperationalActionBase
{
	private static final String FORWARD_ANYWAY = "Anyway";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("add", "start");
		map.put("edit", "start");
		map.put("delete", "delete");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
        return process(true, mapping, form, request, response);
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
        return process(false, mapping, form, request, response);
	}

    private ActionForward process(boolean isToUpdate, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        EditAddressBookForm frm = (EditAddressBookForm) form;
        EditContactOperation operation = createOperation(EditContactOperation.class);

        FieldValuesSource valuesSource = getMapValueSource(frm);
        Form newCategoryForm = isToUpdate ? EditAddressBookForm.EDIT_FORM : EditAddressBookForm.DELETE_FORM;
        FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, newCategoryForm);
        if (processor.process())
        {
            operation.initialize(frm.getContactId(), PersonContext.getPersonDataProvider().getPersonData().getLogin().getId());
            Contact contact = operation.getEntity();
            if(contact == null) {
                saveError(request, StrutsUtils.getMessage("message.mobile.contact.not.found", "paymentsBundle"));
                return mapping.findForward(FORWARD_ANYWAY);
            }
            if(contact.isSberbankClient()) {
                saveError(request, StrutsUtils.getMessage("message.mobile.contact.is.sb.client", "paymentsBundle"));
                return mapping.findForward(FORWARD_ANYWAY);
            }
            contact.setCardNumber(isToUpdate ? frm.getCardNumber() : null);
            operation.save();
        }
        else
        {
            saveErrors(request, processor.getErrors());
        }

        return mapping.findForward(FORWARD_ANYWAY);
    }


    protected MapValuesSource getMapValueSource(ActionFormBase frm)
    {
        EditAddressBookForm form = (EditAddressBookForm) frm;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("contactId", form.getContactId());
        map.put("cardNumber", form.getCardNumber());
        return new MapValuesSource(map);
    }
}
