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
 * ������ ��� ������ �� struts-�����������
 */
public class ActionMessageHelper
{
	static final String SESSION_MESSAGES_KEY = "com.rssl.phizic.web.actions.SESSION_MESSAGES_KEY";


	/**
	 * �������� � ������� �������������� ���������
	 * @param request - �������
	 * @param messages - ������ ���������
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
	 * �������� � ������� �������������� ���������
	 * @param request - �������
	 * @param notices - ������ ���������
	 */
	public static void saveMessages(HttpServletRequest request, ActionMessages notices)
	{
		saveRequestNotices(request, notices, Globals.MESSAGE_KEY);
	}

	/**
	 * �������� � ������� ��������� �� �������
	 * @param request - �������
	 * @param exception - ������
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
	 * �������� � ������� ��������� �� �������
	 * @param request - �������
	 * @param errors - ������ ������
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
	 * �������� � ������� ��������� �� �������
	 * @param request - �������
	 * @param notices - ������
	 */
	public static void saveErrors(HttpServletRequest request, ActionMessages notices)
	{
		saveRequestNotices(request, notices, Globals.ERROR_KEY);
	}

	/**
	 * �������� � ������ ��������� �� �������
	 * @param session - ������
	 * @param errors - ������
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
	 * @return ��������� �� ������� � ��������
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
	 * ��������� ���������� ��������� � ������
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
	 * �������� � ������ �������������� ���������
	 * @param request - �������
	 * @param messages - ������ ���������
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
	 * �������� � ������� ��������� �� �������
	 * @param request - �������
	 * @param notices - ������
	 * @param key ����
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
