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
	
	//������ ������������ �������(�������� �� ��� ������ ���������)
	private final static List<String> ignoreActions =  new ArrayList<String>();

	/**
	 * � ���� ������� ��������� �������� ���������
	 */
	private final static Map<String, String> actionRequestEncodings = new HashMap<String, String>();

	private final static Map<String, String> pathRequestEncodings = new HashMap<String, String>();

	static 
	{
		ignoreActions.add("/private/payments/default-action.do");
		ignoreActions.add("/private/templates/default-action.do");
		ignoreActions.add("/images");

		// ����� ������� ��� �� ����� ��������� ������������ ������� ������� � �������� ��������,
		// ������� ���������� � ���� UTF-8 � ajax-��������
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
	  * �������� �� ��������� �����. �������� ������.
	  * ���� ���������� �� ������ �����:
	  * 1. ����������� ������ ��� ������� �������;
	  * 2. ����������� ������ ��� ������� �������, �� � ����� ���������� ������ �� ����� ������, ��� ��� ��������.
	  * ������ �� �������������� ������������ ����� url �������� �������������� ������� �� ���������� ��������
	  * � ����� ���.
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
		    /** ����� ���� �������� �����, ����� ��������� ������������ ����������� � ������ ���������
		     *  ����� ����� �������� no-store. ����� - must-revalidate. �����, ��� ��������� �������� ������
		     *  ��� ��������� ����� ���������� ����������
		     */
			response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		    response.addHeader("X-Frame-Options", "SAMEORIGIN");
			/*
			 * ������ �� ������ (�� ���������� �� �� ���� ������)
			 */
			isFirstPoint = (Boolean) request.getSession().getAttribute("firstPoint");
			if (isFirstPoint == null)
				isFirstPoint = true;
			//���� ��� ����� ������������ - �� ������ �� ����� ��������
			request.getSession().setAttribute("firstPoint", false);
			if (isFirstPoint)
				setOldUrl(request, url);

			chain.doFilter(request, response);

		    log.trace("END " + request.getMethod() + " Url: " + url);
	    }
        finally
        {
	        // ����������, ��� ����
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
			if(url.startsWith(action)) //����� � ���������� - ������ ������ �� ����(��������� ����������)
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
			// �������� ���������
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