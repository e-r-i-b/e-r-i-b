package com.rssl.common.forms.validators;

import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.expressions.ConstantExpression;

/**
 * @author Evgrafov
 * @ created 30.11.2005
 * @ $Author: koptyaev $
 * @ $Revision: 66037 $
 */

public abstract class FieldValidatorBase implements FieldValidator
{
	public static final String DEFAULT_MODE = "draft-template|pre-template|template|by-template|document|mobile";

	private String mode=DEFAULT_MODE;
	private String  message;
	private Expression enabledExpression;

	public FieldValidatorBase(String message)
	{
		this();
		this.message = message;
	}

	public FieldValidatorBase()
	{
		//инициализируем значения по умолчанию.
		setEnabledExpression(new ConstantExpression(true));
	}
    public String getMode()
    {
        return mode;
    }

    public void setMode(String value)
    {
        mode = value;
    }

    public void setParameter(String name, String value)
    {
        return;
    }

    public String getParameter(String name)
    {
        return null;
    }

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String value)
	{
		message = value;
	}

	protected boolean isValueEmpty(String value)
	{
	    if (value == null || value.equals(""))
	        return true;

	    return false;
	}

	public Expression getEnabledExpression()
	{
		return enabledExpression;
	}

	public void setEnabledExpression(Expression enabledExpression)
	{
		this.enabledExpression = enabledExpression;
	}


	public String getMessageKey()
	{
		return null;
	}
}
