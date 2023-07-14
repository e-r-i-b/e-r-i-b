package com.rssl.phizic.web.common.mobile.profile;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.profile.addressbook.Contact;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.userprofile.addressbook.GetContactOperation;
import com.rssl.phizic.web.actions.ActionFormBase;
import com.rssl.phizic.web.actions.OperationalActionBase;
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
 * @ created 29.10.14
 * $Author$
 * $Revision$
 * Получение аватара по номеру телефона из профиля клиента ЕРИБ
 */
public class GetAvatarAction extends OperationalActionBase
{
	private static final String FORWARD_ANYWAY = "Anyway";

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
        GetAvatarForm frm = (GetAvatarForm) form;
        GetContactOperation operation = createOperation(GetContactOperation.class);

        FieldValuesSource valuesSource = getMapValueSource(frm);
        Form newCategoryForm = GetAvatarForm.EDIT_FORM;
        FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSource, newCategoryForm);
        if (processor.process())
        {
            operation.initialize(frm.getPhoneNumber());
            Contact contact = operation.getEntity();
            if(contact == null) {
                log.info("Контакт отсутствует");
                return mapping.findForward(FORWARD_ANYWAY);
            }
            if(!contact.isSberbankClient()) {
                log.info("Контакт не клиента Сбербанка");
                return mapping.findForward(FORWARD_ANYWAY);
            }
            if(contact.isIncognito()) {
                log.info("Контакт инкогнито");
                return mapping.findForward(FORWARD_ANYWAY);
            }
            if(PersonContext.getPersonDataProvider().getPersonData().isIncognito()){
                log.info("Клиент инкогнито");
                return mapping.findForward(FORWARD_ANYWAY);
            }
            frm.setAvatarPath(contact.getAvatarPath());
        }
        else
        {
            saveErrors(request, processor.getErrors());
        }
        return mapping.findForward(FORWARD_ANYWAY);
    }


    protected MapValuesSource getMapValueSource(ActionFormBase frm)
    {
        GetAvatarForm form = (GetAvatarForm) frm;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("phoneNumber", form.getPhoneNumber());
        return new MapValuesSource(map);
    }
}
