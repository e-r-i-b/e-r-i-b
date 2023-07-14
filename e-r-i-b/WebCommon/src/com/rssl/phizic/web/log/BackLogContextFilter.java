package com.rssl.phizic.web.log;

import com.rssl.phizic.config.locale.MultiLocaleContext;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.promoters.PromoterContext;
import org.apache.commons.codec.binary.Base64;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

/**
 * @ author: Vagin
 * @ created: 25.01.2013
 * @ $Author
 * @ $Revision
 * Фильтр инициализирующий параметры логирования для запросов в CSABack.
 */
public class BackLogContextFilter  implements Filter
{
	private static final String promoIdKey = PromoterContext.class.getName().toUpperCase() + ".PROMOTER_ID";

	public void init(FilterConfig filterConfig) throws ServletException {}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
	{
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		if(StringHelper.isNotEmpty(request.getHeader(promoIdKey)))
			LogThreadContext.setPromoterID(new String(Base64.decodeBase64(request.getHeader(promoIdKey).getBytes())));   //устанавливаем идентификатор промоутера
		if(StringHelper.isNotEmpty(request.getHeader(MultiLocaleContext.LOCALE_KEY)))
			MultiLocaleContext.setLocaleId(new String(Base64.decodeBase64(request.getHeader(MultiLocaleContext.LOCALE_KEY).getBytes())));
		filterChain.doFilter(servletRequest, servletResponse);
	}

	public void destroy(){}
}
