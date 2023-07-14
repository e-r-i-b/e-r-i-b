package com.rssl.phizic.web.ext.sbrf.payments;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.remoteConnectionUDBO.RemoteConnectionUDBOConfig;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.security.SessionIdFilter;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * экшен с сообщением о прекращении удаленного подключения УДБО
 * @author basharin
 * @ created 18.03.15
 * @ $Author$
 * @ $Revision$
 */

public class CancelAcceptInfoUDBOAction extends OperationalActionBase
{
	@Override public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CancelConnectUDBOForm frm = (CancelConnectUDBOForm) form;
		RemoteConnectionUDBOConfig config = ConfigFactory.getConfig(RemoteConnectionUDBOConfig.class);
		frm.setMessageTitle(config.getCancelAcceptInfoUDBOMessageTitle());
		frm.setMessageText(config.getCancelAcceptInfoUDBOMessageText());

		if(!config.isWorkWithoutUDBO())
		{
			HttpSession session = request.getSession(false);
			if (session != null)
				session.setAttribute(SessionIdFilter.INVALIDATE_SESSION_KEY, true);
		}

		return mapping.findForward(FORWARD_START);
	}
}
