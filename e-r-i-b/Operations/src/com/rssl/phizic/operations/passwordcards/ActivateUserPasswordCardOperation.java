package com.rssl.phizic.operations.passwordcards;

import com.rssl.phizic.auth.passwordcards.InvalidPasswordCardStateException;
import com.rssl.phizic.auth.passwordcards.PasswordCard;
import com.rssl.phizic.auth.passwordcards.PasswordCardImpl;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.SecurityLogicException;

/**
 * @author Roshka
 * @ created 14.04.2006
 * @ $Author$
 * @ $Revision$
 */

public class ActivateUserPasswordCardOperation extends PasswordCardOperationBase
{
	private static final SimpleService simpleService = new SimpleService();
	/**
	 * Активировать карту
	 *
	 * @throws BusinessException
	 * @throws com.rssl.phizic.business.BusinessLogicException
	 * @throws com.rssl.phizic.auth.passwordcards.InvalidPasswordCardStateException
	 */
	@Transactional
	public void activate() throws BusinessException, BusinessLogicException, InvalidPasswordCardStateException
	{
		try
		{
			PasswordCardImpl activeCard = (PasswordCardImpl) passwordCardService.getActiveCard(getCard().getLogin());
			if ( activeCard != null )
			{
					activeCard.setState(PasswordCard.STATE_RESERVED);
					simpleService.update(activeCard);
			}
			passwordCardService.activate(getCard());
		}
		catch (InvalidPasswordCardStateException e)
		{
			throw e;
		}
		catch (SecurityLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}
	}

}