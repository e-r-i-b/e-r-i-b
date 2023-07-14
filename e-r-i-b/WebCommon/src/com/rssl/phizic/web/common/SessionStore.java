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
 * Хранилище на базе http-сессии клиента.
 * Реализована как обёртка над сессией текущего запроса WebContext.
 * ВАЖНО! Создавать SessionStore нужно после инициализации WebContext, а удалять перед его разрушением. 
 */
public class SessionStore implements Store
{
	public String getId()
	{
		HttpServletRequest request = WebContext.getCurrentRequest();
		HttpSession session = request.getSession(); // Раз потребовался ID сессии, создадим её, если её нет
		return session.getId();
	}

	public void save(String key, Object obj)
    {
	    HttpServletRequest request = WebContext.getCurrentRequest();
	    HttpSession session = request.getSession(); // Раз потребовалось чего-то сохранить, создадим сессию, если её нет
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
		return request.getSession(); // Раз потребовалось чего-то синхронизировать, создадим сессию, если её нет
	}

	public void clear()
	{
		HttpServletRequest request = WebContext.getCurrentRequest();

		HttpSession session = request.getSession(false);
		if (session != null)
			session.invalidate();

		//обновляем контекст данными новой сессии
		ContextFilter.refreshSessionParameters(request);
	}
}
