package com.rssl.phizic.web.tag.popup;

import com.rssl.phizic.utils.StringHelper;

import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * Всплывающее окно
 * User: Андрей
 * Date: 27.10.2013
 * Time: 10:33:26
 */
public class PopupItem extends BodyTagSupport
{
	private String page;
	private String onclose;
	private boolean closable = true;
	private PopupCollection popupCollection;

    public int doStartTag() throws javax.servlet.jsp.JspException
    {
	    popupCollection = (PopupCollection) findAncestorWithClass(this, PopupCollection.class);
	    if(StringHelper.isNotEmpty(page))
	    {
		    popupCollection.addPoppupItem(new PopupEntity(getId(), page, PopupEntity.ContentType.PAGE, isClosable(), getOnclose()));
		    return SKIP_BODY;
	    }

        return EVAL_BODY_BUFFERED;
    }

    public int doAfterBody() throws javax.servlet.jsp.JspException
    {
	    popupCollection.addPoppupItem(new PopupEntity(getId(), bodyContent.getString(), PopupEntity.ContentType.BODY, isClosable(), getOnclose()));
        return SKIP_BODY;
    }

    public void release()
    {
	    page = null;
	    popupCollection = null;
    }

	public String getPage()
	{
		return page;
	}

	public void setPage(String page)
	{
		this.page = page;
	}

	public String getOnclose()
	{
		return onclose;
	}

	public void setOnclose(String onclose)
	{
		this.onclose = onclose;
	}

	public boolean isClosable()
	{
		return closable;
	}

	public void setClosable(boolean closable)
	{
		this.closable = closable;
	}
}
