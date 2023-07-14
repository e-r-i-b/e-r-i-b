package com.rssl.auth.csa.back.exceptions;

import com.rssl.phizic.common.types.exceptions.LogicException;

/**
 * @author osminin
 * @ created 27.08.13
 * @ $Author$
 * @ $Revision$
 *
 * Ошибка аутентификации АТМ или регистрации пользователя - карта должна быть активная и основная
 */
public class CardNotActiveAndMainException extends LogicException
{
	/**
	 * конструктор
	 * @param message сообщение
	 */
	public CardNotActiveAndMainException(String message)
	{
		super(message);
	}
}
