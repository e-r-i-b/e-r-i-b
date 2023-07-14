package com.rssl.phizicgate.rsV51.bankroll;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.DublicateKeyException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.*;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientService;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.deposit.Deposit;
import com.rssl.phizic.gate.deposit.DepositInfo;
import com.rssl.phizic.gate.deposit.DepositService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.dictionaries.officies.OfficeGateService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.payments.PaymentsConfig;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.rsV51.data.GateRSV51Executor;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Kidyaev
 * Date: 30.09.2005
 * Time: 18:30:57
 */
public class BankrollServiceImpl extends AbstractService implements BankrollService
{
	private static final AccountService accountService = new AccountService();
    public BankrollServiceImpl(GateFactory factory)
    {
        super(factory);
    }

    public List<Account> getClientAccounts(final Client client)
            throws GateException
    {
        try
        {
            return GateRSV51Executor.getInstance().execute(new HibernateAction<List<Account>>()
            {
                public List<Account> run(Session session) throws Exception
                {
                    Query query = createReadonlyQuery(session, "GetClientAccounts")
                            .setParameter("clientId", Long.decode(client.getId()));

                    //noinspection unchecked
                    List<Account> accounts = query.list();
                    return (accounts != null  ?  accounts  :  new ArrayList<Account>());
                }
            });
        }
        catch (Exception e)
        {
           throw new GateException(e);
        }
    }

    public GroupResult<String, Account> getAccount(String... accountIds)
    {
		GroupResult<String, Account> res = new GroupResult<String, Account>();
        for (String accountId: accountIds)
        {
	        try{
		        final Long id = Long.decode(accountId);
				AccountImpl accountImpl = accountService.getAccount(id);
				fillOffice(accountImpl);
				res.putResult(accountId, accountImpl);
	        }
	        catch (IKFLException e)
	        {
		        res.putException(accountId, e);
	        }
        }
	    return res;
    }

	public AccountImpl getClientAccountByNumber(final String accountNumber, final String clientId) throws GateException {
        try
        {
            return GateRSV51Executor.getInstance().execute(new HibernateAction<AccountImpl>()
            {
                public AccountImpl run(Session session) throws Exception
                {
                    Query query = createReadonlyQuery(session, "GetClientAccountByNumber")
                        .setParameter("accountNumber", accountNumber)
	                    .setParameter("clientId", Long.decode(clientId));

                    return (AccountImpl) query.uniqueResult();
                }
            });
        }
        catch (Exception e)
        {
           throw new GateException(e);
        }
    }

    public GroupResult<Account, AccountInfo> getAccountInfo(final Account... accounts)
    {
        GroupResult<Account, AccountInfo> res = new GroupResult<Account, AccountInfo>();
	    for (final Account account: accounts)
	    {
			final Long id = Long.decode(account.getId());

			try
			{
				res.putResult(account, GateRSV51Executor.getInstance().execute(new HibernateAction<AccountInfo>()
				{
					public AccountInfo run(Session session) throws Exception
					{
						Query query = createReadonlyQuery(session, "GetAccountInfo")
								.setParameter("id", id);

						AccountInfoImpl accountInfo = (AccountInfoImpl) query.uniqueResult();
						accountInfo = accountInfo != null  ?  accountInfo  :  new AccountInfoImpl(account);

						return accountInfo;
					}
				}));
			}
			catch (IKFLException e)
			{
				res.putException(account,  e);
			}
			catch (Exception e)
			{
			   res.putException(account,  new SystemException(e));
			}
	    }
	    return res;
    }

	public GroupResult<Object, AbstractBase> getAbstract(java.lang.Long number, Object... object)
	{
		GroupResult<Object, AbstractBase> res = new GroupResult<Object, AbstractBase>();
		for (Object obj: object)
		{
			try{
				if(obj instanceof Account)
					res.putResult(obj, getAccountAbstract((Account)obj, number));
				else if(obj instanceof Card)
					res.putResult(obj, getCardAbstract((Card)obj, number));
				else if(obj instanceof Deposit)
				{
					DepositService depositService=GateSingleton.getFactory().service(DepositService.class);
					DepositInfo depositInfo = depositService.getDepositInfo((Deposit) obj);
					res.putResult(obj, getAccountAbstract(depositInfo.getAccount(), number));
				}
				else
					throw new GateException("Неверный тип объекта");
			}catch (IKFLException e)
			{
				res.putException(obj, e);
			}

		}
		return res;
	}

	public AbstractBase getAbstract(Object object, Calendar fromDate, Calendar toDate) throws GateLogicException, GateException
	{
		if(object instanceof Account)
			return getAccountAbstract((Account)object,fromDate,toDate);
		else if(object instanceof Card)
			return getCardAbstract((Card)object,fromDate,toDate);
		else if(object instanceof Deposit){
			DepositService depositService=GateSingleton.getFactory().service(DepositService.class);
			DepositInfo depositInfo = depositService.getDepositInfo((Deposit) object);
			return getAccountAbstract(depositInfo.getAccount(),fromDate,toDate);
		}
		else
			throw new GateException("Неверный тип объекта");
	}

