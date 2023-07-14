package com.rssl.phizic.web.tags;

import com.rssl.phizic.auth.BlockingReasonDescriptor;
import com.rssl.phizic.auth.BlockingReasonType;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.TagUtils;

/**
 * @author Egorova
 * @ created 01.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class ReasonTypeTag extends org.apache.struts.taglib.html.TextTag
{
	private String value;

	public int doStartTag() throws JspException
	{
		TagUtils.getInstance().write(this.pageContext, BlockingReasonDescriptor.getReasonText(BlockingReasonType.valueOf(value)));
        return EVAL_PAGE;
	}

	public void setValue(String value)
	{
		this.value = value;
	}
}
