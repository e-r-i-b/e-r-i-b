package com.rssl.common.forms.validators;

import com.rssl.common.forms.expressions.ConstantExpression;
import com.rssl.common.forms.expressions.Expression;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Evgrafov
 * @ created 05.12.2005
 * @ $Author: rtishcheva $
 * @ $Revision: 73973 $
 */

public abstract class MultiFieldsValidatorBase implements MultiFieldsValidator
{
	public static final String MOCK_FULL_NAME = "This wouldn't be seen in a real system";
	public static final String DEFAULT_MODE = "draft-template|pre-template|template|by-template|document|mobile";

	private String mode=DEFAULT_MODE;
    private Map<String,String> bindings   = new HashMap<String, String>();
    private Map<String,String> parameters = new HashMap<String, String>();
	private Map<String, String> errorFields = new HashMap<String, String>();
    private String message;
	private Expression enabledExpression;

	public MultiFieldsValidatorBase()
	{
		//инициализируем значения по умолчанию.
		setEnabledExpression(new ConstantExpression(true));
	}

	protected MultiFieldsValidatorBase(String message)
	{
		this();
		this.message = message;
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
        parameters.put(name, value);
    }

    public String getParameter(String name)
    {
        return parameters.get(name);
    }

    public void setBinding(String validatorField, String formField)
    {
        bindings.put(validatorField, formField);
    }

    public String getBinding(String validatorField)
    {
        return bindings.get(validatorField);
    }

	public void setErrorField(String name, String value)
	{
		errorFields.put(name, value);
	}

	public String getErrorField(String validatorField)
	{
		return errorFields.get(validatorField);
	}

	public String getMessage()
    {
        return message;
    }

    public void setMessage(String value)
    {
        this.message = value;
    }

    protected Object retrieveFieldValue(String validatorField, Map values)
    {
        String formField = getBinding(validatorField);

        if(formField == null)
            throw new RuntimeException("Привязка для поля валидатора " + validatorField + " не определена");

        return values.get(formField);
    }

	public String[] getBindingsNames()
	{
		return bindings.keySet().toArray(new String[bindings.size()]);
	}

	public void setEnabledExpression(Expression enabledExpression)
	{
		this.enabledExpression = enabledExpression;
	}

	public Expression getEnabledExpression()
	{
		return enabledExpression;
	}

	public String[] getParametersNames()
	{
		return parameters.keySet().toArray(new String[parameters.size()]);
	}

	public String getMessageKey()
	{
		return null;
	}

	public Collection<String> getErrorFieldNames()
	{
		return  errorFields.values();
	}
}
