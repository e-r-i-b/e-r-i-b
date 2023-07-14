package com.rssl.phizic.web.client.accounts;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 04.08.2006
 * @ $Author$
 * @ $Revision$
 */

public class ShowLongOfferAction  extends OperationalActionBase
{
	public static final String SHOW_FILTER_COMMAND = "filter";

	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		//по умолчанию приходим сюда, по параматрам запроса определяем печать или фильтр
        ShowLongOfferForm frm = (ShowLongOfferForm) form;

		ActivePerson user = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		frm.setUser(user);
		List<AccountLink> res = new ExternalResourceService().getLinks(user.getLogin(), AccountLink.class);
		frm.setResources( res );

		if( frm.getSelectedResources().length == 0 && !frm.getResourceLinks().isEmpty())
		{
			String[] selected = new String[1];
			selected[0] = frm.getResourceLinks().get(0);
			frm.setSelectedResources( selected );
		}

		return mapping.findForward(SHOW_FILTER_COMMAND);
	}
}
