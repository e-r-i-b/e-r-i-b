package com.rssl.phizic.auth;

import com.rssl.phizic.auth.passwordcards.*;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.crypto.CryptoService;
import com.rssl.phizic.security.config.SecurityFactory;
import com.rssl.phizic.security.password.*;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.classic.Session;
import org.hibernate.Transaction;

/**
 * Created by IntelliJ IDEA.
 * User: Evgrafov
 * Date: 01.09.2005
 * Time: 20:51:57
 */
public class CardPasswordValidator implements NamePasswordValidator
{
	private static final SecurityService     securityService       = new SecurityService();
	private static final CryptoService cryptoService = SecurityFactory.cryptoService();
	private static final PasswordCardService passwordCardService   = new PasswordCardService();
	private static final char                DELIMITER_CHAR        = '\n';
	private static final int                 PASSWORD_INDEX        = 0;
	private static final int                 PASSWORD_NUMBER_INDEX = 1;
	private static final int                 CARD_NUMBER_INDEX     = 2;

	/**
	 *  одирование информации о пароле
	 * @param password пароль
	 * @param passwordNumber номер парол€ в карте паролей
	 * @param cardNumber номер карты паролей
	 * @return кодированный пароль
	 */
	static public char[] codePasswordInfo ( String password, Integer passwordNumber, String cardNumber )
	{
		String passwordInfo = password+DELIMITER_CHAR+passwordNumber+DELIMITER_CHAR+cardNumber;
		return passwordInfo.toCharArray();
	}

	/**
	 * ƒекодирование парол€
	 * @param passwordInfo закодированный пароль см. codePasswordInfo
	 * @return массив[]{password, passwordNumber, cardNumber}
	 */
	private static String decodePassword ( char[] passwordInfo )
	{
		String[] strings = convertPasswordInfo2Strings(passwordInfo);
		return strings[PASSWORD_INDEX];
	}

	 // ƒекодирование номера парол€
	private static Integer decodePasswordNumber ( char[] passwordInfo )
	{
		String[] strings = convertPasswordInfo2Strings(passwordInfo);
		return Integer.decode(strings[PASSWORD_NUMBER_INDEX]);
	}

	// ƒекодирование номера карты
	private static String decodeCardNumber ( char[] passwordInfo )
	{
		String[] strings = convertPasswordInfo2Strings(passwordInfo);
		return strings[CARD_NUMBER_INDEX];
	}

	private static String[] convertPasswordInfo2Strings ( char[] passwordInfo )
	{
		String source = String.valueOf(passwordInfo);
		String delimiter = String.valueOf(DELIMITER_CHAR);
		return source.split(delimiter);
	}

	public CommonLogin validateLoginInfo ( final String userId, final char[] passwordInfo )
				throws SecurityLogicException
	{
		String  passwordString = decodePassword(passwordInfo);
		String  passwordHash = cryptoService.hash(passwordString);
		Integer passwordNumber = decodePasswordNumber(passwordInfo);
		String  cardNumber     = decodeCardNumber(passwordInfo);
		Login   login;



		try
		{
			login = securityService.getClientLogin(userId);

			//если осталс€ один ключ кидаем exception, дл€ активации новой карты
			PasswordCard card = passwordCardService.findByNumber(cardNumber);

			if (card.getValidPasswordsCount() == 1)
			{
				throw new LastOneCardPasswordException();
			}
		}
		catch (SecurityDbException e)
		{
			throw new SecurityException(e);
		}

		Session session = null;
		Transaction transaction = null;
		try
		{
			session = HibernateExecutor.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			new ValidateCardPasswordHibernateAction(login, cardNumber, passwordNumber, passwordHash).run(session);
			transaction.commit();
		}
		catch (SecurityDbException e)
		{
			if (transaction != null){
				transaction.rollback();
			}
			throw new SecurityException(e);
		}catch(PasswordCardPermanentBlockedException e){
			transaction.commit();
			throw e;
		}catch(PasswordCardWrongAttemptException e){
			transaction.commit();
			throw e;
		}
		catch (Exception e)
		{
			if (transaction != null){
				transaction.rollback();
			}
			throw new SecurityException(e);
		}finally{
			if (session != null){
				session.close();
			}
		}
		return login;
	}

}
