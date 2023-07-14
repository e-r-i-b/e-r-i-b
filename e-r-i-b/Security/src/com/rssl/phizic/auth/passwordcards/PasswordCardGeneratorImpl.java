package com.rssl.phizic.auth.passwordcards;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.security.SecureRandom;

/**
 * Created by IntelliJ IDEA.
 * User: Kidyaev
 * Date: 12.09.2005
 * Time: 15:36:11
 *  ласс дл€ создани€ таблицы одноразовых паролей
 */
public class PasswordCardGeneratorImpl implements PasswordCardGenerator
{
	private static final int    CARD_NUMBER_LENGTH = 8;
	private static final char[] ALLOWED_CHARS = "0123456789".toCharArray();
	private static final int    ALLOWED_CHARS_LENGTH = ALLOWED_CHARS.length;

	private int passwordCount;

	public PasswordCardGeneratorImpl()
    {
	    SecurityConfig securityConfig = ConfigFactory.getConfig(SecurityConfig.class);
	    this.passwordCount = securityConfig.getCardPasswords();
    }

    public void setPasswordCount(int passwordCount)
    {
        this.passwordCount = passwordCount;
    }

	/**
     * ћетод создает карточку с таблицей паролей дл€ переданного логина
     * @return ћассив паролей CardPasswords
     * @throws Exception
     */
    public PasswordCard generate() throws Exception
    {
        return HibernateExecutor.getInstance().execute(new HibernateAction<PasswordCard>()
        {
            public PasswordCard run(Session session) throws Exception
            {
	            return createCard(session);
            }
        }
        );
    }

	// —оздать и сохранить таблицу паролей
    private PasswordCardImpl createCard(Session session)
    {
	    int failures = 0;

	    PasswordCardImpl newCard;

	    while(true)
	    {
		    newCard = new PasswordCardImpl();
		    newCard.setNumber(createCardNumber());

			newCard.setIssueDate(DateHelper.getCurrentDate());
			newCard.setActivationDate(DateHelper.getCurrentDate());
			newCard.setState(PasswordCard.STATE_REQUESTED);
			newCard.setPasswordsCount((long) passwordCount);
			newCard.setValidPasswordsCount(newCard.getPasswordsCount());
			newCard.setWrongAttempts((long) 0 );

		    try
		    {
			    session.save(newCard);
			    session.flush();
			    break;
		    }
		    catch (ConstraintViolationException e)
		    {
			    failures++;
			    if(failures > 10)
				    throw new RuntimeException("Ќе удаетс€ создать карту паролей", e);

		    }
	    }
	    return newCard;
    }

	private String createCardNumber()
	{
		SecureRandom rnd = new SecureRandom();
		char[] number = new char[CARD_NUMBER_LENGTH - 1];
		for(int i = 0 ; i < CARD_NUMBER_LENGTH - 1; i++)
		{
			number[i] = ALLOWED_CHARS[rnd.nextInt(ALLOWED_CHARS_LENGTH)];
		}

		String value = String.valueOf(number);
		return value + StringHelper.calculateCheckDigit(value);
	}
}
