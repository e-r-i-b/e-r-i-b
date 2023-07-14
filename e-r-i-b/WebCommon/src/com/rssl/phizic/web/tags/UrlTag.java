package com.rssl.phizic.web.tags;

import com.rssl.phizic.web.servlet.jsp.WebPageContext;

import javax.servlet.jsp.JspException;

/**
 * @author Erkin
 * @ created 18.04.2012
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings({"ClassNameSameAsAncestorName"})
public class UrlTag extends org.apache.taglibs.standard.tag.rt.core.UrlTag
{
	@Override
	public int doStartTag() throws JspException
	{
		// Переопределяем contextPath в ссылках
		setPageContext(WebPageContext.create(pageContext, null));
		return super.doStartTag();
	}
}
