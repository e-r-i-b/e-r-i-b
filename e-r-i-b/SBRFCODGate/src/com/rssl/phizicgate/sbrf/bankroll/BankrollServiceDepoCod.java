package com.rssl.phizicgate.sbrf.bankroll;

import com.rssl.phizgate.common.services.types.ClientImpl;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.*;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.impl.DOMMessageImpl;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.sbrf.depoCOD.DepoCODTransportProvider;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.xml.transform.TransformerException;

/**
 * Класс для направления запросов в новый сервис DepoCod
 * @author miklyaev
 * @ created 25.04.14
 * @ $Author$
 * @ $Revision$
 */

public class BankrollServiceDepoCod extends AbstractService implements BankrollService
{
	private static final String ERROR_MESSAGE = "При выполнении запроса получена ошибка, код: [%s]. Текст ошибки: [%s]";

	public BankrollServiceDepoCod(GateFactory factory)
	{
		super(factory);
	}

	public List<Account> getClientAccounts(Client client) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	public GroupResult<String, Account> getAccount(String... accountIds)
	{
		throw new UnsupportedOperationException();
	}

	public GroupResult<Pair<String, Office>, Account> getAccountByNumber(Pair<String, Office>... accountInfo)
	{
		throw new UnsupportedOperationException();
	}

	public GroupResult<Object, AbstractBase> getAbstract(java.lang.Long number, Object... object)
	{
		if (object[0] instanceof Account)
		       return getAccountAbstract(object);
		throw new UnsupportedOperationException();
	}

	private GroupResult<Object, AbstractBase> getAccountAbstract(Object... objects)
	{
		GroupResult<Object, AbstractBase> res = new GroupResult<Object, AbstractBase>();
		for (Object object: objects)
		{
			try
			{
				AccountAbstract accountAbstract = getAccountExtendedAbstract((Account)object, DateHelper.getPreviousMonth(), Calendar.getInstance());
				res.putResult(object, accountAbstract);
			}
			catch (IKFLException e)
			{
				res.putException(object, e);
			}
		}
		return res;
	}

	public AbstractBase getAbstract(Object object, Calendar fromDate, Calendar toDate, Boolean withCardUseInfo) throws GateLogicException, GateException
	{
		if (object instanceof Account)
			return getAccountHistoryExtract((Account)object,fromDate,toDate,withCardUseInfo, false);
		else
			throw new GateException("Неверный тип объекта");
	}

	public List<Card> getClientCards(Client client) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	public GroupResult<String, Card> getCard(String... cardIds)
	{
		throw new UnsupportedOperationException();
	}

	public GroupResult<Card, List<Card>> getAdditionalCards(Card... mainCard)
	{
		throw new UnsupportedOperationException();
	}

	public GroupResult<Card, Account> getCardPrimaryAccount(Card... card)
	{
		throw new UnsupportedOperationException();
	}

	public GroupResult<Card, Client> getOwnerInfo(Card... card)
	{
		throw new UnsupportedOperationException();
	}

	public GroupResult<Pair<String, Office>, Client> getOwnerInfoByCardNumber(Pair<String, Office>... cardInfo)
	{
		throw new UnsupportedOperationException();
	}

	public GroupResult<Account, Client> getOwnerInfo(Account... account)
	{
		throw new UnsupportedOperationException();
	}

	public AccountAbstract getAccountExtendedAbstract(Account account, Calendar fromDate, Calendar toDate) throws GateException, GateLogicException
	{
		return getAccountHistoryExtract(account, fromDate, toDate, Boolean.FALSE, false);
	}

	public GroupResult<Pair<String, Office>, Card> getCardByNumber(Client client, Pair<String, Office>... cardInfo)
	{
		throw new UnsupportedOperationException();
	}

	private GateMessage createAccHistoryFullExtractRq(Account account, Calendar fromDate, Calendar toDate,  Boolean withCardUseInfo) throws GateException, GateLogicException
	{
		Document document = XmlHelper.getDocumentBuilder().newDocument();
		Element root = document.createElementNS("XMLDepoCOD", "message");
		document.appendChild(root);

		GateMessage request = new DOMMessageImpl(document);
		request.addParameter("getAccHistoryFullExtractRequest/Account", account.getNumber());
		request.addParameter("getAccHistoryFullExtractRequest/FromDate", DateHelper.toXMLDateFormat(fromDate.getTime()));
		request.addParameter("getAccHistoryFullExtractRequest/ToDate", DateHelper.toXMLDateFormat(toDate.getTime()));
		request.addParameter("getAccHistoryFullExtractRequest/WithCardUseInfo", withCardUseInfo);
		return request;
	}

