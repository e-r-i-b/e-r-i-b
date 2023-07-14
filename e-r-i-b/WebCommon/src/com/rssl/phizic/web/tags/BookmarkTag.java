package com.rssl.phizic.web.tags;

import javax.servlet.jsp.JspException;

/** User: IIvanova Date: 06.04.2006 Time: 19:07:36 */
public class BookmarkTag extends CheckAccessTag
{
	private static final String NO_SERVICE_ID = "NO_SERVIECE";
	private String action = null;
	protected String param=null;

	protected String makeUrl() {
          StringBuffer result=new StringBuffer();
          result.append(makeActionUrl(action));
          if (param != null) {
              result.append("?");
              result.append(param);
          }
          return result.toString();
    }

	private void setActionAttribute(StringBuffer result)
	{
            String pageServiceId = (String) pageContext.getRequest().getAttribute("mode");
		if(pageServiceId == null)
			pageServiceId = NO_SERVICE_ID;

		if (pageServiceId.equalsIgnoreCase(moduleId) || pageServiceId.equalsIgnoreCase(serviceId))
		{
			result.append(" class='MenuItemSelect'");
		}
		else
		{
			result.append(" class='MenuItem'");
		}

		if (action.startsWith("javascript:"))
		{
			setAttribute(result, "href", action);
		}
		else
		{
			setAttribute(result, "href", makeUrl());
		}
	}


	public int doEndTag() throws JspException
	{
		if (hasAccess())
		{
			StringBuffer result = new StringBuffer();
			result.append("<span class='textNobr headerMenuEl'><a");
			setTitleAttribute(result);
			setActionAttribute(result);
			result.append(">");
			result.append(getBodyContent().getString().trim());
			result.append("</a></span>");
			result.append("&nbsp;&nbsp;&nbsp;&nbsp;");
			outJsp(result);
		}
		release();
		return EVAL_PAGE;
	}

	public void setAction(String action)
	{
		this.action = action;
	}

	public void setParam(String param) {
        this.param = param;
    }

	public void release()
	{
		action = null;
		super.release();
	}
}
