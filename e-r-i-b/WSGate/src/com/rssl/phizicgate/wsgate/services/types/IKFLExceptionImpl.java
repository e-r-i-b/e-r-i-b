package com.rssl.phizicgate.wsgate.services.types;

import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizicgate.wsgate.services.bankroll.generated.ErrorMessage;

/**
 * @ author: filimonova
 * @ created: 23.11.2010
 * @ $Author$
 * @ $Revision$
 */
public class IKFLExceptionImpl extends IKFLException
{
	protected java.lang.String accountNumber;
    protected java.lang.String cardNumber;
    protected Throwable cause;
    protected java.lang.String detailMessage;
    protected ErrorMessage errorMessage;
    protected java.lang.String message;

	public String getAccountNumber()
	{
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber)
	{
		this.accountNumber = accountNumber;
	}

	public String getCardNumber()
	{
		return cardNumber;
	}

	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	public java.lang.Throwable getCause()
	{
		return cause;
	}

	public void setCause(Throwable cause)
	{
		this.cause = cause;
	}

	public String getDetailMessage()
	{
		return detailMessage;
	}

	public void setDetailMessage(String detailMessage)
	{
		this.detailMessage = detailMessage;
	}

	public ErrorMessage getErrorMessage()
	{
		return errorMessage;
	}

	public void setErrorMessage(ErrorMessage errorMessage)
	{
		this.errorMessage = errorMessage;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public void setType(Class<? extends IKFLException> type)
	{
		this.type = type;
	}

	public void setType(String type)
	{
		try
		{
			setType((Class<? extends IKFLException>) Class.forName(type));
		}
		catch (ClassNotFoundException e)
		{
		}
	}
}
