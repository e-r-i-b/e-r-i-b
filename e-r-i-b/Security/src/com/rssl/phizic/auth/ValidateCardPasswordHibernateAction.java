package com.rssl.phizic.auth;

import com.rssl.phizic.auth.passwordcards.*;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.config.SecurityConfig;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Evgrafov
* @ created 03.01.2007
* @ $Author: bogdanov $
* @ $Revision: 57189 $
*/
class ValidateCardPasswordHibernateAction implements HibernateAction<Void>
{
	private static PasswordCardService passwordCardService = new PasswordCardService();
	private final Login   login;
	private final String  cardNumber;
	private final Integer passwordNumber;
	private final String  passwordHash;

	ValidateCardPasswordHibernateAction(Login login, String cardNumber, Integer passwordNumber, String passwordHash)
	{
		this.login          = login;
		this.cardNumber     = cardNumber;
		this.passwordNumber = passwordNumber;
		this.passwordHash   = passwordHash;
	}

	public Void run ( Session session ) throws Exception
	{
		PasswordCardImpl card = (PasswordCardImpl) passwordCardService
				.getActiveCard(login);

		if (card==null)
		{
			throw new SecurityLogicException("Нет активной карты ключей.");
		}
		Query query1 = session.getNamedQuery("com.rssl.phizic.auth.passwordcards.getByNubmer");
		PasswordCardImpl activeCard =(PasswordCardImpl) query1.setParameter("number", card.getNumber()).uniqueResult();
		if (!cardNumber.equals(card.getNumber()))
			throw  new SecurityLogicException("Неверная карта паролей");

		Query query = session
			.getNamedQuery("com.rssl.phizic.auth.passwordcards.getPassword")
			.setParameter("card", activeCard)
			.setParameter("number", passwordNumber);

		CardPassword password = (CardPassword)query.uniqueResult();

		if (password == null)
			throw  new SecurityLogicException("Не указан пароль");

		if (!password.isValid())
			throw  new SecurityLogicException("Пароль уже использован");

		if (password.getStringValue().equals(passwordHash))
		{
			password.setUsed();

			if (activeCard.getValidPasswordsCount() ==0)
			{
				passwordCardService.markAsExhausted(activeCard);
			}

			activeCard.setWrongAttempts((long) 0);

			session.update(password);
			session.update(activeCard);

			return null;
		}
		else
		{
			long wrongAttempts = activeCard.getWrongAttempts() +1;
			long limitAttempts = ConfigFactory.getConfig(SecurityConfig.class).getConfirmAttempts();
			if (wrongAttempts >= limitAttempts)
			{
				activeCard.setState( PasswordCard.STATE_BLOCKED );
				activeCard.setBlockType( PasswordCard.BLOCK_AUTO );
				session.update(activeCard);
				throw  new PasswordCardPermanentBlockedException();
			}
			else
			{
				activeCard.setWrongAttempts(wrongAttempts);
				session.update(activeCard);
				throw  new PasswordCardWrongAttemptException(limitAttempts-wrongAttempts);
			}
		}
	}
}