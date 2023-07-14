package com.rssl.phizic.context;

import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.collections.MapUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.rssl.phizic.context.Constants.*;

/**
 * Контекст значений от rsa.js
 *
 * @author khudyakov
 * @ created 29.01.15
 * @ $Author$
 * @ $Revision$
 */
public class RSAContext
{
	private static RSAEntry entries = new RSAEntry();

	/**
	 * Инициализация контекста
	 * @param request запрос
	 */
	public static void initialize(HttpServletRequest request)
	{
		entries.initialize(request);
	}

	/**
	 * Инициализация контекста
	 * @param session сессия
	 */
	public static void initialize(HttpSession session)
	{
		entries.initialize(session);
	}

	/**
	 * Инициализация контекста
	 * @param map мап с данными
	 */
	public static void initialize(Map<String, String> map)
	{
		entries.initialize(map);
	}

	/**
	 * Дописать в контекст значения пришедшие в ответе от ФМ
	 * @param body данные
	 */
	public static void append(Document body)
	{
		Map<String, String> data = parse(body);
		if (MapUtils.isNotEmpty(data))
		{
			append(data);
		}
	}

	private static Map<String, String> parse(Document body)
	{
		try
		{
			if (body == null)
			{
				return null;
			}

			Element element = body.getDocumentElement();
			if (element == null)
			{
				return null;
			}

			Element deviceTokenFSOElement = XmlHelper.selectSingleNode(element, RSA_DATA_NAME + "/" + DEVICE_TOKEN_FSO_PARAMETER_NAME);
			String deviceTokenFSO = deviceTokenFSOElement == null ? null : StringHelper.getNullIfEmpty(deviceTokenFSOElement.getTextContent());

			Element deviceTokenCookieElement = XmlHelper.selectSingleNode(element, RSA_DATA_NAME + "/" + DEVICE_TOKEN_COOKIE_PARAMETER_NAME);
			String deviceTokenCookie = deviceTokenCookieElement == null ? null : StringHelper.getNullIfEmpty(deviceTokenCookieElement.getTextContent());

			if (StringHelper.isNotEmpty(deviceTokenFSO) || StringHelper.isNotEmpty(deviceTokenCookie))
			{
				Map<String, String> data = new HashMap<String, String>();
				data.put(Constants.SYSTEM_DEVICE_TOKEN_PREFIX_NAME + Constants.DEVICE_TOKEN_FSO_PARAMETER_NAME, deviceTokenFSO);
				data.put(Constants.SYSTEM_DEVICE_TOKEN_PREFIX_NAME + Constants.DEVICE_TOKEN_COOKIE_PARAMETER_NAME, deviceTokenCookie);
				return data;
			}
			return null;
		}
		catch (Exception e)
		{
			throw new RuntimeException("Ошибка при заполнении RSAContext", e);
		}
	}

	/**
	 * Дописать в контекст значения пришедшие в ответе от ФМ
	 * @param data данные
	 */
	public static void append(Map<String, String> data)
	{
		entries.append(data);
	}

	/**
	 * Чистка контекста
	 */
	public static void clear()
	{
		entries.clear();
	}

	/**
	 * @return значение поля domElements
	 */
	public static String getDomElements()
	{
		return entries.getValue().get(DOM_ELEMENTS_PARAMETER_NAME);
	}

	/**
	 * @return значение поля jsEvents
	 */
	public static String getJsEvents()
	{
		return entries.getValue().get(JS_EVENTS_PARAMETER_NAME);
	}

	/**
	 * @return значение поля devicePrint
	 */
	public static String getDevicePrint()
	{
		return entries.getValue().get(DEVICE_PRINT_PARAMETER_NAME);
	}

	/**
	 * @return значение поля deviceTokenCookie пришедшее от клиента
	 */
	public static String getDeviceTokenCookie()
	{
		return entries.getValue().get(DEVICE_TOKEN_COOKIE_PARAMETER_NAME);
	}

	/**
	 * @return значение поля deviceTokenCookie пришедшее от ФМ
	 */
	public static String getSystemDeviceTokenCookie()
	{
		return entries.getValue().get(SYSTEM_DEVICE_TOKEN_PREFIX_NAME + DEVICE_TOKEN_COOKIE_PARAMETER_NAME);
	}

	/**
	 * @return значение поля deviceTokenFSO пришедшее от клиента
	 */
	public static String getDeviceTokenFSO()
	{
		return entries.getValue().get(DEVICE_TOKEN_FSO_PARAMETER_NAME);
	}

	/**
	 * @return значение поля deviceTokenFSO пришедшее от ФМ
	 */
	public static String getSystemDeviceTokenFSO()
	{
		return entries.getValue().get(SYSTEM_DEVICE_TOKEN_PREFIX_NAME + DEVICE_TOKEN_FSO_PARAMETER_NAME);
	}

	/**
	 * @return значение поля mobileSdkData
	 */
	public static String getMobileSdkData()
	{
		return entries.getValue().get(MOBILE_SDK_DATA_PARAMETER_NAME);
	}
}
