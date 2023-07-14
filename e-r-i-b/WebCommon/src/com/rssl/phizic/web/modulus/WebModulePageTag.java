package com.rssl.phizic.web.modulus;

import com.rssl.phizic.web.servlet.WebModuleRequestContext;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author Erkin
 * @ created 23.04.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Тег соответствует всему html-фрагменту, генерируемому веб-модулем
 * Тег выполняет следующие задачи:
 *  - передача заголовка веб-модуля и прочих выходных параметров (кроме html-фрагмента) в веб-приложение
 */
public class WebModulePageTag extends TagSupport
{
	private String description;

	private String breadcrumbs;

	private String filter;

	private String submenu;

	private String title;

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setBreadcrumbs(String breadcrumbs)
	{
		this.breadcrumbs = breadcrumbs;
	}

	public void setFilter(String filter)
	{
		this.filter = filter;
	}

	public void setSubmenu(String submenu)
	{
		this.submenu = submenu;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	public int doStartTag() throws JspException
	{
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public int doEndTag() throws JspException
	{
		WebModuleRequestContext context = WebModuleRequestContext.fromRequest(pageContext.getRequest());
		if (context == null)
			throw new JspException("Не найден WebModuleRequestContext");

		// Заполняем атрибуты контейнера
		context.setUseContainer(true);
		context.setPageDescription(description);
		context.setPageBreadcrumbs(breadcrumbs);
		context.setPageFilter(filter);
		context.setPageSubmenu(submenu);
		context.setPageTitle(title);

		return EVAL_PAGE;
	}
}
