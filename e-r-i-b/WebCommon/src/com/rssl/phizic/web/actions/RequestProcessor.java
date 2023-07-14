package com.rssl.phizic.web.actions;

import com.rssl.phizic.web.modulus.WebAppRequestProcessor;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Rydvanskiy
 * @ created 02.10.2010
 * @ $Author$
 * @ $Revision$
 */

@SuppressWarnings({"ClassNameSameAsAncestorName"})
public class RequestProcessor extends WebAppRequestProcessor
{
	protected org.apache.struts.action.ActionMapping processMapping(HttpServletRequest request,
	                                                                HttpServletResponse response,
	                                                                String path)
			throws IOException
	{

		UserAgentActionMapping mapping = (UserAgentActionMapping) super.processMapping(request, response, path);
		mapping.setUserAgent(request.getHeader("User-Agent"));
		return mapping;
	}
}
