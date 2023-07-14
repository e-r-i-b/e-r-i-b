package com.rssl.phizicgate.sbrf.bankroll;

import com.rssl.phizgate.common.services.bankroll.ExtendedCodeGateImpl;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.OfficeCodeReplacer;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.*;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.clients.ClientSex;
import com.rssl.phizic.gate.config.SpecificGateConfig;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.deposit.Deposit;
import com.rssl.phizic.gate.deposit.DepositInfo;
import com.rssl.phizic.gate.deposit.DepositService;
import com.rssl.phizic.gate.dictionaries.officies.BackRefOfficeGateService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.GateMessagingClientException;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.messaging.impl.DOMMessageImpl;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.DocumentConfig;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.sbrf.client.ClientDocumentImpl;
import com.rssl.phizicgate.sbrf.client.SBRFClient;
import com.rssl.phizicgate.sbrf.depoCOD.DepoCODTransportProvider;
import com.rssl.phizicgate.sbrf.ws.util.ServiceReturnHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.xml.transform.TransformerException;

/**
 * @author Kidyaev
 * @ created 30.09.2005
 * @ $Author$
 * @ $Revision$
 */
public class BankrollServiceImpl extends AbstractService implements BankrollService
{
	private static final String FIX_CLIENT_ID = "0";
	
	/*
	*  CHG027259
	* */


	public BankrollServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public List<Account> getClientAccounts(Client client) throws GateException
	{
		return new ArrayList<Account>();
	}

	public GroupResult<String, Account> getAccount(String... accountIds)
	{
		GroupResult<String, Account> res = new GroupResult<String, Account>();
		for (String accountId: accountIds)
		{
			try{
				Document accountXML = getAccountInfoFromCOD(accountId); // информация о счете из ЦОДа
				res.putResult(accountId, readAccount(accountXML, accountId));
			}
			catch (IKFLException e)
			{
				res.putException(accountId, e);
			}
		}
		return res;
	}

	private Document getAccountInfoFromCOD(String accountNumber) throws GateException, GateLogicException
	{
		WebBankServiceFacade serviceFacade = getFactory().service(WebBankServiceFacade.class);

		GateMessage request = serviceFacade.createRequest("accountInfoDemand_q");
		request.addParameter("account", accountNumber);

		Document document = serviceFacade.sendOnlineMessage(request, null);

		return document;
	}

	/*  заолнение информации о счете из xml */
	private AccountImpl readAccount(Document responce, String accountNumber) throws GateException, GateLogicException
	{
		WebBankServiceFacade serviceFacade = getFactory().service(WebBankServiceFacade.class);

		Element root = responce.getDocumentElement();

		String openAccountDate = XmlHelper.getSimpleElementValue(root, "openDate");
		String accountName = XmlHelper.getSimpleElementValue(root, "accName");
		Boolean isCreditAllowed = Boolean.parseBoolean(XmlHelper.getSimpleElementValue(root, "isCreditAllowed"));
		Boolean isDebitAllowed = Boolean.parseBoolean(XmlHelper.getSimpleElementValue(root, "isDebitAllowed"));
		String status = XmlHelper.getSimpleElementValue(root, "status");
		String balance = XmlHelper.getSimpleElementValue(root, "balance");

		String branch = XmlHelper.getSimpleElementValue(root, "branch");
		String office = XmlHelper.getSimpleElementValue(root, "office");

		AccountImpl account = new AccountImpl();
		if (!StringHelper.isEmpty(openAccountDate))
			account.setOpenDate(XMLDatatypeHelper.parseDate(openAccountDate));
		else account.setOpenDate(null);
		account.setType(accountName);
		account.setDescription(accountName);
		account.setNumber(accountNumber);
		account.setId(accountNumber);
		account.setCurrency(calculateCurrency(accountNumber));
		account.setCreditAllowed(isCreditAllowed);
		account.setDebitAllowed(isDebitAllowed);
		account.setAccountState(AccountState.valueOf(status));
		account.setBalance( new Money( new BigDecimal(balance), account.getCurrency()));
		account.setOffice(getAccountOffice(branch, office));

		GateMessage accountBalance_q = serviceFacade.createRequest("accountBalanceDemand_q");
		accountBalance_q.addParameter("clientId", FIX_CLIENT_ID);
		accountBalance_q.addParameter("account", accountNumber);
		Document accountBalance_a = serviceFacade.sendOnlineMessage(accountBalance_q, null);
		Element accountBalance = accountBalance_a.getDocumentElement();
		String availibleBalance = XmlHelper.getSimpleElementValue(accountBalance, "availableBalance");
		balance = XmlHelper.getSimpleElementValue(root, "balance");
		account.setMaxSumWrite(new Money(new BigDecimal(availibleBalance), account.getCurrency()));
		account.setBalance( new Money( new BigDecimal(balance), account.getCurrency()));


		Document billingDemand_a = accountAbstractRequest(accountNumber, DateHelper.getCurrentDate(), DateHelper.getCurrentDate(), false);		
		Element billingDemand = billingDemand_a.getDocumentElement();
		String interestRate = XmlHelper.getSimpleElementValue(billingDemand, "interestRate");

		account.setInterestRate(new BigDecimal(interestRate));
		account.setLastTransactionDate(Calendar.getInstance());
		return account;
	}