	private AccountAbstract getAccountAbstract(final Account account, final Long number) throws GateException
    {
        try
        {
            return GateRSV51Executor.getInstance().execute(new HibernateAction<AccountAbstract>()
            {
                public AccountAbstract run(Session session) throws Exception
                {
                    AccountAbstractImpl accountAbstract = new AccountAbstractImpl(account.getCurrency(), null, null);

	                List<TransactionBase>  accountTransactions = getAccountTransactions(account, null, null, number);

	                if (accountTransactions.size() == 0)
	                    return accountAbstract;

	                Calendar toDate      = accountTransactions.get(accountTransactions.size() - 1).getDate();
	                Calendar fromDate    = accountTransactions.get(0).getDate();
	                Money openingBalance = accountTransactions.get(0).getBalance();
		            Money closingBalance = accountTransactions.get(accountTransactions.size() - 1).getBalance();
	                Calendar previousOperationDate = getPreviousOperationDate(account, fromDate);

	                accountAbstract.setFromDate(fromDate);
	                accountAbstract.setToDate(toDate);
	                accountAbstract.setOpeningBalance(openingBalance);
                    accountAbstract.setClosingBalance(closingBalance);
	                accountAbstract.setPreviousOperationDate(previousOperationDate);
	                accountAbstract.setTransactions(accountTransactions);

                    return accountAbstract;
                }
            });
        }
        catch (Exception e)
        {
           throw new GateException(e);
        }
    }

	private AccountAbstract getAccountAbstract(final Account account, final Calendar fromDate, final Calendar toDate) throws GateException
    {
        try
        {
            return GateRSV51Executor.getInstance().execute(new HibernateAction<AccountAbstract>()
            {
                public AccountAbstract run(Session session) throws Exception
                {
                    AccountAbstractImpl accountAbstract = new AccountAbstractImpl(account.getCurrency(), fromDate, toDate);

                    Calendar prevFrom = (Calendar) fromDate.clone();
                    prevFrom.add(Calendar.DATE, - 1);
                    Money openingBalance      = getAccountBalance(account, prevFrom);
                    Money closingBalance      = getAccountBalance(account, toDate);
                    List<TransactionBase>  accountTransactions = getAccountTransactions(account, fromDate, toDate, null);

                    accountAbstract.setOpeningBalance(openingBalance);
                    accountAbstract.setClosingBalance(closingBalance);
                    accountAbstract.setTransactions(accountTransactions);
	                Calendar previousOperationDate = getPreviousOperationDate(account, fromDate);
	                accountAbstract.setPreviousOperationDate(previousOperationDate);

                    return accountAbstract;
                }
            });
        }
        catch (Exception e)
        {
           throw new GateException(e);
        }
    }

    public List<Card> getClientCards(final Client client) throws GateException
    {
        try
        {
            List<Card> cards = GateRSV51Executor.getInstance().execute(new HibernateAction<List<Card>>()
            {
                public List<Card> run(Session session) throws Exception
                {
                    Query query = createReadonlyQuery(session, "GetClientCards")
                            .setParameter("clientId", Long.decode(client.getId()));

                    //noinspection unchecked
                    List<Card> cards = query.list();

                    return (cards != null  ?  cards  :  new ArrayList<Card>());
                }
            });
	        for ( int i = 0; i < cards.size(); i++ )
            {
                CardImpl card = ( CardImpl ) cards.get(i);
                card.setOwnerId(Long.decode(client.getId()));
	            fillCard(card);
            }
	        return cards;
        }
        catch (Exception e)
        {
           throw new GateException(e);
        }
    }

    public GroupResult<String, Card> getCard(String... externalIds)
    {
        GroupResult<String, Card> res = new GroupResult<String, Card>();
	    for (String externalId: externalIds)
        {
			try
			{
				final Long cardId   = CardImpl.parseCardId(externalId);
				final Long clientId = CardImpl.parseOwnerId(externalId);
				CardImpl card = ( CardImpl ) GateRSV51Executor.getInstance().execute(new HibernateAction<Card>()
				{
					public Card run(Session session) throws Exception
					{
						Query query = createReadonlyQuery(session, "GetCard")
								.setParameter("cardId",cardId)
								.setParameter("clientId",clientId);

						return ( CardImpl ) query.uniqueResult();
					}
				});
				card.setOwnerId(clientId);
				fillCard(card);
				res.putResult(externalId, card);
			}
			catch (IKFLException e)
			{
				res.putException(externalId,  e);
			}
			catch (Exception e)
			{
				res.putException(externalId,  new SystemException(e));
			}
        }
	    return res;
    }

	private void fillCard(CardImpl card) throws GateException, GateLogicException
	{
		card.setCardType(getCardType(card));
		//Заполнение СКС по карте
		fillPrimaryAccount(card);
	}

    Money getAccountBalance(final Account account, final Calendar date) throws GateException
    {
        try
        {
            return GateRSV51Executor.getInstance().execute(new HibernateAction<Money>()
            {
                public Money run(Session session) throws Exception
                {
                    final Long id = Long.decode(account.getId());

                    Query query =createReadonlyQuery(session, "GetAccountBalance")
                            .setParameter("accountId", id)
                            .setParameter("balanceDate", date.getTime());

                    Double val    = (Double) query.uniqueResult();
                    String strVal = val == null ? "0" : val.toString();

                    return new Money(new BigDecimal(strVal), account.getCurrency());
                }
            });
        }
        catch (Exception e)
        {
           throw new GateException(e);
        }
    }

