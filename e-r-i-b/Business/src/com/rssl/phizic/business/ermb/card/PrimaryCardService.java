package com.rssl.phizic.business.ermb.card;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.CSABackResponseSerializer;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.persons.clients.FakeClient;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ermb.ErmbConfig;
import com.rssl.phizic.gate.AdditionalProductData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientProductsService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.mobilebank.MobileBankService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.PhoneNumberFormat;
import org.apache.commons.collections.CollectionUtils;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Gulov
 * @ created 23.10.13
 * @ $Author$
 * @ $Revision$
 */

/**
 * Определяет приоритетную карту для оплаты по номеру телефона в многоблочной архитектуре ЕРИБ
 */
public class PrimaryCardService
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	public static final String PHONE_NUMBER_IS_NULL = "Номер телефона не может быть равен null";
	public static final String CLIENT_IS_NULL = "Клиент не может быть равен null";
	public static final String CLIENT_INFO_REQUEST_ERROR = "Ошибка при получении информации о клиенту по телефону ";

	private final BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
	private final ClientProductsService productService = GateSingleton.getFactory().service(ClientProductsService.class);

	/**
	 * По номеру телефона делаем запрос в ЦСА для получения информации о клиенте.
	 * Если ЦСА ничего не вернул, по номеру телефона и клиенту делаем запрос в МБК.
	 * Если ЦСА вернул информацию о клиенте, делаем запрос в GFL для получения списка карт клиента.
	 * Так как в ЦСА информация о клиенте содержит паспорт WAY, соответственно получаем карты по
	 * паспорту WAY.
	 * Для каждой карты из полученного списка очищаем кэш по карте и делам запрос CRDWI на получение
	 * полной информации по карте. Если кэш не очистить, тогда CRDWI вернет информацию из кэша с
	 * незаполненными полями, в частности поле UNIAccountType будет назаполненным, а оно
	 * необходимо для определения приоритетной карты.
	 * @param client контекстный клиент, нужен, если ЦСА ничего не вернул и необходимо сделать запрос в МБК.
	 * @param phone номер телефона (can't be null)
	 * @param officeOfChargeOffResource офис списания (may be null)
	 * @return карта. У данной карты в составном поле id используется фейковый логин.
	 */
	public Card getPrimaryCard(Client client, PhoneNumber phone, Office officeOfChargeOffResource) throws BusinessException
	{
		if (phone == null)
			throw new NullPointerException(PHONE_NUMBER_IS_NULL);
		if (client == null)
			throw new NullPointerException(CLIENT_IS_NULL);
		ErmbConfig config = ConfigFactory.getConfig(ErmbConfig.class);
		if (!config.isErmbUse())
			return fromMobileBank(client, phone);

		try
		{
			Client phoneOwnerInfo = findClientInfo(PhoneNumberFormat.MOBILE_INTERANTIONAL.format(phone));
			return getCard(phoneOwnerInfo, officeOfChargeOffResource);
		}
		catch (Exception e) // ЦСА не нашел информации по заданному телефону
		{
			log.error(CLIENT_INFO_REQUEST_ERROR + phone, e);
			return fromMobileBank(client, phone);
		}
	}

	private Card getCard(Client client, Office officeOfChargeOffResource) throws BusinessException
	{
		List<Card> cards = getCardsByClientInfo(client);

		if (CollectionUtils.isEmpty(cards))
			return null;
		// из списка карт выбираем приоритетную
		if (officeOfChargeOffResource == null)
			return PrimaryCardResolver.getPrimaryCard(cards);
		else
			return PrimaryCardRecipientResolver.getReceiverCardBySenderCard(cards, officeOfChargeOffResource);
	}

	protected List<Card> getCardsByClientInfo(Client client) throws BusinessException
	{
		// Делаем запрос в GFL
		List<Card> insufficientCards = getInsufficientCards(productService.getClientProducts(client, Card.class));
		List<Card> cards = new ArrayList<Card>(insufficientCards.size());
		for (Card card : insufficientCards)
			cards.add(getCard(card.getId()));
		return cards;
	}

	private Card fromMobileBank(Client client, PhoneNumber phone) throws BusinessException
	{
		MobileBankService mobileBankService = GateSingleton.getFactory().service(MobileBankService.class);
		try
		{
			return mobileBankService.getCardByPhone(client, PhoneNumberFormat.MOBILE_INTERANTIONAL.format(phone));
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessException(e);
		}
	}

	private Card getCard(String cardId) throws BusinessException
	{
		try
		{
			return GroupResultHelper.getOneResult(bankrollService.getCard(cardId));
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private List<Card> getInsufficientCards(GroupResult<Class, List<Pair<Object, AdditionalProductData>>> products) throws BusinessException
	{
		// GFL не вернул карты, или вернул ошибки
		if (products == null || products.getKeys().isEmpty() || products.getResults().isEmpty())
			return Collections.emptyList();
		List<Pair<Object, AdditionalProductData>> pairs = null;
		try
		{
			pairs = GroupResultHelper.getResult(products, Card.class);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
		List<Card> result = new LinkedList<Card>();
		for (Pair<Object, AdditionalProductData> pair : pairs)
			result.add((Card) pair.getFirst());
		return result;
	}

	private Client findClientInfo(String phone) throws BusinessException
	{
		try
		{
			if (LogThreadContext.getIPAddress() == null)
				LogThreadContext.setIPAddress(ApplicationConfig.getIt().getApplicationHost().getHostAddress());

			Document clientInfoDocument = CSABackRequestHelper.sendGetUserInfoByPhoneRq(phone, false);
			com.rssl.phizic.gate.csa.UserInfo clientInfo = CSABackResponseSerializer.getUserInfo(clientInfoDocument);
			return new FakeClient(clientInfo);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
