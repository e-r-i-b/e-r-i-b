package com.rssl.phizic.web.common.socialApi.ext.sbrf.userprofile;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.messaging.MailFormat;
import com.rssl.phizic.operations.messaging.EditUserContactDataOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.struts.forms.FormHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author EgorovaA
 * @ created 18.06.14
 * @ $Author$
 * @ $Revision$
 *
 * Редактирование email клиента
 */
public class EditEmailAction extends OperationalActionBase
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		EditUserContactDataOperation operation = createOperation(EditUserContactDataOperation.class);
		operation.initialize(personData.getLogin());

		FormProcessor<ActionMessages, ?> processor = FormHelper.newInstance(new RequestValuesSource(currentRequest()), EditEmailForm.FORM);
		if (!processor.process())
		{
			saveErrors(request, processor.getErrors());
			return mapping.findForward(FORWARD_START);
		}
		Map<String, Object> result = processor.getResult();

		operation.setEmailAddress((String) result.get("email"));
		operation.setMailFormat((MailFormat) result.get("mailFormat"));
		operation.save();

		return mapping.findForward(FORWARD_START);
	}
}
