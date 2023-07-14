package com.rssl.phizic.web.security;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.common.types.client.CSAType;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

import static com.rssl.phizic.common.types.security.Constants.AUTHENTICATION_CONTEXT_KEY;

/**
 * @author koptyaev
 * @ created 24.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class CloseCSASessionEvent extends BaseCloseSessionListener
{
	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE);

	public void sessionCreated(HttpSessionEvent httpSessionEvent)
	{
	}

	@Override protected void doSessionDestroyed(HttpSessionEvent httpSessionEvent)
	{
		HttpSession session = httpSessionEvent.getSession();
		try
		{
			if( session!=null )
			{
				// Используем session (вместо AuthenticationManager.getContext),
				// т.к. store может быть не проинициализирован (sessionDestroyed может выполняться не в рамках запроса request/response)
				AuthenticationContext context = (AuthenticationContext) session.getAttribute(AUTHENTICATION_CONTEXT_KEY);
				if (context != null)
				{
					String sid = context.getCSA_SID();
					if (StringHelper.isNotEmpty(sid) && CSAType.CBOL_CA != context.getCsaType())
					{
						String ip = (String)session.getAttribute(SessionIdFilter.IP_ADDRES_KEY);
						LogThreadContext.setIPAddress(ip);
						CSABackRequestHelper.sendCloseSessionRq(sid);
					}
				}
			}
		}
		catch (Throwable e)
		{
			//ошибку бросать не будем, т.к. это по сути дебажная информация
			log.debug("Ошибка при попытке завершения активной сессии", e);
			
		}
	}
}
