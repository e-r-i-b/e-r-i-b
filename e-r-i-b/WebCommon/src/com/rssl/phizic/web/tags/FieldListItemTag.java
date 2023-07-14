package com.rssl.phizic.web.tags;

import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.gate.payments.systems.recipients.ListValue;
import com.rssl.phizic.utils.StringHelper;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author Erkin
* @ created 09.11.2011
* @ $Author$
* @ $Revision$
*/
public class FieldListItemTag extends TagSupport
{
	private String value;
    private ExternalResourceLink link;
	private String title;

	///////////////////////////////////////////////////////////////////////

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

    public ExternalResourceLink getLink() {
        return link;
    }

    public void setLink(ExternalResourceLink link) {
        this.link = link;
    }

	public int doStartTag()
	{
		return SKIP_BODY;
	}

	public int doEndTag() throws JspException
	{
		if (StringHelper.isEmpty(value))
			throw new JspException("Не указан атрибут value");

		FieldBodyTag fieldBodyTag = (FieldBodyTag) findAncestorWithClass(this, FieldBodyTag.class);
        ListValue field;
        if (link != null) {
            field = new ResourceListValue(!StringHelper.isEmpty(this.title) ? this.title : value, value, link);
        } else {
            field = new ListValue(!StringHelper.isEmpty(this.title) ? this.title : value, value);
        }
		fieldBodyTag.addListItem(field);

		return EVAL_PAGE;
	}

	public void release()
	{
		super.release();
		value = null;
		title = null;
	}
}