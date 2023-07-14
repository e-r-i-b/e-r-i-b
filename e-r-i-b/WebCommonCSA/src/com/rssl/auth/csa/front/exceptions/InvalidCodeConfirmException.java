package com.rssl.auth.csa.front.exceptions;

/**
 * Ошибка неправильно введенного кода подтверждения
 * @author niculichev
 * @ created 12.03.14
 * @ $Author$
 * @ $Revision$
 */
public class InvalidCodeConfirmException extends FrontLogicException
{
	private Long attempts;

	public InvalidCodeConfirmException(String message)
	{
		super(message);
	}

	public InvalidCodeConfirmException(String message, Long attempts)
	{
		super(message);
		this.attempts = attempts;
	}

	public InvalidCodeConfirmException(Throwable cause)
	{
		super(cause);
	}

	public InvalidCodeConfirmException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public Long getAttempts()
	{
		return attempts;
	}

	public String getStrAttempts()
	{
		if(attempts == null)
			return null;

		int n = attempts < 0 ? 0 : (int) (attempts % 10);
		switch (n)
		{
			case 0:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
				return attempts + " попыток";
			case 1:
				return attempts + " попытка";
			case 2:
			case 3:
			case 4:
				return attempts + " попытки";
			default:
				throw new IllegalStateException("Не удалось определить текстовку.");
		}
	}
}
