package com.rssl.phizic.web.client.ibk;

import com.rssl.phizic.authgate.AuthConfig;
import com.rssl.phizic.authgate.AuthGateSingleton;
import com.rssl.phizic.authgate.AuthParamsContainer;
import com.rssl.phizic.authgate.authorization.AuthGateService;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.logging.operations.DefaultLogDataReader;
import com.rssl.phizic.logging.operations.LogWriter;
import com.rssl.phizic.operations.person.ClientLogoffOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.security.SessionIdFilter;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Gainanov
 * @ created 14.08.2009
 * @ $Author$
 * @ $Revision$
 */
public abstract class RedirectOthersSystemAction extends OperationalActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	protected abstract String getSystemPage();
	
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AuthConfig config = AuthGateSingleton.getAuthService().getConfig();
		ActionForward forward = new ActionForward(config.getProperty("csa.ibk.url"),true);
		AuthGateService service = AuthGateSingleton.getAuthService();
		AuthParamsContainer container = new AuthParamsContainer();
		container.addParameter("SID", AuthenticationContext.getContext().getCSA_SID());
		container.addParameter("AuthTokenRq","4");
		container.addParameter("NextService", "WB");
		container.addParameter("ASPKey", AuthenticationContext.getContext().getRandomString());
		container.addParameter("BackRef", config.getProperty("ikfl.login.back.url"));
		container.addParameter("Page", getSystemPage());
		container.addParameter("ISEDBO", "0");
		AuthParamsContainer result = service.moveSession(container);
		forward.setPath(forward.getPath() + "?AuthToken=" + result.getParameter("AuthToken"));

		Calendar start = GregorianCalendar.getInstance();
		try
		{
			if(PersonContext.isAvailable())
			{
				ClientLogoffOperation operation = new ClientLogoffOperation();
				operation.clearCache();
			}
		}
		finally
		{
			HttpSession session = request.getSession(false);
            if (session != null)
               session.setAttribute(SessionIdFilter.INVALIDATE_SESSION_KEY, true);

			Calendar end = GregorianCalendar.getInstance();
			DefaultLogDataReader reader = new DefaultLogDataReader(LogWriter.LOGOFF);
			reader.setOperationKey(com.rssl.phizic.security.config.Constants.LOGOFF_DEFAULT_OPERATION_KEY);

			logWriter.writeSecurityOperation(reader, start, end);
		}

		return forward;
	}
}
