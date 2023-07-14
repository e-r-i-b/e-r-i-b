package com.rssl.phizic.web.util;

import com.rssl.phizic.common.types.exceptions.InvalidSessionException;
import org.apache.commons.collections.MapUtils;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Erkin
 * @ created 04.02.2013
 * @ $Author$
 * @ $Revision$
 */

public class HttpSessionUtils
{
	/**
	 * ���������� ������� ������.
	 * �� ������ ������, ���� � ���.
	 * @param request - �������, ������ �������� ������������
	 * @param attributeKey - �������� �������� ������
	 * @return �������� �������� ������ ��� null, ���� ������� �� ������ ��� ������ ���
	 */
	public static <T> T getSessionAttribute(HttpServletRequest request, String attributeKey)
	{
		HttpSession session = request.getSession(false);
		if (session == null)
			return null;
		//noinspection unchecked
		return (T) getAttribute(session, attributeKey);
	}

	/**
	 * ������� ������� ������.
	 * �� ������ ������, ���� � ���.
	 * @param request - �������, ������ �������� ������������
	 * @param attributeKey - �������� �������� ������
	 * @return �������� ��������� �������� ������ ��� null, ���� ������� �� ������ ��� ������ ���
	 */
	public static <T> T removeSessionAttribute(HttpServletRequest request, String attributeKey)
	{
		HttpSession session = request.getSession(false);
		if (session == null)
			return null;
		//noinspection unchecked
		T attributeValue = (T) getAttribute(session, attributeKey);
		if (attributeValue != null)
			removeAttribute(session, attributeKey);
		return attributeValue;
	}

	/**
	 * �������� �������� ������ � ����.
	 * @param session - ������
	 * @return ���� "���� -> ����������" ��������� ������,
	 * ������ ����, ���� ������ ��� / ��������� ���
	 */
	public static Map<String, Object> mapSessionAttributes(HttpSession session)
	{
		List<String> names = new LinkedList<String>();
		Enumeration namesEnum = getAttributeNames(session);
		while (namesEnum.hasMoreElements())
			names.add((String) namesEnum.nextElement());

		Map<String, Object> attributes = new HashMap<String, Object>(names.size());
		for (String name : names)
			attributes.put(name, getAttribute(session, name));
		return attributes;
	}

	/**
	 * ����� ���� ��������� � ������.
	 * ������ ������, ���� � ��� (� ���� ���� �� �����).
	 * @param session - ������
	 * @param attributes - ���� "���� -> ����������" ��������� ������
	 */
	public static void putSessionAttributes(HttpSession session, Map<String, Object> attributes)
	{
		if (MapUtils.isEmpty(attributes))
			return;

		for (Map.Entry<String, Object> entry : attributes.entrySet())
			setAttribute(session, entry);
	}

	/**
	 * ������� ��� �������� ������.
	 * @param session - ������
	 */
	public static void clearSessionAttributes(HttpSession session)
	{
		List<String> names = new LinkedList<String>();
		Enumeration namesEnum = getAttributeNames(session);
		while (namesEnum.hasMoreElements())
			names.add((String) namesEnum.nextElement());

		for (String name : names)
			removeAttribute(session, name);
	}


	private static <T> T getAttribute(HttpSession session, String attributeKey)
	{
		try
		{
			//noinspection unchecked
			return (T) session.getAttribute(attributeKey);
		}
		catch (IllegalStateException ise)
		{
			throw new InvalidSessionException("��������� ������� �������� ������� ���������������� ������", ise);
		}
	}

	private static void removeAttribute(HttpSession session, String attributeKey)
	{
		try
		{
			session.removeAttribute(attributeKey);
		}
		catch (IllegalStateException ise)
		{
			throw new InvalidSessionException("��������� ������� �������� ������� ���������������� ������", ise);
		}
	}

	private static Enumeration getAttributeNames(HttpSession session)
	{
		try
		{
			return session.getAttributeNames();
		}
		catch (IllegalStateException ise)
		{
			throw new InvalidSessionException("��������� ������� �������� ������� ���������������� ������", ise);
		}
	}

	private static void setAttribute(HttpSession session, Map.Entry<String, Object> entry)
	{
		try
		{
			session.setAttribute(entry.getKey(), entry.getValue());
		}
		catch (IllegalStateException ise)
		{
			throw new InvalidSessionException("��������� ������� �������� ������� ���������������� ������", ise);
		}
	}
}
