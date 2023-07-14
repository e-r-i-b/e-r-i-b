package com.rssl.phizic.web.common;

import com.rssl.phizic.utils.store.Store;
import com.rssl.phizic.web.WebContext;
import com.rssl.phizic.web.util.HttpSessionUtils;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 27.09.2005
 * Time: 14:57:16
 */

/**
 * ��������� �� ���� http-������ �������.
 * ����������� ��� ������ ��� ������� �������� ������� WebContext.
 * �����! ��������� SessionStore ����� ����� ������������� WebContext, � ������� ����� ��� �����������. 
 */
public class SessionStore implements Store
{
	public String getId()
	{
		HttpServletRequest request = WebContext.getCurrentRequest();
		HttpSession session = request.getSession(); // ��� ������������ ID ������, �������� �, ���� � ���
		return session.getId();
	}

	public void save(String key, Object obj)
    {
	    HttpServletRequest request = WebContext.getCurrentRequest();
	    HttpSession session = request.getSession(); // ��� ������������� ����-�� ���������, �������� ������, ���� � ���
        session.setAttribute(key,obj);
    }

    public Object restore(String key)
    {
	    HttpServletRequest request = WebContext.getCurrentRequest();
        return HttpSessionUtils.getSessionAttribute(request, key);
    }

	public void remove(String key)
	{
		HttpServletRequest request = WebContext.getCurrentRequest();
		HttpSessionUtils.removeSessionAttribute(request, key);
	}

	public Object getSyncObject()
	{
		HttpServletRequest request = WebContext.getCurrentRequest();
		return request.getSession(); // ��� ������������� ����-�� ����������������, �������� ������, ���� � ���
	}

	public void clear()
	{
		HttpServletRequest request = WebContext.getCurrentRequest();

		HttpSession session = request.getSession(false);
		if (session != null)
			session.invalidate();

		//��������� �������� ������� ����� ������
		ContextFilter.refreshSessionParameters(request);
	}
}
