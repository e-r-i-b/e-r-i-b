package com.rssl.phizic.security.password;

/**
 * @author Kidyaev
 * @ created 22.12.2005
 * @ $Author$
 * @ $Revision$
 */
public class UserPassword extends PasswordBase
{
	public boolean isValid()
	{
		return getValue() != null;
	}

	public void setValid(boolean value) { }

    public String toString()
    {
        return getStringValue();
    }
}