	private Currency calculateCurrency(String accountNumber) throws GateException, GateLogicException
	{
		String numericCurrencyCode = calculateNumeticCurrencyCode(accountNumber);
		CurrencyService currencyService = getCurrencyService();

        Currency currency = currencyService.findByNumericCode(numericCurrencyCode);
		if (currency == null)
			throw new UnknownAccountCurrencyException(accountNumber);

		return currency;
	}

	/** Патч - затычка по случаю смены кода валюты у России */
	private String calculateNumeticCurrencyCode(String accountNumber) throws GateException
	{
		if (accountNumber.length() < 9) throw new GateException("Неправильная длина номера счета");
		String code = accountNumber.substring(5, 8);
		return code.equals("810") ? "643" : code;
	}


	// Получение офиса для объекта счет, с переконвертированием из старых кодов (CHG027259)
	private Office getAccountOffice(String branch, String office) throws GateException, GateLogicException
	{

		branch = ConfigFactory.getConfig(OfficeCodeReplacer.class).replaceCode(ConfigFactory.getConfig(DocumentConfig.class).getOfficeCodeRegion(), branch);

		try{
			return getSBRFOffice(branch, office);
		} catch (GateException e)
		{
			return getSBRFOffice(branch, null);
		}
	}

	//получение офиса по  branch и office
	private Office getSBRFOffice(String branch, String office) throws GateException, GateLogicException
    {
	    BackRefOfficeGateService officeGateService = getFactory().service(BackRefOfficeGateService.class);

	    // идем в базу, офис должен быть там
	    Office officeImpl = officeGateService.getOfficeByCode(new ExtendedCodeGateImpl(ConfigFactory.getConfig(DocumentConfig.class).getOfficeCodeRegion(), branch, office));
	    if(officeImpl == null)
	        throw new GateException("Не найден офис. branch=" + branch + " ." + "office_string=" + office);

		return officeImpl;
	}

	public GroupResult<Object, AbstractBase> getAbstract(java.lang.Long number, Object... object)
	{
		if (object[0] instanceof Account)
		       return getAccountAbstract(object);
		throw new UnsupportedOperationException();
	}

	public AbstractBase getAbstract(Object object, Calendar fromDate, Calendar toDate, Boolean withCardUseInfo) throws GateLogicException, GateException
	{
		if(object instanceof Account)
			return getAccountAbstract((Account)object,fromDate,toDate);
		else if(object instanceof Card)
			return getCardAbstract((Card)object,fromDate,toDate);
		else if(object instanceof Deposit){
			DepositService depositService= ServiceReturnHelper.getInstance().service(object, DepositService.class);
			DepositInfo depositInfo = depositService.getDepositInfo((Deposit) object);
			return getAccountAbstract(depositInfo.getAccount(),fromDate,toDate);
		}
		else
			throw new GateException("Неверный тип объекта");
//		throw new GateException("Нереализовано");
	}

 	public AccountAbstract getAccountAbstract(final Account account, final Calendar fromDate, final Calendar toDate) throws GateException, GateLogicException
	{
		Document document = accountAbstractRequest(account, fromDate, toDate, false);

		AccountAbstractImpl accountAbstract = new AccountAbstractImpl();
		fillAbstract(document, accountAbstract);

		return accountAbstract;
	}

