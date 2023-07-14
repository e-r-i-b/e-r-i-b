package com.rssl.common.forms.validators;

/**
 * Проверяет на то, что значнеие НЕ удовлетворяет выражению
 * Использовать, когда проще проверить, что указанные символы НЕ встречаются
 *
 * @author egorova
 * @ created 24.11.2010
 * @ $Author$
 * @ $Revision$
 */

public class NotMatchRegexpFieldValidator  extends RegexpFieldValidator
{
	public NotMatchRegexpFieldValidator()
	{
		super();
	}
	
	public NotMatchRegexpFieldValidator(String regexp)
	{
		super(regexp);
	}

    public NotMatchRegexpFieldValidator(String regexp, String message)
	{
		super(regexp, message);
	}

   public boolean validatePattern(String value)
    {
        return !super.validatePattern(value);
    }
}
