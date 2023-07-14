package com.rssl.phizic.web.actions;

import com.rssl.phizic.auth.modes.CompositeException;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Erkin
 * @ created 06.09.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Хедпер для работы со struts-сообщениями
 */
public class ActionMessageHelper
{
	static final String SESSION_MESSAGES_KEY = "com.rssl.phizic.web.actions.SESSION_MESSAGES_KEY";


	/**
	 * Записать в реквест информационные сообщения
	 * @param request - реквест
	 * @param messages - список сообщений
	 */
	public static void saveMessages(HttpServletRequest request, Collection<String> messages)
	{
		if (CollectionUtils.isEmpty(messages))
		{
			return;
		}

		ActionMessages notices = new ActionMessages();
		for (String message : messages)
		{
			notices.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(message, false));
		}

		saveRequestNotices(request, notices, Globals.MESSAGE_KEY);
	}

	/**
	 * Записать в реквест информационные сообщения
	 * @param request - реквест
	 * @param notices - список сообщений
	 */
	public static void saveMessages(HttpServletRequest request, ActionMessages notices)
	{
		saveRequestNotices(request, notices, Globals.MESSAGE_KEY);
	}

	/**
	 * Записать в реквест сообщения об ошибках
	 * @param request - реквест
	 * @param exception - ошибка
	 */
	public static void saveError(HttpServletRequest request, Exception exception)
	{
		ActionMessages actionErrors = new ActionMessages();

		if (exception instanceof CompositeException)
		{
			for (Exception e : ((CompositeException) exception).getExceptions())
			{
				if (StringHelper.isNotEmpty(e.getMessage()))
					actionErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
			}
		}
		else if (StringHelper.isNotEmpty(exception.getMessage()))
			actionErrors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(exception.getMessage(), false));

		saveErrors(request, actionErrors);
	}

	/**
	 * Записать в реквест сообщения об ошибках
	 * @param request - реквест
	 * @param errors - список ошибок
	 */
	public static void saveErrors(HttpServletRequest request, List<String> errors)
	{
		if (CollectionUtils.isEmpty(errors))
		{
			return;
		}

		ActionMessages notices = new ActionMessages();
		for (String error : errors)
		{
			notices.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(error, false));
		}
		saveRequestNotices(request, notices, Globals.ERROR_KEY);
    }

	/**
	 * Записать в реквест сообщения об ошибках
	 * @param request - реквест
	 * @param notices - ошибки
	 */
	public static void saveErrors(HttpServletRequest request, ActionMessages notices)
	{
		saveRequestNotices(request, notices, Globals.ERROR_KEY);
	}

	/**
	 * Записать в сессию сообщения об ошибках
	 * @param session - сессия
	 * @param errors - ошибки
	 */
	public static void saveErrors(HttpSession session, ActionMessages errors)
	{
		if (errors == null || errors.isEmpty())
			return;

		ActionMessages allErrors = (ActionMessages) session.getAttribute(Globals.ERROR_KEY);
		if (allErrors != null)
			allErrors.add(errors);
		else allErrors = errors;

		session.setAttribute(Globals.ERROR_KEY, allErrors);
	}

	/**
	 * @param request
	 * @return сообщения об ошибках в реквесте
	 */
	public static ActionMessages getErrors(HttpServletRequest request)
	{
	    ActionMessages errors = (ActionMessages) request.getAttribute(Globals.ERROR_KEY);
	    if (errors == null)
	        errors = new ActionMessages();
	    return errors;
	}

	/**
	 *
	 * Групповое сохранение сообщений в сессию
	 *
	 * @param request
	 * @param messages
	 */
	public static void saveSessionMessages(HttpServletRequest request, ActionMessages messages)
	{
		HttpSession session = request.getSession();
		ActionMessages allMessages = (ActionMessages) session.getAttribute(SESSION_MESSAGES_KEY);

		if (allMessages != null)
		{
			allMessages.add(messages);
		}
		else
		{
			allMessages = messages;
		}
		session.setAttribute(SESSION_MESSAGES_KEY, allMessages);
	}

	public static void saveSessionMessage(HttpServletRequest request, String message)
	{
		ActionMessages actionMessages = new ActionMessages();
	    actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(message, false) );
		saveSessionMessages(request, actionMessages);
	}

	/**
	 * Записать в сессию информационные сообщения
	 * @param request - реквест
	 * @param messages - список сообщений
	 */
	public static void saveSessionMessage(HttpServletRequest request, Collection<String> messages)
	{
		if (CollectionUtils.isEmpty(messages))
		{
			return;
		}

		ActionMessages notices = new ActionMessages();
		for (String message : messages)
		{
			notices.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(message, false));
		}

		saveSessionMessages(request, notices);
	}

	/**
	 * Записать в реквест сообщения об ошибках
	 * @param request - реквест
	 * @param notices - ошибки
	 * @param key ключ
	 */
	private static void saveRequestNotices(HttpServletRequest request, ActionMessages notices, String key)
	{
		if (notices == null || notices.isEmpty())
		{
			return;
		}

		ActionMessages current = (ActionMessages) request.getAttribute(key);
		if (current != null)
		{
			current.add(notices);
		}
		else
		{
			current = notices;
		}

		request.setAttribute(key, current);
	}
}
