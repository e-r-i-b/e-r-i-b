package com.rssl.phizic.web.security;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.config.csaadmin.CSAAdminGateConfig;
import com.rssl.phizic.logging.operations.DefaultLogDataReader;
import com.rssl.phizic.logging.operations.LogWriter;
import com.rssl.phizic.logging.operations.OperationLogFactory;
import org.apache.struts.action.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author mihaylov
 * @ created 23.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Ёкшен выхода сотрудника из системы.
 */
public class AdminLogoffAction extends Action
{
	private static final String FORWARD_LOGOFF = "GotoUnauthorizedIndex";

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CSAAdminGateConfig csaAdminConfig = ConfigFactory.getConfig(CSAAdminGateConfig.class);

		Calendar start = GregorianCalendar.getInstance();
		HttpSession session = request.getSession(false);
        if (session != null)
			session.setAttribute(SessionIdFilter.INVALIDATE_SESSION_KEY, true);
		Calendar end = GregorianCalendar.getInstance();

		LogWriter logWriter = OperationLogFactory.getLogWriter();
		DefaultLogDataReader reader = new DefaultLogDataReader(LogWriter.LOGOFF);
		reader.setKey(com.rssl.phizic.logging.Constants.LOGOFF_KEY);
		reader.setOperationKey(com.rssl.phizic.security.config.Constants.LOGOFF_DEFAULT_OPERATION_KEY);
		logWriter.writeSecurityOperation(reader, start, end);

		if(csaAdminConfig.isMultiBlockMode())
			return new ActionRedirect(csaAdminConfig.getLoginUrl());
		else
			return mapping.findForward(FORWARD_LOGOFF);
	}

}
