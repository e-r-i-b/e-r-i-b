package com.rssl.phizic.web.promoter;

import com.rssl.phizic.business.promoters.PromoterSessionHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.promoters.PromoterContext;

import java.io.IOException;
import java.util.Map;
import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @ author: Gololobov
 * @ created: 05.12.2012
 * @ $Author$
 * @ $Revision$
 */
public class PromoterContextFilter implements Filter
{
	public void init(FilterConfig filterConfig) throws ServletException	{}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
	{
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		try
		{
			//Ищем в cookie данные по смене промоутера и добавляем в контекст промоутера ID смены
			Cookie[] cookies = request.getCookies();
			Map<String, Object> promoterSessionInfoMap = PromoterSessionHelper.getPromoterDataFromCookie(cookies);
			StringBuilder promoter = new StringBuilder();

			String promoterIdStart = (String) promoterSessionInfoMap.get(PromoterSessionHelper.PROMOTERID_COOKIE_NAME);
			if (StringHelper.isNotEmpty(promoterIdStart))
				promoter.append(promoterIdStart);

			String promoterId = (String) promoterSessionInfoMap.get(PromoterSessionHelper.PROMOID_FIELD_NAME);
			String promoterSessionId = (String) promoterSessionInfoMap.get(PromoterSessionHelper.PROMOSESSIONID_FIELD_NAME);
			if (StringHelper.isNotEmpty(promoterId))
				promoter.append(promoterId);

			///Добавление ИД открытой смены промоутера в контекст промоутера
			PromoterContext.setShift(promoterSessionId);
			//Добавление идентификатора промоутера текущей смены в контекст.
			PromoterContext.setPromoterID(promoter.toString());

			filterChain.doFilter(servletRequest, servletResponse);
		}
		finally
		{
			PromoterContext.clear();
		}
	}

	public void destroy(){}
}
