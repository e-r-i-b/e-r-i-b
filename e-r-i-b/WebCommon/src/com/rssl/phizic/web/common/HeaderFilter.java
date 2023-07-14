package com.rssl.phizic.web.common;

import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Kidyaev
 * @ created 15.09.2005
 * @ $Author: khudyakov $
 * @ $Revision:8897 $
 */
public class HeaderFilter implements Filter
{
    private Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
    private String defaultRequestEncoding =null;
	
	//список игнорируемых экшенов(например те что делают редиректы)
	private final static List<String> ignoreActions =  new ArrayList<String>();

	/**
	 * У этих экшенов кодировка запросов особенная
	 */
	private final static Map<String, String> actionRequestEncodings = new HashMap<String, String>();

	private final static Map<String, String> pathRequestEncodings = new HashMap<String, String>();

	static 
	{
		ignoreActions.add("/private/payments/default-action.do");
		ignoreActions.add("/private/templates/default-action.do");
		ignoreActions.add("/images");

		// сфера сказала что не будет корректно обрабатывать русские символы в названии шаблонов,
		// которые посылаются в виде UTF-8 в ajax-запросах
		actionRequestEncodings.put("/async/payments/template.do", "Windows-1251");
		actionRequestEncodings.put("/private/async/userprofile/editIdentifier.do", "Windows-1251");
		actionRequestEncodings.put("/async/payments/quicklyCreateTemplate.do", "Windows-1251");
		actionRequestEncodings.put("/async/payments/quicklyCreateReminder.do", "Windows-1251");
		actionRequestEncodings.put("/payOrderPaymentLogin.do", "UTF-8");
		actionRequestEncodings.put("/async/confirm.do", "UTF-8");

		pathRequestEncodings.put("/private/async/", "UTF-8");
		pathRequestEncodings.put("/private/widget/", "UTF-8");
	}

	/*
	  * Работает по следующей схеме. Получаем запрос.
	  * Если редиректим на другой адрес:
	  * 1. Срабатывает фильтр для первого запроса;
	  * 2. Срабатывает фильтр для второго запроса, но в через переменную сессии мы можем понять, что это редирект.
	  * Исходя из вышесказанного определяется какой url является действительной ссылкой на предыдущую страницу
	  * а какой нет.
	*/
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException
    {
	    HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest  request  = (HttpServletRequest)  req;

	    String queryString = request.getQueryString();
	    String qs = (queryString != null) ? "?" + queryString : "";
	    String url = request.getRequestURI() + qs;
	    Boolean isFirstPoint = false;
	    try
	    {
		    log.trace("START " + request.getMethod() + " Url: " + url );

		    String requestEncoding = getActionRequestEncoding(request, url);
			if ( requestEncoding != null )
			{
				request.setCharacterEncoding(requestEncoding);
			}

			response.setContentType("text/html;charset=windows-1251");
			response.addHeader("Expires", "-1");
			response.addHeader("Pragma", "no-cache");
		    /** такая куча директив нужна, чтобы корректно отрабатывало кэширование в разных браузерах
		     *  Хрому нужна директва no-store. Опере - must-revalidate. Также, эта директива работает только
		     *  при просмотре через защищенное соединение
		     */
			response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		    response.addHeader("X-Frame-Options", "SAMEORIGIN");
			/*
			 * Первый ли запрос (не редиректом ли мы сюда пришли)
			 */
			isFirstPoint = (Boolean) request.getSession().getAttribute("firstPoint");
			if (isFirstPoint == null)
				isFirstPoint = true;
			//если нас потом перенаправят - мы поймем по этому атрибуту
			request.getSession().setAttribute("firstPoint", false);
			if (isFirstPoint)
				setOldUrl(request, url);

			chain.doFilter(request, response);

		    log.trace("END " + request.getMethod() + " Url: " + url);
	    }
        finally
        {
	        // возвращаем, как было
	        if (isFirstPoint)
                request.getSession().setAttribute("firstPoint", true);
        }
    }

    public void init(FilterConfig filterConfig)
    {
        defaultRequestEncoding = filterConfig.getInitParameter("requestEncoding");
    }

    public void destroy()
    {
    }

	private void setOldUrl(HttpServletRequest request, String url)
	{
		HttpSession session = request.getSession();
		String contextPath = request.getContextPath();

		if (url.startsWith(contextPath))
			url = url.substring(contextPath.length());
		
		for(String action: ignoreActions)
			if(url.startsWith(action)) //экшен в игнорлисте - делать ничего не надо(оставляем предыдущий)
				return;

		String currentUrl = (String)session.getAttribute("curUrl");
		String previosUrl = (String)session.getAttribute("oldUrl");

		if(!url.equals(currentUrl))
		{
			previosUrl = currentUrl;
			currentUrl = url;
			session.setAttribute("oldUrl", previosUrl);
			session.setAttribute("curUrl", currentUrl);
		}
	}

	protected String getActionRequestEncoding(HttpServletRequest request, String url)
	{
		for (Map.Entry<String, String> entry: actionRequestEncodings.entrySet())
		{
			// обрезаем параметры
			String baseUrl = url.indexOf('?') != -1 ? url.substring(0, url.indexOf('?')): url;
			if(baseUrl.endsWith(entry.getKey()))
				return entry.getValue();
		}

		String contextPath = request.getContextPath();
		String path = null;
		if (url.startsWith(contextPath))
			path = url.substring(contextPath.length());
		if (path != null)
		{
			for (Map.Entry<String, String> entry: pathRequestEncodings.entrySet()) {
				if (path.startsWith(entry.getKey()))
					return entry.getValue();
			}
		}

		return defaultRequestEncoding;
	}
}