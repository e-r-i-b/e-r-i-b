package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;

import java.math.BigInteger;

/**
 * @author Egorova
 * @ created 23.05.2008
 * @ $Author$
 * @ $Revision$
 */
public class LengthFieldValidator extends FieldValidatorBase
{
	public static final String PARAMETER_MIN_LENGHT = "minlength";
    public static final String PARAMETER_MAX_LENGHT = "maxlength";

    protected BigInteger minlength = null;
    protected BigInteger maxlength = null;

    public LengthFieldValidator()
    {

    }

	public LengthFieldValidator(BigInteger maxlength)
	{
		this.maxlength = maxlength;
	}

	public LengthFieldValidator(BigInteger minlength, BigInteger maxlength)
	{
		this.minlength = minlength;
		this.maxlength = maxlength;
	}

    public LengthFieldValidator(int minlength, int maxlength)
    {
        this.minlength = new BigInteger("" + minlength);
        this.maxlength = new BigInteger("" + maxlength);
    }

	public String getParameter(String name)
    {
        if (name.equals(PARAMETER_MIN_LENGHT))
            return minlength.toString();
        else if (name.equals(PARAMETER_MAX_LENGHT))
            return maxlength.toString();
        else
            return super.getParameter(name);
    }

    public void setParameter(String name, BigInteger value)
    {
        if (name.equals(PARAMETER_MIN_LENGHT))
            minlength = value;
        else if (name.equals(PARAMETER_MAX_LENGHT))
            maxlength = value;
        else
            super.setParameter(name, value.toString());
    }

    public boolean validate(String value) throws TemporalDocumentException
    {
        if(isValueEmpty(value))
            return true;

        if(minlength != null && value.length() < minlength.intValue())
          return false;

        if (maxlength != null && value.length() > maxlength.intValue())
          return false;

        return true;
    }
}