	protected void fillAbstract(Document document, AccountAbstractImpl accountAbstract) throws GateException, GateLogicException
	{
		CurrencyService currencyService = getFactory().service(CurrencyService.class);

		Element response = document.getDocumentElement();
		String startDate = XmlHelper.getSimpleElementValue(response, "startDate");
		String endDate = XmlHelper.getSimpleElementValue(response, "endDate");
		String openingBalance = XmlHelper.getSimpleElementValue(response, "beginningBalance");
		String closingBalance = XmlHelper.getSimpleElementValue(response, "outBalance");
		String currencyCode = XmlHelper.getSimpleElementValue(response, "currencyCode");
		String previousOperationDate = XmlHelper.getSimpleElementValue(response, "prevOperDate");
		NodeList nodeList = null;
		try
		{
			nodeList = XmlHelper.selectNodeList(response, "row");
		}
		catch(TransformerException ex)
		{
			throw new GateException(ex);
		}

		if (!StringHelper.isEmpty(startDate))
			accountAbstract.setFromDate( XMLDatatypeHelper.parseDate(startDate) );
		else accountAbstract.setFromDate( null );
		if (!StringHelper.isEmpty(endDate))
			accountAbstract.setToDate( XMLDatatypeHelper.parseDate(endDate) );
		else accountAbstract.setToDate( null );
		Currency currency = currencyService.findByAlphabeticCode( currencyCode );
		accountAbstract.setOpeningBalance( new Money(new BigDecimal(openingBalance),currency) );
		if(closingBalance!=null && closingBalance.length()!=0)
			accountAbstract.setClosingBalance( new Money(new BigDecimal(closingBalance),currency) );
		if(previousOperationDate!=null && previousOperationDate.length()!=0)
			accountAbstract.setPreviousOperationDate(XMLDatatypeHelper.parseDate(previousOperationDate));

		List<TransactionBase> transactions = new ArrayList<TransactionBase>(nodeList.getLength());
		for (int i = 0; i < nodeList.getLength(); i++)
		{
			Element element = (Element) nodeList.item(i);
			String balance = XmlHelper.getSimpleElementValue(element, "balance");
			String corAccount = XmlHelper.getSimpleElementValue(element, "correspondent");
			String isDepit = XmlHelper.getSimpleElementValue(element, "debit");
			String sum = XmlHelper.getSimpleElementValue(element, "sum");
			String date = XmlHelper.getSimpleElementValue(element, "date");
			String description = XmlHelper.getSimpleElementValue(element, "name");
			String documentNumber = XmlHelper.getSimpleElementValue(element, "document");
			String operatonType = XmlHelper.getSimpleElementValue(element, "aspect");

			AccountTransactionImpl transaction = new AccountTransactionImpl();
			transaction.setBalance(new Money(new BigDecimal(balance),currency));
			transaction.setCounteragentAccount(corAccount);
			if(Boolean.parseBoolean(isDepit))
			{
				transaction.setDebitSum( new Money(new BigDecimal(sum),currency) );
			}
			else
			{
				transaction.setCreditSum( new Money(new BigDecimal(sum),currency) );
			}
			if (!StringHelper.isEmpty(date))
				transaction.setDate( XMLDatatypeHelper.parseDate(date) );
			else transaction.setDate(null);
			transaction.setDescription( description );
			transaction.setDocumentNumber(documentNumber);
			transaction.setOperationCode(operatonType);

			transactions.add( transaction );
		}
		accountAbstract.setTransactions( transactions );
	}

	protected Document accountAbstractRequest(Account account, Calendar startDate, Calendar endDate, boolean isExtended) throws GateException, GateLogicException
	{
		return accountAbstractRequest(account.getNumber(), startDate, endDate, isExtended);
	}

	private Document accountAbstractRequest(String accountNumber, Calendar startDate, Calendar endDate, boolean isExtended) throws GateException, GateLogicException
	{
		// Здесь обыгрывается ситуация,
		// когда клиент хочет получить выписку ранним утром следующего дня
		// (т.е. в то время, когда дату опердня ещё не успели актуализировать сотрудники).
		// В таких условиях ЦОД отказывается работать и выдаёт ошибку типа "ERROR_CLIENT"
		// со словами "Дата начала периода выписки должна быть меньше либо равна yyyy-MM-dd ."
		// см. BUG019550: Ошибка при открытии страницы счетов и вкладов
		//
		// Алгоритм решения следующий.
		// В случае ошибки ERROR_CLIENT:
		//    попытаться распарсить ответ и получить из него дату опердня
		//    повторить попытку с датой начала = дата опердня

		// 1. Попытка получить выписку с оригинальной датой начала
		try
		{
			return tryAccountAbstractRequest(accountNumber, startDate, endDate, isExtended);
		}
		catch (GateMessagingClientException ex)
		{
			// 2. Попытка получить выписку с датой начала, указанной в тексте ошибки...
			DateFormat format = new SimpleDateFormat("'Дата начала периода выписки должна быть меньше либо равна 'dd.MM.yyyy");
			try	{
				startDate = DateHelper.toCalendar(format.parse(ex.getMessage()));
				if (startDate != null)
					return tryAccountAbstractRequest(accountNumber, startDate, endDate, isExtended);
			}
			// не удалось найти дату в тексте ошибки (другая ошибка или формат поменялся)
			catch (ParseException ignored) {}

			throw ex;
		}
	}

