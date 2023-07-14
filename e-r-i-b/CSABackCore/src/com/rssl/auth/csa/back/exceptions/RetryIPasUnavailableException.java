package com.rssl.auth.csa.back.exceptions;

/**
 * @ author: Vagin
 * @ created: 17.04.2013
 * @ $Author
 * @ $Revision
 * Исключение, сигнализирующее о недоступности сервиса iPas(временная непредвиденая ошибка)
 */
public class RetryIPasUnavailableException extends ServiceUnavailableException
{
	private final String description;

	public RetryIPasUnavailableException(String description, Exception cause)
	{
		super(cause);
		this.description = description;
	}

	public RetryIPasUnavailableException(String description) {
		super(description);
		this.description = description;
	}

	/**
	 * @return описание ошибки для пользователя
	 */
	public String getDescription()
	{
		return description;
	}
}