    private List<AccountTransaction> getAccountTransactions_OLD(final Account account, final Calendar fromDate, final Calendar toDate) throws GateException
    {
        try
         {
             return GateRSV51Executor.getInstance().execute(new HibernateAction<List<AccountTransaction>>()
             {
                 public List<AccountTransaction> run(Session session) throws Exception
                 {
                     final Long id = Long.decode(account.getId());
                     String queryName = "GetAccountTransactions";
                     Query query = createReadonlyQuery(session, queryName);

                     query.setParameter("id", id)
                             .setParameter("fromDate", fromDate.getTime())
                             .setParameter("toDate", toDate.getTime());

                     //noinspection unchecked
                     List<AccountTransaction> accountTransactions = query.list();
	                 PaymentsConfig config =ConfigFactory.getConfig(PaymentsConfig.class);
	                 for(AccountTransaction accountTransaction : accountTransactions)
	                 {
		                 if(accountTransaction.getCounteragentBank() == null || accountTransaction.getCounteragentBank().equals(""))
			                 ((AccountTransactionImpl)accountTransaction).setCounteragentBank(config.getOurBankBic());
	                 }
                     setCounteragents(accountTransactions);

                     return (accountTransactions != null  ?  accountTransactions  :  new ArrayList<AccountTransaction>() );
                 }
             });
         }
         catch (Exception e)
         {
            throw new GateException(e);
         }
    }

    private Query createReadonlyQuery(Session session, String queryName)
    {
        Query query = session.getNamedQuery(queryName);
        query.setReadOnly(true).setFlushMode(FlushMode.NEVER);

        return query;
    }

    private void setCounteragents(final List<AccountTransaction> transactions) throws GateException
    {
        if ( transactions == null )
            return;

        try
        {
            GateRSV51Executor.getInstance().execute(new HibernateAction<Void>()
            {
                public Void run(Session session) throws Exception
                {
                    for (AccountTransaction transaction : transactions)
                    {
                        AccountTransactionImpl transactionImpl = (AccountTransactionImpl) transaction;
                        final Long id = Long.valueOf(transactionImpl.getAccountId());
                        final Calendar date = transactionImpl.getDate();
                        final Long dayNumber = Long.valueOf(transactionImpl.getDayNumber());

                        Query query = createReadonlyQuery(session, "GetCounteragent")
                                .setParameter("id", id)
                                .setParameter("date", date.getTime())
                                .setParameter("dayNumber", dayNumber);

	                    ReceiverTransaction receiverTransaction = (ReceiverTransaction)query.uniqueResult();
	                    if (receiverTransaction != null)
	                    {
                          transactionImpl.setCounteragent(receiverTransaction.getCounteragent());
	                      transactionImpl.setReceiver(receiverTransaction);
	                    }
                    }

                    return null;
                }
            });
        }
        catch (Exception e)
        {
           throw new GateException(e);
        }
    }

    Currency getCardCurrency(final Long cardId) throws GateException
    {
        try
        {
            return GateRSV51Executor.getInstance().execute(new HibernateAction<Currency>()
            {
                public Currency run(Session session) throws Exception
                {
                    Query query = createReadonlyQuery(session, "GetCardCurrency")
                            .setParameter("cardId", cardId);
                    Long currencyId = (Long) query.uniqueResult();
	                CurrencyService currencyService = getFactory().service(CurrencyService.class);
	                return currencyService.findById(String.valueOf(currencyId));
                }
            });
        }
        catch (Exception e)
        {
           throw new GateException(e);
        }
    }

    public GroupResult<Card, CardInfo> getCardInfo(final Card... cards)
    {
		GroupResult<Card, CardInfo> res = new GroupResult<Card, CardInfo>();
	    for (Card card: cards)
		{
			try
			{
				final Long     cardId            = CardImpl.parseCardId(card.getId());
				Account primaryAccount = getCardAccount(card);
				final Currency curr              = primaryAccount.getCurrency();
				final Long     primaryAccountId  = Long.valueOf(primaryAccount.getId());

				res.putResult(card, GateRSV51Executor.getInstance().execute(new HibernateAction<CardInfoImpl>()
				{
					public CardInfoImpl run(Session session) throws Exception
					{
						Query cardInfoQuery = createReadonlyQuery(session, "GetCardInfo")
								.setParameter("cardId", cardId);

						CardInfoImpl cardInfo = (CardInfoImpl) cardInfoQuery.uniqueResult();

						cardInfo.setLastOperationDate( getCardDateInfo(primaryAccountId, cardId) );

						BigDecimal decimalHoldSum  = cardInfo.getDecimalHoldSum();

						if (decimalHoldSum != null)
							cardInfo.setHoldSum(new Money(decimalHoldSum, curr));
						return cardInfo;
					}
				}));
			}
			catch (IKFLException e)
			{
				res.putException(card, e);
			}
			catch (Exception e)
			{
			   res.putException(card, new SystemException(e));
			}
		}
	    return res;
    }

