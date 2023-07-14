package com.rssl.common.forms;

/**
 * @author Mescheryakova
 * @ created 27.05.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Исключение, кидающееся в хендлере,
 * если введенная сумма в предодобренном кредите больше подставленной по умолчанию суммы
 * В этом случае нужно сделать редирект на заполненную форму редактирования непредодобренного кредита
 */

public class ChangeCreditTypeRedirectDocumentLogicException extends RedirectDocumentLogicException
{

	/** @param message сообщение */
	public ChangeCreditTypeRedirectDocumentLogicException(String message)
	{
		super(message);
	}

	/** @param cause причина */
	public ChangeCreditTypeRedirectDocumentLogicException(Throwable cause)
	{
		super(cause == null ? null : cause.getMessage(), cause);
	}

	/**
	 * @param message сообщение
	 * @param cause   причина
	 */
	public ChangeCreditTypeRedirectDocumentLogicException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
