package com.rssl.phizicgate.rsV51.test;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.config.GateConfig;
import com.rssl.phizic.gate.bankroll.*;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.rsV51.bankroll.BankrollServiceImpl;
import com.rssl.phizicgate.rsV51.clients.ClientServiceImpl;

import java.util.*;

/**
 * Нагрузочный тест для проверки работы шлюза
 * @author Kidyaev
 * @ created 21.10.2005
 * @ $Author: gladishev $
 * @ $Revision:2418 $
 */
public class LoadingTest extends RSRetaileGateTestCaselBase
{
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Общие настройки /////////////////////////////////////////////////////////////////////////////////////////////////

    // Максимальное кол-во клиентов
    private static final int     MAX_CLIENTS_COUNT  = 100;
    // Максимальное кол-во счетов у клиента
    private static final int     MAX_ACCOUNTS_COUNT = 100;
    // Максимальное кол-во карт у клиента
    private static final int     MAX_CARDS_COUNT    = 100;
    // Вывод информации на экран
    private static final boolean isLoging           = false;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Настройки для testFullClientsInfo() /////////////////////////////////////////////////////////////////////////////

    // Кол-во итераций (Выбрать всех клиентов, для каждого получить все счета и карты и т.д )
    private final int ITERATION_COUNT = 0;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Настройки для testClientsWorkImitation() ////////////////////////////////////////////////////////////////////////

    // Кол-во потоков
    private final int THREAD_COUNT = 10;
    // Время работы теста (часы)
    private final int WORK_HOURS   = 0;
    // Время работы теста (минуты)
    private final int WORK_MINUTES = 0;
    // Время работы теста (секунды)
    private final int WORK_SECONDS = 10;

    private ClientService   clientServise   = TestGateFactory.instance().service(ClientService.class);
    private BankrollService bankrollService = TestGateFactory.instance().service(BankrollService.class);

    private Calendar        fromDate        = new GregorianCalendar(2000, 0, 0); // Начальная дата интервала для получения выписок
    private Calendar        toDate          = new GregorianCalendar();           // Конечная дата интервала для получения выписок
    private Calendar        endWorkTime     = new GregorianCalendar();           // Время завершения работы теста

    private volatile boolean isEndWork = false;                                  // Флаг для завершения работы потоков

	private static class TestGateFactory implements GateFactory
	{
		private ClientService   clientServise;
		private BankrollService bankrollService;

		/** Конфигурация фабрики служб */
		public void initialize() throws GateException
		{
			clientServise = new ClientServiceImpl(this);
			bankrollService = new BankrollServiceImpl(this);
		}

		@SuppressWarnings({"unchecked"})
		public <S extends Service> S service(Class<S> serviceInterface)
		{
			if(serviceInterface.equals(ClientService.class))
				return (S) clientServise;

			if (serviceInterface.equals(BankrollService.class))
				return (S) bankrollService;

			throw new RuntimeException();
		}

		public <C extends GateConfig> C config(Class<C> configInterface)
		{
			throw new UnsupportedOperationException();
		}

		public Collection<? extends Service> services()
		{
			throw new UnsupportedOperationException();
		}

		public static GateFactory instance()
		{
			try
			{
				TestGateFactory factory = new TestGateFactory();
				factory.initialize();
				return factory;
			}
			catch (GateException e)
			{
				throw new RuntimeException(e);
			}
		}
	}

    /**
     * Класс, имитирующий работу пользователя
     */
    private class ClientThread implements Runnable
    {
        private Client client;

        // Состояния
        private static final int MAIN_PAGE_STATE        = 0;
        private static final int ACCOUNTS_STATE         = 1;
        private static final int CARDS_STATE            = 2;
        private static final int ACCOUNT_ABSTRACT_STATE = 3;
        private static final int CARD_ABSTRACT_STATE    = 4;

        // Правила перехода
        private Map<Integer,int[]> transitions  = new HashMap<Integer, int[]>();

        // Описания состояний для вывода на экран
        private Map<Integer,String> descriptions = new HashMap<Integer, String>();

