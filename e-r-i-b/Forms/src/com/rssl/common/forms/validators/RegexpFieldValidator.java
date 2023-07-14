package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;

import java.util.regex.Pattern;

/**
 * Проверка строки на предмет соответствия регулярному выражению
 * @author Evgrafov
 * @ created 01.12.2005
 * @ $Author: khudyakov $
 * @ $Revision: 28554 $
 */

public class RegexpFieldValidator extends FieldValidatorBase
{
    public static final String PARAMETER_REGEXP = "regexp";

    private Pattern pattern;

	public RegexpFieldValidator()
	{
		pattern = Pattern.compile("");
	}

	public RegexpFieldValidator(String regexp)
	{
		pattern = Pattern.compile(regexp);
	}

	public RegexpFieldValidator(String regexp, String message)
	{
		this(regexp);
		this.setMessage(message);
	}

	public RegexpFieldValidator(String regexp, String message, String mode)
	{
		this(regexp);
		this.setMessage(message);
		this.setMode(mode);
	}

	public void setParameter(String name, String value)
	{
		if ( name.equalsIgnoreCase(PARAMETER_REGEXP) )
		{
			pattern = Pattern.compile(value);
		}
	}

    public String getParameter(String name)
    {
	    if ( name.equalsIgnoreCase(PARAMETER_REGEXP) )
	    {
		    return pattern.pattern();
	    }

        return null;
    }

    public boolean validate(String value) throws TemporalDocumentException
    {
	    if ( isValueEmpty(value) )
	    {
		    return true;
	    }

        return validatePattern(value);
    }

	protected boolean validatePattern(String value)
	{
		return pattern.matcher(value).matches();
	}
}
