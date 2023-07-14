package com.rssl.phizic.errors;

/**
 * @author gladishev
 * @ created 15.11.2007
 * @ $Author$
 * @ $Revision$
 */

public class ErrorMessage
{
	private Long id;
	private String regExp;
	private String message;
	private ErrorType errorType;
	private ErrorSystem system;

	public void setId(long id)
	{
		this.id = id;
	}

	public Long getId()
    {
        return id;
    }

	public void setRegExp(String regExp)
	{
		this.regExp = regExp;
	}

	public String getRegExp()
	{
		return regExp;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getMessage()
	{
		return message;
	}

	public void setErrorType(ErrorType errorType)
	{
		this.errorType = errorType;
	}

	public void setErrorType(String errorType)
	{
		this.errorType = ErrorType.valueOf(errorType);
	}

	public ErrorType getErrorType()
	{
		return errorType;
	}

	public void setSystem(ErrorSystem system)
	{
		this.system = system;
	}

	public void setSystem(String system)
	{
		this.system = ErrorSystem.valueOf(system);
	}

	public ErrorSystem getSystem()
	{
		return system;
	}
}
