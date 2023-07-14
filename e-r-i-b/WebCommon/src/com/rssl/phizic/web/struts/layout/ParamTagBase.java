package com.rssl.phizic.web.struts.layout;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.JspException;

/**
 * @author Krenev
 * @ created 23.01.2009
 * @ $Author$
 * @ $Revision$
 */
public abstract class ParamTagBase extends BodyTagSupport
{
	private Object collectionTag;
	private String value;
	private boolean condition = true;
	private boolean skip = true;
	private boolean valueSpecified = false;

	public int doStartTag() throws JspException
	{
		skip = !getCondition();
		if (skip)
		{
			return SKIP_BODY;
		}
		collectionTag = getAncestorTag();
		return EVAL_BODY_BUFFERED;
	}

	protected abstract Object getAncestorTag();

	protected abstract boolean isAllowedProperty(String name);


	public int doEndTag() throws JspException
	{
		if (skip)
		{
			return SKIP_BODY;
		}
		if (!isAllowedProperty(getId())){
			throw new JspException("Unsupported Property "+getId());
		}
		Object result;
		if (value != null) //если задано значение явно, то использкем его
			result = value;
		else if (valueSpecified)
			result = null;
		else if (bodyContent == null || bodyContent.getString() == null)
			result = "";
		else
			result = bodyContent.getString().trim();
		try
		{
			BeanUtils.setProperty(collectionTag, getId(), result);
		}
		catch (IllegalAccessException e)
		{
			throw new JspException(e);
		}
		catch (InvocationTargetException e)
		{
			throw new JspException(e);
		}
		return EVAL_PAGE;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
		valueSpecified = true;
	}

	public boolean getCondition()
	{
		return condition;
	}

	public void setCondition(boolean condition)
	{
		this.condition = condition;
	}

	public void release()
	{
		super.release();
		collectionTag = null;
		condition = true;
		valueSpecified = false;
		value = null;
	}
}
