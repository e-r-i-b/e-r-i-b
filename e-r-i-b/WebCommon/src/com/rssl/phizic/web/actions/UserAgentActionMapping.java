package com.rssl.phizic.web.actions;

import com.rssl.phizic.web.util.UserAgentUtil;
import org.apache.struts.action.ActionForward;

/**
 * @author Rydvanskiy
 * @ created 04.10.2010
 * @ $Author$
 * @ $Revision$
 */

public class UserAgentActionMapping extends org.apache.struts.action.ActionMapping
{
	private String JSP_EXT = "jsp";
	private String userAgent;

	public String getUserAgent()
	{
		return userAgent;
	}

	public void setUserAgent(String userAgent)
	{
		this.userAgent = userAgent;
	}

	/**
	 * Метод, возращающий постфикс в зависимости от User-Agent клиента
	 * @return String postfix
	 */
	public String getAgentPostfix()
	{
		return UserAgentUtil.findByName(userAgent).getPrefix();
	}

	/**
	 * Метод, модифицирующий  путь в зависимости от User-Agent`а
	 * @param path
	 * @return String модифицирванный путь
	 */
	public String getAgentRelativePath (String path)
	{
		if (path == null) return null;
		String[] result = path.split("\\.");
		if (result.length == 2 && JSP_EXT.equals(result[1]))
			return (result[0]+getAgentPostfix()+"."+JSP_EXT);
		return path;
	}

	public ActionForward findForward(String name)
	{
		return super.findForward(name + getAgentPostfix());
	}

	public String getForward()
	{
        return getAgentRelativePath (this.forward);
    }
}