	private Document tryAccountAbstractRequest(String accountNumber, Calendar fromDate, Calendar toDate, boolean isExtended) throws GateException, GateLogicException
	{
		WebBankServiceFacade serviceFacade = getFactory().service(WebBankServiceFacade.class);
		GateMessage requestCOD = serviceFacade.createRequest("billingDemand_q");
		requestCOD.addParameter("clientId", FIX_CLIENT_ID);
		requestCOD.addParameter("copying", isExtended);
		requestCOD.addParameter("account", accountNumber);
		requestCOD.addParameter("startDate", DateHelper.toXMLDateFormat(fromDate.getTime()));
		requestCOD.addParameter("endDate", DateHelper.toXMLDateFormat(toDate.getTime()));

		return serviceFacade.sendOnlineMessage(requestCOD.getDocument(), null);
	}

	public List<Card> getClientCards(Client client) throws GateException
	{
		return new ArrayList<Card>();
	}

	public GroupResult<String, Card> getCard(String... cardNumber)
	{
		GroupResult<String, Card> cards = new GroupResult<String, Card>();

		for (String cardNum: cardNumber)
		{
			try{
				Currency currency = getFactory().service(CurrencyService.class).getNationalCurrency();
				SBRFCardImpl card = new SBRFCardImpl();
				card.setNumber(cardNum);
				card.setId(cardNum);
				card.setMain(true);
				//Client owner = getOwnerInfo(account);
				card.setDescription("Банковская Карта");
				Calendar date = Calendar.getInstance();
				date.set(Calendar.YEAR, 2000);
				card.setIssueDate(date);
				date.set(Calendar.YEAR, 3000);
				card.setExpireDate(date);
				card.setDisplayedExpireDate(DateHelper.toDisplayedExpiredate(date));
				card.setCardState(CardState.active);
				card.setAvailableLimit(new Money(new BigDecimal(1234521L),currency));
				card.setCurrency(currency);
				//Заполнение СКС карты
				fillPrimaryAccount(card);
				cards.putResult(cardNum, card);
			}catch (IKFLException ex)
			{
				cards.putException(cardNum, ex);
			}
		}
		return cards;
	}

	public GroupResult<Account, Client> getOwnerInfo(Account... accounts)
	{
		Document document = null;
		GroupResult<Account, Client> res = new GroupResult<Account, Client>();
		for (Account account: accounts)
		{
			try
			{
				document = getAccountInfoFromCOD(account.getNumber());
				Client client = readClient(document);
				res.putResult(account,  client);
			}
			catch(IKFLException ex)
			{
				res.putException(account,  ex);
			}
		}
		return res;
	}

