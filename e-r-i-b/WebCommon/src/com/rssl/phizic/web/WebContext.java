package com.rssl.phizic.web;

import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.web.util.HttpSessionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 17.10.2005 Time: 12:51:27 */
public class WebContext
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
    private static ThreadLocal<HttpServletRequest>  currentRequest  = new ThreadLocal<HttpServletRequest>();
    private static ThreadLocal<HttpServletResponse> currentResponce = new ThreadLocal<HttpServletResponse>();

    public static HttpServletRequest getCurrentRequest()
    {
        return currentRequest.get();
    }

    public static void setCurrentRequest(HttpServletRequest request)
    {
        currentRequest.set(request);
    }

    public static HttpServletResponse getCurrentResponce()
    {
        return currentResponce.get();
    }

    public static void setCurrentResponce(HttpServletResponse response)
    {
        currentResponce.set(response);
    }

	public static String getOldUrl()
	{
		try
		{
			HttpServletRequest request = currentRequest.get();
			Object oldUrl = HttpSessionUtils.getSessionAttribute(request, "oldUrl");
			return (oldUrl != null) ? oldUrl.toString() : null;
		}
		catch (Exception e)
		{
			log.error("Ошибка определения атрибута oldUrl", e);
			return "";
		}
	}
}
