package com.rssl.phizicgate.rsV51.bankroll;

import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.*;
import com.rssl.phizic.gate.clients.ClientService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizicgate.rsV51.data.GateRSV51Executor;
import com.rssl.phizicgate.rsV51.test.RSRetaileGateTestCaselBase;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author Kidyaev
 * @ created 11.10.2005
 * @ $Author: jatsky $
 * @ $Revision:4994 $
 */

@SuppressWarnings({"JavaDoc"})
public class BankrollServiceTest extends RSRetaileGateTestCaselBase
{
    private static final Long   CLIENT_ID                          = 1774L;
	private static final String EXTERNAL_CLIENT_ID                 = CLIENT_ID.toString();
	private static final Long   CARD_ID                            = 2L;
	private static final String EXTERNAL_CARD_ID                   = CLIENT_ID + String.valueOf(CardImpl.ID_SEPARATOR) + CARD_ID;
    private static final String EXTERNAL_CARD_ID_WITH_TRANSACTIONS = EXTERNAL_CARD_ID;
	private static final int    YEAR                               = 2000;
	private static final int    Z_STRING_LENGTH                    = 130;
	private static final ClientService clientService = GateSingleton.getFactory().service(ClientService.class);

	public BankrollServiceTest(String string) throws GateException
    {
        super(string);
    }

    public void testGetClientAccounts() throws IKFLException
    {
        BankrollServiceImpl service  = new BankrollServiceImpl(gateFactory);
        String          clientId = EXTERNAL_CLIENT_ID;
        List<Account>   accounts = service.getClientAccounts(clientService.getClientById(clientId));

        assertNotNull("accounts = null", accounts);
        assertTrue("accounts.size() = 0", accounts.size() > 0);

        Account account1 = accounts.get(0);
        Account account2 = GroupResultHelper.getOneResult(service.getAccount(account1.getId()));

        assertEquals("account1 != account2", account1, account2);

        AccountInfo accountInfo = GroupResultHelper.getOneResult(service.getAccountInfo(account1));
        assertNotNull("accountInfo = null", accountInfo);

	    AccountInfo accountInfo2 = GroupResultHelper.getOneResult(service.getAccountInfo(account2));
	    assertNotNull("accountInfo2 = null", accountInfo2);

	    // Получение выписки
        AccountAbstract accountAbstract = (AccountAbstract)service.getAbstract( accounts.get(3), new GregorianCalendar(YEAR, 1, 1), new GregorianCalendar() );
        assertNotNull("accountAbstract = null", accountAbstract);
        assertNotNull("accountAbstract.getOpeningBalance()", accountAbstract.getOpeningBalance());
        assertNotNull("accountAbstract.getClosingBalance()", accountAbstract.getClosingBalance());
        assertNotNull("accountAbstract.getAccountTransactions()", accountAbstract.getTransactions());
        assertTrue("accountAbstract.getAccountTransactions().size() = 0", accountAbstract.getTransactions().size() > 0 );

	    //Получение счета клиента по номеру
	    AccountImpl account3 = service.getClientAccountByNumber(account1.getNumber(),clientId);
	    assertNotNull("account by number = null", account3);
	    assertTrue("account FNCach = null",account3.getBranch()!=0);

    }

    public void testGetClientCards() throws GateException, GateLogicException
    {
        BankrollService service  = new BankrollServiceImpl(gateFactory);
        String          clientId = EXTERNAL_CLIENT_ID;
        List<Card>      cards    = service.getClientCards(clientService.getClientById(clientId));

        assertNotNull(cards);
        assertTrue(cards.size() > 0);
	    for (Card card : cards)
	    {
		    assertNotNull(card.getCardType());
	    }

        Card card1 = cards.get(0);
        try
        {
            Card card2 = GroupResultHelper.getOneResult(service.getCard(card1.getId()));
	        assertEquals(card1, card2);
        }
        catch (LogicException e)
        {
	        throw new GateLogicException(e);
        }
        catch (SystemException e)
        {
	        throw new GateException(e);
        }
    }

    public void testGetCardCurrency() throws GateException
    {
        BankrollServiceImpl service  = new BankrollServiceImpl(gateFactory);
        service.getCardCurrency(CARD_ID);
    }