	private Client readClient(Document document) throws GateException, GateLogicException
	{
		SBRFClient client =  new SBRFClient();
		Element root = document.getDocumentElement();

		try
		{
			Element depositor = XmlHelper.selectSingleNode(root, "depositor");
			String fullName = XmlHelper.getSimpleElementValue(depositor, "fullName");
			client.setFullName(fullName);
			String birthDay  = XmlHelper.getSimpleElementValue(depositor, "birthdate");
			if(birthDay != null)
			{
				client.setBirthDay(XMLDatatypeHelper.parseDate(birthDay));
			}
			String gender  = XmlHelper.getSimpleElementValue(depositor, "gender");
			if(gender != null)
			{
				if(gender.equals("MALE"))
					client.setSex(ClientSex.GENDER_MALE);
				if(gender.equals("FEMALE"))
					client.setSex(ClientSex.GENDER_FEMALE);
			}
			ClientDocumentImpl clientDocument = new ClientDocumentImpl();

			Element identityCard = XmlHelper.selectSingleNode(depositor, "identityCard");
			String type = XmlHelper.getSimpleElementValue(identityCard, "type");
			if(type.equals("REGULAR_PASSPORT_RF"))
				clientDocument.setDocumentType(ClientDocumentType.REGULAR_PASSPORT_RF);
			if(type.equals("MILITARY_IDCARD"))
				clientDocument.setDocumentType(ClientDocumentType.MILITARY_IDCARD);
			if(type.equals("SEAMEN_PASSPORT"))
				clientDocument.setDocumentType(ClientDocumentType.SEAMEN_PASSPORT);
			if(type.equals("RESIDENСЕ_PERMIT_RF"))
				clientDocument.setDocumentType(ClientDocumentType.RESIDENTIAL_PERMIT_RF);
			if(type.equals("FOREIGN_PASSPORT_RF"))
				clientDocument.setDocumentType(ClientDocumentType.FOREIGN_PASSPORT_RF);
			if(type.equals("OTHER"))
				clientDocument.setDocumentType(ClientDocumentType.OTHER);

			String typeName = XmlHelper.getSimpleElementValue(identityCard, "typeName");
			clientDocument.setDocTypeName(typeName);
			String series = XmlHelper.getSimpleElementValue(identityCard, "series");
			clientDocument.setDocSeries(series);
			String number = XmlHelper.getSimpleElementValue(identityCard, "number");
			clientDocument.setDocNumber(number);
			String authoruty = XmlHelper.getSimpleElementValue(identityCard, "authoruty");
			clientDocument.setDocIssueBy(authoruty);
			String dateOfIssue  = XmlHelper.getSimpleElementValue(depositor, "dateOfIssue");
			if(!StringHelper.isEmpty(dateOfIssue))
			{
				clientDocument.setDocIssueDate(XMLDatatypeHelper.parseDate(dateOfIssue));
			}
			List<ClientDocument> documents =  new ArrayList<ClientDocument>(1);
			documents.add(clientDocument);
			client.setDocuments(documents);

			String branch = XmlHelper.getSimpleElementValue(root, "branch");
			String office = XmlHelper.getSimpleElementValue(root, "office");

			client.setOffice(getSBRFOffice(branch, office));
		}
		catch(TransformerException ex)
		{
			throw new GateException(ex);
		}
		return client;
	}

	private CurrencyService getCurrencyService()
	{
		return getFactory().service(CurrencyService.class);
	}

	private GroupResult<Object, AbstractBase> getAccountAbstract(Object... objects)
	{
		GroupResult<Object, AbstractBase> res = new GroupResult<Object, AbstractBase>();
		for (Object object: objects)
		{
			try{
				AccountAbstract accountAbstract = getAccountExtendedAbstract((Account)object, DateHelper.getPreviousMonth(), Calendar.getInstance());
				res.putResult(object, accountAbstract);
			}catch (IKFLException e)
			{
				res.putException(object, e);
			}
		}
		return res;
	}

	public AccountAbstract getAccountExtendedAbstract(Account account, Calendar fromDate, Calendar toDate) throws GateException, GateLogicException
	{
		CurrencyService currencyService = getFactory().service(CurrencyService.class);

		Document document = accountAbstractRequest(account, fromDate, toDate, true);

		AccountAbstractImpl accountAbstract = new AccountAbstractImpl();
		fillAbstract(document, accountAbstract);

		Element response = document.getDocumentElement();

		String closedSum = XmlHelper.getSimpleElementValue(response, "ClosedSum");
		String closedDate = XmlHelper.getSimpleElementValue(response, "ClosedDate");
		String currencyCode = XmlHelper.getSimpleElementValue(response, "currencyCode");
		String additionalInformation = XmlHelper.getSimpleElementValue(response, "AdditionalInform");

		NodeList nodeList = null;
		try
		{
			nodeList = XmlHelper.selectNodeList(response, "Proxy");
		}
		catch(TransformerException ex)
		{
			throw new GateException(ex);
		}

		if(closedSum!=null)
		{
			Currency currency = currencyService.findByAlphabeticCode( currencyCode );
			accountAbstract.setClosedSum(new Money( new BigDecimal(closedSum), currency));
		}
		if(closedDate!=null)
			accountAbstract.setClosedDate(XMLDatatypeHelper.parseDate(closedDate));

		accountAbstract.setAdditionalInformation(additionalInformation);

		List<Trustee> trustees = new ArrayList<Trustee>(nodeList.getLength());
		for (int i = 0; i < nodeList.getLength(); i++)
		{
			Element element = (Element) nodeList.item(i);

			String name = XmlHelper.getSimpleElementValue(element, "name");
			String date = XmlHelper.getSimpleElementValue(element, "date");

			TrusteeImpl trusteesDocument = new TrusteeImpl();
			if (!StringHelper.isEmpty(date))
				trusteesDocument.setEndingDate( XMLDatatypeHelper.parseDate(date) );
			else trusteesDocument.setEndingDate( null );
			trusteesDocument.setName( name );
			trustees.add( trusteesDocument );
		}

		accountAbstract.setTrusteesDocuments(trustees);

		return accountAbstract;
	}