	public AccountAbstract getAccHistoryFullExtract(Account account, Calendar fromDate, Calendar toDate, Boolean withCardUseInfo) throws GateException, GateLogicException
	{
		return getAccountHistoryExtract(account, fromDate, toDate, withCardUseInfo, true);
	}

	private AccountAbstract getAccountHistoryExtract(Account account, Calendar fromDate, Calendar toDate, Boolean withCardUseInfo, boolean isPension) throws GateException, GateLogicException
	{
		GateMessage request = createAccHistoryFullExtractRq(account, fromDate, toDate, withCardUseInfo);
		AccountAbstractImpl accountAbstract = new AccountAbstractImpl();

		try
		{
			byte[] requestEncoded = XmlHelper.convertDomToText(request.getDocument(), "Windows-1251").getBytes();
			byte[] response = DepoCODTransportProvider.doGetAccHistoryFullExtractRequest(requestEncoded);
			fillAccountAbstract(accountAbstract, response, isPension);
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
		catch (SAXException e)
		{
			throw new GateException(e);
		}
		catch (IOException e)
		{
			throw new GateException(e);
		}
		catch (ParseException e)
		{
			throw new GateException(e);
		}

		return accountAbstract;
	}

	private AccountAbstractImpl fillAccountAbstract(AccountAbstractImpl accountAbstract, byte[] response, boolean isPension) throws SAXException, IOException, TransformerException, ParseException, GateException, GateLogicException
	{
		Document responseXML = XmlHelper.getDocumentBuilder().parse(new InputSource(new StringReader(new String(response))));

		String errorCode = XmlHelper.getElementValueByPath(responseXML.getDocumentElement(), "getAccHistoryFullExtractResponse/Error/Code");
		String errorMessage = XmlHelper.getElementValueByPath(responseXML.getDocumentElement(), "getAccHistoryFullExtractResponse/Error/Message");

		if(!("0".equals(errorCode)))
			throw new GateLogicException(String.format(ERROR_MESSAGE, errorCode, errorMessage));

		String fromDate = XmlHelper.getElementValueByPath(responseXML.getDocumentElement(), "getAccHistoryFullExtractResponse/FromDate");
		accountAbstract.setFromDate(DateHelper.toCalendar(DateHelper.fromXMlDateToDate(fromDate)));
		String toDate = XmlHelper.getElementValueByPath(responseXML.getDocumentElement(), "getAccHistoryFullExtractResponse/ToDate");
		accountAbstract.setToDate(DateHelper.toCalendar(DateHelper.fromXMlDateToDate(toDate)));

		CurrencyService currencyService = getFactory().service(CurrencyService.class);
		String curCode = XmlHelper.getElementValueByPath(responseXML.getDocumentElement(), "getAccHistoryFullExtractResponse/Account/CurCode");
		Currency currency = currencyService.findByNumericCode(curCode);
		if (currency == null)
			throw new GateLogicException("Неправильный код валюты");

		String actualDate = XmlHelper.getElementValueByPath(responseXML.getDocumentElement(), "getAccHistoryFullExtractResponse/Date");
		accountAbstract.setClosedDate(DateHelper.toCalendar(DateHelper.fromXMlDateToDate(actualDate)));
		accountAbstract.setAdditionalInformation(XmlHelper.getElementValueByPath(responseXML.getDocumentElement(), "getAccHistoryFullExtractResponse/HistoryLine/Pension/Name"));
		List<TransactionBase> transactions = new ArrayList<TransactionBase>();
		NodeList historyLines = XmlHelper.selectNodeList(responseXML.getDocumentElement(), "getAccHistoryFullExtractResponse/HistoryLine");
		for (int i = 0; i < historyLines.getLength(); i++)
		{
			Element node = (Element) historyLines.item(i);
			if (isPension && XmlHelper.selectSingleNode(node, "Pension") == null)
				continue;
			AccountTransactionImpl transaction = new AccountTransactionImpl();

			String sum = XmlHelper.getElementValueByPath(node, "OperSumm");
			boolean isDebit = Boolean.parseBoolean(XmlHelper.getElementValueByPath(node, "IsDebit"));

			Money transactionAmount = null;
			if (StringHelper.isNotEmpty(sum))
			{
				transactionAmount = new Money(new BigDecimal(sum), currency);
				if (isDebit)
					transaction.setCreditSum(transactionAmount);
				else
					transaction.setDebitSum(transactionAmount);
			}

			String date = XmlHelper.getElementValueByPath(node, "WayDate");
			String balance = XmlHelper.getElementValueByPath(node, "Remainder");
			transaction.setDate(DateHelper.toCalendar(DateHelper.fromXMlDateToDate(date)));

			transaction.setDescription(XmlHelper.getElementValueByPath(node, "OperTypeName"));
			transaction.setOperationCode(XmlHelper.getElementValueByPath(node, "OperType"));
			//при получении справки о видах и размерах пенсии заполняем OperCode  из "Pension/Name"
			if (isPension)
				transaction.setOperationCode(XmlHelper.getElementValueByPath(node, "Pension/Name"));
			Money accountBalance = null;
			if (StringHelper.isNotEmpty(balance))
			{
				accountBalance = new Money(new BigDecimal(balance), currency);
				transaction.setBalance(accountBalance);
			}

			if (accountBalance != null && transactionAmount != null)
			{
				if (i == 0)
				{
					Money openingBalance =  isDebit ? accountBalance.sub(transactionAmount) : accountBalance.add(transactionAmount);
					accountAbstract.setOpeningBalance(openingBalance);
				}
				else if (i == historyLines.getLength() - 1)
				{
					accountAbstract.setClosingBalance(accountBalance);
				}
			}

			String cardUse = XmlHelper.getElementValueByPath(node, "CardUseData");
			if(StringHelper.isNotEmpty(cardUse))
			{
				transaction.setCardUseData(getCardUseData(node));
			}

			transactions.add(transaction);
		}
		String accountNumber = XmlHelper.getElementValueByPath(responseXML.getDocumentElement(), "getAccHistoryFullExtractResponse/Account/Account");
		//если операции не пришли, то ставим баланс с помощью запроса остатка на счёте
		if (historyLines.getLength() == 0 && StringHelper.isNotEmpty(accountNumber))
		{
			Money remainder = getAccountRemainder(accountNumber, accountAbstract.getFromDate(), currency);
			accountAbstract.setOpeningBalance(remainder);
			accountAbstract.setClosingBalance(remainder);
		}

		accountAbstract.setTransactions(transactions);
		return accountAbstract;
	}

	private CardUseDataImpl getCardUseData(Element node) throws TransformerException, ParseException
	{
		CardUseDataImpl cardUseData = new CardUseDataImpl();
		cardUseData.setAuthCode(XmlHelper.getElementValueByPath(node, "CardUseData/AuthCode"));
		cardUseData.setCardNumber(XmlHelper.getElementValueByPath(node, "CardUseData/CardNumber"));
		cardUseData.setClientName(XmlHelper.getElementValueByPath(node, "CardUseData/ClientName"));
		cardUseData.setConfirmClerkName(XmlHelper.getElementValueByPath(node, "CardUseData/ConfirmClerkName"));
		cardUseData.setPaymasterName(XmlHelper.getElementValueByPath(node, "CardUseData/PaymasterName"));

		String clerkNumber = XmlHelper.getElementValueByPath(node, "CardUseData/ConfirmClerkNumber");
		if(StringHelper.isNotEmpty(clerkNumber))
			cardUseData.setConfirmClerkNumber(Long.parseLong(clerkNumber));

		String paymasterNumber = XmlHelper.getElementValueByPath(node, "CardUseData/PaymasterNumber");
		if(StringHelper.isNotEmpty(paymasterNumber))
			cardUseData.setPaymasterNumber(Long.parseLong(paymasterNumber));

		String experationDate = XmlHelper.getElementValueByPath(node, "CardUseData/ExperationDate");
		cardUseData.setExperationDate(
				DateHelper.toCalendar(DateHelper.fromXMlDateToDate(experationDate))
		);

		String authDate = XmlHelper.getElementValueByPath(node, "CardUseData/AuthDate");
		if(StringHelper.isNotEmpty(authDate))
			cardUseData.setAuthDate(
					DateHelper.toCalendar(DateHelper.fromXMlDateToDate(authDate))
			);

		String authTime = XmlHelper.getElementValueByPath(node, "CardUseData/AuthTime");
		if(StringHelper.isNotEmpty(authTime))
			cardUseData.setAuthTime(
					DateHelper.toCalendar(DateHelper.fromXMlTimeToDate(authTime))
			);

		String confirmDate = XmlHelper.getElementValueByPath(node, "CardUseData/ConfirmDate");
		if(StringHelper.isNotEmpty(confirmDate))
			cardUseData.setConfirmDate(
					DateHelper.toCalendar(DateHelper.fromXMlDateToDate(confirmDate))
			);

		String confirmTime = XmlHelper.getElementValueByPath(node, "CardUseData/ConfirmTime");
		if(StringHelper.isNotEmpty(confirmTime))
			cardUseData.setConfirmTime(
					DateHelper.toCalendar(DateHelper.fromXMlTimeToDate(confirmTime))
			);

		String paymentDate = XmlHelper.getElementValueByPath(node, "CardUseData/PaymentDate");
		if(StringHelper.isNotEmpty(paymentDate))
			cardUseData.setPaymentDate(
					DateHelper.toCalendar(DateHelper.fromXMlDateToDate(paymentDate))
			);

		String paymentTime = XmlHelper.getElementValueByPath(node, "CardUseData/PaymentTime");
		if(StringHelper.isNotEmpty(paymentTime))
			cardUseData.setPaymentTime(
					DateHelper.toCalendar(DateHelper.fromXMlTimeToDate(paymentTime))
			);

		String operationDate = XmlHelper.getElementValueByPath(node, "PayDate");
		if(StringHelper.isNotEmpty(operationDate))
			cardUseData.setOperationDate(
					DateHelper.toCalendar(DateHelper.fromXMlDateToDate(operationDate))
			);

		String operationTime = XmlHelper.getElementValueByPath(node, "CardUseData/OperationTime");
		if(StringHelper.isNotEmpty(operationTime))
			cardUseData.setOperationTime(
					DateHelper.toCalendar(DateHelper.fromXMlTimeToDate(operationTime))
			);

		return cardUseData;
	}

	/**
	 * Создать запрос остатка по счету
	 * @param account - Номер счета
	 * @param forDate - Дата, на которую запрашивают остаток
	 * @return запрос
	 * @throws GateException
	 * @throws GateLogicException
	 */
	private GateMessage createAccountRemainderRq(String account, Calendar forDate) throws GateException, GateLogicException
	{
		Document document = XmlHelper.getDocumentBuilder().newDocument();
		Element root = document.createElementNS("XMLDepoCOD", "message");
		document.appendChild(root);

		GateMessage request = new DOMMessageImpl(document);
		request.addParameter("accountRemainderRequest/Account", account);
		request.addParameter("accountRemainderRequest/ForDate", DateHelper.toXMLDateFormat(forDate.getTime()));
		return request;
	}

	/**
	 * Обработка запроса остатка по счету
	 * @param currency - Валюта счета
	 * @param response - Ответ
	 * @return остаток на счете
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerException
	 * @throws ParseException
	 * @throws GateException
	 * @throws GateLogicException
	 */
	private Money executeAccountRemainderRs(Currency currency, byte[] response) throws SAXException, IOException, TransformerException, ParseException, GateException, GateLogicException
	{
		Document responseXML = XmlHelper.getDocumentBuilder().parse(new InputSource(new StringReader(new String(response))));

		String errorCode = XmlHelper.getElementValueByPath(responseXML.getDocumentElement(), "accountRemainderResponse/Error/Code");
		String errorMessage = XmlHelper.getElementValueByPath(responseXML.getDocumentElement(), "accountRemainderResponse/Error/Message");

		if(!("0".equals(errorCode)))
			throw new GateLogicException(String.format(ERROR_MESSAGE, errorCode, errorMessage));

		//Остаток счета
		Money remainder = null;
		if (StringHelper.isNotEmpty(XmlHelper.getElementValueByPath(responseXML.getDocumentElement(), "accountRemainderResponse/Remainder")))
			remainder = new Money(new BigDecimal(XmlHelper.getElementValueByPath(responseXML.getDocumentElement(), "accountRemainderResponse/Remainder")), currency);

		return remainder;
	}

	/**
	 * Получить текущий остаток на счете
	 * @param account - Номер счета
	 * @param forDate - Дата, на которую запрашивают остаток-
	 * @param currency - Валюта счета
	 * @return остаток на счете
	 * @throws GateException
	 * @throws GateLogicException
	 */
	private Money getAccountRemainder(String account, Calendar forDate, Currency currency) throws GateException, GateLogicException
	{
		GateMessage request = createAccountRemainderRq(account, forDate);
		Money remainder = new Money();

		try
		{
			byte[] requestEncoded = XmlHelper.convertDomToText(request.getDocument(), "Windows-1251").getBytes();
			byte[] response = DepoCODTransportProvider.doAccountRemainderRequest(requestEncoded);
			remainder = executeAccountRemainderRs(currency, response);
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
		catch (SAXException e)
		{
			throw new GateException(e);
		}
		catch (IOException e)
		{
			throw new GateException(e);
		}
		catch (ParseException e)
		{
			throw new GateException(e);
		}

		return remainder;
	}
}