    public void testGetCardInfo() throws GateException, GateLogicException
    {
        BankrollServiceImpl service  = new BankrollServiceImpl(gateFactory);
        try{
	        Card card = GroupResultHelper.getOneResult(service.getCard(EXTERNAL_CARD_ID));
			CardInfo cardInfo = GroupResultHelper.getOneResult(service.getCardInfo(card));

			assertNotNull(cardInfo);
			assertNotNull(cardInfo.getHoldSum());
        }
        catch (LogicException e)
        {
	        throw new GateLogicException(e);
        }
        catch (SystemException e)
        {
	        throw new GateException(e);
        }
    }


    public void testGetCardAbstract() throws GateLogicException, GateException
    {
        BankrollServiceImpl service  = new BankrollServiceImpl(gateFactory);
        try{
	        Card                card     = GroupResultHelper.getOneResult(service.getCard(EXTERNAL_CARD_ID_WITH_TRANSACTIONS));
			assertNotNull(card);

			CardAbstract cardAbstract = (CardAbstract)service.getAbstract(card, new GregorianCalendar(YEAR, 1, 1), new GregorianCalendar());

			assertNotNull(cardAbstract);
			assertNotNull(cardAbstract.getOpeningBalance());
			assertNotNull(cardAbstract.getClosingBalance());
			assertNotNull(cardAbstract.getTransactions());
        }
        catch (LogicException e)
        {
	        throw new GateLogicException(e);
        }
        catch (SystemException e)
        {
	        throw new GateException(e);
        }

    }

    public void testGetCardPrimaryAccount() throws GateException, GateLogicException
    {
        BankrollServiceImpl service  = new BankrollServiceImpl(gateFactory);
        try{
	        Card card = GroupResultHelper.getOneResult(service.getCard(EXTERNAL_CARD_ID));
	        Account cardPrimaryAccount = GroupResultHelper.getOneResult(service.getCardPrimaryAccount(card));
		    assertNotNull(cardPrimaryAccount);
        }
        catch (LogicException e)
        {
	        throw new GateLogicException(e);
        }
        catch (SystemException e)
        {
	        throw new GateException(e);
        }

    }

	public void testGetAdditionalCards() throws GateException
    {
        try{
	        BankrollServiceImpl service  = new BankrollServiceImpl(gateFactory);
			Card mainCard = GroupResultHelper.getOneResult(service.getCard(EXTERNAL_CARD_ID));
			List<Card> cards= GroupResultHelper.getOneResult(service.getAdditionalCards(mainCard));
			assertNotNull(cards);
			for (Card card : cards)
			{
				assertFalse(card.isMain());
				assertNotNull(card.getCardType());
	        }
        }catch (IKFLException e)
        {
	        throw new GateException(e);
        }
    }

    public void testGetZStringsFromPervasive() throws GateException
    {
        try
        {
            List<String> zStrings = GateRSV51Executor.getInstance().execute(new HibernateAction<List<String>>()
            {
                public List<String> run(Session session) throws Exception
                {
                    Query query    = session.getNamedQuery("GetZStrings");
	                //noinspection unchecked
	                List<String>  zStrings = query.list();

                    return (zStrings != null  ?  zStrings  :  new ArrayList<String>());
                }
            });

            assertTrue( zStrings.size() > 0 );
            assertTrue((zStrings.get(0)).length() == Z_STRING_LENGTH);

        }
        catch (Exception e)
        {
           throw new GateException(e);
        }
    }

	public void testIdComposeParse()
	{
		long   ownerId_1 = Long.parseLong("32");
		long   cardId_1  = Long.parseLong("23");
		String externalId = CardImpl.composeExternalId(ownerId_1, cardId_1);

		assertEquals( "32*23", externalId );
		assertEquals(ownerId_1, CardImpl.parseOwnerId(externalId) );
		assertEquals(cardId_1, CardImpl.parseCardId(externalId) );


		long   ownerId_2    = Long.parseLong("3333333333");
		long   cardId_2     = Long.parseLong("4444444444");
		String newExternalId = CardImpl.composeExternalId(ownerId_2, cardId_2);

		assertEquals( "3333333333*4444444444", newExternalId );
		assertEquals(ownerId_2, CardImpl.parseOwnerId(newExternalId) );
		assertEquals(cardId_2, CardImpl.parseCardId(newExternalId) );
	}
}


