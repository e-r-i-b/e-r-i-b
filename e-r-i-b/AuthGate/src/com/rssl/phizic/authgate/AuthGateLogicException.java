package com.rssl.phizic.authgate;

/**
 * @author Gainanov
 * @ created 07.08.2009
 * @ $Author$
 * @ $Revision$
 */
public class AuthGateLogicException extends Exception
{
	private AuthStatusType errCode;

	public AuthStatusType getErrCode()
	{
		return errCode;
	}

	public void setErrCode(AuthStatusType errCode)
	{
		this.errCode = errCode;
	}

	public AuthGateLogicException()
	{
		super();
	}

	public AuthGateLogicException(String message)
	{
		super(message);
	}

	public AuthGateLogicException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public AuthGateLogicException(Throwable cause)
	{
		super(cause);
	}


	public AuthGateLogicException(String message, AuthStatusType errCode)
	{

		super(message);
		this.errCode = errCode;
	}

	public AuthGateLogicException(String message, Throwable cause, AuthStatusType errCode)
	{
		super(message, cause);
		this.errCode = errCode;
	}

	public AuthGateLogicException(Throwable cause, AuthStatusType errCode)
	{
		super(cause);
		this.errCode = errCode;
	}
}
