package com.rssl.phizicgate.sbcms.bankroll;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
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
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.sbcms.messaging.CMSMessagingService;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.xml.transform.TransformerException;

/**
 * @author Egorova
 * @ created 04.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class BankrollServiceImpl extends AbstractService implements BankrollService
{
	public BankrollServiceImpl(GateFactory factory)
    {
        super(factory);
    }

	/**
	 * Возвращает только 10 последних операций.
	 */
	public GroupResult<Object, AbstractBase> getAbstract(java.lang.Long number, Object... object)
	{
		GroupResult<Object, AbstractBase> res = new GroupResult<Object, AbstractBase>();
		for (Object obj: object)
		{
			try{
			if(obj instanceof Card)
					res.putResult(obj, getCardAbstract((Card)obj));
			else
				throw new GateException("Неверный тип объекта");
			}catch (IKFLException ex)
			{
				res.putException(obj, ex);
			}
		}
		return res;
	}

	private AbstractBase getCardAbstract(Card card) throws GateLogicException, GateException
	{
		Document document = cardRequest(card,"HISTORY");

		CardAbstractImpl cardAbstract = new CardAbstractImpl();
		fillAbstract(document, cardAbstract);

		return cardAbstract;

	}

	protected void fillAbstract(Document document, CardAbstractImpl cardAbstract) throws GateLogicException, GateException
	{
		CurrencyService currencyService = getFactory().service(CurrencyService.class);

		Element response = document.getDocumentElement();

		String date = XmlHelper.getSimpleElementValue(response, "DATE_TIME");
		String amount = XmlHelper.getSimpleElementValue(response, "AMOUNT");

		String currencyCode = XmlHelper.getSimpleElementValue(response, "CUR_ISO");
		String currencyISOCode = "RUR".equals(currencyCode) ? "RUB" : currencyCode;
		Currency currency = currencyService.findByAlphabeticCode(currencyISOCode);

		NodeList nodeList = null;
		try
		{
			nodeList = XmlHelper.selectNodeList(response, "OPERATION");
		}
		catch(TransformerException ex)
		{
			throw new GateException(ex);
		}
		//Дата выписки начальная и конечная одинаковые
		Calendar abstrDate = XMLDatatypeHelper.parseDateTime(date);

		cardAbstract.setFromDate( abstrDate );
		cardAbstract.setToDate( abstrDate );

		cardAbstract.setClosingBalance( new Money(new BigDecimal(amount),currency) );

		List<TransactionBase> operations = new ArrayList<TransactionBase>(nodeList.getLength());
		for (int i = 0; i < nodeList.getLength(); i++)
		{
			Element element = (Element) nodeList.item(i);
			String depit_credit = XmlHelper.getSimpleElementValue(element, "DB_CR");
			String sum = XmlHelper.getSimpleElementValue(element, "AMOUNT");
			String tDate = XmlHelper.getSimpleElementValue(element, "DATE");
			String description = XmlHelper.getSimpleElementValue(element, "NAME");

			String tCurrencyCode = XmlHelper.getSimpleElementValue(response, "CUR_ISO");
			String tcurrencyISOCode = "RUR".equals(currencyCode) ? "RUB" : tCurrencyCode;
			Currency tCurrency = currencyService.findByAlphabeticCode(tcurrencyISOCode);

			CardOperationImpl operation = new CardOperationImpl();

			if (!StringHelper.isEmpty(tDate))
				operation.setDate(XMLDatatypeHelper.parseDate(tDate));
			else operation.setDate(null);
			operation.setDescription(description);
			if (depit_credit.equals("D"))
				operation.setDebitSum(new Money(new BigDecimal(sum),tCurrency));
			else
				operation.setCreditSum(new Money(new BigDecimal(sum),tCurrency));

			operations.add(operation);
		}
		cardAbstract.setTransactions(operations);
	}

   public AbstractBase getAbstract(Object object, Calendar fromDate, Calendar toDate, Boolean withCardUseInfo) throws GateLogicException, GateException
   {
	   try
	   {
		   return GroupResultHelper.getOneResult(getAbstract(null, object));
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


	private Document cardRequest(Card card, String type) throws GateLogicException, GateException
	{
		CMSMessagingService messagingService = new CMSMessagingService();
		messagingService = messagingService.getInstance();
		GateMessage request = messagingService.createRequest(type, card);
		return messagingService.sendOnlineMessage(request.getDocument(), null);
	}

   public List<Card> getClientCards(Client client) throws GateException
   {
	    throw new UnsupportedOperationException();
   }

   public GroupResult<String, Card> getCard(String... cardId)
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

	//счетов нет
	public List<Account> getClientAccounts(Client client) throws GateException
	{
		return new ArrayList<Account>();
	}
	//счетов нет
   public GroupResult<String, Account> getAccount(String... accountId)
   {
		throw new UnsupportedOperationException();
   }

	//счетов нет
   public GroupResult<Account, Client> getOwnerInfo(Account... account)
   {
	   throw new UnsupportedOperationException();
   }
	//счетов нет
   public AccountAbstract getAccountExtendedAbstract(Account account, Calendar fromDate, Calendar toDate) throws GateException
   {
	   throw new UnsupportedOperationException();
   }

	public GroupResult<Pair<String, Office>, Account> getAccountByNumber(Pair<String, Office>... accountInfo)
	{
		throw new UnsupportedOperationException();
	}

	public GroupResult<Pair<String,Office>,Card> getCardByNumber(Client client, Pair<String, Office>... cardInfo)
	{
		throw new UnsupportedOperationException();
	}

	public AccountAbstract getAccHistoryFullExtract(Account account, Calendar fromDate, Calendar toDate, Boolean withCardUseInfo) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}
}
