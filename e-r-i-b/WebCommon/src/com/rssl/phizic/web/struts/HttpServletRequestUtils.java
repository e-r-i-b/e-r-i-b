package com.rssl.phizic.web.struts;

import com.rssl.phizic.dataaccess.query.OrderParameter;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.struts.layout.TemplatedCollectionTag;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.upload.MultipartRequestHandler;
import org.apache.struts.util.ModuleUtils;
import org.apache.struts.util.RequestUtils;
import org.apache.commons.lang.ArrayUtils;

import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author mihaylov
 * @ created 18.07.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * ����� ��� ��������� ���������� ��������
 */
public class HttpServletRequestUtils
{

	/**
	 * �������� Map �� �������� � ����������� �������.
	 * ������� request.getParameterMap() �� �������� � ������, ���� form.enctype="multipart/form-data"
	 * @param request - �������
	 * @return Map � ����������� HTTP �������
	 */
	public static Map getParameterMap(HttpServletRequest request)
	{
		String contentType = request.getContentType();
		String method = request.getMethod();

		if ((contentType != null) && (contentType.startsWith("multipart/form-data")) && (method.equalsIgnoreCase("POST")))
		{
			MultipartRequestHandler multipartHandler = getRequestHandler(request);
			try
			{
				multipartHandler.handleRequest(request);
				return multipartHandler.getTextElements();
			}
			catch (ServletException e)
			{
				throw new RuntimeException(e);
			}			
		}
		else
			return request.getParameterMap();
	}
	
	/**
	 * @param request - �������
	 * @return ������� ��� ��������� ������� �� ����� � form.enctype="multipart/form-data"
	 */
	private static MultipartRequestHandler getRequestHandler(HttpServletRequest request)
	{
		ModuleUtils.getInstance().selectModule(request, request.getSession().getServletContext());
		ModuleConfig moduleConfig = ModuleUtils.getInstance().getModuleConfig(request);
		String multipartClass = moduleConfig.getControllerConfig().getMultipartClass();
		try
		{
			return (MultipartRequestHandler) RequestUtils.applicationInstance(multipartClass);
		}
		catch (ClassNotFoundException e)
		{
			throw new RuntimeException("������ ��� ��������� �������� ��� multipart/form-data �������", e);
		}
		catch (IllegalAccessException e)
		{
			throw new RuntimeException("������ ��� ��������� �������� ��� multipart/form-data �������", e);
		}
		catch (InstantiationException e)
		{
			throw new RuntimeException("������ ��� ��������� �������� ��� multipart/form-data �������", e);
		}
	}

	/**
	 * @param obj - �����
	 * @return � ������ null, ������ ������, ����� toString. ���� �������� ������ ��������, �� toString ��� 1-�� �������� �������
	 * ����������� ���������� ��� ��������� ���������� ��������, ��� ��� �� ������� ������ ���������� � ���� Map< String, String[] > 
	 */
	public static String getEmptyIfNull(Object obj)
	{
		if(obj == null)
			return "";
		else if(obj instanceof Object[])
		{
			Object[] objects = (Object[]) obj;
			return getEmptyIfNull(objects[0]);
		}
		else
			return StringHelper.getEmptyIfNull(obj.toString());
	}

	/**
	 * ��������� ��������� ���������� �������� �� ���������.
	 * @param request
	 * @param parameters ��������� ����������.
	 */
	public static void addSortParameters(HttpServletRequest request, OrderParameter... parameters)
	{
		if (ArrayUtils.isEmpty(parameters))
			return;
		
		HttpSession session = request.getSession();

		for(int i = 0; i < parameters.length; i++)
		{
			session.setAttribute(TemplatedCollectionTag.ORDER_PARAMETER_NAME + i, parameters[i]);						
		}
	}
}