    private CardAbstract getCardAbstract(final Card card, final Long number) throws GateException
    {
        try
        {
            return GateRSV51Executor.getInstance().execute(new HibernateAction<CardAbstract>()
            {
                public CardAbstract run(Session session) throws Exception
                {
					Account primaryAccount = getCardAccount(card);
					CardAbstractImpl cardAbstract   = new CardAbstractImpl(primaryAccount.getCurrency(), null, null);

                    List<TransactionBase> cardOperations = getCardOperations(card, null, null, number);
	                if (cardOperations.size() == 0)
	                    return cardAbstract;

	                Calendar toDate      = ((CardOperationImpl) cardOperations.get(cardOperations.size() - 1)).getOperationDate();
	                Calendar fromDate    = ((CardOperationImpl) cardOperations.get(0)).getOperationDate();
                    List<CardOperation> unsettledOperations = getCardUnsettledOperations(card, fromDate, toDate);
	                Money openingBalance = cardOperations.get(0).getBalance();
			        Money closingBalance = cardOperations.get(cardOperations.size() - 1).getBalance();

                    cardAbstract.setToDate(toDate);
	                cardAbstract.setFromDate(fromDate);
	                cardAbstract.setOpeningBalance(openingBalance);
                    cardAbstract.setClosingBalance(closingBalance);
                    cardAbstract.setTransactions(cardOperations);
	                cardAbstract.setUnsettledOperations(unsettledOperations);

                    return cardAbstract;
                }
            });
        }
        catch (Exception e)
        {
           throw new GateException(e);
        }
    }

	private CardAbstract getCardAbstract(final Card card, final Calendar fromDate, final Calendar toDate) throws GateException
    {
        try
        {
            return GateRSV51Executor.getInstance().execute(new HibernateAction<CardAbstract>()
            {
                public CardAbstract run(Session session) throws Exception
                {
	                Account primaryAccount = getCardAccount(card);
	                Calendar         prevFrom       = (Calendar) fromDate.clone();
                    CardAbstractImpl cardAbstract   = new CardAbstractImpl(primaryAccount.getCurrency(), fromDate, toDate);

                    // -Пока входящий и исходящий остаток для карты получаем из ее основного счета
                    prevFrom.add(Calendar.DATE, - 1);
                    Money openingBalance = getAccountBalance(primaryAccount, prevFrom);
                    Money closingBalance = getAccountBalance(primaryAccount, toDate);
                    List<TransactionBase> cardOperations = getCardOperations(card, fromDate, toDate, null);
                    List<CardOperation> unsettledOperations = getCardUnsettledOperations(card, fromDate, toDate);

                    cardAbstract.setOpeningBalance(openingBalance);
                    cardAbstract.setClosingBalance(closingBalance);
                    cardAbstract.setTransactions(cardOperations);
	                cardAbstract.setUnsettledOperations(unsettledOperations);

                    return cardAbstract;
                }
            });
        }
        catch (Exception e)
        {
           throw new GateException(e);
        }
    }

