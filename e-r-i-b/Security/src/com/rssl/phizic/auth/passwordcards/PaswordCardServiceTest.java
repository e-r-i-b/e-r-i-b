package com.rssl.phizic.auth.passwordcards;

import com.rssl.phizic.auth.CardPasswordValidator;
import com.rssl.phizic.auth.CheckLoginTest;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.security.config.SecurityFactory;
import com.rssl.phizic.security.crypto.CryptoService;
import com.rssl.phizic.security.test.SecurityTestBase;
import com.rssl.phizic.utils.test.annotation.ExcludeTest;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Evgrafov
 * @ created 22.12.2005
 * @ $Author: bogdanov $
 * @ $Revision: 57189 $
 */
@ExcludeTest(configurations = "sbrf")
public class PaswordCardServiceTest extends SecurityTestBase
{
	private static final String TEST_PWD = "BOOOO";

	public void testService() throws Exception
    {
        PasswordCardService service = new PasswordCardService();
        Login login = CheckLoginTest.getTestLogin();

        // prepare
        PasswordCard activeCard = service.getActiveCard(login);
        if(activeCard != null)
            service.forceRemove(activeCard);

        // создать
        PasswordCard[] cards = service.create(1, 5);
        PasswordCard card = cards[0];
        assertEquals(PasswordCard.STATE_REQUESTED, card.getState());
        assertEquals(card.getPasswordsCount(), card.getValidPasswordsCount());

	    List<CardPassword> cardPasswords = create5passwords();
	    service.addPasswordsAndMarkAsNew(card, cardPasswords);
	    assertEquals(PasswordCard.STATE_NEW, card.getState());

	    // назначить
        service.assign(login, card);
        assertEquals(PasswordCard.STATE_RESERVED, card.getState());

        // создать резервную
        PasswordCard reservedCard = service.create(1, 5)[0];
	    List<CardPassword> reservePasswords = create5passwords();
	    service.addPasswordsAndMarkAsNew(reservedCard, reservePasswords);
	    service.assign(login, reservedCard);

	    // активировать
        service.activate(card);
        assertEquals(PasswordCard.STATE_ACTIVE, card.getState());

        PasswordCard card2 = service.getActiveCard(login);
        assertNotNull(card2);
        assertEquals(card.getId(), card2.getId());

        // использовать
        List<CardPassword> list = service.getAllCardPaswords(card);

        CardPasswordValidator validator = new CardPasswordValidator();

	    Boolean isException=false;

        for (int i = 0; i < list.size(); i++)
        {
            CardPassword password = list.get(i);

            char[] passwordChars =
                    CardPasswordValidator.codePasswordInfo(TEST_PWD, password.getNumber(), card.getNumber());

            Integer nextUnusedCardPasswordNumber = service.getNextUnusedCardPasswordNumber(card);

//            assertEquals(i + 1, nextUnusedCardPasswordNumber.intValue());
			try
			{
                validator.validateLoginInfo(login.getUserId(), passwordChars);
			}
			catch(LastOneCardPasswordException ex)
			{   //когда остается последний пароль, должен прилететь exception
				assertTrue( i == list.size()-1  );
				isException = true;
			}
        }
	    //не прилетел exception, хотя все пароли должны быть использованы
	    assertTrue( isException  );

        // reload
        card = service.findById(card.getId());
	    service.markAsExhausted(card);
	    //так как мы не дали использовать последний ключ, то активируем след. карту в ручную
	    service.activate(reservedCard);
        PasswordCard newActiveCard = service.getActiveCard(login);
        assertNotNull(newActiveCard);

        service.forceRemove(card);
        service.forceRemove(newActiveCard);
    }

	private List<CardPassword> create5passwords()
	{
		CryptoService cryptoProvider = SecurityFactory.cryptoService();
		char[] chars = cryptoProvider.hash(TEST_PWD).toCharArray();

		List<CardPassword> cardPasswords = new ArrayList<CardPassword>();
		for(int i = 0; i < 5; i++)
		{
			CardPassword cardPassword = new CardPassword();
			cardPassword.setNumber(i + 1);
			cardPassword.setValue(chars);
			cardPasswords.add(cardPassword);
		}
		return cardPasswords;
	}

	public void _testCreate_Hard() throws Exception
    {
        final PasswordCardService service = new PasswordCardService();

        HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
        {
            public Void run(Session session) throws Exception
            {
                // создать
                PasswordCard[] cards = service.create(1000, 500);

                //удаление
                for (int i = 0; i < cards.length; i++)
                {
                    PasswordCard card = cards[i];
                    service.forceRemove(card);
                }
                return null;
            }
        });

    }

	public void testNegativeService() throws Exception
    {
        PasswordCardService service = new PasswordCardService();
        Login login = CheckLoginTest.getTestLogin();

        // prepare
        PasswordCard activeCard = service.getActiveCard(login);
        if(activeCard != null)
            service.forceRemove(activeCard);

        // создать
        PasswordCard[] cards = service.create(1, 5);
        PasswordCard card = cards[0];
        assertEquals(PasswordCard.STATE_REQUESTED, card.getState());
        assertEquals(card.getPasswordsCount(), card.getValidPasswordsCount());

	    List<CardPassword> cardPasswords = create5passwords();
	    service.addPasswordsAndMarkAsNew(card, cardPasswords);
	    assertEquals(PasswordCard.STATE_NEW, card.getState());

	    // назначить
        service.assign(login, card);
        assertEquals(PasswordCard.STATE_RESERVED, card.getState());

	    // активировать
        service.activate(card);
        assertEquals(PasswordCard.STATE_ACTIVE, card.getState());

        // использовать
        List<CardPassword> list = service.getAllCardPaswords(card);

        CardPasswordValidator validator = new CardPasswordValidator();

	    Boolean isPasswordCardPermanentBlockedException =false;

	    int attempts = ConfigFactory.getConfig(SecurityConfig.class).getConfirmAttempts();
	    for (int i = 0; i < attempts; i++)
        {
            CardPassword password = list.get(i);
	        String wrongPassword = "WRONG"+TEST_PWD;
            char[] passwordChars =
                    CardPasswordValidator.codePasswordInfo(wrongPassword, password.getNumber(), card.getNumber());
	        Boolean isException=false;
	        try{
                validator.validateLoginInfo(login.getUserId(), passwordChars);
	        } catch(PasswordCardWrongAttemptException e){
		        isException = true;
	        } catch (PasswordCardPermanentBlockedException e){
		        isPasswordCardPermanentBlockedException =true;
		        assertTrue( i!= attempts );
		        isException = true;
	        }
	        //не прилетел exception, хотя пароль заведомо неверен
	        assertTrue( isException  );
        }
	    //не прилетел exception о блокировке карты.
	    assertTrue(isPasswordCardPermanentBlockedException);

        // reload
        card = service.findById(card.getId());
	    //так как мы не дали использовать последний ключ, то активируем след. карту в ручную
	    service.forceRemove(card);
    }
}
