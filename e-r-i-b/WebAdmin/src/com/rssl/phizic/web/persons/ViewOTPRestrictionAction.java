package com.rssl.phizic.web.persons;

import com.rssl.phizic.operations.person.ViewOTPRestrictionOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lepihina
 * @ created 16.12.2011
 * @ $Author$
 * @ $Revision$
 */
public class ViewOTPRestrictionAction extends OperationalActionBase
{
	protected static final String FORWARD_START = "Start";

	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ViewOTPRestrictionForm frm = (ViewOTPRestrictionForm)form;

		ViewOTPRestrictionOperation operation = createOperation(ViewOTPRestrictionOperation.class);
		Long personId = PersonUtils.getPersonId(currentRequest());
		operation.setPersonId(personId);

		if (operation.getPerson() == null)
		{
			ActionMessage message = new ActionMessage("Не найден клиент с id =" + personId, false);
		    ActionMessages msgs = new ActionMessages();
			msgs.add(ActionMessages.GLOBAL_MESSAGE, message);
			saveErrors(request, msgs);
			return mapping.findForward(FORWARD_START);
		}

		frm.setActivePerson(operation.getPerson());
		frm.setData(operation.getMainCards());

		return mapping.findForward(FORWARD_START); 
	}
}