	private List<TransactionBase> getCardOperations (final Card card, final Calendar fromDate, final Calendar toDate, Long number) throws GateException, GateLogicException
	{
		final Long    cardId           = CardImpl.parseCardId(card.getId());
		final Account primaryAccount;
		try
		{
			primaryAccount = getCardAccount(card);
		}
		catch (LogicException e)
		{
			throw new GateLogicException(e);
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
		final Long    primaryAccountId = Long.decode(primaryAccount.getId());

		Document document = null;

        WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage message = service.createRequest("getCardOperations_q");
		message.addParameter("accountReferenc", primaryAccountId);
		message.addParameter("cardId", cardId);

		if (fromDate != null)
			message.addParameter("fromDate", String.format("%1$te.%1$tm.%1$tY", fromDate.getTime()));
		else
			message.addParameter("fromDate", null);
		if (toDate != null)
			message.addParameter("toDate", String.format("%1$te.%1$tm.%1$tY", toDate.getTime()));
		else
			message.addParameter("toDate", null);

		message.addParameter("number", number);

	    try
	    {
		   document = service.sendOnlineMessage(message, null);
	    }
		catch (GateLogicException e)
	    {
		    throw new GateException(e.getMessage());
	    }
		List<TransactionBase> cardOperations = new ArrayList<TransactionBase>();

		NodeList list = document.getElementsByTagName("transaction");
		int length = list.getLength();

		for(int i=0; i<length; ++i)
		{
			Currency curr = null;
			Money summa = null;
			CurrencyService currencyService = getFactory().service(CurrencyService.class);

			CardOperationImpl operation = new CardOperationImpl();
			Element elem = (Element)list.item(i);

			String valueParam;

			valueParam = getParam(elem, "operationId");
			if (valueParam != null)
			   operation.setOperationId(valueParam);

			valueParam = getParam(elem, "operationDate");
			if (valueParam != null)
			{
				try
				{
					operation.setOperationDate(DateHelper.parseCalendar(valueParam));
				}
				catch (ParseException e)
				{
					operation.setOperationDate(null);
				}
			}

			valueParam = getParam(elem, "operationCurrencyId");
			if (valueParam != null)
			   operation.setTransactionKind(Long.parseLong(valueParam));

			valueParam = getParam(elem, "transactionCurrencyId");
			if (valueParam != null)
				curr = currencyService.findById(valueParam);

			valueParam = getParam(elem, "doubleCreditSum");
			if ((curr != null) && (valueParam != null) && (!valueParam.equals("")))
			{
			   summa = new Money( new BigDecimal(valueParam), curr);
			   operation.setCreditSum(summa);
			}

			valueParam = getParam(elem, "doubleDebitSum");
			if ((curr != null) && (valueParam != null) && (!valueParam.equals("")))
			{
			   summa = new Money( new BigDecimal(valueParam), curr);
			   operation.setDebitSum(summa);
			}

			valueParam = getParam(elem, "doubleAccountSum");
			if ((curr != null) && (valueParam != null))
			{
			   summa = new Money( new BigDecimal(valueParam), curr);
			   operation.setAccountCreditSum(summa);
			   operation.setAccountDebitSum(summa);
			}

			valueParam = getParam(elem, "transactionFlag");
			if (valueParam != null)
			   operation.setTransactionFlag(Long.parseLong(valueParam));

			valueParam = getParam(elem, "transactionKind");
			if (valueParam != null)
			   operation.setTransactionKind(Long.parseLong(valueParam));

			valueParam = getParam(elem, "operationDate");
			if (valueParam != null)
			{
				try
				{
					operation.setDate(DateHelper.parseCalendar(valueParam));
				}
				catch (ParseException e)
				{
					operation.setDate(null);
				}
			}

			valueParam = getParam(elem, "description");
			if (valueParam != null)
			   operation.setDescription(valueParam);

			cardOperations.add(operation);
		}

		for (TransactionBase cardOperation : cardOperations)
		{
			CardOperationImpl operationImpl = (CardOperationImpl) cardOperation;
			operationImpl.setOperationCard(card);
		}
		return cardOperations;
	}

    private List<CardOperation> getCardUnsettledOperations(final Card card, final Calendar fromDate, final Calendar toDate) throws GateException, GateLogicException
    {
        try 
        {
	        final Long    cardId           = CardImpl.parseCardId(card.getId());
            final Account primaryAccount = getCardAccount(card);
	        final Long    primaryAccountId = Long.decode(primaryAccount.getId());
			return GateRSV51Executor.getInstance().execute(new HibernateAction<List<CardOperation>>()
            {
                public List<CardOperation> run(Session session) throws Exception
                {
                    Query query = createReadonlyQuery(session, "GetUnsettledOperations")
                                        .setParameter("accountId", primaryAccountId)
                                        .setParameter("fromDate", fromDate.getTime())
                                        .setParameter("toDate", toDate.getTime())
                                        .setParameter("cardId", cardId);

	                //noinspection unchecked
	                List<CardOperationImpl> cardOperations = query.list();
	                for (CardOperationImpl cardOperation : cardOperations)
	                {
		                cardOperation.setOperationCard(card);
	                }

                    return (List<CardOperation>) (cardOperations != null  ?  cardOperations  :  new ArrayList<CardOperation>());
                }
            });
        }
        catch (LogicException e)
        {
	        throw new GateLogicException(e);
        }
        catch (SystemException e)
        {
            throw new GateException(e);
        }
	    catch (Exception e)
	    {
		    throw new GateException(e);
	    }
    }

    public GroupResult<Card, Account> getCardPrimaryAccount(Card... cards)
    {
		GroupResult<Card, Account> res = new GroupResult<Card, Account>();
	    for (Card card: cards)
		{
			try
			{
				final Long cardId = CardImpl.parseCardId(card.getId());
				res.putResult(card, GateRSV51Executor.getInstance().execute(new HibernateAction<Account>()
				{
					public Account run(Session session) throws Exception
					{
						Query query = createReadonlyQuery(session, "GetCardPrimaryAccountId");
						query.setParameter("cardId", cardId);
						Long accountId = (Long) query.uniqueResult();
						AccountImpl accountImpl = accountService.getAccount(accountId);
						fillOffice(accountImpl);
						return accountImpl;
					}
				}));
			}
			catch (IKFLException e)
			{
				res.putException(card, e);
			}
			catch (Exception e)
			{
				res.putException(card, new SystemException(e));
			}
		}
	    return res;
    }

	public GroupResult<Card, Client> getOwnerInfo(Card... cards)
	{
        GroupResult<Card, Client> res = new GroupResult<Card, Client>();
		for (Card card: cards)
        {
			final Long cardId = CardImpl.parseCardId(card.getId());

			try
			{
				Long clientId = GateRSV51Executor.getInstance().execute(new HibernateAction<Long>()
				{
					public Long run(Session session) throws Exception
					{
						Query query = createReadonlyQuery(session, "GetCardOwnerId")
								.setParameter("cardId", cardId);

						return (Long) query.uniqueResult();
					}
				});
				ClientService service = GateSingleton.getFactory().service(ClientService.class);
				res.putResult(card, service.getClientById(clientId.toString()));
			}
			catch (Exception ex)
			{
				try{
					res.putException(card, new GateException(ex));
				}catch (DublicateKeyException e)
				{
					throw new RuntimeException(e);
				}
			}
        }
		return res;
	}

	public GroupResult<Pair<String, Office>, Client> getOwnerInfoByCardNumber(Pair<String, Office>... cardInfo)
	{
		throw new UnsupportedOperationException();
	}

	public GroupResult<Card, OverdraftInfo> getCardOverdraftInfo(Calendar date, Card... cards)
	{
		GroupResult<Card, OverdraftInfo> res = new GroupResult<Card, OverdraftInfo>();
		for (Card card: cards)
		{
			try{
				OverdraftInfoImpl info = new OverdraftInfoImpl();

				Account account = getCardAccount(card);
				Document document = getOverdraftInfo(account.getId(),date);

				Currency cur = account.getCurrency();

				if (document != null)
				{
					Element paramElem = document.getDocumentElement();
					fillOverdraftInfoFromXML(paramElem, info, cur);
				}

				try{
					res.putResult(card, info);
				} catch (DublicateKeyException ex)
				{
					throw new GateLogicException(ex);
				}
			}catch (IKFLException ex)
			{
				try{
					res.putException(card, ex);
				} catch (DublicateKeyException e)
				{
					throw new RuntimeException(e);
				}
			}
		}
		return res;
	}

	public GroupResult<Account, Client> getOwnerInfo(Account... accounts)
	{
		GroupResult<Account, Client> res = new GroupResult<Account, Client>();

		for (Account account: accounts)
		{
			final Long accountId = Long.valueOf(account.getId());

			try
			{
				Long clientId = GateRSV51Executor.getInstance().execute(new HibernateAction<Long>()
				{
					public Long run(Session session) throws Exception
					{
						Query query = createReadonlyQuery(session, "GetAccountOwnerId")
								.setParameter("accountId", accountId);

						return (Long) query.uniqueResult();
					}
				});
				ClientService service = GateSingleton.getFactory().service(ClientService.class);
				res.putResult(account, service.getClientById(clientId.toString()));
			}
			catch (IKFLException e)
			{
				res.putException(account, e);
			}
			catch(Exception ex)
			{
				res.putException(account, new SystemException(ex));
			}
		}
		return res;	
	}

	public GroupResult<Card, List<Card>> getAdditionalCards(Card... mainCards)
	{
		GroupResult<Card, List<Card>> res = new GroupResult<Card, List<Card>>();
		for (Card mainCard: mainCards)
		{
			 try
			 {
				 if(!mainCard.isMain())
					throw new GateException("Аргумент должен быть ОСНОВНОЙ картой");
				 final Account account = getCardAccount(mainCard);
				 List<Card> cards = GateRSV51Executor.getInstance().execute(new HibernateAction<List<Card>>()
				 {
					 public List<Card> run(Session session) throws Exception
					 {
						 String queryName = "GetAdditionalCards";
						 Query query = createReadonlyQuery(session, queryName);
						 query.setParameter("accId", account.getId());

						 //noinspection unchecked
						 return query.list();
					 }
				 });
				 for (Card card : cards)
				 {
					fillCard((CardImpl)card);
				 }
				 res.putResult(mainCard, cards);
			 }
			 catch (IKFLException e)
			 {
				res.putException(mainCard, e);
			 }
			 catch (Exception e)
			 {
				res.putException(mainCard, new SystemException(e));
			 }
		}
		return res;
	}

	private String getParam (Element element, String name)
	{
		NodeList list = element.getElementsByTagName(name);
		String valueElem = "";

		if (list.getLength() > 0)
		{
			valueElem = list.item(0).getTextContent();
			if (!valueElem.equals(""))
			   return valueElem;
			else
			   return null;
		}

		return null;
	}

	private Document getOverdraftInfo (String referenc, Calendar date) throws GateException
	{
		Document document = null;

        WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage message = service.createRequest("getOvInfo_q");
		message.addParameter("referenc", referenc);
		message.addParameter("endDate", String.format("%1$te.%1$tm.%1$tY", date.getTime()));
	    try
	    {
		   document = service.sendOnlineMessage(message, null);
	    }
		catch (GateLogicException e)
	    {
		    throw new GateException(e.getMessage());
	    }

		return document;
	}

	private Calendar getCardDateInfo (Long primaryAccountId, Long cardId) throws GateException
	{
		Document document = null;

        WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage message = service.createRequest("getCardLastOperationDate_q");
		message.addParameter("accountReferenc", primaryAccountId);
		message.addParameter("cardId", cardId);
	    try
	    {
		   document = service.sendOnlineMessage(message, null);
	    }
		catch (GateLogicException e)
	    {
		    throw new GateException(e.getMessage());
	    }

		String valueParam = getParam(document.getDocumentElement(), "date");
		try
		{
			if(valueParam!=null)
			return DateHelper.parseCalendar(valueParam);
			else
				return null;
		}
		catch (ParseException e)
		{
			throw new GateException(e);
		}
	}

	private void fillOverdraftInfoFromXML(Element paramElem, OverdraftInfo infoIn, Currency cur) throws GateException
	{
		OverdraftInfoImpl info = (OverdraftInfoImpl)infoIn;

		String valueParam = getParam(paramElem, "датаОткрытияКарты");
		if (valueParam != null)
		{
			try
			{
				info.setOpenDate(DateHelper.parseCalendar(valueParam));
			}
			catch (ParseException e)
			{
				throw new GateException(e);
			}
		}

		valueParam = getParam(paramElem, "лимитКарты");
		if (valueParam != null)
		{
		   Money summa = new Money( new BigDecimal(valueParam.replace(',','.')), cur);
		   info.setLimit(summa);
		}

		valueParam = getParam(paramElem, "просроченныйДолг");
		if (valueParam != null)
		{
		   Money summa = new Money( new BigDecimal(valueParam.replace(',','.')), cur);
		   info.setUnsettledDebtSum(summa);
		}

		valueParam = getParam(paramElem, "штрафЗаПросрочку");
		if (valueParam != null)
		{
		   Money summa = new Money( new BigDecimal(valueParam.replace(',','.')), cur);
		   info.setUnsettledPenalty(summa);
		}

		valueParam = getParam(paramElem, "суммаОвердрафта");
		if (valueParam != null)
		{
		   Money summa = new Money( new BigDecimal(valueParam.replace(',','.')), cur);
		   info.setCurrentOverdraftSum(summa);
		}

		valueParam = getParam(paramElem, "срочныеПроценты");
		if (valueParam != null)
		{
		   Money summa = new Money( new BigDecimal(valueParam.replace(',','.')), cur);
		   info.setRate(summa);
		}

		valueParam = getParam(paramElem, "техническийОвердрафт");
		if (valueParam != null)
		{
		   Money summa = new Money( new BigDecimal(valueParam.replace(',','.')), cur);
		   info.setTechnicalOverdraftSum(summa);
		}

		valueParam = getParam(paramElem, "штрафЗаТехническийОвердрафт");
		if (valueParam != null)
		{
		   Money summa = new Money( new BigDecimal(valueParam.replace(',','.')), cur);
		   info.setTechnicalPenalty(summa);
		}

		valueParam = getParam(paramElem, "общаяСуммаДолга");
		if (valueParam != null)
		{
		   Money summa = new Money( new BigDecimal(valueParam.replace(',','.')), cur);
		   info.setTotalDebtSum(summa);
		}

		valueParam = getParam(paramElem, "датаВыносаНаПросрочку");
		if (valueParam != null)
		{
			try
			{
				info.setUnsetltedDebtCreateDate(DateHelper.parseCalendar(valueParam));
			}
			catch (ParseException e)
			{
				throw new GateException(e);
			}
		}

		valueParam = getParam(paramElem, "доступныйЛимит");
		if (valueParam != null)
		{
		   Money summa = new Money( new BigDecimal(valueParam.replace(',','.')), cur);
		   info.setLimit(summa);
		}
	}

	private CardType getCardType(final Card card) throws GateException, GateLogicException
	{
		Calendar date;
		try
		{
			final Account account = getCardAccount(card);
			date = GateRSV51Executor.getInstance().execute(new HibernateAction<Calendar>()
			{
				public Calendar run(Session session) throws Exception
				{
					Query query = createReadonlyQuery(session, "getOverdraftRegDate")
							.setParameter("referenc", account.getId());

					return (Calendar)query.uniqueResult();
				}
			});
		}
		catch (LogicException e)
		{
			throw new GateLogicException(e);
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
		if(date!=null)
			return CardType.overdraft;
		else
			return CardType.debit;
	}

	private Calendar getPreviousOperationDate(final Account account, final Calendar from) throws GateException
	{
        try
        {
            return GateRSV51Executor.getInstance().execute(new HibernateAction<Calendar>()
            {
                public Calendar run(Session session) throws Exception
                {
                    final Long id = Long.decode(account.getId());

                    Query query =createReadonlyQuery(session, "GetPreviousOperationDate")
                            .setParameter("accountId", id)
                            .setParameter("fromDate", from.getTime());

	                Calendar d = (Calendar)query.uniqueResult();
	                return d;
                }
            });
        }
        catch (Exception e)
        {
           throw new GateException(e);
        }
	}

	private List<TransactionBase> getAccountTransactions(final Account account, final Calendar fromDate, final Calendar toDate, final Long number) throws GateException, GateLogicException
	{
		final Long accountId = Long.decode(account.getId());

		Document document = null;

	    WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage message = service.createRequest("getAccountTransactions_q");
		message.addParameter("accountReferenc", accountId);

		if (fromDate != null)
			message.addParameter("fromDate", String.format("%1$te.%1$tm.%1$tY", fromDate.getTime()));
		else
		    message.addParameter("fromDate", "");

		if (toDate != null)
			message.addParameter("toDate", String.format("%1$te.%1$tm.%1$tY", toDate.getTime()));
		else
		    message.addParameter("toDate", "");

		if(number != null)
			message.addParameter("number", number);
		else
		    message.addParameter("number", "");

		try
		{
		   document = service.sendOnlineMessage(message, null);
		}
		catch (GateLogicException e)
		{
			throw new GateException(e.getMessage());
		}
		List<TransactionBase> accountTransactions = new ArrayList<TransactionBase>();

		NodeList list = document.getElementsByTagName("transaction");
		int length = list.getLength();

		for(int i=0; i<length; ++i)
		{
			Currency curr = null;
			Money summa = null;
			CurrencyService currencyService = getFactory().service(CurrencyService.class);

			AccountTransactionImpl operation = new AccountTransactionImpl();
			Element elem = (Element)list.item(i);

			String valueParam;

			valueParam = getParam(elem, "date");
			if (valueParam != null)
			{
				try
				{
					operation.setDate(DateHelper.parseCalendar(valueParam));
				}
				catch (ParseException e)
				{
					throw new GateException(e);
				}
			}

			valueParam = getParam(elem, "transactionCurrencyId");
			if (valueParam != null)
				curr = currencyService.findById(valueParam);

			valueParam = getParam(elem, "creditSum");
			if ((curr != null) && (valueParam != null) && (!valueParam.equals("")))
			{
			   summa = new Money( new BigDecimal(valueParam), curr);
			   operation.setCreditSum(summa);
			}

			valueParam = getParam(elem, "debitSum");
			if ((curr != null) && (valueParam != null) && (!valueParam.equals("")))
			{
			   summa = new Money( new BigDecimal(valueParam), curr);
			   operation.setDebitSum(summa);
			}

			valueParam = getParam(elem, "balance");
			if ((curr != null) && (valueParam != null) && (!valueParam.equals("")))
			{
			   summa = new Money( new BigDecimal(valueParam), curr);
			   operation.setBalance(summa);
			}

			valueParam = getParam(elem, "description");
			if (valueParam != null)
			   operation.setDescription(valueParam);

			valueParam = getParam(elem, "counteragent");
			if (valueParam != null)
			   operation.setCounteragent(valueParam);

			PaymentsConfig config =ConfigFactory.getConfig(PaymentsConfig.class);
			valueParam = getParam(elem, "counteragentBank");
			if (valueParam != null)
			{
				if(valueParam.equals(""))
					operation.setCounteragentBank(config.getOurBankBic());
				 else
					operation.setCounteragentBank(valueParam);
			}else
				operation.setCounteragentBank(config.getOurBankBic());

			valueParam = getParam(elem, "counteragentAccount");
			if (valueParam != null)
			   operation.setCounteragentAccount(valueParam);

			valueParam = getParam(elem, "counteragentCorAccount");
			if (valueParam != null)
			   operation.setCounteragentCorAccount(valueParam);

			valueParam = getParam(elem, "documentNumber");
			if (valueParam != null)
			   operation.setDocumentNumber(valueParam);

			valueParam = getParam(elem, "operationCode");
			if (valueParam != null)
			   operation.setOperationCode(valueParam);
			
			accountTransactions.add(operation);
		}

		return accountTransactions;
	}
	public AccountAbstract getAccountExtendedAbstract(Account account, Calendar fromDate, Calendar toDate) throws GateException
	{
		throw new UnsupportedOperationException("Не реализован метод AccountAbstract getAccountExtendedAbstract(Account account, Calendar fromDate, Calendar toDate)");
	}

	public GroupResult<Pair<String, Office>, Account> getAccountByNumber(Pair<String, Office>... accountInfo)
	{
		GroupResult<Pair<String, Office>, Account> result = new GroupResult<Pair<String, Office>, Account>();
		for(Pair<String, Office> info: accountInfo)
		{
			try
			{
				AccountImpl accountImpl = accountService.getAccountByNumber(info.getFirst());
				fillOffice(accountImpl);
				result.putResult(info, accountImpl);
			}
			catch (IKFLException e)
			{
				result.putException(info, e);
			}
		}
		return result;
	}

	public GroupResult<Pair<String,Office>,Card> getCardByNumber(Client client, Pair<String, Office>... cardInfo)
	{
		throw new UnsupportedOperationException();
	}

	public AccountAbstract getAccHistoryFullExtract(Account account, Calendar fromDate, Calendar toDate) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	private void fillOffice(AccountImpl accountImpl) throws GateLogicException, GateException
    {
	    OfficeGateService officeGateService = getFactory().service(OfficeGateService.class);
	    accountImpl.setOffice(officeGateService.getOfficeById(String.valueOf(accountImpl.getBranch())));
    }

	/**
	 * Заполнение информации по СКС карты 
	 * @param card - карта
	 */
	private void fillPrimaryAccount(final CardImpl card) throws GateException
	{
		try
		{
			Long primaryAccountExternalId =GateRSV51Executor.getInstance().execute(new HibernateAction<Long>()
			{
				public Long run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("GetCardPrimaryAccountId")
		                  .setReadOnly(true).setFlushMode(FlushMode.NEVER)
						  .setParameter("cardId", card.getId());
					Long accountId = (Long) query.uniqueResult();

					return accountId;
				}
			});
			card.setPrimaryAccountExternalId(primaryAccountExternalId != null ? String.valueOf(primaryAccountExternalId) : null);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	private Account getCardAccount(Card card) throws SystemException, LogicException
	{
		Account primaryAccount = GroupResultHelper.getOneResult(getCardPrimaryAccount(card));
		if (primaryAccount == null)
			throw new GateException("Не обнаружен карт-счет для карты: "+card.getNumber());
		return primaryAccount;
	}
}
