package com.rssl.phizic.web.atm;

import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.servlet.HttpServletEditableRequest;
import org.apache.struts.taglib.html.Constants;

import java.io.IOException;
import java.util.Collections;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Erkin
 * @ created 08.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class TransactionTokenFilter implements Filter
{
	private static final String TRANSACTION_TOKEN_PARAM_NAME = "transactionToken";

	public void init(FilterConfig filterConfig) {}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
	{
		HttpServletEditableRequest req = obtainEditableRequest((HttpServletRequest) servletRequest);
		String transactionToken = req.getParameter(TRANSACTION_TOKEN_PARAM_NAME);
		if (!StringHelper.isEmpty(transactionToken)) {
			req.putParameter(Constants.TOKEN_KEY, transactionToken);
			req.removeParameter(TRANSACTION_TOKEN_PARAM_NAME);
		}

		filterChain.doFilter(req, servletResponse);
	}

	private HttpServletEditableRequest obtainEditableRequest(HttpServletRequest servletRequest)
	{
		if (servletRequest instanceof HttpServletEditableRequest)
			return (HttpServletEditableRequest) servletRequest;
		
		return new HttpServletEditableRequest(servletRequest, Collections.<String, String[]>emptyMap());
	}

	public void destroy() {}
}
