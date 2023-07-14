package com.rssl.phizic.web.tags;

import com.rssl.phizic.business.basket.BasketHelper;
import com.rssl.phizic.business.basket.InvoiceMessage;
import com.rssl.phizic.utils.store.Store;
import com.rssl.phizic.utils.store.StoreManager;
import org.apache.commons.collections.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.*;

/**
 * Кастомный тэг для вывода сообщений об обработке инвойсов
 * @author niculichev
 * @ created 16.10.14
 * @ $Author$
 * @ $Revision$
 */
public class InvoiceMessageTag extends BodyTagSupport
{
	private Iterator<InvoiceMessage> iterator;
	private String id;

	public int doStartTag() throws javax.servlet.jsp.JspException
	{
		List<InvoiceMessage> messages = InvoiceMessage.removeMessages();
		if(CollectionUtils.isEmpty(messages))
			return SKIP_BODY;

		iterator = messages.iterator();
		return EVAL_BODY_BUFFERED;
	}

	public void doInitBody() throws javax.servlet.jsp.JspException
	{
		pageContext.setAttribute(id, iterator.next());
	}

	public int doAfterBody() throws javax.servlet.jsp.JspException
	{
		try
		{
			InvoiceMessage info = (InvoiceMessage) pageContext.getAttribute(id);

			String body = bodyContent.getString();
			bodyContent.clearBody();

			bodyContent.print("<div class=\"invoice-message ");
			bodyContent.print("action-" + info.getType().name() + "\">");
			bodyContent.print(body);
			bodyContent.print("</div>");

			bodyContent.writeOut(getPreviousOut());
		}
		catch (IOException e)
		{
			throw new JspException(e.getMessage(), e);
		}

		if(iterator.hasNext())
		{
			pageContext.setAttribute(id, iterator.next());
			return EVAL_BODY_BUFFERED;
		}

		return SKIP_BODY;
	}

	public int doEndTag() throws javax.servlet.jsp.JspException
	{
		return EVAL_PAGE;
	}


	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * Информация о переменных сценария
	 */
	public static class InvoiceMessageTagInfo extends TagExtraInfo
	{
		public VariableInfo[] getVariableInfo(TagData data)
		{
			return new VariableInfo[] {new VariableInfo(data.getId(), "java.lang.Object", true, VariableInfo.NESTED)};
		}
	}
}
