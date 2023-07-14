package com.rssl.phizicgate.rsretailV6r4.bankroll;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.rsretailV6r4.messaging.RetailXMLHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;
import javax.xml.transform.TransformerException;

/**
 * @author krenev
 * @ created 15.07.2010
 * @ $Author$
 * @ $Revision$
 */
public class RequestHelper extends AbstractService
{
	public RequestHelper(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * Получить идентифкаторы карточных счетов по номерам карт
	 * @param cardNumbers номера карт
	 * @return мап номер карты -> идентификатор карточного счета
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public Map<String, String> getCardsAccountIds(String... cardNumbers) throws GateException, GateLogicException
	{
		WebBankServiceFacade service = getFactory().service(WebBankServiceFacade.class);
		GateMessage message = service.createRequest("getPrimaryCardAccount_q");
		for (String cardNumber : cardNumbers)
		{
			message.addParameter("cardReferenc", cardNumber);
		}

		Document responce = service.sendOnlineMessage(message, null);
		try
		{
			NodeList nodes = XmlHelper.selectNodeList(responce.getDocumentElement(), "result");
			int count = nodes.getLength();
			Map<String, String> result = new HashMap<String, String>();
			for (int i = 0; i < count; i++)
			{
				Element element = (Element) nodes.item(i);
				String cardReferenc = RetailXMLHelper.getElementValue(element, "cardReferenc");
				String accountReferenc = RetailXMLHelper.getElementValue(element, "accountReferenc");
				result.put(cardReferenc, accountReferenc);
			}
			return result;
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Получить идентифкаторы карточных счетов по картам
	 * @param cards карты
	 * @return мап номер карты -> идентификатор карточного счета
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public Map<Card, String> getCardsAccountIds(Card[] cards) throws GateException, GateLogicException
	{
		String[] numbers = new String[cards.length];
		int i = 0;

		for (Card card : cards)
		{
			numbers[i++] = card.getNumber();
		}
		Map<String, String> cardsAccountNumbers = getCardsAccountIds(numbers);
		Map<Card, String> result = new HashMap<Card, String>();
		for (Card card : cards)
		{
			String cardAccountNumber = cardsAccountNumbers.get(card.getNumber());
			result.put(card, cardAccountNumber);
		}
		return result;
	}
}

