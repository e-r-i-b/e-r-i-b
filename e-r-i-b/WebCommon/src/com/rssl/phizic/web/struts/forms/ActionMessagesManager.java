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
	 * @return Map вида < имя поля, ошибка валидации>
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
			//если в values лежит не ActionMessages идем дальше
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
	 * Возвращает список ошибок.
	 * 
	 * @param bundle бандл.
	 * @param messages actionMessages.
	 * @return список ошибок.
	 */
	private static List<String> getErrorList(String bundle, ActionMessages messages)
	{
		List<String> errorsList = new ArrayList<String>();
		if(messages == null)
			return errorsList;

		Set<String> addedErrors = new HashSet<String>(); //Чтобы не дублировались сообщения.
		Iterator iterator = messages.get();
		while(iterator.hasNext())
		{
			ActionMessage message = (ActionMessage) iterator.next();
			//если message.getValues() это ActionMessage, то это ошибки валидации поля
			if (message.getValues() != null && message.getValues()[0] instanceof ActionMessage)
			{
				continue;
			}

			String error = message.isResource() ?
					getResourcesMessage(bundle, message.getKey(), message.getValues()) :
					message.getKey();

			if(StringHelper.isEmpty(error))
				throw new IllegalStateException("Пустая ошибка в ActionMessages. Проверьте подключен ли к бандлу " + bundle + " необходимый файл с ресурсами и корректность сохранения ошибки в ActionMessages");

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
	 * @return List с ошибками валидации формы
	 */
	public static List<String> getFormValidationError(String bundle, String name)
	{
		HttpServletRequest currentRequest = WebContext.getCurrentRequest();
		ActionMessages messages = (ActionMessages) currentRequest.getAttribute(name);
		return getErrorList(bundle, messages);
	}

	/**
	 * возвращает список ошибок, хранящийся в сессии
	 * @param bundle bundle
	 * @param name ключ
	 * @return список ошибок
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
