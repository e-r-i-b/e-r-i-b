package com.rssl.phizic.web.struts.forms;

import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.WebContext;
import com.rssl.phizic.web.common.messages.MessageConfigRouter;
import com.rssl.phizic.web.util.HttpSessionUtils;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.util.*;
import javax.servlet.http.HttpServletRequest;

/**
 * @author mihaylov
 * @ created 05.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class ActionMessagesManager
{
	public static final String ERROR = Globals.ERROR_KEY;
	public static final String MESSAGE = Globals.MESSAGE_KEY;
	public static final String COMMON_BUNDLE = "commonBundle";

	/**
	 *
	 * @return Map ���� < ��� ����, ������ ���������>
	 */
	public static Map<String,String> getFieldsValidationError(String bundle,String name)
	{
		Map<String,String> errorsMap = new HashMap<String,String>();

		HttpServletRequest currentRequest = WebContext.getCurrentRequest();
		ActionMessages messages = (ActionMessages) currentRequest.getAttribute(name);
		if(messages == null)
			return errorsMap;
		Iterator iterator = messages.get();
		while(iterator.hasNext())
		{
			ActionMessage message = (ActionMessage) iterator.next();
			//���� � values ����� �� ActionMessages ���� ������
			if(message.getValues() == null || !(message.getValues()[0] instanceof ActionMessage))
				continue;
			String fieldKey = message.getKey();
			message = (ActionMessage) message.getValues()[0];

			String msg;
			if(message.isResource())
			{
				msg = getResourcesMessage(bundle, message.getKey(), message.getValues());
			}
			else
			{
				msg = message.getKey();
			}
			errorsMap.put(fieldKey,msg);
		}
		return errorsMap;
	}

	/**
	 * ���������� ������ ������.
	 * 
	 * @param bundle �����.
	 * @param messages actionMessages.
	 * @return ������ ������.
	 */
	private static List<String> getErrorList(String bundle, ActionMessages messages)
	{
		List<String> errorsList = new ArrayList<String>();
		if(messages == null)
			return errorsList;

		Set<String> addedErrors = new HashSet<String>(); //����� �� ������������� ���������.
		Iterator iterator = messages.get();
		while(iterator.hasNext())
		{
			ActionMessage message = (ActionMessage) iterator.next();
			//���� message.getValues() ��� ActionMessage, �� ��� ������ ��������� ����
			if (message.getValues() != null && message.getValues()[0] instanceof ActionMessage)
			{
				continue;
			}

			String error = message.isResource() ?
					getResourcesMessage(bundle, message.getKey(), message.getValues()) :
					message.getKey();

			if(StringHelper.isEmpty(error))
				throw new IllegalStateException("������ ������ � ActionMessages. ��������� ��������� �� � ������ " + bundle + " ����������� ���� � ��������� � ������������ ���������� ������ � ActionMessages");

			if (!addedErrors.contains(error))
			{
				errorsList.add(error);
				addedErrors.add(error);
			}
		}
		return errorsList;
	}

	/**
	 *
	 * @return List � �������� ��������� �����
	 */
	public static List<String> getFormValidationError(String bundle, String name)
	{
		HttpServletRequest currentRequest = WebContext.getCurrentRequest();
		ActionMessages messages = (ActionMessages) currentRequest.getAttribute(name);
		return getErrorList(bundle, messages);
	}

	/**
	 * ���������� ������ ������, ���������� � ������
	 * @param bundle bundle
	 * @param name ����
	 * @return ������ ������
	 */
	public static List<String> getSessionErrors(String bundle, String name)
	{
		HttpServletRequest request = WebContext.getCurrentRequest();
		ActionMessages messages = HttpSessionUtils.removeSessionAttribute(request, name);
		return getErrorList(bundle, messages);
	}

	private static String getResourcesMessage(String bundle, String key, Object[] values)
	{
		return MessageConfigRouter.getInstance().message(bundle, key, values);
	}
}
