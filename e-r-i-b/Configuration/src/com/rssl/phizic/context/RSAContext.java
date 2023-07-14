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
 * �������� �������� �� rsa.js
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
	 * ������������� ���������
	 * @param request ������
	 */
	public static void initialize(HttpServletRequest request)
	{
		entries.initialize(request);
	}

	/**
	 * ������������� ���������
	 * @param session ������
	 */
	public static void initialize(HttpSession session)
	{
		entries.initialize(session);
	}

	/**
	 * ������������� ���������
	 * @param map ��� � �������
	 */
	public static void initialize(Map<String, String> map)
	{
		entries.initialize(map);
	}

	/**
	 * �������� � �������� �������� ��������� � ������ �� ��
	 * @param body ������
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
			throw new RuntimeException("������ ��� ���������� RSAContext", e);
		}
	}

	/**
	 * �������� � �������� �������� ��������� � ������ �� ��
	 * @param data ������
	 */
	public static void append(Map<String, String> data)
	{
		entries.append(data);
	}

	/**
	 * ������ ���������
	 */
	public static void clear()
	{
		entries.clear();
	}

	/**
	 * @return �������� ���� domElements
	 */
	public static String getDomElements()
	{
		return entries.getValue().get(DOM_ELEMENTS_PARAMETER_NAME);
	}

	/**
	 * @return �������� ���� jsEvents
	 */
	public static String getJsEvents()
	{
		return entries.getValue().get(JS_EVENTS_PARAMETER_NAME);
	}

	/**
	 * @return �������� ���� devicePrint
	 */
	public static String getDevicePrint()
	{
		return entries.getValue().get(DEVICE_PRINT_PARAMETER_NAME);
	}

	/**
	 * @return �������� ���� deviceTokenCookie ��������� �� �������
	 */
	public static String getDeviceTokenCookie()
	{
		return entries.getValue().get(DEVICE_TOKEN_COOKIE_PARAMETER_NAME);
	}

	/**
	 * @return �������� ���� deviceTokenCookie ��������� �� ��
	 */
	public static String getSystemDeviceTokenCookie()
	{
		return entries.getValue().get(SYSTEM_DEVICE_TOKEN_PREFIX_NAME + DEVICE_TOKEN_COOKIE_PARAMETER_NAME);
	}

	/**
	 * @return �������� ���� deviceTokenFSO ��������� �� �������
	 */
	public static String getDeviceTokenFSO()
	{
		return entries.getValue().get(DEVICE_TOKEN_FSO_PARAMETER_NAME);
	}

	/**
	 * @return �������� ���� deviceTokenFSO ��������� �� ��
	 */
	public static String getSystemDeviceTokenFSO()
	{
		return entries.getValue().get(SYSTEM_DEVICE_TOKEN_PREFIX_NAME + DEVICE_TOKEN_FSO_PARAMETER_NAME);
	}

	/**
	 * @return �������� ���� mobileSdkData
	 */
	public static String getMobileSdkData()
	{
		return entries.getValue().get(MOBILE_SDK_DATA_PARAMETER_NAME);
	}
}
