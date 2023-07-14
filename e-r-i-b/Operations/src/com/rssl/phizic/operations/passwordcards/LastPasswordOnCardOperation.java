package com.rssl.phizic.operations.passwordcards;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.auth.passwordcards.*;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.passwords.InvalidNewCardNumberException;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.crypto.CryptoService;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.security.config.SecurityFactory;
import com.rssl.phizic.security.password.PermanentBlockedException;
import com.rssl.phizic.security.password.WrongAttemptException;

/**
 * @author Gainanov
 * @ created 20.05.2008
 * @ $Author$
 * @ $Revision$
 */
public class LastPasswordOnCardOperation extends OperationBase
{
	private static PasswordCardService passwordCardService = new PasswordCardService();
	private static SimpleService simpleService = new SimpleService();
	private Login login;
	private AuthenticationContext context;
	private PasswordCardImpl currentCard;

	public void initialize(AuthenticationContext context) throws SecurityDbException
	{
		this.context = context;
		this.login = (Login) context.getLogin();
		currentCard = (PasswordCardImpl) passwordCardService.getActiveCard(login);
	}

	public Integer getNextUnusedPasswordNumber() throws BusinessException
	{
		try
		{
			return passwordCardService.getNextUnusedCardPasswordNumber(currentCard);
		}
		catch (SecurityDbException ex)
		{
			throw new BusinessException(ex);
		}
	}

	public String getActiveCardNumber() throws BusinessException
	{
		return currentCard.getNumber();
	}

	public PasswordCard findCardByNumber(String newCardNumber) throws BusinessException
	{
		try
		{
			return passwordCardService.findByNumber(newCardNumber);
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}
	}

	public PasswordCard getCurrentCard() throws SecurityDbException
	{
		return passwordCardService.getActiveCard(login);
	}

	public void activateNewCard(String password, String newCardNumber) throws BusinessException,
			InvalidNewCardNumberException, SecurityLogicException
	{
		if (currentCard == null)
		{
			throw new NoActivePasswordCardException();
		}

		PasswordCard newCard = findCardByNumber(newCardNumber);
		if (newCard == null)
		{
			throw new InvalidNewCardNumberException();
		}

		checkCardOwner(newCard);
		checkPassword(password);

		try
		{
			passwordCardService.markAsExhausted(currentCard);
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}
		currentCard.setWrongAttempts(0L);
		simpleService.update(currentCard);
		active(newCard);
	}

	private void checkCardOwner(PasswordCard newCard) throws InvalidNewCardNumberException
	{
		SecurityConfig securityConfig = ConfigFactory.getConfig(SecurityConfig.class);
		if (securityConfig.isCardPasswordAutoAssign())
		{
			if (newCard.getLogin() != null && !newCard.getLogin().equals(login))
			{
				throw new InvalidNewCardNumberException();
			}
		}
		else
		{
			if (newCard.getLogin() == null || !newCard.getLogin().equals(login))
			{
				throw new InvalidNewCardNumberException();
			}
		}
	}

	private void checkPassword(String password) throws BusinessException, SecurityLogicException
	{
		//получаем след. не использованный пароль
		CardPassword passwordFromCard = passwordCardService.getCardPasswordByNumber(context.getPasswordNumber(), currentCard);
		if (passwordFromCard == null || !passwordFromCard.isValid())
		{
			throw new BusinessException("Не найден текущий пароль на карточке паролей");
		}

		CryptoService cryptoService = SecurityFactory.cryptoService();
		if (!passwordFromCard.getStringValue().equals(cryptoService.hash(password)))
		{
			long wrongAttempts = currentCard.getWrongAttempts() + 1;
			long limitAttempts = ConfigFactory.getConfig(SecurityConfig.class).getConfirmAttempts();
			if (wrongAttempts >= limitAttempts)
			{
				currentCard.setBlockType(PasswordCard.BLOCK_AUTO);
				try
				{
					passwordCardService.lock(currentCard);
				}
				catch (SecurityDbException e)
				{
					throw new BusinessException(e);
				}
				throw new PermanentBlockedException();
			}
			currentCard.setWrongAttempts(wrongAttempts);
			simpleService.update(currentCard);
			throw new WrongAttemptException(limitAttempts - wrongAttempts);
		}
		passwordFromCard.setUsed();
		simpleService.update(passwordFromCard);
	}

	public void activateNewCard(String newCardNumber) throws BusinessException, InvalidNewCardNumberException, SecurityLogicException
	{
		PasswordCard newCard = findCardByNumber(newCardNumber);
		if (newCard == null)
		{
			throw new InvalidNewCardNumberException();
		}
		checkCardOwner(newCard);
		active(newCard);
	}

	private void active(PasswordCard newCard) throws SecurityLogicException, BusinessException
	{
		try
		{
			if (newCard.getLogin() == null)
			{
				passwordCardService.assign(login, newCard);
			}
			passwordCardService.activate(newCard);
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}
	}
}