	public CardAbstract getCardAbstract(final Card card, final Calendar fromDate, final Calendar toDate) throws GateException
	{
		// TODO Или имплементировать или вообще убрать и заменить XML версией
		return new SBRFCardAbstract();
	}

	public GroupResult<Card, List<Card>> getAdditionalCards(Card... mainCard)
	{
		//todo заглушка, в сбербанке нельзя получить такую информацию.
		return new GroupResult<Card, List<Card>>();
	}

	public GroupResult<Card, Account> getCardPrimaryAccount(Card... cards)
	{
		RequestHelper helper = new RequestHelper(getFactory());

		GroupResult<Card, Account> res = new GroupResult<Card, Account>();
			for (Card card: cards)
			{
				try{
					String account = helper.getCardAccountNumber(card.getNumber());
					res.putResult(card, GroupResultHelper.getOneResult(getAccount(account)));
				}catch (IKFLException ex)
				{
					res.putException(card, ex);
				}
			}
			return res;
	}

	public GroupResult<Card, Client> getOwnerInfo(Card... cards)
	{
		GroupResult<Card, Client> res = new GroupResult<Card, Client>();
		GroupResult<Card, Account> accounts = getCardPrimaryAccount(cards);
		for (Card card: accounts.getKeys())
		{
			try{
				Account account = GroupResultHelper.getResult(accounts,card);
				if (account == null)
					throw new GateException("Не обнаружен карт-счет для карты: "+card.getNumber());
				Client owner = GroupResultHelper.getResult(getOwnerInfo(account), account);
				res.putResult(card, owner);
			} catch (IKFLException ex)
			{
				res.putException(card, ex);
			}
		}
		return res;
	}

	public GroupResult<Pair<String, Office>, Client> getOwnerInfoByCardNumber(Pair<String, Office>... cardInfo)
	{
		throw new UnsupportedOperationException();
	}

	public GroupResult<Pair<String, Office>, Account> getAccountByNumber(Pair<String, Office>... accountInfo)
	{
		GroupResult<Pair<String, Office>, Account> result = new GroupResult<Pair<String, Office>, Account>();
		for (Pair<String, Office> info: accountInfo)
		{
			try{
				result.putResult(info, GroupResultHelper.getOneResult(getAccount(info.getFirst())));
			}
			catch(IKFLException e)
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

	public AccountAbstract getAccHistoryFullExtract(Account account, Calendar fromDate, Calendar toDate, Boolean withCardUseInfo) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	private String getClientIdByAccount(Account account) throws GateException, GateLogicException
	{
		//TODO: после тестирования BUG024530 в банке убрать (всегда возвращать 0)
//		BackRefBankrollService backRefBankrollService = getFactory().service(BackRefBankrollService.class);
//		String clientId = getClientId(backRefBankrollService.findAccountBusinessOwner(account));
//		return clientId;
		return "0";
	}

	private String getClientId(String clientId) throws GateException, GateLogicException
	{
		//TODO: после тестирования BUG024530 в банке убрать (всегда возвращать 0)
//		BackRefClientService backRefClientService = getFactory().service(BackRefClientService.class);
//		if (clientId != null)
//		{
//			String creationType = backRefClientService.getClientCreationType(clientId);
//			if (!SBOL_CREATION_TYPE.equals(creationType)) return "0";
//		}
//		return clientId;
		return "0";
	}

	/**
	 * Заполнение СКС карты
	 * @param card - карта
	 */
	public void fillPrimaryAccount(SBRFCardImpl card) throws GateException, GateLogicException
	{
		try
		{
			RequestHelper helper = new RequestHelper(getFactory());
			String accountId = helper.getCardAccountNumber(card.getNumber());
			card.setPrimaryAccountExternalId(accountId);
			card.setPrimaryAccountNumber(accountId);			
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
}
