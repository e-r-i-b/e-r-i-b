package com.rssl.phizic.web.tags;

import com.rssl.phizic.gate.payments.systems.recipients.ValidatorField;
import com.rssl.phizic.utils.StringHelper;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Тэг для валидатора
 * @author Jatsky
 * @ created 19.07.2013
 * @ $Author$
 * @ $Revision$
 */

public class ValidatorItemTag extends TagSupport
{
	private String type;
	private String message;
	private String parameter;

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getParameter()
	{
		return parameter;
	}

	public void setParameter(String parameter)
	{
		this.parameter = parameter;
	}

	public int doStartTag()
	{
		return SKIP_BODY;
	}

	public int doEndTag() throws JspException
	{
		if (StringHelper.isEmpty(type))
			throw new JspException("Не указан атрибут type");

		if (StringHelper.isEmpty(message))
			throw new JspException("Не указан атрибут message");

		if (StringHelper.isEmpty(parameter))
			throw new JspException("Не указан атрибут parameter");

		FieldBodyTag fieldBodyTag = (FieldBodyTag) findAncestorWithClass(this, FieldBodyTag.class);
		ValidatorField field = new ValidatorField(type, message, parameter);
		fieldBodyTag.addValidatorItems(field);

		return EVAL_PAGE;
	}

	public void release()
	{
		super.release();
		type = null;
		message = null;
		parameter = null;
	}


}
