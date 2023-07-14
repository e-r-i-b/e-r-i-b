package com.rssl.phizic.web.security;

import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.operations.LogWriter;
import com.rssl.phizic.logging.operations.OperationLogFactory;
import com.rssl.phizic.logging.operations.DefaultLogDataReader;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.person.ClientLogoffOperation;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.Action;

import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 @author: EgorovaA
 @ created: 26.02.2013
 @ $Author$
 @ $Revision$
 */
public class LogoffActionBase extends Action
{
	private static final String FORWARD_LOGOFF = "GotoUnauthorizedIndex";
	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Calendar start = GregorianCalendar.getInstance();
		Calendar end;
		try
		{
			if(PersonContext.isAvailable() && !PersonContext.getPersonDataProvider().getPersonData().isGuest())
			{
				preClearCache();
				ClientLogoffOperation operation = new ClientLogoffOperation();
				operation.clearCache();
			}
		}
		catch (Exception e)
		{
			log.error("Ошибка при выходе клиента", e);
		}
		finally
		{
			HttpSession session = request.getSession(false);
            if (session != null)
				session.setAttribute(SessionIdFilter.INVALIDATE_SESSION_KEY, true);

			end = GregorianCalendar.getInstance();
			LogWriter logWriter = OperationLogFactory.getLogWriter();
			DefaultLogDataReader reader = new DefaultLogDataReader(LogWriter.LOGOFF);
			reader.setKey(com.rssl.phizic.logging.Constants.LOGOFF_KEY);
			reader.setOperationKey(com.rssl.phizic.security.config.Constants.LOGOFF_DEFAULT_OPERATION_KEY);

			logWriter.writeSecurityOperation(reader, start, end);
		}
		return findForward(mapping, request);
	}

	protected ActionForward findForward(ActionMapping mapping, HttpServletRequest request)
	{
		return mapping.findForward(FORWARD_LOGOFF);
	}

	protected void preClearCache() throws Exception {}
}
