package com.rssl.phizic.web.tags;

import com.rssl.phizic.utils.StringHelper;

import java.io.IOException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.JspException;

/**
 * @author Rydvanskiy
 * @ created 28.07.2010
 * @ $Author$
 * @ $Revision$
 */

public class LinksListItemTag extends BodyTagSupport
{
	private String title = null;
	private String href = null;
	private String styleClass = null;
	private String onClick = null;
	private boolean outputByItself = false;
	private String image = null;

	public boolean isOutputByItself()
	{
		return outputByItself;
	}

	public void setOutputByItself(boolean outputByItself)
	{
		this.outputByItself = outputByItself;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getHref()
	{
		return href;
	}

	public void setHref(String href)
	{
		this.href = href;
	}

	public String getStyleClass()
	{
		return styleClass;
	}

	public void setStyleClass(String styleClass)
	{
		this.styleClass = styleClass;
	}

	public String getOnClick()
	{
		return onClick;
	}

	public void setOnClick(String onClick)
	{
		this.onClick = onClick;
	}

	public String getImage()
	{
		return image;
	}

	public void setImage(String image)
	{
		this.image = image;
	}

	public int doEndTag () throws JspException
	{
		StringBuffer buff = new StringBuffer();

		onClick = (href == null && onClick == null)? "return false;":onClick;
		href = (href == null)? "#":href;

		if (styleClass!= null && styleClass.equals("note"))
		{
			buff.append("<div class=\"" + styleClass + "\">" + title + "</div>");
		}
		else
		{
			buff.append("<a href=\""+href+"\"");

			if ( onClick != null && onClick.length() > 0 )
				buff.append(" onclick=\""+onClick+"\"");

			if ( styleClass != null && styleClass.length() > 0 )
				buff.append(" class=\""+styleClass+"\"");

			buff.append(">" + title + (!StringHelper.isEmpty(image) ? image : "") + "</a>");
		}

		if (outputByItself)
		{
			try
			{
				pageContext.getOut().print("<li>" + buff + "</li>");
			}
			catch (IOException e)
			{
				throw new JspException(e.getMessage(), e);
			}
		}
		else
		{
			LinksListTag linksList = (LinksListTag)findAncestorWithClass (this, LinksListTag.class);
			linksList.addItem(new StringBuffer("<li>" + buff + "</li>"));
		}
		return super.doEndTag();
	}

	public void release() {
		super.release();
		title = null;
		href = null;
		styleClass = null;
		onClick = null;
	}

}
