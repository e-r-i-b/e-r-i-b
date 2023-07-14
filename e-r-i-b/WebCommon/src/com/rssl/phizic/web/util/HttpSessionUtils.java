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
	 * Возвращает атрибут сессии.
	 * Не создаёт сессию, если её нет.
	 * @param request - реквест, сессия которого используется
	 * @param attributeKey - название атрибута сессии
	 * @return значение атрибута сессии или null, если атрибут не найден или сессии нет
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
	 * Удаляет атрибут сессии.
	 * Не создаёт сессию, если её нет.
	 * @param request - реквест, сессия которого используется
	 * @param attributeKey - название атрибута сессии
	 * @return значение удалённого атрибута сессии или null, если атрибут не найден или сессии нет
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
	 * Собирает атрибуты сессии в мапу.
	 * @param session - сессия
	 * @return мапа "ключ -> значенение" атрибутов сессии,
	 * пустая мапа, если сессии нет / атрибутов нет
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
	 * Кладёт мапу атрибутов в сессию.
	 * Создаёт сессию, если её нет (и если мапа не пуста).
	 * @param session - сессия
	 * @param attributes - мапа "ключ -> значенение" атрибутов сессии
	 */
	public static void putSessionAttributes(HttpSession session, Map<String, Object> attributes)
	{
		if (MapUtils.isEmpty(attributes))
			return;

		for (Map.Entry<String, Object> entry : attributes.entrySet())
			setAttribute(session, entry);
	}

	/**
	 * Удаляет все атрибуты сессии.
	 * @param session - сессия
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
			throw new InvalidSessionException("Совершена попытка получить атрибут инвалидированной сессии", ise);
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
			throw new InvalidSessionException("Совершена попытка получить атрибут инвалидированной сессии", ise);
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
			throw new InvalidSessionException("Совершена попытка получить атрибут инвалидированной сессии", ise);
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
			throw new InvalidSessionException("Совершена попытка получить атрибут инвалидированной сессии", ise);
		}
	}
}
