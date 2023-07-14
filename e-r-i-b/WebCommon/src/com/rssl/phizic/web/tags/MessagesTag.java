package com.rssl.phizic.web.tags;

import com.rssl.phizic.web.struts.forms.ActionMessagesKeys;
import com.rssl.phizic.web.struts.forms.ActionMessagesManager;
import org.apache.struts.taglib.TagUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * @author mihaylov
 * @ created 23.04.2010
 * @ $Author$
 * @ $Revision$
 */

public class MessagesTag extends BodyTagSupport
{

	private String field    = null;
	private String bundle   = null;
	private String message   = null;
	private String title    = null;
	private Iterator mapIterator = null;
	private Iterator listIterator = null;
	private boolean showSessionMessages;   //“ребуетс€ ли показать сообщени€, сохранЄные в сессии.

	public String getField()
	{
		return field;
	}

	public void setField(String field)
	{
		this.field = field;
	}

	public String getBundle()
	{
		return bundle;
	}

	public void setBundle(String bundle)
	{
		this.bundle = bundle;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * “ребуетс€ ли показывать сообщени€, хран€щиес€ в сессии
	 * @return ƒа, если требуетс€. Ќет, в противном случае.
	 */
	public boolean isShowSessionMessages()
	{
		return showSessionMessages;
	}

	/**
	 * ”становка флага, сигнализирующего показывать ли сообщени€, хран€щиес€ в сессии или нет. ѕо умолчанию нет.
	 * @param showSessionMessage ‘лаг, отвечающий за показ сообщений.
	 */
	public void setShowSessionMessages(boolean showSessionMessage)
	{
		this.showSessionMessages = showSessionMessage;
	}

	public int doStartTag() throws JspException
	{
		String name = ActionMessagesKeys.valueOf(message).getKey();
		//вытаскиваем сообщени€ дл€ полей
		Map<String,String> errorsMap = ActionMessagesManager.getFieldsValidationError(bundle,name);
		mapIterator = errorsMap.entrySet().iterator();

		//вытаскиваем сообщени€ дл€ формы
		List<String> errorList = ActionMessagesManager.getFormValidationError(bundle, name);
		//вытаскиваем сообщени€ из сессии, возникшие при неактивности внешней системы или если требует бизнесс логика
		if (ActionMessagesKeys.inactiveExternalSystem.getKey().equals(name) || showSessionMessages)
		{
			errorList.addAll(ActionMessagesManager.getSessionErrors(bundle, name));
		}
		listIterator = errorList.iterator();

		//если сообщени€ есть, сохран€ем в контест
		//если нет, завершаем обработку тега
		return inspectMessage();
	}

	public int doAfterBody() throws JspException {
		//выводим сообщени€(аттрибуты) в тело тега
		if (bodyContent != null)
		{
			TagUtils.getInstance().writePrevious(pageContext, bodyContent.getString());
			bodyContent.clearBody();
		}
		return inspectMessage();
	}

	private int inspectMessage()
	{
		if(mapIterator.hasNext())
		{
			Map.Entry entry = (Map.Entry)mapIterator.next();
			setAttribute(field,entry.getKey().toString());
			setAttribute(id,entry.getValue().toString());
			return EVAL_BODY_TAG;
		}

		if(listIterator.hasNext())
		{
			String msg = (String) listIterator.next();
			if(msg.contains("<title>") && msg.contains("</title>"))
			{
				setAttribute(title, msg.substring(msg.indexOf("<title>")+"<title>".length(),msg.indexOf("</title>")));
				setAttribute(id, msg.substring(msg.indexOf("</title>")+"</title>".length(), msg.length()));
			}
			else
				setAttribute(id,msg);
			setAttribute(field, null);
			return EVAL_BODY_TAG;
		}

		return SKIP_BODY;
	}

	private void setAttribute(String id,String str)
	{
		pageContext.setAttribute(id, str);
	}

}
