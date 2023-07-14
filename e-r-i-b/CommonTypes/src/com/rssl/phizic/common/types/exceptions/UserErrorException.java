package com.rssl.phizic.common.types.exceptions;

/**
 * @author Erkin
 * @ created 07.04.2013
 * @ $Author$
 * @ $Revision$
 */

import com.rssl.phizic.common.types.TextMessage;

/**
 * Исключение, вызванное неприемлемым действием пользователя.
 * Должно расцениваться как проблема, блокирующая дальнейшее выполнение текущей задачи.
 * Не предполагает сколько-нибудь содержательной обработки (т.е. необрабатываемое).
 * Должно быть перехвачено на "верхнем уровне" и выведено в журнал пользователя.
 * Для пользователя исключение выглядит как сообщение с объяснением причины ошибки
 * и (возможно) указанием по её исправлению.
 * Примеры:
 * - ошибка ввода (ошибка валидации)
 * - ошибка, готовая для показа пользователю (из внешней системы)
 */
public class UserErrorException extends RuntimeException
{
	private TextMessage textMessage;

	public UserErrorException(TextMessage textMessage)
	{
		super(textMessage.getText());
		this.textMessage = textMessage;
	}

	/**
	 * ctor
	 * @param textMessage
	 * @param cause
	 */
	public UserErrorException(TextMessage textMessage, Throwable cause)
	{
		super(textMessage.getText(), cause);
		this.textMessage = textMessage;
	}

	public UserErrorException(Throwable cause)
	{
		super(cause == null ? null : cause.getMessage(), cause);
		this.textMessage = new TextMessage(cause == null ? null : cause.getMessage());
	}

	public TextMessage getTextMessage()
	{
		return textMessage;
	}
}