        ClientThread(Client client)
        {
            this.client = client;

            transitions.put( new Integer(MAIN_PAGE_STATE),        new int [] {ACCOUNTS_STATE, CARDS_STATE}             );
            transitions.put( new Integer(ACCOUNTS_STATE),         new int [] {MAIN_PAGE_STATE, ACCOUNT_ABSTRACT_STATE} );
            transitions.put( new Integer(CARDS_STATE),            new int [] {MAIN_PAGE_STATE, CARD_ABSTRACT_STATE}    );
            transitions.put( new Integer(ACCOUNT_ABSTRACT_STATE), new int [] {MAIN_PAGE_STATE, ACCOUNTS_STATE}         );
            transitions.put( new Integer(CARD_ABSTRACT_STATE),    new int [] {MAIN_PAGE_STATE, CARDS_STATE}            );

            descriptions.put( new Integer(MAIN_PAGE_STATE),        "Главная страница");
            descriptions.put( new Integer(ACCOUNTS_STATE),         "Счета"           );
            descriptions.put( new Integer(CARDS_STATE),            "Карты"           );
            descriptions.put( new Integer(ACCOUNT_ABSTRACT_STATE), "Выписка по счету");
            descriptions.put( new Integer(CARD_ABSTRACT_STATE),    "Выписка по карте");

        }

        public void run()
        {
            List     accounts        = null;
            List     cards           = null;
            Account  currentAccount  = null;
            Card     currentCard     = null;
            int      state           = MAIN_PAGE_STATE;

            log("Thread started.");

            while ( !isEndWork )
            {
                log( descriptions.get(new Integer(state)) );

                try
                {
                    switch ( state )
                    {
                        case MAIN_PAGE_STATE:
                            break;

                        case ACCOUNTS_STATE:
                            accounts = getAccounts(client);
                            initializeAccounts(accounts);
                            break;

                        case CARDS_STATE:
                            cards = getCards(client);
                            initializeCards(cards);
                            break;

                        case ACCOUNT_ABSTRACT_STATE:
                            if ( accounts.size() != 0 )
                            {
                                currentAccount = (Account) getRandomItem(accounts);
                                getRandomAccountAbstract(currentAccount);
                            }
                            break;

                        case CARD_ABSTRACT_STATE:
                            if ( cards.size() != 0 )
                            {
                                currentCard = (Card) getRandomItem(cards);
                                getRandomCardAbstract(currentCard);
                            }
                            break;

                        default:
                            String message = "ERROR in thread(" + Thread.currentThread().getName() + "): Неизвестное состояние (" + state + ")";
                            throw new GateException( message );
                    } // switch ( state )

                    state = getNextRandomState(state);
                }
                catch ( GateException ex )
                {
                    System.err.println( ex.getMessage() );
                    System.err.println( ex.getStackTrace() );

                    break;
                }
                catch ( GateLogicException ex )
                {
                    System.err.println( ex.getMessage() );
                    System.err.println( ex.getStackTrace() );

                    break;
                }
            }  // while ( operationsCount++ < OPERATION_COUNT  && !isEndWork )

            log("Thread terminated.");
        }

        private int getNextRandomState(int currentState)
        {
            int [] states = transitions.get(new Integer(currentState));
            return states[ (new Random()).nextInt( states.length ) ];
        }

        private Object getRandomItem(List items)
        {
            return items.get( (new Random()).nextInt(items.size()) );
        }
    }


    public LoadingTest(String string) throws GateException
    {
        super(string);
    }

    protected void setUp() throws Exception
    {
        super.setUp();

        endWorkTime.add(Calendar.HOUR_OF_DAY, WORK_HOURS);
        endWorkTime.add(Calendar.MINUTE     , WORK_MINUTES);
        endWorkTime.add(Calendar.SECOND     , WORK_SECONDS);
    }

    public void testFullClientsInfo() throws GateException, GateLogicException
    {
        for ( int  i = 0; i < ITERATION_COUNT; i++ )
        {
            getFullClientsInfo();
        }
    }

