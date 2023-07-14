package com.rssl.phizic.auth.passwordcards;

import com.rssl.phizic.security.SecurityLogicException;

/**
 * @author Kidyaev
 * @ created 03.02.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 3384 $
 */
public class InvalidPasswordCardStateException extends SecurityLogicException
{
	private final PasswordCard card;
	private final String       newState;

	/**
	 * @param card - карта
	 * @param newState - новое состояние карты (константа из интерфейса {@link PasswordCard})
	 */
	public InvalidPasswordCardStateException(PasswordCard card, String newState)
	{
		this.card     = card;
		this.newState = newState;
	}

	public String getNewState()
	{
		return newState;
	}

	public PasswordCard getCard()
	{
		return card;
	}
}
