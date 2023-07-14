package com.rssl.phizic.web.tags;

import com.rssl.phizic.web.servlet.jsp.WebPageContext;

import javax.servlet.jsp.JspException;

/**
 * @author Erkin
 * @ created 26.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class HtmlLinkTag extends org.apache.struts.taglib.html.LinkTag
{
	private String contextPathName = null;

	public void setContextPathName(String contextPathName)
	{
		this.contextPathName = contextPathName;
	}

	@Override
	public int doStartTag() throws JspException
	{
		// Переопределяем contextPath в ссылках
		setPageContext(WebPageContext.create(pageContext, contextPathName));
		return super.doStartTag();
	}
}
