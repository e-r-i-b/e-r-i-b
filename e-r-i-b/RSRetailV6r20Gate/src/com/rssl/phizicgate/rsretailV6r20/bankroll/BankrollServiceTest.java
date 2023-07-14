package com.rssl.phizicgate.rsretailV6r20.bankroll;

import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.*;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizicgate.rsretailV6r20.junit.RSRetailV6r20GateTestCaseBase;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author Danilov
 * @ created 03.10.2007
 * @ $Author$
 * @ $Revision$
 */
public class BankrollServiceTest extends RSRetailV6r20GateTestCaseBase
{
    private static final Long   CLIENT_ID                          = 32323L;
	private static final String EXTERNAL_CLIENT_ID                 = CLIENT_ID.toString();
	private static final Long   CARD_ID                            = 9L;
	private static final String EXTERNAL_CARD_ID                   = CLIENT_ID + String.valueOf(CardImpl.ID_SEPARATOR) + CARD_ID;
	private static final int    YEAR                               = 2007;
    private static final String EXTERNAL_CARD_ID_WITH_TRANSACTIONS = EXTERNAL_CARD_ID;

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

    public void testGetCardPrimaryAccount() throws GateException
    {
        try
        {
	        BankrollServiceImpl service  = new BankrollServiceImpl(gateFactory);
			Card card = GroupResultHelper.getOneResult(service.getCard(EXTERNAL_CARD_ID));
	        Account cardPrimaryAccount = GroupResultHelper.getOneResult(service.getCardPrimaryAccount(card));
			assertNotNull(cardPrimaryAccount);
        }catch (IKFLException e)
        {
	        throw new GateException(e);
        }
    }

	public void testGetAccount() throws GateLogicException, GateException
	{
		BankrollServiceImpl service  = new BankrollServiceImpl(gateFactory);
		GroupResult<String, Account> account = service.getAccount("10000012");
		assertNotNull(account.getResult("10000012"));
	}
    public void testGetClientAccounts() throws IKFLException
    {
        BankrollServiceImpl service  = new BankrollServiceImpl(gateFactory);
	    ClientService clientService = GateSingleton.getFactory().service(ClientService.class);

        List<Account> accounts = service.getClientAccounts(clientService.getClientById(EXTERNAL_CLIENT_ID));

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
	    /*BackRefBankrollService backRefBankrollService = GateSingleton.getFactory().service(BackRefBankrollService .class);
	    String accountExternalId = backRefBankrollService.findAccountExternalId(Long.valueOf(clientId), account1.getNumber());
	    AccountImpl account3 = new AccountService().getAccount(Long.decode(accountExternalId));
	    assertNotNull("account by number = null", account3);
	    assertTrue("account FNCach = null",account3.getBranch()!=0);*/
    }

	public void testGetAccountAbstract() throws GateException, GateLogicException
	{
		BankrollServiceImpl service  = new BankrollServiceImpl(gateFactory);

		Account account = service.getAccountByNumber("4230181067702000039001");

	    // Получение выписки
        AccountAbstract accountAbstract = service.getAccountExtendedAbstract(account, new GregorianCalendar(YEAR, 1, 1), new GregorianCalendar() );
        assertNotNull("accountAbstract = null", accountAbstract);
	}

	public void testGetClientCards() throws GateException, GateLogicException
    {
        BankrollService service  = new BankrollServiceImpl(gateFactory);
	    ClientService clientService = GateSingleton.getFactory().service(ClientService.class);
        List<Card>      cards    = service.getClientCards(clientService.getClientById(EXTERNAL_CLIENT_ID));

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
        catch (SystemException e)
        {
	        throw new GateException(e);
        }
	    catch (LogicException e)
	    {
		    throw new GateLogicException(e);
	    }
    }

    public void testGetOwnerInfo() throws Exception
    {
        BankrollService service  = new BankrollServiceImpl(gateFactory);
	    Card card = GroupResultHelper.getOneResult(service.getCard(EXTERNAL_CARD_ID));
	    Client client = GroupResultHelper.getOneResult(service.getOwnerInfo(card));
	    assertNotNull(client);
    }

	public void testGetCardInfo() throws Exception
    {
        BankrollServiceImpl service  = new BankrollServiceImpl(gateFactory);
        Card card = GroupResultHelper.getOneResult(service.getCard(EXTERNAL_CARD_ID));
        CardInfo cardInfo = GroupResultHelper.getOneResult(service.getCardInfo(card));
	    assertNotNull(cardInfo);
    }

	public void testGetCardOverdraftInfo() throws GateException
    {
        try{
			BankrollServiceImpl service  = new BankrollServiceImpl(gateFactory);
			Card card = GroupResultHelper.getOneResult(service.getCard(EXTERNAL_CARD_ID));
			OverdraftInfo overdraftInfo = GroupResultHelper.getOneResult(service.getCardOverdraftInfo(Calendar.getInstance(), card));

			assertNotNull(overdraftInfo);
			assertNotNull(overdraftInfo.getCurrentOverdraftSum());
        }catch (IKFLException e)
        {
	        throw new GateException(e);
        }
    }

    public void testGetCardAbstract() throws IKFLException
    {
	    BankrollServiceImpl service  = new BankrollServiceImpl(gateFactory);
        Card                card     = GroupResultHelper.getOneResult(service.getCard(EXTERNAL_CARD_ID_WITH_TRANSACTIONS));

        assertNotNull(card);

        CardAbstract cardAbstract = (CardAbstract)service.getAbstract(card, new GregorianCalendar(YEAR, 1, 1), new GregorianCalendar());

        assertNotNull(cardAbstract);
        assertNotNull(cardAbstract.getOpeningBalance());
        assertNotNull(cardAbstract.getClosingBalance());
        assertNotNull(cardAbstract.getTransactions());

	    List<CardOperation> cardUnsettledOperations = cardAbstract.getUnsettledOperations();
	    for (CardOperation cardUnsettledOperation : cardUnsettledOperations)
	    {
		    assertNotNull(cardUnsettledOperation.getOperationCard());
        }
    }
}
