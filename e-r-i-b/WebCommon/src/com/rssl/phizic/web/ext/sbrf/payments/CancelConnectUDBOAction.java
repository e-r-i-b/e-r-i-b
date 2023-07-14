package com.rssl.phizic.web.ext.sbrf.payments;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.remoteConnectionUDBO.RemoteConnectionUDBOConfig;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * экшен с сообщением о прекращении удаленного подключения УДБО
 * @author basharin
 * @ created 24.12.14
 * @ $Author$
 * @ $Revision$
 */

public class CancelConnectUDBOAction extends OperationalActionBase
{
	@Override public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CancelConnectUDBOForm frm = (CancelConnectUDBOForm) form;
		RemoteConnectionUDBOConfig config = ConfigFactory.getConfig(RemoteConnectionUDBOConfig.class);
		frm.setMessageTitle(config.getCancelConnectUDBOMessageTitle());
		frm.setMessageText(config.getCancelConnectUDBOMessageText());

		return mapping.findForward(FORWARD_START);
	}
}
