package com.rssl.phizicgate.rsretailV6r4.bankroll;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
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
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.rsretailV6r4.clients.ClientServiceImpl;
import com.rssl.phizicgate.rsretailV6r4.data.RSRetailV6r4Executor;
import com.rssl.phizicgate.rsretailV6r4.dictionaries.currencies.CurrencyServiceImpl;
import com.rssl.phizicgate.rsretailV6r4.messaging.RetailXMLHelper;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.xml.transform.TransformerException;

/**
 * @author Danilov
 * @ created 03.10.2007
 * @ $Author$
 * @ $Revision$
 */
public class BankrollServiceImpl extends AbstractService implements BankrollService
{
	private static final AccountService accountService = new AccountService();
	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE);
	public BankrollServiceImpl(GateFactory factory)
    {
        super(factory);
    }

	private CardType getCardType(final Card card) throws GateException
	{
		String cardType = null;
		try
        {
            cardType = RSRetailV6r4Executor.getInstance().execute(new HibernateAction<String>()
            {
                public String run(Session session) throws Exception
                {
                    Query query = createReadonlyQuery(session, "GetCardType")
                            .setParameter("cardNumber", card.getNumber());
                    return (String)query.uniqueResult();
                }
            });
        }
        catch (Exception e)
        {
           throw new GateException(e);
        }

		return parseCardType(cardType);
	}

	protected CardType parseCardType(String cardType) throws GateException
	{
		if(cardType.contains("d"))
	        return CardType.debit;
	    else if(cardType.contains("c"))
	        return CardType.credit;
	    else if(cardType.contains("o"))
	        return CardType.overdraft;
	    else throw new GateException("Неизвестный вид карты");
	}

	public List<Account> getClientAccounts(final Client client) throws GateException, GateLogicException
	{
        try
        {
            return RSRetailV6r4Executor.getInstance().execute(new HibernateAction<List<Account>>()
            {
                public List<Account> run(Session session) throws Exception
                {
                    Query query = createReadonlyQuery(session, "GetClientAccounts")
                            .setParameter("clientId", Long.decode(client.getId()));

	                //считаем, что канал широкий, а процесс соединения медленный
	                query.setFetchSize(30);	                

                    List<Account> retailAccounts = (List<Account>) query.list();

	                if(retailAccounts == null)
	                    return new ArrayList<Account>();

	                List<Account> accounts= new ArrayList<Account>(retailAccounts.size());

	                for(Account account: retailAccounts)
	                {
		                if(account.getCurrency().getNumber() != null)
		                {
		                    accounts.add(account);
		                }
		                else
		                {
			                log.error("Невозможно импортировать счет "+ account.getNumber() + ". Валюта счета не найдена в справочнике");
		                }
	                }
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
		GroupResult<String, Account> accounts = new GroupResult<String, Account>();
		for (String accountId: accountIds)
		{
			try{
				final Long id = Long.decode(accountId);
				accounts.putResult(accountId, getAccount(id));
			}
			catch (IKFLException e)
			{
				accounts.putException(accountId, e);
			}
		}
		return accounts;
	}

	public AccountImpl getAccount(long accountId) throws GateException, GateLogicException
	{
		AccountImpl accountImpl = accountService.getAccount(accountId);
		fillOffice(accountImpl);
		fillRate(accountImpl);
		return accountImpl;
	}

	public GroupResult<Account, AccountInfo> getAccountInfo(final Account... accounts)
	{
        final GroupResult<Account, AccountInfo> res = new GroupResult<Account, AccountInfo>();
		for (final Account account: accounts)
		{
			final Long id = Long.decode(account.getId());
			try
            {
				res.putResult(account,  RSRetailV6r4Executor.getInstance().execute(new HibernateAction<AccountInfo>()
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
				res.putException(account, e);
			}
			catch (Exception e)
			{
			   res.putException(account, new SystemException(e));
			}
		}
		return res;
	}

	public AccountImpl getAccountByNumber(final String accountNumber) throws GateException {
        try
        {
            return RSRetailV6r4Executor.getInstance().execute(new HibernateAction<AccountImpl>()
            {
                public AccountImpl run(Session session) throws Exception
                {
                    Query query = createReadonlyQuery(session, "GetClientAccountByNumber")
                        .setParameter("accountNumber", accountNumber);

	                AccountImpl accountImpl = (AccountImpl) query.uniqueResult();
                    fillOffice(accountImpl);
	                fillRate(accountImpl);
	                return accountImpl;
                }
            });
        }
        catch (Exception e)
        {
           throw new GateException(e);
        }
    }

	public AbstractBase getAbstract(Object object, Calendar fromDate, Calendar toDate) throws GateLogicException, GateException
	{
		if(object instanceof Account)
			return getAccountAbstract((Account)object,fromDate,toDate);
		if(object instanceof Card)
			return getCardAbstract((Card)object,fromDate,toDate);
		if(object instanceof Deposit){
			DepositService depositService=GateSingleton.getFactory().service(DepositService.class);
			DepositInfo depositInfo = depositService.getDepositInfo((Deposit) object);
			return getAccountAbstract(depositInfo.getAccount(),fromDate,toDate);
		}
		throw new GateException("Неверный тип объекта");

	}

	public GroupResult<Object, AbstractBase> getAbstract(java.lang.Long number, Object... object)
	{
		GroupResult<Object, AbstractBase> res = new GroupResult<Object, AbstractBase>();
		for (Object obj: object)
		{
			try{
				if(obj instanceof Account)
				{
					res.putResult(obj, getAccountAbstract((Account)obj, null, null));
					continue;
				}
				if(obj instanceof Card)
				{
					res.putResult(obj, getCardAbstract((Card)obj, number));
					continue;
				}
				if(obj instanceof Deposit){
					DepositService depositService=GateSingleton.getFactory().service(DepositService.class);
					DepositInfo depositInfo = depositService.getDepositInfo((Deposit) obj);
						res.putResult(obj, getAccountAbstract(depositInfo.getAccount(), null, null));
					continue;
				}
				throw new GateException("Неверный тип объекта");
			}catch (IKFLException e)
			{
				res.putException(obj, e);
			}
		}
		return res;
	}

	private AccountAbstract getAccountAbstract(final Account account, final Long number) throws GateException, GateLogicException
	{
		AccountAbstractImpl accountAbstract = new AccountAbstractImpl(account.getCurrency(), null, null);
		final Long        primaryAccountId = Long.valueOf(account.getId());

		Document document = null;

		WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage message = service.createRequest("getAccountTransactions_q");
		message.addParameter("accountReferenc", primaryAccountId);
		message.addParameter("number", number);

		try
		{
			document = service.sendOnlineMessage(message, null);
		}
		catch (GateLogicException e)
		{
			throw new GateException(e.getMessage());
		}

		Element root = document.getDocumentElement();


		return accountAbstract;
	}

	private AccountAbstract getAccountAbstract(final Account account, final Calendar fromDate, final Calendar toDate) throws GateException, GateLogicException
	{
		AccountAbstractImpl accountAbstract = new AccountAbstractImpl(account.getCurrency(), fromDate, toDate);

		final Long        primaryAccountId = Long.valueOf(account.getId());

		Document document = null;

		WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage message = service.createRequest("getAccountTransactions_q");
		message.addParameter("accountReferenc", primaryAccountId);

		message.addParameter("fromDate", String.format("%1$te.%1$tm.%1$tY", fromDate == null ? DateHelper.getPreviousMonth(): fromDate.getTime()));
		message.addParameter("toDate",   String.format("%1$te.%1$tm.%1$tY", toDate == null ? Calendar.getInstance() : toDate.getTime()));

		try
		{
			document = service.sendOnlineMessage(message, null);
		}
		catch (GateLogicException e)
		{
			throw new GateException(e.getMessage());
		}

		Element root = document.getDocumentElement();


		accountAbstract.setPreviousOperationDate(RetailXMLHelper.getElementDate(root, "previousOperationDate"));
		// процентная ставка
		String value = RetailXMLHelper.getElementValue(root, "interestRate");
//		if (value != null)
//			accountAbstract.setInterestRate(new BigDecimal(value.trim()));
		accountAbstract.setOpeningBalance(RetailXMLHelper.getElementMoney(root, "incomingRest", account.getCurrency()));
		accountAbstract.setClosingBalance(RetailXMLHelper.getElementMoney(root, "outcomingRest", account.getCurrency()));

		// транзакции
		List<TransactionBase> accountTransactions = fillAccountTransactions(document, "transactions/transaction");
		accountAbstract.setTransactions(accountTransactions);
		return accountAbstract;
	}

	private AbstractBase getCardAbstract(final Card card, final Long number) throws GateException
	{
		try
        {
            return RSRetailV6r4Executor.getInstance().execute(new HibernateAction<CardAbstract>()
            {
                public CardAbstract run(Session session) throws Exception
                {
	                CardInfoImpl     cardInfo     = (CardInfoImpl) GroupResultHelper.getOneResult(getCardInfo(card));
	                Currency         currency     = card.getCurrency();
                    CardAbstractImpl cardAbstract = new CardAbstractImpl(currency, null, null);

	                List<TransactionBase> cardOperations = getCardOperations(card, number);

	                if (cardOperations.size() == 0)
	                    return cardAbstract;

	                Calendar toDate      = ((CardOperationImpl) cardOperations.get(cardOperations.size() - 1)).getOperationDate();
	                Calendar fromDate    = ((CardOperationImpl) cardOperations.get(0)).getOperationDate();

	                Money openingBalance = cardOperations.get(0).getBalance();
			        Money closingBalance = cardOperations.get(cardOperations.size() - 1).getBalance();

                    cardAbstract.setToDate(toDate);
	                cardAbstract.setFromDate(fromDate);
                    cardAbstract.setTransactions(cardOperations);

	                if(card.isMain())
	                {
                        cardAbstract.setOpeningBalance(openingBalance);
                        cardAbstract.setClosingBalance(closingBalance);
	                }

                    return cardAbstract;
                }
            });
        }
		catch (IKFLException e)
		{
			log.error("Ошибка при получении cardInfo", e);
			throw new GateException(e);
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
            return RSRetailV6r4Executor.getInstance().execute(new HibernateAction<CardAbstract>()
            {
                public CardAbstract run(Session session) throws Exception
                {
	                CardInfoImpl     cardInfo     = (CardInfoImpl)GroupResultHelper.getOneResult(getCardInfo(card));
	                Currency         currency     = card.getCurrency();
                    CardAbstractImpl cardAbstract = new CardAbstractImpl(currency, fromDate, toDate);

	                toDate.add(Calendar.DAY_OF_MONTH, 1);
	                Money openingBalance = getCardBalance(card, fromDate, currency);
                    Money closingBalance = getCardBalance(card, toDate, currency);
	                if (closingBalance == null) {
		                closingBalance = openingBalance;
	                }
	                List<TransactionBase> cardOperations = getCardOperations(card, fromDate, toDate);
                    //List<CardOperation> unsettledOperations = getCardOperations(card, fromDate, toDate, "GetUnsettledOperations");

	                if(card.isMain())
	                {
                        cardAbstract.setOpeningBalance(openingBalance);
                        cardAbstract.setClosingBalance(closingBalance);
	                }
                    cardAbstract.setTransactions(cardOperations);
	                //cardAbstract.setUnsettledOperations(unsettledOperations);// фтекущей реализации не используется

                    return cardAbstract;
                }
            });
        }
        catch (IKFLException e)
        {
	        log.error("Ошибка при получении cardInfo", e);
	        throw new GateException(e);
        }
        catch (Exception e)
        {
           throw new GateException(e);
        }
	}

	Money getCardBalance(final Card card, final Calendar date, final Currency currency) throws GateException
    {
        try
        {
            return RSRetailV6r4Executor.getInstance().execute(new HibernateAction<Money>()
            {
                public Money run(Session session) throws Exception
                {
	                final Long cardId = CardImpl.parseCardId(card.getId());

                    Query query = createReadonlyQuery(session, "GetCardBalance")
                        .setParameter("cardId", cardId)
						.setParameter("toDate", date.getTime());

                    Double val    = (Double) query.uniqueResult();
                    String strVal = val == null ? "0" : val.toString();

                    return new Money(new BigDecimal(strVal), currency);
                }
            });
        }
        catch (Exception e)
        {
           throw new GateException(e);
        }
    }

	public List<Card> getClientCards(final Client client) throws GateException, GateLogicException
	{
        try
        {
            List<Card> cards = RSRetailV6r4Executor.getInstance().execute(new HibernateAction<List<Card>>()
            {
                public List<Card> run(Session session) throws Exception
                {
                    Query query = createReadonlyQuery(session, "GetClientCards")
                            .setParameter("clientId", Long.decode(client.getId()));

                    //noinspection unchecked
                    List<Card> cards = query.list();

	                if (cards != null)
	                {
		                for (Card card : cards)
		                {
			                CardImpl cardI = (CardImpl)card;
			                cardI.setCardType(getCardType(card));//устанавливаем тип карты (Дебетовая, Кредитная, Овердрафт)
		                }
	                }

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

	private void fillCard(CardImpl card) throws GateException, GateLogicException
	{
		card.setCardType(getCardType(card));
		//Заполнение СКС по карте
		fillPrimaryAccount(card);
	}


	public GroupResult<String, Card> getCard(String... externalIds)
	{
        GroupResult<String, Card> res = new GroupResult<String, Card>();
		for (String externalId: externalIds)
        {
			final Long cardId   = CardImpl.parseCardId(externalId);
			final Long clientId = CardImpl.parseOwnerId(externalId);

			try
			{
				CardImpl card = RSRetailV6r4Executor.getInstance().execute(new HibernateAction<CardImpl>()
				{
					public CardImpl run(Session session) throws Exception
					{
						Query query = createReadonlyQuery(session, "GetCard")
								.setParameter("cardId",cardId).setParameter("clientId", clientId)
								;

						return ( CardImpl ) query.uniqueResult();
					}
				});

				card.setOwnerId(clientId);
				fillCard(card);
				res.putResult(externalId, card);
			}
			catch (IKFLException e)
			{
				res.putException(externalId, e);
			}
			catch (Exception e)
			{
				res.putException(externalId, new SystemException(e));
			}
        }
		return res;
	}

	public GroupResult<Card, CardInfo> getCardInfo(final Card... cards)
	{
		GroupResult<Card, CardInfo> res = new GroupResult<Card, CardInfo>();
		for (Card card:cards)
		{
			final Long cardId   = CardImpl.parseCardId(card.getId());

			try
			{
				res.putResult(card, RSRetailV6r4Executor.getInstance().execute(new HibernateAction<CardInfoImpl>()
				{
					public CardInfoImpl run(Session session) throws Exception
					{
						Query cardInfoQuery = createReadonlyQuery(session, "GetCardInfo")
								.setParameter("cardId", cardId);

						CardInfoImpl cardInfo = (CardInfoImpl) cardInfoQuery.uniqueResult();

						Query lastOperationQuery = createReadonlyQuery(session, "GetCardLastOperationDate")
											.setParameter("cardId", cardId);

						Calendar operationDate = (Calendar) lastOperationQuery.uniqueResult();
						if (operationDate != null && cardInfo != null)
						   cardInfo.setLastOperationDate(operationDate);
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

	public GroupResult<Card, List<Card>> getAdditionalCards(Card... mainCards)
	{
		GroupResult<Card, List<Card>> res = new GroupResult<Card, List<Card>>();
		for (Card mainCard: mainCards)
		{
			try
			 {
				if(!mainCard.isMain())
					throw new GateException("Аргумент должен быть ОСНОВНОЙ картой");
				 Account account = getCardAccount(mainCard);
				 final String referenc = account.getId();

				 List<Card> cards = RSRetailV6r4Executor.getInstance().execute(new HibernateAction<List<Card>>()
				 {
					 public List<Card> run(Session session) throws Exception
					 {
						 String queryName = "GetAdditionalCards";
						 Query query = createReadonlyQuery(session, queryName);
						 query.setParameter("accId", referenc);

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

	public GroupResult<Card, Account> getCardPrimaryAccount(Card... cards)
	{
		GroupResult<Card, Account> res = new GroupResult<Card, Account>();
		for (Card card: cards)
		{
			final Long cardId   = CardImpl.parseCardId(card.getId());

			try
			{
				res.putResult(card, RSRetailV6r4Executor.getInstance().execute(new HibernateAction<Account>()
				{
					public Account run(Session session) throws Exception
					{
						Query query = createReadonlyQuery(session, "GetCardPrimaryAccountId")
								.setParameter("cardId", cardId);

						Long accountId = (Long) query.uniqueResult();
						if (accountId == null)
							return null;
						else return getAccount(accountId);
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
			final Long cardId   = CardImpl.parseCardId(card.getId());
			try
			{
				Long clientId = RSRetailV6r4Executor.getInstance().execute(new HibernateAction<Long>()
				{
					public Long run(Session session) throws Exception
					{
						Query query = createReadonlyQuery(session, "GetCardOwnerId")
								.setParameter("cardId", cardId);
						return (Long) query.uniqueResult();

					}
				});
				ClientService service = new ClientServiceImpl();
				res.putResult(card, service.getClientById(clientId.toString()));
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

	public GroupResult<Pair<String, Office>, Client> getOwnerInfoByCardNumber(Pair<String, Office>... cardInfo)
	{
		throw new UnsupportedOperationException();
	}

	public GroupResult<Card, OverdraftInfo> getCardOverdraftInfo(Calendar date, Card... cards)
	{
		/*
		по ТЗ:
		Calendar getOpenDate();В текущей реализации не используется
		Money getLimit();В текущей реализации не используется
		Money getUnsettledDebtSum();В текущей реализации не используется
		Money getUnsettledPenalty();В текущей реализации не используется
		Money getCurrentOverdraftSum();
		Money getRate();В текущей реализации не используется
		Money getTechnicalOverdraftSum();В текущей реализации не используется
		Money getTechnicalPenalty();В текущей реализации не используется
		Calendar getUnsetltedDebtCreateDate();В текущей реализации не используется
		*/
		GroupResult<Card, OverdraftInfo> res = new GroupResult<Card, OverdraftInfo>();
		for (final Card card: cards)
		{
			try
			{
				final Long cardId   = CardImpl.parseCardId(card.getId());
				OverdraftInfoImpl overdraftInfo = RSRetailV6r4Executor.getInstance().execute(new HibernateAction<OverdraftInfoImpl>()
				{
					public OverdraftInfoImpl run(Session session) throws Exception
					{
						Query query = createReadonlyQuery(session, "GetOverdraftInfo")
								.setParameter("cardId", cardId);

						return (OverdraftInfoImpl) query.uniqueResult();
					}
				});
				if(overdraftInfo == null)
					return null;
				BigDecimal totalDebtSumValue = RSRetailV6r4Executor.getInstance().execute(new HibernateAction<BigDecimal>()
				{
					public BigDecimal run(Session session) throws Exception
					{
						Query query = createReadonlyQuery(session, "GetTotalDebtSum")
								.setParameter("cardId", Long.valueOf(card.getId()));

						Double aDouble = query.uniqueResult() == null ? 0.0 : (Double)query.uniqueResult();
						return new BigDecimal( aDouble );
					}
				});
				overdraftInfo.setTotalDebtSum(new Money(totalDebtSumValue, overdraftInfo.getCurrentOverdraftSum().getCurrency()));
				BigDecimal availableLimitValue = RSRetailV6r4Executor.getInstance().execute(new HibernateAction<BigDecimal>()
				{
					public BigDecimal run(Session session) throws Exception
					{
						Query query = createReadonlyQuery(session, "GetAvailableLimit")
								.setParameter("cardId", Long.valueOf(card.getId()));

						Double aDouble = query.uniqueResult() == null ? 0.0 : (Double)query.uniqueResult();
						return new BigDecimal( aDouble );
					}
				});
				res.putResult(card, overdraftInfo);
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

	public GroupResult<Account, Client> getOwnerInfo(Account... accounts)
	{
		GroupResult<Account, Client> res = new GroupResult<Account, Client>();
		for (Account account: accounts)
		{
			final Long accountId = Long.valueOf(account.getId());
			try
			{
				Long clientId = RSRetailV6r4Executor.getInstance().execute(new HibernateAction<Long>()
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

	private Query createReadonlyQuery(Session session, String queryName)
    {
        Query query = session.getNamedQuery(queryName);
        query.setReadOnly(true).setFlushMode(FlushMode.NEVER);

        return query;
    }


	/* из document считывает список транзакций по тэгу tagName, заполняет массив*/
	private List<TransactionBase> fillAccountTransactions(Document document, String tagName) throws GateException, GateLogicException
	{
		List<TransactionBase> accountTransactions = new ArrayList<TransactionBase>();

		Element root = document.getDocumentElement();
		try {
			NodeList list = XmlHelper.selectNodeList(root, tagName);

			int length = list.getLength();

			for(int i = 0; i < length; i++)
			{
				Currency curr = null;
				CurrencyService currencyService = new CurrencyServiceImpl(getFactory());

				AccountTransactionImpl operation = new AccountTransactionImpl();
				Element elem = (Element)list.item(i);

				operation.setDate(RetailXMLHelper.getElementDate(elem, "date"));

				String valueParam = getParam(elem, "transactionCurrencyId");
				if (valueParam != null)
				{
					curr = currencyService.findById(valueParam);
				}
				operation.setCreditSum(RetailXMLHelper.getElementMoney(elem, "creditSum", curr));
				operation.setDebitSum(RetailXMLHelper.getElementMoney(elem, "debitSum", curr));
				operation.setBalance(RetailXMLHelper.getElementMoney(elem, "balance", curr));

				operation.setDescription(RetailXMLHelper.getElementValue(elem, "description"));
				operation.setCounteragent(RetailXMLHelper.getElementValue(elem, "counteragent"));
				operation.setCounteragentBank(RetailXMLHelper.getElementValue(elem, "counteragentBank"));
				operation.setCounteragentAccount(RetailXMLHelper.getElementValue(elem, "counteragentAccount"));
				operation.setBookAccount(RetailXMLHelper.getElementValue(elem, "bookAccount"));
				operation.setDocumentNumber(RetailXMLHelper.getElementValue(elem, "documentNumber"));
				operation.setOperationCode(RetailXMLHelper.getElementValue(elem, "operationCode"));

				accountTransactions.add(operation);
			}

		}
		catch (TransformerException ex)
		{
			throw new GateException(ex);
		}

		return accountTransactions;
	}

	/**
	 *
	 * @param document xml-документ
	 * @param tagName имя тэга для считывания
	 * @return  значение считываемого тега
	 * @throws GateException
	 */
	private List<Trustee> fillAccountTrustees(Document document, String tagName) throws GateException
	{
		List<Trustee> accountTrustees = new ArrayList<Trustee>();

		NodeList list = document.getElementsByTagName(tagName);
		int length = list.getLength();

		for(int i = 0; i < length; i++)
		{
			TrusteeImpl operation = new TrusteeImpl();
			Element elem = (Element)list.item(i);

			operation.setEndingDate(RetailXMLHelper.getElementDate(elem, "endingDate"));

			String valueParam = getParam(elem, "fullName");
			if (valueParam != null)
				operation.setName(valueParam);

			accountTrustees.add(operation);
		}

		return accountTrustees;
	}

    private List<TransactionBase> getCardOperations(final Card card, final Calendar fromDate, final Calendar toDate) throws GateException
    {
	    final Long cardId   = CardImpl.parseCardId(card.getId());

        try
        {
            return RSRetailV6r4Executor.getInstance().execute(new HibernateAction<List<TransactionBase>>()
            {
                public List<TransactionBase> run(Session session) throws Exception
                {
	                String  queryName = "GetAdditionalCardOperations";
	                if (card.isMain()){
	                    queryName = "GetOperations";
	                }
                    Query query = createReadonlyQuery(session, queryName)
                                        .setParameter("fromDate", fromDate.getTime())
                                        .setParameter("toDate", toDate.getTime())
                                        .setParameter("cardId", cardId);

	                //noinspection unchecked
	                List<CardOperationImpl> cardOperations = query.list();
	                for (CardOperationImpl cardOperation : cardOperations)
	                {
		                String externalId = cardOperation.getOperationCardId();
		                if (externalId != null){
		                    Card operationCard = GroupResultHelper.getOneResult(getCard(externalId));
		                    cardOperation.setOperationCard(operationCard);
		                }
	                }

                    return (List<TransactionBase>) (cardOperations != null  ?  cardOperations  :  new ArrayList<TransactionBase>());
                }
            });
        }
        catch (Exception e)
        {
            throw new GateException(e);
        }
    }

	private List<TransactionBase> getCardOperations(final Card card, final Long number) throws GateException
    {
	    final Long cardId   = CardImpl.parseCardId(card.getId());

        try
        {
            return RSRetailV6r4Executor.getInstance().execute(new HibernateAction<List<TransactionBase>>()
            {
                public List<TransactionBase> run(Session session) throws Exception
                {
	                String  queryName = "GetAdditionalCardOperationsByNumber";
	                if (card.isMain()){
	                    queryName = "GetOperationsByNumber";
	                }
                    Query query = createReadonlyQuery(session, queryName)
                                        .setParameter("cardId", cardId)
                                        .setParameter("number", number);

	                //noinspection unchecked
	                List<CardOperationImpl> cardOperations = query.list();
	                for (CardOperationImpl cardOperation : cardOperations)
	                {
		                String externalId = cardOperation.getOperationCardId();
		                if (externalId != null){
		                    Card operationCard = GroupResultHelper.getOneResult(getCard(externalId));
		                    cardOperation.setOperationCard(operationCard);
		                }
	                }

                    return (List<TransactionBase>) (cardOperations != null  ?  cardOperations  :  new ArrayList<TransactionBase>());
                }
            });
        }
        catch (Exception e)
        {
            throw new GateException(e);
        }
    }

	private String getParam(Element element, String name)
	{
		NodeList list = element.getElementsByTagName(name);
		if (list.getLength() == 0)
		{
			return null;
		}
		String valueElem = list.item(0).getTextContent();
		return valueElem.length() == 0 ? null : valueElem;
	}

	/**
	 * справка о состоянии вклада
	 * @param account  счет
	 * @param fromDate дата1
	 * @param toDate   дата2
	 * @return  требуемые для справки о состоянии данные.
	 * @throws GateException
	 */
	public AccountAbstract getAccountExtendedAbstract(Account account, Calendar fromDate, Calendar toDate) throws GateException, GateLogicException
	{
		Document document = null;
		final Long        primaryAccountId = Long.valueOf(account.getId());


		WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage message = service.createRequest("getAccountTransactions_q");
		message.addParameter("accountReferenc", primaryAccountId);
		message.addParameter("fromDate", String.format("%1$te.%1$tm.%1$tY", fromDate.getTime()));
		message.addParameter("toDate",   String.format("%1$te.%1$tm.%1$tY", toDate.getTime()));

		try
		{
			document = service.sendOnlineMessage(message, null);
		}
		catch (GateLogicException e)
		{
			throw new GateException(e.getMessage());
		}

		AccountAbstractImpl accountAbstract = new AccountAbstractImpl(account.getCurrency(), fromDate, toDate);


		Element root = document.getDocumentElement();

		//дата предыдущей операции
		accountAbstract.setPreviousOperationDate(RetailXMLHelper.getElementDate(root, "previousOperationDate"));

		// процентная ставка
		String value = RetailXMLHelper.getElementValue(root, "interestRate");
//		if (value != null)
//			accountAbstract.setInterestRate(new BigDecimal(value.trim()));


		//дата закрытия счета (если закрыт)
		accountAbstract.setClosedDate(RetailXMLHelper.getElementDate(root, "closedDate"));

		accountAbstract.setClosedSum(RetailXMLHelper.getElementMoney(root, "closedSum", account.getCurrency()));

		// транзакции
		List<TransactionBase> accountTransactions = fillAccountTransactions(document, "transactions/transaction");
		accountAbstract.setTransactions(accountTransactions);

		//доверенности
		List<Trustee> accountTrustees = fillAccountTrustees(document, "trustees/trustee");
		accountAbstract.setTrusteesDocuments(accountTrustees);

		accountAbstract.setOpeningBalance(RetailXMLHelper.getElementMoney(root, "incomingRest", account.getCurrency()));
		accountAbstract.setClosingBalance(RetailXMLHelper.getElementMoney(root, "outcomingRest", account.getCurrency()));

		return accountAbstract;

	}

	/**
	 * получить счет по номеру в заданном офисе
	 * @param accountInfo - информация о счетах - номер счета и офис
	 * @return  список счетов
	 */
	public GroupResult<Pair<String, Office>, Account> getAccountByNumber(Pair<String, Office>... accountInfo)
	{
		GroupResult<Pair<String, Office>, Account> result = new GroupResult<Pair<String, Office>, Account>();
		for (Pair<String, Office> info: accountInfo)
		{
			try
			{
				AccountImpl accountImpl = getAccountByNumber(info.getFirst());
				fillOffice(accountImpl);
				fillRate(accountImpl);
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

	protected void fillOffice(AccountImpl accountImpl) throws GateLogicException, GateException
    {
	    OfficeGateService officeGateService = getFactory().service(OfficeGateService.class);
	    accountImpl.setOffice(officeGateService.getOfficeById(String.valueOf(accountImpl.getBranch())));
    }

	protected void fillRate(AccountImpl accountImpl) throws GateLogicException, GateException
	{
		WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		Date currentDate = DateHelper.getCurrentDate().getTime();

		GateMessage message = service.createRequest("getAccountTransactions_q");
		message.addParameter("accountReferenc", accountImpl.getId());
		message.addParameter("fromDate", String.format("%1$te.%1$tm.%1$tY", currentDate));
		message.addParameter("toDate", String.format("%1$te.%1$tm.%1$tY", currentDate));

		Document document = service.sendOnlineMessage(message, null);
		Element root = document.getDocumentElement();

		// процентная ставка
		String value = RetailXMLHelper.getElementValue(root, "interestRate");
		if (value != null)
			accountImpl.setInterestRate(new BigDecimal(value.trim()));
	}

	/**
	 * Заполнение СКС по карте 
	 * @param card - карта
	 */
	protected void fillPrimaryAccount(final CardImpl card) throws GateException, GateLogicException
	{
		GroupResult<Card, Account> res = new GroupResult<Card, Account>();
		try
		{
			long accountId = RSRetailV6r4Executor.getInstance().execute(new HibernateAction<Long>()
			{
				public Long run(Session session) throws Exception
				{
					Long cardId   = CardImpl.parseCardId(card.getId());
					Query query = createReadonlyQuery(session, "GetCardPrimaryAccountId")
							.setParameter("cardId", cardId);

					return (Long) query.uniqueResult();
					}
				});
			card.setPrimaryAccountExternalId(String.valueOf(accountId));
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

	private Account getCardAccount(Card card) throws SystemException, LogicException
	{
		Account primaryAccount = GroupResultHelper.getOneResult(getCardPrimaryAccount(card));
		if (primaryAccount == null)
			throw new GateException("Не обнаружен карт-счет для карты: "+card.getNumber());
		return primaryAccount;
	}
}
