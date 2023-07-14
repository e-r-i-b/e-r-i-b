package com.rssl.phizic.context;

import org.apache.commons.collections.MapUtils;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.rssl.phizic.context.Constants.*;

/**
 * �������� �������� ���������� ��������
 *
 * @author khudyakov
 * @ created 29.01.15
 * @ $Author$
 * @ $Revision$
 */
public class HeaderContext
{
	private static HeaderEntry entries = new HeaderEntry();

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
	 * ������������� ��������� ���� ������������� ������
	 * @param map - ������������� ������
	 */
	public static void initialize(Map<String, String> map)
	{
		entries.initialize(map);
	}

	/**
	 * ������ ���������
	 */
	public static void clear()
	{
		entries.clear();
	}

	/**
	 * @return �������� Accept ���������
	 */
	public static String getHttpAccept()
	{
		return entries.getValue().get(HTTP_ACCEPT_HEADER_NAME);
	}

	/**
	 * @return �������� Accept-Charset ���������
	 */
	public static String getHttpAcceptChars()
	{
		return entries.getValue().get(HTTP_ACCEPT_CHARS_HEADER_NAME);
	}

	/**
	 * @return �������� Accept-Encoding ���������
	 */
	public static String getHttpAcceptEncoding()
	{
		return entries.getValue().get(HTTP_ACCEPT_ENCODING_HEADER_NAME);
	}

	/**
	 * @return �������� Accept-Language ���������
	 */
	public static String getHttpAcceptLanguage()
	{
		return entries.getValue().get(HTTP_ACCEPT_LANGUAGE_HEADER_NAME);
	}

	/**
	 * @return �������� Referer ���������
	 */
	public static String getHttpReferrer()
	{
		return entries.getValue().get(HTTP_REFERRER_HEADER_NAME);
	}

	/**
	 * @return �������� User-Agent ���������
	 */
	public static String getUserAgent()
	{
		return entries.getValue().get(HTTP_USER_AGENT_HEADER_NAME);
	}

	/**
	 * @return �������� ������������� ��������
	 */
	public static String getPageId()
	{
		return entries.getValue().get(PAGE_ID_PARAMETER_NAME);
	}
}
