package com.rssl.phizic.web.security;

import com.rssl.phizic.einvoicing.EInvoicingConstants;
import com.rssl.phizic.utils.StringHelper;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author vagin
 * @ created 23.09.13
 * @ $Author$
 * @ $Revision$
 * Филтр отключающий проверку XSS фильтром для оплаты с сайта ФНС.
 */
public class PayOrderParametersFilter implements Filter
{
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException
	{
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request  = (HttpServletRequest)  req;
		//для оплаты с сайта ФНС и UEC игнорируем проверку XSS фильтра.
		if(StringHelper.isNotEmpty(request.getParameter(EInvoicingConstants.FNS_PAY_INFO)) || StringHelper.isNotEmpty(request.getParameter(EInvoicingConstants.UEC_PAY_INFO)))
			response.addHeader("X-XSS-Protection","0");
		filterChain.doFilter(req, res);
	}

	public void init(FilterConfig filterConfig) throws ServletException
	{
	}

	public void destroy()
	{
	}
}
