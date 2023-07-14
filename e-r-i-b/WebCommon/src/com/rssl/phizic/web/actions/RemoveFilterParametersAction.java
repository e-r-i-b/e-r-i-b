package com.rssl.phizic.web.actions;

import com.rssl.phizic.business.filters.FilterParametersService;
import com.rssl.phizic.business.BusinessException;

import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

/**
 * Created by IntelliJ IDEA.
 * @author mihaylov
 * created 17.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class RemoveFilterParametersAction implements HttpSessionListener
{
	private static FilterParametersService filterParametersService = new FilterParametersService();

	static void destroySession(HttpSession session)
    {
	    try{
			filterParametersService.removeAllForSession(session.getId());
	    }
		catch(BusinessException e)
		{
		}
    }

	/** No-op for now */
    public void sessionCreated(HttpSessionEvent event) {
    }

	public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        destroySession(session);
    }
}
