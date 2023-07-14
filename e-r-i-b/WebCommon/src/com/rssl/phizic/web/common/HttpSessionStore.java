package com.rssl.phizic.web.common;

import com.rssl.phizic.utils.store.Store;

import javax.servlet.http.HttpSession;

/**
 * @author mihaylov
 * @ created 25.11.13
 * @ $Author$
 * @ $Revision$
 *
 * ��������� �� ���� http ������. ������ ���������� � ������������, � �� ������� �� ��������.
 * ���������� ��� ��� ����, ��� ��� ��������.
 */
public class HttpSessionStore implements Store
{
	private HttpSession session;

	/**
	 * ����������� ���������
	 * @param session - ������
	 */
	public HttpSessionStore(HttpSession session)
	{
		this.session = session;
	}

	public String getId()
	{
		return session.getId();
	}

	public void save(String key, Object obj)
	{
		session.setAttribute(key,obj);
	}

	public Object restore(String key)
	{
		return session.getAttribute(key);
	}

	public void clear()
	{
	}

	public void remove(String key)
	{
		session.removeAttribute(key);
	}

	public Object getSyncObject()
	{
		return session;
	}
}
