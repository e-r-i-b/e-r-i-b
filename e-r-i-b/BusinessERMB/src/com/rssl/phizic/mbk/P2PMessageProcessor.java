package com.rssl.phizic.mbk;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.CSABackResponseSerializer;
import com.rssl.auth.csa.wsclient.exceptions.ProfileNotFoundException;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.business.ermb.card.PrimaryCardResolver;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.persons.clients.FakeClient;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.AdditionalProductData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientProductsService;
import com.rssl.phizic.gate.mobilebank.MobileBankService;
import com.rssl.phizic.gate.mobilebank.MobilePaymentCardResult;
import com.rssl.phizic.gate.mobilebank.P2PRequest;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.logging.writers.MessageLogWriter;
import com.rssl.phizic.messaging.ErmbXmlMessage;
import com.rssl.phizic.messaging.MessageProcessorBase;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.PhoneNumberFormat;
import org.apache.commons.collections.CollectionUtils;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.rssl.phizic.logging.messaging.System.mbk;

/**
 * @author Erkin
 * @ created 24.10.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Обработчик P2P-запросов от МБК.
 * По номеру телефона находит карту и ФИО и возвращает их в МБК
 */
public class P2PMessageProcessor extends MessageProcessorBase<ErmbXmlMessage>
{
	private static final int SUCCESS_CODE = 0;
	private static final int PHONE_IS_NOT_ERMB_CLIENT_CODE = -1;
	private static final int NO_ACTIVE_CARD_CODE = -3;

	/**
	 * ctor
	 * @param module - модуль
	 */
	public P2PMessageProcessor(Module module)
	{
		super(module);
	}

	@Override
	protected void doProcessMessage(ErmbXmlMessage xmlRequest)
	{
		P2PRequest request = xmlRequest.getP2PRequest();
		try
		{

			// 1. Ищем клиента по телефону
			Client client = findClient(request.phone);
			if (client == null)
			{
				sendBadResult(request, PHONE_IS_NOT_ERMB_CLIENT_CODE);
				return;
			}

			// 2. Получаем карты клиента
			Collection<Card> cards = getClientCards(client);
			if (CollectionUtils.isEmpty(cards))
			{
				sendBadResult(request, NO_ACTIVE_CARD_CODE);
				return;
			}

			// 3. Из полученных карт выбираем приоритетную
			Card primaryCard = PrimaryCardResolver.getPrimaryCard(cards);
			if (primaryCard == null)
			{
				sendBadResult(request, NO_ACTIVE_CARD_CODE);
				return;
			}

			sendGoodResult(request, client, primaryCard);
		}
		catch (Exception e)
		{
			log.error("Сбой на обработке p2p-запроса МБК " + xmlRequest.getMessage(), e);
		}
		finally
		{
			writeMessageLog(request, xmlRequest.getMessage());
		}
	}

	private Client findClient(PhoneNumber phone) throws Exception
	{
		try
		{
			String phoneAsString = PhoneNumberFormat.MOBILE_INTERANTIONAL.format(phone);
			Document document = CSABackRequestHelper.sendGetUserInfoByPhoneRq(phoneAsString, false);
			UserInfo clientCSAInfo = CSABackResponseSerializer.getUserInfo(document);
			if (clientCSAInfo == null)
				return null;
			return new FakeClient(clientCSAInfo);
		}
		catch (ProfileNotFoundException ignored)
		{
			return null;
		}
	}

	private Collection<Card> getClientCards(Client client) throws Exception
	{
		ClientProductsService clientProductsService = GateSingleton.getFactory().service(ClientProductsService.class);

		GroupResult<Class, List<Pair<Object, AdditionalProductData>>> getCardsResult = clientProductsService.getClientProducts(client, Card.class);

		List<Pair<Object, AdditionalProductData>> cardsPairs = GroupResultHelper.getOneResult(getCardsResult);
		if (CollectionUtils.isEmpty(cardsPairs))
			return null;

		Collection<Card> cards = new ArrayList<Card>(cardsPairs.size());
		for (Pair<Object, AdditionalProductData> cardPair : cardsPairs)
			cards.add((Card) cardPair.getFirst());
		return cards;
	}

	private void sendBadResult(P2PRequest request, int badResultCode) throws Exception
	{
		MobileBankService mbkService = GateSingleton.getFactory().service(MobileBankService.class);

		mbkService.sendMobilePaymentCardResult(new MobilePaymentCardResult(request.id, "", "", badResultCode, ""));
	}

	private void sendGoodResult(P2PRequest request, Client client, Card card) throws Exception
	{
		MobileBankService mbkService = GateSingleton.getFactory().service(MobileBankService.class);

		String personName = PersonHelper.getFormattedPersonName(client.getFirstName(), client.getSurName(), client.getPatrName());

		mbkService.sendMobilePaymentCardResult(new MobilePaymentCardResult(request.id, card.getNumber(), personName, SUCCESS_CODE, ""));
	}

	@Override
	public boolean writeToLog()
	{
		// собственное логирование
		return false;
	}

	private void writeMessageLog(P2PRequest request, String requestXML)
	{
		try
		{
			MessageLogWriter messageLogWriter = MessageLogService.getMessageLogWriter();
			MessagingLogEntry logEntry = MessageLogService.createLogEntry();

			logEntry.setMessageRequestId((request != null) ? String.valueOf(request.id) : "unexpected-id");
			logEntry.setMessageRequest(requestXML);
			logEntry.setMessageType(request.getClass().getSimpleName());
			logEntry.setApplication(Application.MBK_P2P_Processor);
			logEntry.setSystem(mbk);

			messageLogWriter.write(logEntry);
		}
		catch (Exception e)
		{
			log.error("Ошибка записи сообщения в журнал", e);
		}
	}
}