    public void testClientsWorkImitation() throws GateException, GateLogicException
    {
        if ( THREAD_COUNT == 0 )
            return;

        List         clients = getClients();
        ListIterator it      = clients.listIterator();

        if ( !it.hasNext() )
        {
            System.out.println("Некорректные данные в БД: нет ни одного клиента");
            return;
        }

        Thread childThreads [] = new Thread [THREAD_COUNT];

	    int duplicateNumber = 0;
        for ( int  i = 0; i < THREAD_COUNT; i++ )
        {
            if ( !it.hasNext() )
            {
                it = clients.listIterator();
                duplicateNumber++;
            }

            Client client   = (Client) it.next();
            childThreads[i] = new Thread(new ClientThread(client), duplicateNumber + ", " + client.getId() + ", " + client.getFullName());

            childThreads[i].start();
        }

        try
        {
            while ( endWorkTime.after(new GregorianCalendar()) )
            {
                Thread.sleep(10000);
            }

            isEndWork = true;

            for ( int  i = 0; i < THREAD_COUNT; i++ )
            {
                childThreads[i].join();
            }
        }
        catch (InterruptedException e)
        {
            System.err.println( e.getMessage() );
            e.printStackTrace();
        }
    }

    private void getFullClientsInfo() throws GateException, GateLogicException
    {
        List clients = getClients();

        for ( ListIterator clIt = clients.listIterator(); clIt.hasNext(); )
        {
            Client client = (Client) clIt.next();

            try
            {
                List accounts = getAccounts(client);
                initializeAccounts(accounts);
                getAccountsAbstracts(accounts);

                List cards = getCards(client);
                initializeCards(cards);
                getCardsAbstracts(cards);
            }
            catch ( Exception ex )
            {
                System.err.println( "Некорректные данные в БД для: (ID: " + client.getId() + ") " + client.getFullName() );
            }
        }
    }

    private List getCards(Client client) throws GateException, GateLogicException
    {
        List cards = bankrollService.getClientCards(client);
        return cards.subList( 0, Math.min( cards.size(), MAX_CARDS_COUNT) );
    }

    private List<Account> getAccounts(Client client) throws GateException, GateLogicException
    {
        List<Account> accounts = bankrollService.getClientAccounts(client);
        return accounts.subList( 0, Math.min(accounts.size(), MAX_ACCOUNTS_COUNT) );
    }

    private List<Client> getClients() throws GateException, GateLogicException
    {
        List<Client> clients = clientServise.getClientsByTemplate(null, null, 0, 0);
        return clients.subList( 0, Math.min(clients.size(), MAX_CLIENTS_COUNT) );
    }

    private void initializeAccounts(List<Account> accounts) throws GateException, GateLogicException
    {
	    for (Account account : accounts)
	    {
		    bankrollService.getAccountInfo(account);
	    }
    }

    private void getAccountsAbstracts(List<Account> accounts) throws GateLogicException, GateException
    {
	    for (Account account : accounts)
	    {
		    getRandomAccountAbstract(account);
	    }
    }

    private AccountAbstract getRandomAccountAbstract(Account account) throws GateLogicException, GateException
    {
        Calendar fromLocalDate = generateDate(fromDate, toDate);
        Calendar toLocalDate   = generateDate(fromLocalDate, toDate);

        return  (AccountAbstract)bankrollService.getAbstract( account, fromLocalDate, toLocalDate);
    }

    private void initializeCards(List<Card> cards) throws GateException, GateLogicException
    {
	    for (Card card : cards)
	    {
		    bankrollService.getCardInfo(card);
	    }
    }

    private void getCardsAbstracts(List<Card> cards) throws GateLogicException, GateException
    {
	    for (Card card : cards)
	    {
		    getRandomCardAbstract(card);
	    }
    }

    private CardAbstract getRandomCardAbstract(Card card) throws GateLogicException, GateException
    {
        Calendar fromLocalDate = generateDate(fromDate, toDate);
        Calendar toLocalDate   = generateDate(fromLocalDate, toDate);

        return  (CardAbstract)bankrollService.getAbstract( card, fromLocalDate, toLocalDate);
    }

    private Calendar generateDate(Calendar fromDate, Calendar toDate)
    {
       long fromTimeInMillis = fromDate.getTimeInMillis();
       long toTimeInMillis   = toDate.getTimeInMillis();
       long delta            = Math.round( (new Random()).nextDouble() * (double)(toTimeInMillis - fromTimeInMillis));
       Calendar newDate      = new GregorianCalendar();
       newDate.setTimeInMillis(fromTimeInMillis + delta);

       return newDate;
    }

    private void log(String message)
    {
        if ( isLoging )
            System.out.println(Thread.currentThread() + ": " + message);
    }
}
