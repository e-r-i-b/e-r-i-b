package com.rssl.phizic.web.persons.list;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.operations.person.list.ChooseClientOperation;
import org.apache.struts.action.*;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Ёкшен выбора клиента дл€ редактировани€ (редирект на мобильный банк вместо анкеты клиента)
 * @author Puzikov
 * @ created 11.09.14
 * @ $Author$
 * @ $Revision$
 */

public class ChooseClientErmbAction extends ChooseClientAction
{
	private static final String FORWARD_SHOW_ERMB_FORM = "ShowForm";

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ChooseClientForm frm = (ChooseClientForm) form;
		ChooseClientOperation operation = createOperation(ChooseClientOperation.class);
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new MapValuesSource(frm.getFields()), ChooseClientForm.SEARCH_FORM_BY_PHONE);
		if (processor.process())
		{
			String phone = (String) processor.getResult().get("ermbActivePhone");
			operation.initialize(phone);
			List<ActivePerson> clients = operation.getClients();
			frm.setData(clients);
		}
		else
		{
			saveErrors(currentRequest(), processor.getErrors());
		}
		return createActionForward(frm);
	}

	protected ActionForward createActionForward(ChooseClientForm form)
	{
		List<ActivePerson> clients = form.getData();
		if (clients.size() != 1)
			return getCurrentMapping().findForward(FORWARD_START);

		ActivePerson onlyPerson = clients.get(0);
		ActionForward editForward = getCurrentMapping().findForward(FORWARD_SHOW_ERMB_FORM);
		ActionRedirect redirect = new ActionRedirect(editForward.getPath());
		redirect.addParameter(PERSON_ID_PARAMETER, onlyPerson.getId());
		return redirect;
	}
}
