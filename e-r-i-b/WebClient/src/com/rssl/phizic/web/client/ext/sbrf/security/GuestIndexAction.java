package com.rssl.phizic.web.client.ext.sbrf.security;

import com.rssl.phizic.auth.GuestLoginImpl;
import com.rssl.phizic.business.sbnkd.SberbankForEveryDayHelper;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author niculichev
 * @ created 10.12.14
 * @ $Author$
 * @ $Revision$
 *
 */
public class GuestIndexAction extends OperationalActionBase
{
	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		updateForm((GuestIndexForm)form);
		return mapping.findForward(FORWARD_SHOW);
	}

	private void updateForm(GuestIndexForm form)
	{
		if (PersonContext.isAvailable())
		{
			form.setClaimsList(SberbankForEveryDayHelper.findClaimInfoByGuestLogin(((GuestLoginImpl)PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin())));
		}
	}
}
