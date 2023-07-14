package com.rssl.phizic.web.tags;

import com.rssl.phizic.utils.StringHelper;
import org.apache.taglibs.standard.tag.common.core.ImportSupport;
import org.apache.taglibs.standard.tag.common.core.ParamParent;
import org.apache.taglibs.standard.tag.common.core.ParamSupport;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

/**
 * User: IIvanova
 * Date: 11.04.2006
 * Time: 18:08:01
 */

/**
 * Формирование ссылки. ссылка может формироваться либо по прямому заданному url,
 * либо по параметру action и множеству вложенных тегов param.
 * Приоритет
 */
public class LinkTag extends CheckAccessTag implements ParamParent
{
	private static final String ONCLICK_JS = "loadNewAction('','')";
	private ParamSupport.ParamManager params = null;

	protected String  action=null;
	protected String  url=null;
	protected String  param=null;
	protected Boolean loadTime=true;
	protected String  styleClass=null;
	protected String  onclick=null;

	public String getStyleClass()
	{
		return styleClass;
	}

	public void setStyleClass(String styleClass)
	{
		this.styleClass = styleClass;
	}

	public void addParameter(String name, String value)
	{
		if (!StringHelper.isEmpty(value))
		{
			params.addParameter(name, value);
		}
    }

	protected String makeUrl()
	{
		String baseUrl = url;
		if (StringHelper.isEmpty(baseUrl))
		{
			StringBuilder result = new StringBuilder();
			result.append(makeActionUrl(action));
			if (param != null)
			{
				result.append("?");
				result.append(param);
			}
			baseUrl = result.toString();
		}
		return params.aggregateParams(baseUrl);
    }

	public int doStartTag() throws JspException
	{
		params = new ParamSupport.ParamManager();

		return super.doStartTag();
    }

	public int doEndTag() throws JspException
	{
		StringBuffer sb = new StringBuffer();
		if (hasAccess())
		{
			sb.append("<a");
			if (styleClass != null)
			{
				sb.append(" class='" + styleClass + "' ");
			}
			setAttribute(sb, "href", makeUrl());
			if (loadTime)
				onclick = onclick == null ? ONCLICK_JS : onclick + ONCLICK_JS;
			setAttribute(sb, "onclick", onclick);
			sb.append(">");
			sb.append(getBodyContent().getString().trim());
			sb.append("</a>");
		}
		else
		{
			sb.append("<div class='" + styleClass + "'>");
			sb.append(getBodyContent().getString().trim());
			sb.append("</div>");
		}

		String result = sb.toString();

		if (!ImportSupport.isAbsoluteUrl(result))
		{
			HttpServletResponse response = ((HttpServletResponse) pageContext.getResponse());
			result = response.encodeURL(result);
		}
		try
		{
			pageContext.getOut().print(result);
		}
		catch (IOException e)
		{}
		release();
		
		return EVAL_PAGE;
	}

    public void setAction(String action)
    {
        this.action = action;
    }

	public void setUrl(String url)
	{
	    this.url = url;
	}

	/**
	 *
	 * @deprecated используйте вложенные теги param (см TAS027908)
	 */
	@Deprecated
    public void setParam(String param)
    {
        this.param = param;
    }

	public void setLoadTime(Boolean loadTime)
	{
        this.loadTime = loadTime;
    }

	public void setOnclick(String onclick)
	{
		this.onclick = onclick;
	}

	public void release()
    {
		action=null;
	    url=null;
		param=null;
		loadTime=true;
	    params = null;
	    onclick = null;
		super.release();
    }
}
