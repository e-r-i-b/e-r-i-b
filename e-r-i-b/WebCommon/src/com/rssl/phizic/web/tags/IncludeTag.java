package com.rssl.phizic.web.tags;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import org.apache.struts.taglib.TagUtils;
import org.apache.struts.util.RequestUtils;

import java.io.BufferedInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

/**
 * @author Rydvanskiy
 * @ created 29.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class IncludeTag extends org.apache.struts.taglib.bean.IncludeTag
{
	private static final int port = getPort();
	private Map<String, String> params = new HashMap();
	private static final String JSESSIONID = "JSESSIONID=";
	private static final String COOKIE = "Cookie";

	private static int getPort()
	{
		return ApplicationConfig.getIt().getApplicationPort();
	}

	public void setParams(Map<String, String> params)
	{
		this.params = params;
	}

	public Map getParams()
	{
		return this.params;
	}

	public int doStartTag() throws JspException
	{
		String page = super.getPage();
		if (page == null)
			return super.doStartTag();

		boolean flag = (page.indexOf("?") > 0) ? true : false;
		for (Map.Entry<String, String> param : params.entrySet())
		{

			if (!flag)
			{
				page += "?";
				flag = true;
			}
			else
				page += "&";
			try
			{
				page += param.getKey() + "=" + URLEncoder.encode(param.getValue(), "CP1251");
			}
			catch (UnsupportedEncodingException e)
			{
				throw new JspException(e);
			}
		}

		super.setPage(page);
		// Set up a URLConnection to read the requested resource
		Map params = TagUtils.getInstance().computeParameters(pageContext, null, null, null, null, null, null, null, transaction);
		// FIXME - <html:link> attributes
		String urlString = null;
		URL url = null;
		try {
		    urlString = TagUtils.getInstance().computeURLWithCharEncoding(pageContext, forward, href, page, null,null, params, anchor, false, useLocalEncoding);
		    if (urlString.indexOf(':') < 0) {
		        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			    //копипаст из-за иного способа получения хоста и порта 
			    url = new URL(new URL(RequestUtils.createServerUriStringBuffer(request.getScheme(), "localhost", port, request.getRequestURI()).toString()), urlString);
		    }
		    else
		    {
			    url = new URL(urlString);
			}
		}
		catch (MalformedURLException e) {
			TagUtils.getInstance().saveException(pageContext, e);
		    throw new JspException(messages.getMessage("include.url", e.toString()));
		}

		URLConnection conn = null;
		try {
		    // Set up the basic connection
		    conn = url.openConnection();
		    conn.setAllowUserInteraction(false);
		    conn.setDoInput(true);
		    conn.setDoOutput(false);
		    // Add a session id cookie if appropriate
		    HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		    addCookie(conn, urlString, request);
		    // Connect to the requested resource
		    conn.connect();
		} catch (Exception e) {
		    TagUtils.getInstance().saveException(pageContext, e);
		    throw new JspException(messages.getMessage("include.open", url.toString(), e.toString()));
		}

		// Copy the contents of this URL
		StringBuffer sb = new StringBuffer();
		try {
		    BufferedInputStream is = new BufferedInputStream(conn.getInputStream());
		    InputStreamReader in = new InputStreamReader(is); // FIXME - encoding
		    char buffer[] = new char[BUFFER_SIZE];
		    int n = 0;
		    while (true) {
		        n = in.read(buffer);
		        if (n < 1)
		            break;
		        sb.append(buffer, 0, n);
		    }
		    in.close();
		} catch (Exception e) {
		    TagUtils.getInstance().saveException(pageContext, e);
		    throw new JspException(messages.getMessage("include.read", url.toString(), e.toString()));
		}

		// Define the retrieved content as a page scope attribute
		pageContext.setAttribute(id, sb.toString());

		// Skip any body of this tag
		return (SKIP_BODY);
	}

	//для вебсферы, т.к. она возвращает не то значение в  getRequestedSessionId.
	//берем то, что уже лежит в JSESSIONID
	//см. http://www-01.ibm.com/support/docview.wss?uid=swg21254066
    protected void addCookie(URLConnection conn, String urlString, HttpServletRequest request) {
        if ( (conn instanceof HttpURLConnection) && urlString.startsWith(request.getContextPath())
              && (request.getRequestedSessionId() != null) && request.isRequestedSessionIdFromCookie())
        {
	        List<String> jsessionIds = getjSession(request);
	        if(jsessionIds.size()==0)
	        {
		        addJSessionCookie(conn,request.getRequestedSessionId());
		        return;
	        }

	        for (String jsessionId : jsessionIds)
	        {
		        addJSessionCookie(conn, jsessionId);
	        }

        }
    }

	private void addJSessionCookie(URLConnection conn, String value)
	{
		String old = conn.getRequestProperty(COOKIE);
		StringBuffer buffer = new StringBuffer(old==null?"":old);
		buffer.append(JSESSIONID);
		buffer.append(value);
		buffer.append(";");
		conn.setRequestProperty(COOKIE, buffer.toString());
	}

	private List<String> getjSession(HttpServletRequest request)
	{
		List<String> result = new ArrayList<String>(0);
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies)
		{
			if("JSESSIONID".equals(cookie.getName()))
				result.add(cookie.getValue());
		}
		return result;
	}

	public void release()
	{
		super.release();
		params = null;
	}
}
