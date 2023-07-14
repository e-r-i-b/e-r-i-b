package com.rssl.phizic.web.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 *  ласс дл€ парсинга тега, вставл€ющего в список LinksList элемент с произвольным html-кодом внутри
 * @author tisov
 * @ created 15.11.13
 * @ $Author$
 * @ $Revision$
 */

public class LinksListCommonItemTag extends BodyTagSupport
{
	private String body = null;

	/**
	 * геттер тела тега
	 * @return - тело тега
	 */
	public String getBody()
	{
		return this.body;
	}

	/**
	 * сеттер тела тега
	 * @param newBody - новое тело тега
	 */
	public void setBody(String newBody)
	{
		this.body = newBody;
	}

	public int doEndTag () throws JspException
	{
		StringBuffer buff = new StringBuffer();
		buff.append(this.body);
		LinksListTag linksList = (LinksListTag)findAncestorWithClass (this, LinksListTag.class);
		linksList.addItem(buff);
		return super.doEndTag();
	}

	public void release() {
		super.release();
		this.body = null;
	}

}
