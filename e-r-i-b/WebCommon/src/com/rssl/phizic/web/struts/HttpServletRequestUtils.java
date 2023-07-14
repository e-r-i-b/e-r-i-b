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
 * Класс для обработки параметров реквеста
 */
public class HttpServletRequestUtils
{

	/**
	 * Получаем Map из реквеста с параметрами запроса.
	 * Обычный request.getParameterMap() не подходит в случае, если form.enctype="multipart/form-data"
	 * @param request - реквест
	 * @return Map с параметрами HTTP запроса
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
	 * @param request - реквест
	 * @return хендлер для обработки запроса от формы с form.enctype="multipart/form-data"
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
			throw new RuntimeException("Ошибка при получении хендлера для multipart/form-data запроса", e);
		}
		catch (IllegalAccessException e)
		{
			throw new RuntimeException("Ошибка при получении хендлера для multipart/form-data запроса", e);
		}
		catch (InstantiationException e)
		{
			throw new RuntimeException("Ошибка при получении хендлера для multipart/form-data запроса", e);
		}
	}

	/**
	 * @param obj - обект
	 * @return в случае null, пустая строка, иначе toString. Если передаем массив объектов, то toString для 1-го элемента массива
	 * реализовано специально для получения параметров реквеста, так как из реквест данные получаются в виде Map< String, String[] > 
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
	 * Добавляет параметры сортировки столбцов по умолчанию.
	 * @param request
	 * @param parameters параметры сортировки.
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
